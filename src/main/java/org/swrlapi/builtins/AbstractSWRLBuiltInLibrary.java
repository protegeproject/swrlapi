package org.swrlapi.builtins;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.builtins.arguments.SQWRLCollectionVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLPropertyBuiltInArgument;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.exceptions.InvalidSWRLBuiltInArgumentException;
import org.swrlapi.exceptions.InvalidSWRLBuiltInArgumentNumberException;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.exceptions.SWRLBuiltInLibraryException;
import org.swrlapi.exceptions.SWRLBuiltInMethodRuntimeException;
import org.swrlapi.factory.LiteralFactory;
import org.swrlapi.factory.OWLLiteralFactory;
import org.swrlapi.factory.SQWRLResultValueFactory;
import org.swrlapi.factory.SWRLAPIOWLDataFactory;
import org.swrlapi.factory.SWRLBuiltInArgumentFactory;
import org.swrlapi.literal.Literal;
import org.swrlapi.literal.XSDDate;
import org.swrlapi.literal.XSDDateTime;
import org.swrlapi.literal.XSDDuration;
import org.swrlapi.literal.XSDTime;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class that must be subclassed by a class implementing a library of SWRL built-in methods.
 * <p/>
 * Provides invocation context for invoked built-ins (such the name of invoking rule, whether the invocation is in the
 * consequent or the antecedent) and access to the invoking {@link org.swrlapi.builtins.SWRLBuiltInBridge}. Also
 * provides implementations for a large number of SWRL built-in argument processing methods.
 *
 * @see org.swrlapi.builtins.SWRLBuiltInLibrary
 * @see org.swrlapi.builtins.SWRLBuiltInContext
 * @see SWRLBuiltInInputArgumentHandler
 * @see SWRLBuiltInResultArgumentHandler
 * @see org.swrlapi.builtins.SWRLBuiltInArgumentCreator
 */
public abstract class AbstractSWRLBuiltInLibrary
    implements SWRLBuiltInLibrary, SWRLBuiltInInputArgumentHandler, SWRLBuiltInResultArgumentHandler,
    SWRLBuiltInArgumentCreator
{
  @NonNull private final String libraryName;

  // Bridge, rule name, built-in index, and head or body location within rule of built-in currently invoking its
  // associated Java implementation. The invokingRuleName, invokingBuiltInIndex, and isInConsequent variables are valid
  // only when a built-in currently being invoked so should only be retrieved through their associated accessor methods
  // from within a built-in; the invokingBridge method is valid only in built-ins and in the reset method.
  @Nullable private SWRLBuiltInBridge invokingBridge;
  @NonNull private String invokingRuleName = "";
  @NonNull private Long invocationPatternID;
  @NonNull private Map<@NonNull String, @NonNull Long> invocationPatternMap;

  private int invokingBuiltInIndex = -1;
  private boolean isInConsequent = false;

  protected AbstractSWRLBuiltInLibrary(@NonNull String libraryName)
  {
    this.invokingBridge = null;
    this.libraryName = libraryName;
    this.invocationPatternID = 0L;
    this.invocationPatternMap = new HashMap<>();
  }

  @NonNull @Override public String getLibraryName()
  {
    return this.libraryName;
  }

  @NonNull @Override public SWRLBuiltInBridge getBuiltInBridge() throws SWRLBuiltInLibraryException
  {
    if (this.invokingBridge == null)
      throw new SWRLBuiltInLibraryException(
          "invalid call to getInvokingBridge - should only be called from within a built-in");

    return this.invokingBridge;
  }

  @NonNull @Override public String getInvokingRuleName() throws SWRLBuiltInLibraryException
  {
    if (this.invokingRuleName.length() == 0)
      throw new SWRLBuiltInLibraryException(
          "invalid call to getInvokingRuleName - should only be called from within a built-in");

    return this.invokingRuleName;
  }

  @Override public int getInvokingBuiltInIndex() throws SWRLBuiltInLibraryException
  {
    if (this.invokingBuiltInIndex == -1)
      throw new SWRLBuiltInLibraryException(
          "invalid call to getInvokingBuiltInIndex - should only be called from within a built-in");

    return this.invokingBuiltInIndex;
  }

  @Override public boolean getIsInConsequent() throws SWRLBuiltInLibraryException
  {
    if (this.invokingBridge == null)
      throw new SWRLBuiltInLibraryException(
          "invalid call to getIsInConsequent - should only be called from within a built-in");

    return this.isInConsequent;
  }

  @Override public void checkThatInConsequent() throws SWRLBuiltInException
  {
    if (this.invokingBridge == null)
      throw new SWRLBuiltInLibraryException(
          "invalid call to checkThatInConsequent - should only be called from within a built-in");

    if (!this.isInConsequent)
      throw new SWRLBuiltInException("built-in can only be used in consequent");
  }

  @Override public void checkThatInAntecedent() throws SWRLBuiltInException
  {
    if (this.invokingBridge == null)
      throw new SWRLBuiltInLibraryException(
          "invalid call to checkThatInAntecedent - should only be called from within a built-in");

    if (this.isInConsequent)
      throw new SWRLBuiltInException("built-in can only be used in antecedent");
  }

  @Override public abstract void reset() throws SWRLBuiltInLibraryException;

  @Override public void invokeResetMethod(@NonNull SWRLBuiltInBridge bridge) throws SWRLBuiltInLibraryException
  {
    synchronized (this) {
      this.invokingBridge = bridge;

      reset();

      this.invocationPatternID = 0L;
      this.invocationPatternMap = new HashMap<>();

      this.invokingBridge = null;
    }
  }

  @Override public boolean invokeBuiltInMethod(@NonNull Method method, @NonNull SWRLBuiltInBridge bridge,
      @NonNull String ruleName, @NonNull String prefix, @NonNull String builtInMethodName, int builtInIndex,
      boolean inConsequent, @NonNull List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    String builtInName = prefix + ":" + builtInMethodName;
    Boolean result;

    synchronized (this) { // Only one built-in per library may be invoked simultaneously
      this.invokingBridge = bridge;
      this.invokingRuleName = ruleName;
      this.invokingBuiltInIndex = builtInIndex;
      this.isInConsequent = inConsequent;

      try { // Invoke the built-in method.
        result = (Boolean)method.invoke(this, arguments);
      } catch (InvocationTargetException e) { // The built-in implementation threw an exception.
        Throwable targetException = e.getTargetException();
        if (targetException instanceof SWRLBuiltInException) { // An explicit BuiltInException was thrown by the
          // built-in.
          throw new SWRLBuiltInException(
              "exception thrown by built-in " + builtInName + " in rule " + ruleName + ": " + targetException
                  .getMessage(), targetException);
        } else if (targetException instanceof RuntimeException) { // A runtime exception was thrown by the built-in.
          throw new SWRLBuiltInMethodRuntimeException(ruleName, builtInName, targetException.getMessage(),
              targetException);
        } else
          throw new SWRLBuiltInException(
              "unknown exception thrown by built-in " + builtInName + " in rule " + ruleName + ": " + e.toString(), e);
      } catch (Throwable e) { // Should be one of IllegalAccessException or IllegalArgumentException
        throw new SWRLBuiltInLibraryException(
            "internal built-in library exception when invoking built-in " + builtInName + " in rule " + ruleName + ": "
                + e.getMessage(), e);
      }
      this.invokingBridge = null;
      this.invokingRuleName = "";
      this.invokingBuiltInIndex = -1;
      this.isInConsequent = false;
    }

    return result;
  }

  @NonNull @Override public SQWRLResultValueFactory getSQWRLResultValueFactory() throws SWRLBuiltInLibraryException
  {
    return getBuiltInBridge().getSWRLAPIOWLDataFactory().getSQWRLResultValueFactory();
  }

  /**
   * Create a string that represents a key of a unique invocation pattern for a built-in for a
   * bridge/rule/built-in/arguments combination.
   */
  @NonNull @Override public String createInvocationPattern(@NonNull SWRLBuiltInBridge bridge, @NonNull String ruleName,
      int builtInIndex, boolean inConsequent, @NonNull List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    String pattern = "" + bridge.hashCode() + "." + ruleName + "." + builtInIndex + "." + inConsequent;
    String result;

    for (int i = 0; i < arguments.size(); i++)
      pattern += "." + getArgumentAsAPropertyValue(i, arguments);

    if (this.invocationPatternMap.containsKey(pattern))
      result = this.invocationPatternMap.get(pattern).toString();
    else {
      this.invocationPatternMap.put(pattern, this.invocationPatternID);
      result = this.invocationPatternID.toString();
      this.invocationPatternID++;
    }

    return result;
  }

  @NonNull @Override public IRI createIRI(@NonNull String fullName) throws SWRLBuiltInException
  {
    try {
      return IRI.create(fullName);
    } catch (RuntimeException e) {
      throw new SWRLBuiltInException(
          "error creating IRI from full name " + fullName + ": " + (e.getMessage() != null ? e.getMessage() : ""), e);
    }
  }

  // Argument handling methods

  protected void checkNumberOfArgumentsAtLeastOne(@NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    if (arguments.size() < 1)
      throw new InvalidSWRLBuiltInArgumentNumberException(1, 0, "at least");
  }

  @Override public void checkNumberOfArgumentsEqualTo(int expecting, int actual)
      throws InvalidSWRLBuiltInArgumentNumberException
  {
    if (expecting != actual)
      throw new InvalidSWRLBuiltInArgumentNumberException(expecting, actual);
  }

  @Override public void checkNumberOfArgumentsAtLeast(int expectingAtLeast, int actual)
      throws InvalidSWRLBuiltInArgumentNumberException
  {
    if (actual < expectingAtLeast)
      throw new InvalidSWRLBuiltInArgumentNumberException(expectingAtLeast, actual, "at least");
  }

  @Override public void checkNumberOfArgumentsAtMost(int expectingAtMost, int actual)
      throws InvalidSWRLBuiltInArgumentNumberException
  {
    if (actual > expectingAtMost)
      throw new InvalidSWRLBuiltInArgumentNumberException(expectingAtMost, actual, "at most");
  }

  @Override public void checkNumberOfArgumentsInRange(int expectingAtLeast, int expectingAtMost, int actual)
      throws InvalidSWRLBuiltInArgumentNumberException
  {
    if (actual > expectingAtMost || actual < expectingAtLeast)
      throw new InvalidSWRLBuiltInArgumentNumberException(expectingAtMost, actual, expectingAtLeast + " to");
  }

  @Override public void checkThatAllArgumentsAreLiterals(@NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
      checkThatArgumentIsALiteral(argumentNumber, arguments);
  }

  @Override public void checkThatAllArgumentsAreNumeric(@NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
      checkThatArgumentIsNumeric(argumentNumber, arguments);
  }

  @Override public void checkThatAllArgumentsAreIntegers(@NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
      checkThatArgumentIsAnInt(argumentNumber, arguments);
  }

  @Override public boolean areAllArgumentsBytes(@NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
      if (!isArgumentAByte(argumentNumber, arguments))
        return false;
    return true;
  }

  @Override public boolean areAllArgumentsShorts(@NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
      if (!isArgumentAShort(argumentNumber, arguments))
        return false;
    return true;
  }

  @Override public boolean areAllArgumentsIntegers(@NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
      if (!isArgumentAnInt(argumentNumber, arguments))
        return false;
    return true;
  }

  @Override public boolean areAllArgumentsLongs(@NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
      if (!isArgumentALong(argumentNumber, arguments))
        return false;
    return true;
  }

  @Override public boolean areAllArgumentsFloats(@NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
      if (!isArgumentAFloat(argumentNumber, arguments))
        return false;
    return true;
  }

  @Override public boolean areAllArgumentsDoubles(@NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
      if (!isArgumentADouble(argumentNumber, arguments))
        return false;
    return true;
  }

  @Override public boolean isArgumentConvertibleToByte(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    return (isArgumentNumeric(argumentNumber, arguments) && isArgumentAByte(argumentNumber, arguments));
  }

  @Override public boolean isArgumentConvertibleToShort(int argumentNumber,
      @NonNull List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return (isArgumentNumeric(argumentNumber, arguments) && isArgumentAByte(argumentNumber, arguments)
        && isArgumentAShort(argumentNumber, arguments));
  }

  @Override public boolean isArgumentConvertibleToInteger(int argumentNumber,
      @NonNull List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return (isArgumentNumeric(argumentNumber, arguments) && isArgumentAByte(argumentNumber, arguments)
        && isArgumentAShort(argumentNumber, arguments) && isArgumentAnInt(argumentNumber, arguments));
  }

  @Override public boolean isArgumentConvertibleToLong(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    return (isArgumentNumeric(argumentNumber, arguments) && isArgumentAByte(argumentNumber, arguments)
        && isArgumentAShort(argumentNumber, arguments) && isArgumentAnInt(argumentNumber, arguments) && isArgumentALong(
        argumentNumber, arguments));
  }

  @Override public boolean isArgumentConvertibleToFloat(int argumentNumber,
      @NonNull List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return (isArgumentNumeric(argumentNumber, arguments) && isArgumentAByte(argumentNumber, arguments)
        && isArgumentAShort(argumentNumber, arguments) && isArgumentAnInt(argumentNumber, arguments) && isArgumentALong(
        argumentNumber, arguments) && isArgumentAFloat(argumentNumber, arguments));
  }

  @Override public boolean isArgumentConvertibleToDouble(int argumentNumber,
      @NonNull List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return (isArgumentNumeric(argumentNumber, arguments));
  }

  @Override public boolean isWidestNumericArgumentAByte(@NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
      if (isArgumentAShort(argumentNumber, arguments) || isArgumentAnInt(argumentNumber, arguments) || isArgumentALong(
          argumentNumber, arguments) || isArgumentAFloat(argumentNumber, arguments) || isArgumentADouble(argumentNumber,
          arguments))
        return false;
    return true;
  }

  @Override public boolean isWidestNumericArgumentAShort(@NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
      if (isArgumentAnInt(argumentNumber, arguments) || isArgumentALong(argumentNumber, arguments) || isArgumentAFloat(
          argumentNumber, arguments) || isArgumentADouble(argumentNumber, arguments))
        return false;
    return true;
  }

  @Override public boolean isWidestNumericArgumentAnInt(@NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
      if (isArgumentALong(argumentNumber, arguments) || isArgumentAFloat(argumentNumber, arguments)
          || isArgumentADouble(argumentNumber, arguments))
        return false;
    return true;
  }

  @Override public boolean isWidestNumericArgumentALong(@NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
      if (isArgumentAFloat(argumentNumber, arguments) || isArgumentADouble(argumentNumber, arguments))
        return false;
    return true;
  }

  @Override public boolean isWidestNumericArgumentAFloat(@NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
      if (isArgumentADouble(argumentNumber, arguments))
        return false;
    return true;
  }

  @Override public boolean areAllArgumentsBooleans(@NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
      if (!isArgumentABoolean(argumentNumber, arguments))
        return false;
    return true;
  }

  @Override public boolean areAllArgumentLiterals(@NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
      if (!isArgumentALiteral(argumentNumber, arguments))
        return false;
    return true;
  }

  @Override public boolean areAllArgumentsNumeric(@NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
      if (!isArgumentNumeric(argumentNumber, arguments))
        return false;
    return true;
  }

  @Override public boolean areAllArgumentsStrings(@NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
      if (!isArgumentAString(argumentNumber, arguments))
        return false;
    return true;
  }

  @Override public boolean areAllArgumentsOfAnOrderedType(@NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
      if (!isArgumentOfAnOrderedType(argumentNumber, arguments))
        return false;
    return true;
  }

  @Override public void checkThatAllArgumentsAreFloats(@NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
      checkThatArgumentIsAFloat(argumentNumber, arguments);
  }

  @Override public void checkThatAllArgumentsAreStrings(@NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
      checkThatArgumentIsAString(argumentNumber, arguments);
  }

  @Override public void checkThatAllArgumentsAreOfAnOrderedType(@NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
      checkThatArgumentIsOfAnOrderedType(argumentNumber, arguments);
  }

  @Override public void checkThatArgumentIsNumeric(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    if (!isArgumentNumeric(argumentNumber, arguments))
      throw new InvalidSWRLBuiltInArgumentException(argumentNumber,
          makeInvalidArgumentTypeMessage(arguments.get(argumentNumber), "numeric"));
  }

  @Override public void checkThatArgumentIsOfAnOrderedType(int argumentNumber,
      @NonNull List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    if (!isArgumentOfAnOrderedType(argumentNumber, arguments))
      throw new InvalidSWRLBuiltInArgumentException(argumentNumber,
          makeInvalidArgumentTypeMessage(arguments.get(argumentNumber), "ordered type"));
  }

  @Override public boolean isArgumentOfAnOrderedType(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    return (isArgumentNumeric(argumentNumber, arguments) || isArgumentAString(argumentNumber, arguments));
  }

  @Override public boolean isArgumentAnIndividual(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    checkArgumentNumber(argumentNumber, arguments);

    return arguments.get(argumentNumber) instanceof SWRLNamedIndividualBuiltInArgument;
  }

  public boolean isArgumentALiterak(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    checkArgumentNumber(argumentNumber, arguments);

    return arguments.get(argumentNumber) instanceof SWRLLiteralBuiltInArgument;
  }

  @Override public void checkThatArgumentIsAnIndividual(int argumentNumber,
      @NonNull List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    if (!isArgumentAnIndividual(argumentNumber, arguments)) {
      throw new InvalidSWRLBuiltInArgumentException(argumentNumber,
          makeInvalidArgumentTypeMessage(arguments.get(argumentNumber), "individual"));
    }
  }

  @Override public void checkThatArgumentIsALiteral(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    if (!isArgumentALiteral(argumentNumber, arguments)) {
      throw new InvalidSWRLBuiltInArgumentException(argumentNumber,
          makeInvalidArgumentTypeMessage(arguments.get(argumentNumber), "literal"));
    }
  }

  @NonNull @Override public IRI getArgumentAsAnIndividualIRI(int argumentNumber,
      @NonNull List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkThatArgumentIsAnIndividual(argumentNumber, arguments);

    return ((SWRLNamedIndividualBuiltInArgument)arguments.get(argumentNumber)).getIRI();
  }

  @NonNull @Override public SWRLNamedIndividualBuiltInArgument getArgumentAsAnIndividual(int argumentNumber,
      @NonNull List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkThatArgumentIsAnIndividual(argumentNumber, arguments);

    return (SWRLNamedIndividualBuiltInArgument)arguments.get(argumentNumber);
  }

  @NonNull @Override public IRI getArgumentAsAClassIRI(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    checkThatArgumentIsAClass(argumentNumber, arguments);

    return ((SWRLClassBuiltInArgument)arguments.get(argumentNumber)).getIRI();
  }

  @NonNull @Override public SWRLClassBuiltInArgument getArgumentAsAClass(int argumentNumber,
      @NonNull List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkThatArgumentIsAClass(argumentNumber, arguments);

    return (SWRLClassBuiltInArgument)arguments.get(argumentNumber);
  }

  @NonNull public SWRLPropertyBuiltInArgument getArgumentAsAProperty(int argumentNumber,
      @NonNull List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkThatArgumentIsAProperty(argumentNumber, arguments);

    return (SWRLPropertyBuiltInArgument)arguments.get(argumentNumber);
  }

  @NonNull @Override public SWRLObjectPropertyBuiltInArgument getArgumentAsAnObjectProperty(int argumentNumber,
      @NonNull List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkThatArgumentIsAnObjectProperty(argumentNumber, arguments);

    return (SWRLObjectPropertyBuiltInArgument)arguments.get(argumentNumber);
  }

  @NonNull @Override public SWRLDataPropertyBuiltInArgument getArgumentAsADataProperty(int argumentNumber,
      @NonNull List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkThatArgumentIsADataProperty(argumentNumber, arguments);

    return (SWRLDataPropertyBuiltInArgument)arguments.get(argumentNumber);
  }

  @NonNull @Override public IRI getArgumentAsAnIRI(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    checkThatArgumentIsAClassPropertyOrIndividual(argumentNumber, arguments);

    if (isArgumentAClass(argumentNumber, arguments))
      return ((SWRLClassBuiltInArgument)arguments.get(argumentNumber)).getIRI();
    else if (isArgumentAProperty(argumentNumber, arguments))
      return ((SWRLPropertyBuiltInArgument)arguments.get(argumentNumber)).getIRI();
    else if (isArgumentAnIndividual(argumentNumber, arguments))
      return ((SWRLNamedIndividualBuiltInArgument)arguments.get(argumentNumber)).getIRI();
    else
      throw new SWRLBuiltInException(
          "internal error: unknown argument type " + arguments.get(argumentNumber).getClass());
  }

  @NonNull @Override public IRI getArgumentAsAPropertyIRI(int argumentNumber,
      @NonNull List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkThatArgumentIsAProperty(argumentNumber, arguments);

    return ((SWRLPropertyBuiltInArgument)arguments.get(argumentNumber)).getIRI();
  }

  @Override public void checkArgumentNumber(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    if ((argumentNumber < 0) || (argumentNumber >= arguments.size()))
      throw new SWRLBuiltInException("(0-offset) argument number #" + argumentNumber + " is out of bounds");
  }

  @Override public boolean isArgumentNumeric(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    return isArgumentALiteral(argumentNumber, arguments) && getArgumentAsALiteral(argumentNumber, arguments)
        .isNumeric();
  }

  @Override public boolean isArgumentNonNumeric(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    return isArgumentALiteral(argumentNumber, arguments) && !getArgumentAsALiteral(argumentNumber, arguments)
        .isNumeric();
  }

  @Override public void checkThatArgumentIsNonNumeric(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    if (!isArgumentNonNumeric(argumentNumber, arguments))
      throw new InvalidSWRLBuiltInArgumentException(argumentNumber,
          makeInvalidArgumentTypeMessage(arguments.get(argumentNumber), "non-numeric"));
  }

  // Bytes

  @Override public void checkThatArgumentIsAByte(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    if (!isArgumentAByte(argumentNumber, arguments))
      throw new InvalidSWRLBuiltInArgumentException(argumentNumber,
          makeInvalidArgumentTypeMessage(arguments.get(argumentNumber), "byte"));
  }

  @Override public boolean isArgumentAByte(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    return isArgumentALiteral(argumentNumber, arguments) && (getArgumentAsALiteral(argumentNumber, arguments).isByte());
  }

  @Override public short getArgumentAsAByte(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    return getArgumentAsALiteral(argumentNumber, arguments).getByte();
    // Will throw exception if invalid.
  }

  // Shorts

  @Override public void checkThatArgumentIsAShort(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    if (!isArgumentAShort(argumentNumber, arguments))
      throw new InvalidSWRLBuiltInArgumentException(argumentNumber,
          makeInvalidArgumentTypeMessage(arguments.get(argumentNumber), "short"));
  }

  @Override public boolean isArgumentAShort(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    return isArgumentALiteral(argumentNumber, arguments) && (getArgumentAsALiteral(argumentNumber, arguments)
        .isShort());
  }

  @Override public short getArgumentAsAShort(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    return getArgumentAsALiteral(argumentNumber, arguments).getShort();
    // Will throw exception if invalid.
  }

  // Integers

  @Override public void checkThatArgumentIsAnInt(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    if (!isArgumentAnInt(argumentNumber, arguments))
      throw new InvalidSWRLBuiltInArgumentException(argumentNumber,
          makeInvalidArgumentTypeMessage(arguments.get(argumentNumber), "int"));
  }

  @Override public boolean isArgumentAnInt(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    return isArgumentALiteral(argumentNumber, arguments) && (getArgumentAsALiteral(argumentNumber, arguments).isInt());
  }

  @Override public int getArgumentAsAnInt(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    return getArgumentAsALiteral(argumentNumber, arguments).getInt();
    // Will throw exception if invalid.
  }

  @Override public int getArgumentAsAPositiveInt(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    int i = getArgumentAsALiteral(argumentNumber, arguments).getInt(); // Will throw LiteralException

    if (i < 0)
      throw new InvalidSWRLBuiltInArgumentException(argumentNumber,
          makeInvalidArgumentTypeMessage(arguments.get(argumentNumber), "expecting positive integer"));
    return i;
  }

  @Override public boolean isArgumentALiteral(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    checkThatArgumentIsBound(argumentNumber, arguments);

    return (arguments.get(argumentNumber) instanceof SWRLLiteralBuiltInArgument);
  }

  @Override public boolean isArgumentAProperty(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    checkThatArgumentIsBound(argumentNumber, arguments);

    return (arguments.get(argumentNumber) instanceof SWRLPropertyBuiltInArgument);
  }

  @Override public boolean isArgumentADataProperty(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    checkThatArgumentIsBound(argumentNumber, arguments);

    return (arguments.get(argumentNumber) instanceof SWRLDataPropertyBuiltInArgument);
  }

  @Override public boolean isArgumentAnObjectProperty(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    checkThatArgumentIsBound(argumentNumber, arguments);

    return (arguments.get(argumentNumber) instanceof SWRLObjectPropertyBuiltInArgument);
  }

  @Override public void checkThatArgumentIsAProperty(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    if (!isArgumentAProperty(argumentNumber, arguments))
      throw new InvalidSWRLBuiltInArgumentException(argumentNumber,
          makeInvalidArgumentTypeMessage(arguments.get(argumentNumber), "property"));
  }

  @Override public void checkThatArgumentIsAnObjectProperty(int argumentNumber,
      @NonNull List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    if (!isArgumentAnObjectProperty(argumentNumber, arguments))
      throw new InvalidSWRLBuiltInArgumentException(argumentNumber,
          makeInvalidArgumentTypeMessage(arguments.get(argumentNumber), "object property"));
  }

  @Override public void checkThatArgumentIsADataProperty(int argumentNumber,
      @NonNull List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    if (!isArgumentADataProperty(argumentNumber, arguments))
      throw new InvalidSWRLBuiltInArgumentException(argumentNumber,
          makeInvalidArgumentTypeMessage(arguments.get(argumentNumber), "data property"));
  }

  @Override public void checkThatArgumentIsAClassPropertyOrIndividual(int argumentNumber,
      @NonNull List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    if (!isArgumentAClassPropertyOrIndividual(argumentNumber, arguments))
      throw new InvalidSWRLBuiltInArgumentException(argumentNumber,
          makeInvalidArgumentTypeMessage(arguments.get(argumentNumber), "class, property, or individual"));
  }

  @Override public boolean isArgumentAClassPropertyOrIndividual(int argumentNumber,
      @NonNull List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return isArgumentAClass(argumentNumber, arguments) || isArgumentAProperty(argumentNumber, arguments)
        || isArgumentAnIndividual(argumentNumber, arguments);
  }

  @Override public boolean isArgumentAClass(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    checkThatArgumentIsBound(argumentNumber, arguments);

    return (arguments.get(argumentNumber) instanceof SWRLClassBuiltInArgument);
  }

  @Override public void checkThatArgumentIsAClass(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    if (!isArgumentAClass(argumentNumber, arguments))
      throw new InvalidSWRLBuiltInArgumentException(argumentNumber,
          makeInvalidArgumentTypeMessage(arguments.get(argumentNumber), "class"));
  }

  @NonNull @Override public OWLLiteral getArgumentAsAnOWLLiteral(int argumentNumber,
      @NonNull List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkThatArgumentIsALiteral(argumentNumber, arguments);

    SWRLLiteralBuiltInArgument argument = (SWRLLiteralBuiltInArgument)arguments.get(argumentNumber);

    return argument.getLiteral();
  }

  @NonNull @Override public OWLLiteral getArgumentAsAnOWLLiteral(SWRLBuiltInArgument argument)
      throws SWRLBuiltInException
  {
    checkThatArgumentIsALiteral(argument);

    SWRLLiteralBuiltInArgument a = (SWRLLiteralBuiltInArgument)argument;

    return a.getLiteral();
  }

  // Longs

  @Override public void checkThatArgumentIsALong(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    if (!isArgumentALong(argumentNumber, arguments))
      throw new InvalidSWRLBuiltInArgumentException(argumentNumber,
          makeInvalidArgumentTypeMessage(arguments.get(argumentNumber), "long"));
  }

  @Override public boolean isArgumentALong(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    return isArgumentALiteral(argumentNumber, arguments) && (getArgumentAsALiteral(argumentNumber, arguments).isLong());
  }

  @Override public long getArgumentAsALong(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    return getArgumentAsALiteral(argumentNumber, arguments).getLong(); // Will throw DatatypeConversionException
    // if
    // invalid.
  }

  @Override public long getArgumentAsAPositiveLong(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    long l = getArgumentAsALiteral(argumentNumber, arguments).getLong(); // Will throw
    // DatatypeConversionException if
    // invalid.

    if (l < 0)
      throw new InvalidSWRLBuiltInArgumentException(argumentNumber,
          makeInvalidArgumentTypeMessage(arguments.get(argumentNumber), "expecting positive long"));

    return l;
  }

  // Floats

  @Override public void checkThatArgumentIsAFloat(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    if (!isArgumentAFloat(argumentNumber, arguments))
      throw new InvalidSWRLBuiltInArgumentException(argumentNumber,
          makeInvalidArgumentTypeMessage(arguments.get(argumentNumber), "float"));
  }

  @Override public boolean isArgumentAFloat(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    return isArgumentALiteral(argumentNumber, arguments) && (getArgumentAsALiteral(argumentNumber, arguments)
        .isFloat());
  }

  @Override public float getArgumentAsAFloat(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    return getArgumentAsALiteral(argumentNumber, arguments).getFloat(); // Will throw DatatypeConversionException
    // if
    // invalid.
  }

  @Override public float getArgumentAsAFloat(SWRLBuiltInArgument argument) throws SWRLBuiltInException
  {
    return getArgumentAsALiteral(argument).getFloat(); // Will throw DatatypeConversionException if invalid.
  }

  // Double

  @Override public void checkThatArgumentIsADouble(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    if (!isArgumentADouble(argumentNumber, arguments))
      throw new InvalidSWRLBuiltInArgumentException(argumentNumber,
          makeInvalidArgumentTypeMessage(arguments.get(argumentNumber), "double"));
  }

  @Override public boolean isArgumentADouble(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    return isArgumentALiteral(argumentNumber, arguments) && (getArgumentAsALiteral(argumentNumber, arguments)
        .isDouble());
  }

  @Override public double getArgumentAsADouble(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    checkArgumentNumber(argumentNumber, arguments);

    return getArgumentAsADouble(arguments.get(argumentNumber));
  }

  @Override public double getArgumentAsADouble(SWRLBuiltInArgument argument) throws SWRLBuiltInException
  {
    return getArgumentAsALiteral(argument).getDouble(); // Will throw DatatypeConversionException if invalid.
  }

  // Booleans

  @Override public void checkThatArgumentIsABoolean(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    if (!isArgumentABoolean(argumentNumber, arguments))
      throw new InvalidSWRLBuiltInArgumentException(argumentNumber,
          makeInvalidArgumentTypeMessage(arguments.get(argumentNumber), "boolean"));
  }

  @Override public boolean isArgumentABoolean(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    return isArgumentALiteral(argumentNumber, arguments) && (getArgumentAsALiteral(argumentNumber, arguments)
        .isBoolean());
  }

  @Override public boolean getArgumentAsABoolean(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    checkThatArgumentIsABoolean(argumentNumber, arguments);

    return getArgumentAsALiteral(argumentNumber, arguments).getBoolean();
  }

  // Strings

  @Override public void checkThatArgumentIsAString(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    if (!isArgumentAString(argumentNumber, arguments))
      throw new InvalidSWRLBuiltInArgumentException(argumentNumber,
          makeInvalidArgumentTypeMessage(arguments.get(argumentNumber), "string"));
  }

  @Override public boolean isArgumentAString(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    return isArgumentALiteral(argumentNumber, arguments) && getArgumentAsALiteral(argumentNumber, arguments).isString();
  }

  @NonNull @Override public String getArgumentAsAString(int argumentNumber,
      @NonNull List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkThatArgumentIsAString(argumentNumber, arguments);

    return getArgumentAsALiteral(argumentNumber, arguments).getString();
  }

  // Time

  @Override public void checkThatArgumentIsATime(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    if (!isArgumentATime(argumentNumber, arguments))
      throw new InvalidSWRLBuiltInArgumentException(argumentNumber,
          makeInvalidArgumentTypeMessage(arguments.get(argumentNumber), "xsd:time"));
  }

  @Override public boolean isArgumentATime(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    return isArgumentALiteral(argumentNumber, arguments) && getArgumentAsALiteral(argumentNumber, arguments).isTime();
  }

  @Override @NonNull public XSDTime getArgumentAsATime(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    checkThatArgumentIsATime(argumentNumber, arguments);

    return getArgumentAsALiteral(argumentNumber, arguments).getTime();
  }

  // Date

  @Override public void checkThatArgumentIsADate(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    if (!isArgumentADate(argumentNumber, arguments))
      throw new InvalidSWRLBuiltInArgumentException(argumentNumber,
          makeInvalidArgumentTypeMessage(arguments.get(argumentNumber), "xsd:Date"));
  }

  @Override public boolean isArgumentADate(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    return isArgumentALiteral(argumentNumber, arguments) && getArgumentAsALiteral(argumentNumber, arguments).isDate();
  }

  @NonNull @Override public XSDDate getArgumentAsADate(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    checkThatArgumentIsADate(argumentNumber, arguments);

    return getArgumentAsALiteral(argumentNumber, arguments).getDate();
  }

  // DateTime

  @Override public void checkThatArgumentIsADateTime(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    if (!isArgumentADateTime(argumentNumber, arguments))
      throw new InvalidSWRLBuiltInArgumentException(argumentNumber,
          makeInvalidArgumentTypeMessage(arguments.get(argumentNumber), "xsd:DateTime"));
  }

  @Override public boolean isArgumentADateTime(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    return isArgumentALiteral(argumentNumber, arguments) && getArgumentAsALiteral(argumentNumber, arguments)
        .isDateTime();
  }

  @NonNull @Override public XSDDateTime getArgumentAsADateTime(int argumentNumber,
      @NonNull List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkThatArgumentIsADateTime(argumentNumber, arguments);

    return getArgumentAsALiteral(argumentNumber, arguments).getDateTime();
  }

  // Duration

  @Override public void checkThatArgumentIsADuration(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    if (!isArgumentADuration(argumentNumber, arguments))
      throw new InvalidSWRLBuiltInArgumentException(argumentNumber,
          makeInvalidArgumentTypeMessage(arguments.get(argumentNumber), "xsd:Duration"));
  }

  @Override public boolean isArgumentADuration(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    return isArgumentALiteral(argumentNumber, arguments) && getArgumentAsALiteral(argumentNumber, arguments)
        .isDuration();
  }

  @NonNull @Override public XSDDuration getArgumentAsADuration(int argumentNumber,
      @NonNull List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkThatArgumentIsADuration(argumentNumber, arguments);

    return getArgumentAsALiteral(argumentNumber, arguments).getDuration();
  }

  // Unbound argument processing methods.

  @Override public boolean hasUnboundArguments(@NonNull List<SWRLBuiltInArgument> arguments)
  {
    for (SWRLBuiltInArgument argument : arguments)
      if (argument.isVariable() && argument.asVariable().isUnbound())
        return true;

    return false;
  }

  @Override public void checkThatAllArgumentsAreBound(@NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    if (hasUnboundArguments(arguments))
      throw new SWRLBuiltInException("all arguments must be bound");
  }

  @Override public void checkThatArgumentIsBound(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    if (isUnboundArgument(argumentNumber, arguments))
      throw new SWRLBuiltInException("not expecting an unbound argument for (0-offset) argument #" + argumentNumber);
  }

  @Override public boolean isUnboundArgument(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    checkArgumentNumber(argumentNumber, arguments);

    return arguments.get(argumentNumber).isVariable() && arguments.get(argumentNumber).asVariable().isUnbound();
  }

  protected boolean isBoundArgument(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    checkArgumentNumber(argumentNumber, arguments);

    return arguments.get(argumentNumber).isVariable() && arguments.get(argumentNumber).asVariable().isBound();
  }

  /**
   * Get 0-offset position of first unbound argument; return -1 if no unbound arguments are found.
   */
  @Override public int getFirstUnboundArgument(@NonNull List<SWRLBuiltInArgument> arguments)
  {
    for (int index = 0; index < arguments.size(); index++)
      if (arguments.get(index).isVariable() && arguments.get(index).asVariable().isUnbound())
        return index;

    return -1;
  }

  @Override public void checkForUnboundArguments(@NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    checkForUnboundArguments(arguments,
        "built-in does not support variable binding - unbound argument '" + getFirstUnboundArgument(arguments) + "'");
  }

  @Override public void checkForUnboundArguments(@NonNull List<SWRLBuiltInArgument> arguments, @NonNull String message)
      throws SWRLBuiltInException
  {
    if (hasUnboundArguments(arguments))
      throw new SWRLBuiltInException(message + " (0-offset) argument #" + getFirstUnboundArgument(arguments));
  }

  @Override public void checkThatArgumentsWereBoundVariables(@NonNull List<SWRLBuiltInArgument> arguments,
      @NonNull String message) throws SWRLBuiltInException
  {
    for (SWRLBuiltInArgument argument : arguments)
      if (!argument.wasBoundVariable())
        throw new SWRLBuiltInException(message + " " + argument);
  }

  @Override public void checkForUnboundNonFirstArguments(@NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    if (hasUnboundArguments(arguments.subList(1, arguments.size())))
      throw new SWRLBuiltInException("built-in supports variable binding only for the first argument - "
          + "unbound variables used as other arguments");
  }

  @Override public String getVariablePrefixedName(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    checkArgumentNumber(argumentNumber, arguments);

    if (!arguments.get(argumentNumber).isVariable())
      throw new SWRLBuiltInException(
          "internal error: attempt to get variable name of non-variable argument " + argumentNumber);

    return arguments.get(argumentNumber).asVariable().getVariablePrefixedName();
  }

  @NonNull private String makeInvalidArgumentTypeMessage(@NonNull SWRLBuiltInArgument argument,
      @NonNull String expectedTypeName)
  {
    String message = "expecting " + expectedTypeName + ", got ";

    if (argument.isVariable() && argument.asVariable().isUnbound())
      message += "unbound argument with variable name " + argument.asVariable().getVariablePrefixedName();
    else {
      if (argument instanceof SWRLClassBuiltInArgument) {
        SWRLClassBuiltInArgument classArgument = (SWRLClassBuiltInArgument)argument;
        message += "class with IRI " + classArgument.getIRI();
      } else if (argument instanceof SWRLPropertyBuiltInArgument) {
        SWRLPropertyBuiltInArgument propertyArgument = (SWRLPropertyBuiltInArgument)argument;
        message += "property with IRI " + propertyArgument.getIRI();
      } else if (argument instanceof SWRLNamedIndividualBuiltInArgument) {
        SWRLNamedIndividualBuiltInArgument individualArgument = (SWRLNamedIndividualBuiltInArgument)argument;
        message += "individual with IRI " + individualArgument.getIRI();
      } else if (argument instanceof SWRLLiteralBuiltInArgument) {
        SWRLLiteralBuiltInArgument literal = (SWRLLiteralBuiltInArgument)argument;
        message += "literal with value " + literal.toString();
      } else
        message += "unknown type " + argument.getClass();
    }
    return message;
  }

  /**
   * Take an bound Argument object with types ClassArgument, PropertyArgument, IndividualArgument, or LiteralArgument
   * and return it as a property value representation. Class, property and individual arguments are represented by their
   * IRIs; data value objects are represented by the appropriate Java type. Primitive XSD datatypes that do not have a
   * corresponding Java type are not yet supported.
   */
  @Override public Object getArgumentAsAPropertyValue(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    checkThatArgumentIsBound(argumentNumber, arguments);

    SWRLBuiltInArgument argument = arguments.get(argumentNumber);

    if (argument instanceof SWRLClassBuiltInArgument) {
      SWRLClassBuiltInArgument classArgument = (SWRLClassBuiltInArgument)argument;
      return classArgument.getIRI();
    } else if (argument instanceof SWRLPropertyBuiltInArgument) {
      SWRLPropertyBuiltInArgument propertyArgument = (SWRLPropertyBuiltInArgument)argument;
      return propertyArgument.getIRI();
    } else if (argument instanceof SWRLNamedIndividualBuiltInArgument) {
      SWRLNamedIndividualBuiltInArgument individualArgument = (SWRLNamedIndividualBuiltInArgument)argument;
      return individualArgument.getIRI();
    } else if (argument instanceof SWRLLiteralBuiltInArgument) {
      Literal literal = getArgumentAsALiteral(argument);
      if (literal.isByte())
        return literal.getByte();
      else if (literal.isShort())
        return literal.getShort();
      else if (literal.isInt())
        return literal.getInt();
      else if (literal.isLong())
        return literal.getLong();
      else if (literal.isFloat())
        return literal.getFloat();
      else if (literal.isDouble())
        return literal.getDouble();
      else if (literal.isString())
        return literal.getString();
      else
        throw new SWRLBuiltInException(
            "literal with value " + literal.toString() + " not supported - strings and numeric literals only");
    } else
      throw new SWRLBuiltInException("argument " + argument + " of unknown type " + argument.getClass());
  }

  @Override public void checkForUnboundArguments(@NonNull String ruleName, @NonNull String builtInName,
      @NonNull List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    for (SWRLBuiltInArgument argument : arguments) {
      if (argument.isVariable() && argument.asVariable().isUnbound())
        throw new SWRLBuiltInException(
            "built-in " + builtInName + " in rule " + ruleName + " " + "returned with unbound argument ?" + argument
                .asVariable().getVariableName());
      else if (argument.isMultiValueVariable() && argument.asMultiValueVariable().hasNoArguments())
        throw new SWRLBuiltInException(
            "built-in " + builtInName + " in rule " + ruleName + " " + "returned with empty multi-argument ?" + argument
                .asVariable().getVariableName());
    }
  }

  @NonNull @Override public List<SWRLBuiltInArgument> cloneArguments(@NonNull List<SWRLBuiltInArgument> arguments)
  {
    return new ArrayList<>(arguments);
  }

  @Override public boolean processResultArgument(@NonNull List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
      @NonNull Collection<SWRLBuiltInArgument> resultArguments) throws SWRLBuiltInException
  {
    checkArgumentNumber(resultArgumentNumber, arguments);

    if (isUnboundArgument(resultArgumentNumber, arguments)) {
      IRI variableIRI = arguments.get(resultArgumentNumber).asVariable().getIRI();
      SWRLMultiValueVariableBuiltInArgument resultMultiArgument = createSWRLMultiValueVariableBuiltInArgument(
          variableIRI);
      resultArguments.forEach(resultMultiArgument::addArgument);
      arguments.get(resultArgumentNumber).asVariable().setBuiltInResult(resultMultiArgument);
      return !resultMultiArgument.hasNoArguments();
    } else {
      SWRLBuiltInArgument argument = arguments.get(resultArgumentNumber);
      return resultArguments.contains(argument);
    }
  }

  @Override public boolean processResultArgument(@NonNull List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
      SWRLBuiltInArgument resultArgument) throws SWRLBuiltInException
  {
    checkArgumentNumber(resultArgumentNumber, arguments);

    if (isUnboundArgument(resultArgumentNumber, arguments)) {
      arguments.get(resultArgumentNumber).asVariable().setBuiltInResult(resultArgument);
      return true;
    } else {
      SWRLBuiltInArgument argument = arguments.get(resultArgumentNumber);
      return argument.equals(resultArgument);
    }
  }

  @Override public boolean processResultArgument(@NonNull List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
      SWRLLiteralBuiltInArgument resultArgument) throws SWRLBuiltInException
  {
    checkArgumentNumber(resultArgumentNumber, arguments);

    if (isUnboundArgument(resultArgumentNumber, arguments)) {
      arguments.get(resultArgumentNumber).asVariable().setBuiltInResult(resultArgument);
      return true;
    } else {
      Literal argumentLiteral = getArgumentAsALiteral(resultArgumentNumber, arguments);
      Literal resultArgumentLiteral = getArgumentAsALiteral(resultArgument);
      return argumentLiteral.equals(resultArgumentLiteral);
    }
  }

  @Override public boolean processResultArgument(@NonNull List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
      OWLLiteral resultArgument) throws SWRLBuiltInException
  {
    return processResultArgument(arguments, resultArgumentNumber, createLiteralBuiltInArgument(resultArgument));
  }

  @Override public boolean processResultArgument(@NonNull List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
      byte resultArgument) throws SWRLBuiltInException
  {
    return processResultArgument(arguments, resultArgumentNumber, createLiteralBuiltInArgument(resultArgument));
  }

  @Override public boolean processResultArgument(@NonNull List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
      short resultArgument) throws SWRLBuiltInException
  {
    return processResultArgument(arguments, resultArgumentNumber, createLiteralBuiltInArgument(resultArgument));
  }

  @Override public boolean processResultArgument(@NonNull List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
      int resultArgument) throws SWRLBuiltInException
  {
    return processResultArgument(arguments, resultArgumentNumber, createLiteralBuiltInArgument(resultArgument));
  }

  @Override public boolean processResultArgument(@NonNull List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
      long resultArgument) throws SWRLBuiltInException
  {
    return processResultArgument(arguments, resultArgumentNumber, createLiteralBuiltInArgument(resultArgument));
  }

  @Override public boolean processResultArgument(@NonNull List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
      float resultArgument) throws SWRLBuiltInException
  {
    return processResultArgument(arguments, resultArgumentNumber, createLiteralBuiltInArgument(resultArgument));
  }

  @Override public boolean processResultArgument(@NonNull List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
      double resultArgument) throws SWRLBuiltInException
  {
    return processResultArgument(arguments, resultArgumentNumber, createLiteralBuiltInArgument(resultArgument));
  }

  @Override public boolean processResultArgument(@NonNull List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
      @NonNull String resultArgument) throws SWRLBuiltInException
  {
    return processResultArgument(arguments, resultArgumentNumber, createLiteralBuiltInArgument(resultArgument));
  }

  @Override public boolean processResultArgument(@NonNull List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
      boolean resultArgument) throws SWRLBuiltInException
  {
    return processResultArgument(arguments, resultArgumentNumber, createLiteralBuiltInArgument(resultArgument));
  }

  @Override public boolean processResultArgument(@NonNull List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
      @NonNull XSDTime resultArgument) throws SWRLBuiltInException
  {
    return processResultArgument(arguments, resultArgumentNumber, createLiteralBuiltInArgument(resultArgument));
  }

  @Override public boolean processResultArgument(@NonNull List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
      @NonNull XSDDate resultArgument) throws SWRLBuiltInException
  {
    return processResultArgument(arguments, resultArgumentNumber, createLiteralBuiltInArgument(resultArgument));
  }

  @Override public boolean processResultArgument(@NonNull List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
      @NonNull XSDDateTime resultArgument) throws SWRLBuiltInException
  {
    return processResultArgument(arguments, resultArgumentNumber, createLiteralBuiltInArgument(resultArgument));
  }

  @Override public boolean processResultArgument(@NonNull List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
      @NonNull XSDDuration resultArgument) throws SWRLBuiltInException
  {
    return processResultArgument(arguments, resultArgumentNumber, createLiteralBuiltInArgument(resultArgument));
  }

  @NonNull @Override public SWRLClassBuiltInArgument createClassBuiltInArgument(OWLClass cls)
  {
    return getSWRLBuiltInArgumentFactory().getClassBuiltInArgument(cls);
  }

  @NonNull @Override public SWRLNamedIndividualBuiltInArgument createIndividualBuiltInArgument(
      OWLNamedIndividual individual)
  {
    return getSWRLBuiltInArgumentFactory().getNamedIndividualBuiltInArgument(individual);
  }

  @NonNull @Override public SWRLObjectPropertyBuiltInArgument createObjectPropertyBuiltInArgument(
      OWLObjectProperty property)
  {
    return getSWRLBuiltInArgumentFactory().getObjectPropertyBuiltInArgument(property);
  }

  @NonNull @Override public SWRLDataPropertyBuiltInArgument createDataPropertyBuiltInArgument(OWLDataProperty property)
  {
    return getSWRLBuiltInArgumentFactory().getDataPropertyBuiltInArgument(property);
  }

  @NonNull @Override public SWRLAnnotationPropertyBuiltInArgument createAnnotationPropertyBuiltInArgument(
      OWLAnnotationProperty property)
  {
    return getSWRLBuiltInArgumentFactory().getAnnotationPropertyBuiltInArgument(property);
  }

  @NonNull @Override public SWRLDatatypeBuiltInArgument createDatatypeBuiltInArgument(OWLDatatype datatype)
  {
    return getSWRLBuiltInArgumentFactory().getDatatypeBuiltInArgument(datatype);
  }

  @Override @NonNull public SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(OWLLiteral literal)
  {
    return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(literal);
  }

  @NonNull @Override public SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(@NonNull String s)
  {
    return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(s);
  }

  @NonNull @Override public SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(boolean b)
  {
    return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(b);
  }

  @NonNull @Override public SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(byte b)
  {
    return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(b);
  }

  @NonNull @Override public SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(short s)
  {
    return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(s);
  }

  @NonNull @Override public SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(int i)
  {
    return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(i);
  }

  @NonNull @Override public SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(long l)
  {
    return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(l);
  }

  @NonNull @Override public SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(float f)
  {
    return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(f);
  }

  @NonNull @Override public SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(double d)
  {
    return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(d);
  }

  @NonNull @Override public SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(XSDDate date)
  {
    return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(date);
  }

  @NonNull @Override public SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(XSDTime time)
  {
    return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(time);
  }

  @NonNull @Override public SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(XSDDateTime dateTime)
  {
    return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(dateTime);
  }

  @NonNull @Override public SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(XSDDuration duration)
  {
    return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(duration);
  }

  @NonNull @Override public SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(URI uri)
  {
    return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(uri);
  }

  @NonNull @Override public SWRLMultiValueVariableBuiltInArgument createSWRLMultiValueVariableBuiltInArgument(
      IRI variableIRI)
  {
    return getSWRLBuiltInArgumentFactory().getMultiValueVariableBuiltInArgument(variableIRI);
  }

  @NonNull @Override public SWRLMultiValueVariableBuiltInArgument createSWRLMultiValueVariableBuiltInArgument(
      IRI variableIRI, List<SWRLBuiltInArgument> arguments)
  {
    return getSWRLBuiltInArgumentFactory().getMultiValueVariableBuiltInArgument(variableIRI, arguments);
  }

  @NonNull @Override public SQWRLCollectionVariableBuiltInArgument createSQWRLCollectionVariableBuiltInArgument(
      @NonNull IRI variableIRI, @NonNull String queryName, @NonNull String collectionName,
      @NonNull String collectionGroupID)
  {
    return getSWRLBuiltInArgumentFactory()
        .getSQWRLCollectionVariableBuiltInArgument(variableIRI, queryName, collectionName, collectionGroupID);
  }

  @NonNull protected SWRLLiteralBuiltInArgument createLeastNarrowNumericLiteralBuiltInArgument(double value,
      @NonNull List<SWRLBuiltInArgument> boundInputNumericArguments) throws SWRLBuiltInException
  {
    OWLLiteral literal = createLeastNarrowNumericOWLLiteral(value, boundInputNumericArguments);

    return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(literal);
  }

  @NonNull protected SWRLAPIOWLOntology getSWRLAPIOWLOntology() throws SWRLBuiltInLibraryException
  {
    return getBuiltInBridge().getSWRLAPIOWLOntology();
  }

  @NonNull protected SWRLAPIOWLDataFactory getSWRLAPIOWLDataFactory() throws SWRLBuiltInLibraryException
  {
    return getBuiltInBridge().getSWRLAPIOWLDataFactory();
  }

  @NonNull protected OWLNamedIndividual injectOWLNamedIndividualOfClass(@NonNull OWLClass cls)
  {
    OWLNamedIndividual individual = getSWRLAPIOWLDataFactory().getInjectedOWLNamedIndividual();
    OWLDeclarationAxiom declarationAxiom = getSWRLAPIOWLDataFactory().getOWLIndividualDeclarationAxiom(individual);
    OWLClassAssertionAxiom classAssertionAxiom = getSWRLAPIOWLDataFactory().getOWLClassAssertionAxiom(cls, individual);
    getBuiltInBridge().getIRIResolver().recordOWLNamedIndividual(individual);
    getBuiltInBridge().injectOWLAxiom(declarationAxiom);
    getBuiltInBridge().injectOWLAxiom(classAssertionAxiom);

    return individual;
  }

  private void checkThatArgumentIsALiteral(SWRLBuiltInArgument argument) throws SWRLBuiltInException
  {
    if (!(argument instanceof SWRLLiteralBuiltInArgument))
      throw new InvalidSWRLBuiltInArgumentException(makeInvalidArgumentTypeMessage(argument, "data value"));
  }

  @NonNull private SWRLBuiltInArgumentFactory getSWRLBuiltInArgumentFactory() throws SWRLBuiltInLibraryException
  {
    return getBuiltInBridge().getSWRLAPIOWLDataFactory().getSWRLBuiltInArgumentFactory();
  }

  @NonNull private LiteralFactory getLiteralFactory() throws SWRLBuiltInLibraryException
  {
    return getBuiltInBridge().getSWRLAPIOWLDataFactory().getLiteralFactory();
  }

  @NonNull private OWLLiteral createLeastNarrowNumericOWLLiteral(double value,
      @NonNull List<SWRLBuiltInArgument> boundInputNumericArguments) throws SWRLBuiltInException
  {
    if (isWidestNumericArgumentAByte(boundInputNumericArguments))
      return getOWLLiteralFactory().getOWLLiteral((byte)value);
    else if (isWidestNumericArgumentAShort(boundInputNumericArguments))
      return getOWLLiteralFactory().getOWLLiteral((short)value);
    else if (isWidestNumericArgumentAnInt(boundInputNumericArguments))
      return getOWLLiteralFactory().getOWLLiteral((int)value);
    else if (isWidestNumericArgumentALong(boundInputNumericArguments))
      return getOWLLiteralFactory().getOWLLiteral((long)value);
    else if (isWidestNumericArgumentAFloat(boundInputNumericArguments))
      return getOWLLiteralFactory().getOWLLiteral((float)value);
    else
      return getOWLLiteralFactory().getOWLLiteral(value);
  }

  @NonNull private OWLLiteralFactory getOWLLiteralFactory() throws SWRLBuiltInLibraryException
  {
    return getBuiltInBridge().getSWRLAPIOWLDataFactory().getOWLLiteralFactory();
  }

  @NonNull private Literal getArgumentAsALiteral(int argumentNumber, @NonNull List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    checkThatArgumentIsALiteral(argumentNumber, arguments);

    SWRLLiteralBuiltInArgument argument = (SWRLLiteralBuiltInArgument)arguments.get(argumentNumber);

    return getLiteralFactory().getLiteral(argument.getLiteral());
  }

  @NonNull private Literal getArgumentAsALiteral(SWRLBuiltInArgument argument) throws SWRLBuiltInException
  {
    checkThatArgumentIsALiteral(argument);

    SWRLLiteralBuiltInArgument a = (SWRLLiteralBuiltInArgument)argument;

    return getLiteralFactory().getLiteral(a.getLiteral());
  }

}
