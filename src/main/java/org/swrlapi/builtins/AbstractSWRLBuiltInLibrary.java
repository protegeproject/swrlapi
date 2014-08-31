package org.swrlapi.builtins;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentFactory;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLPropertyBuiltInArgument;
import org.swrlapi.core.SWRLAPILiteral;
import org.swrlapi.core.SWRLAPILiteralFactory;
import org.swrlapi.core.SWRLAPIOWLDataFactory;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.xsd.XSDDate;
import org.swrlapi.core.xsd.XSDDateTime;
import org.swrlapi.core.xsd.XSDDuration;
import org.swrlapi.core.xsd.XSDTime;
import org.swrlapi.exceptions.InvalidSWRLBuiltInArgumentException;
import org.swrlapi.exceptions.InvalidSWRLBuiltInArgumentNumberException;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.exceptions.SWRLBuiltInMethodRuntimeException;
import org.swrlapi.exceptions.SWRLBuiltInLibraryException;
import org.swrlapi.sqwrl.values.SQWRLResultValueFactory;

/**
 * A class that must be subclassed by a class implementing a library of SWRL built-in methods.
 * <p/>
 * Provides invocation context for invoked built-ins (such the name of invoking rule, whether the invocation is
 * in the consequent or the antecedent) and access to the invoking {@link org.swrlapi.builtins.SWRLBuiltInBridge}.
 * Also provides implementations for a large number of SWRL built-in argument processing methods.
 *
 * @see org.swrlapi.builtins.SWRLBuiltInLibrary
 * @see org.swrlapi.builtins.SWRLBuiltInContext
 * @see SWRLBuiltInInputArgumentHandler
 * @see SWRLBuiltInResultArgumentHandler
 * @see org.swrlapi.builtins.SWRLBuiltInArgumentCreator
 */
public abstract class AbstractSWRLBuiltInLibrary implements SWRLBuiltInLibrary, SWRLBuiltInInputArgumentHandler,
		SWRLBuiltInResultArgumentHandler, SWRLBuiltInArgumentCreator
{
	private final String libraryName;

	// Bridge, rule name, built-in index, and head or body location within rule of built-in currently invoking its
	// associated Java implementation. The invokingRuleName, invokingBuiltInIndex, and isInConsequent variables are valid
	// only when a built-in currently being invoked so should only be retrieved through their associated accessor methods
	// from within a built-in; the invokingBridge method is valid only in built-ins and in the reset method.
	private SWRLBuiltInBridge invokingBridge;
	private String invokingRuleName = "";
	private int invokingBuiltInIndex = -1;
	private boolean isInConsequent = false;
	private Long invocationPatternID;
	private Map<String, Long> invocationPatternMap;

	public AbstractSWRLBuiltInLibrary(String libraryName)
	{
		this.libraryName = libraryName;
		this.invocationPatternID = 0L;
		this.invocationPatternMap = new HashMap<String, Long>();
	}

	@Override
	public String getLibraryName()
	{
		return this.libraryName;
	}

	@Override
	public SWRLBuiltInBridge getBuiltInBridge() throws SWRLBuiltInLibraryException
	{
		if (this.invokingBridge == null)
			throw new SWRLBuiltInLibraryException(
					"invalid call to getInvokingBridge - should only be called from within a built-in");

		return this.invokingBridge;
	}

	@Override
	public String getInvokingRuleName() throws SWRLBuiltInLibraryException
	{
		if (this.invokingRuleName.length() == 0)
			throw new SWRLBuiltInLibraryException(
					"invalid call to getInvokingRuleName - should only be called from within a built-in");

		return this.invokingRuleName;
	}

	@Override
	public int getInvokingBuiltInIndex() throws SWRLBuiltInLibraryException
	{
		if (this.invokingBuiltInIndex == -1)
			throw new SWRLBuiltInLibraryException(
					"invalid call to getInvokingBuiltInIndex - should only be called from within a built-in");

		return this.invokingBuiltInIndex;
	}

	@Override
	public boolean getIsInConsequent() throws SWRLBuiltInLibraryException
	{
		if (this.invokingBridge == null)
			throw new SWRLBuiltInLibraryException(
					"invalid call to getIsInConsequent - should only be called from within a built-in");

		return this.isInConsequent;
	}

	@Override
	public void checkThatInConsequent() throws SWRLBuiltInException
	{
		if (this.invokingBridge == null)
			throw new SWRLBuiltInLibraryException(
					"invalid call to checkThatInConsequent - should only be called from within a built-in");

		if (!this.isInConsequent)
			throw new SWRLBuiltInException("built-in can only be used in consequent");
	}

	@Override
	public void checkThatInAntecedent() throws SWRLBuiltInException
	{
		if (this.invokingBridge == null)
			throw new SWRLBuiltInLibraryException(
					"invalid call to checkThatInAntecedent - should only be called from within a built-in");

		if (this.isInConsequent)
			throw new SWRLBuiltInException("built-in can only be used in antecedent");
	}

	@Override
	public abstract void reset() throws SWRLBuiltInLibraryException;

	@Override
	public void invokeResetMethod(SWRLBuiltInBridge bridge) throws SWRLBuiltInLibraryException
	{
		synchronized (this) {
			this.invokingBridge = bridge;

			reset();

			this.invocationPatternID = 0L;
			this.invocationPatternMap = new HashMap<String, Long>();

			this.invokingBridge = null;
		}
	}

	@Override
	public boolean invokeBuiltInMethod(Method method, SWRLBuiltInBridge bridge, String ruleName, String prefix,
			String builtInMethodName, int builtInIndex, boolean inConsequent, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
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
				if (targetException instanceof SWRLBuiltInException) { // An explicit BuiltInException was thrown by the built-in.
					throw new SWRLBuiltInException("exception thrown by built-in " + builtInName + " in rule " + ruleName + ": "
							+ targetException.getMessage(), targetException);
				} else if (targetException instanceof RuntimeException) { // A runtime exception was thrown by the built-in.
					throw new SWRLBuiltInMethodRuntimeException(ruleName, builtInName, targetException.getMessage(),
							targetException);
				} else
					throw new SWRLBuiltInException("unknown exception thrown by built-in " + builtInName + " in rule " + ruleName
							+ ": " + e.toString(), e);
			} catch (Throwable e) { // Should be one of IllegalAccessException or IllegalArgumentException
				throw new SWRLBuiltInLibraryException("internal built-in library exception when invoking built-in "
						+ builtInName + " in rule " + ruleName + ": " + e.getMessage(), e);
			}
			this.invokingBridge = null;
			this.invokingRuleName = "";
			this.invokingBuiltInIndex = -1;
			this.isInConsequent = false;
		}

		return result;
	}

	@Override
	public SQWRLResultValueFactory getSQWRLResultValueFactory() throws SWRLBuiltInLibraryException
	{
		return getBuiltInBridge().getSWRLAPIOWLDataFactory().getSQWRLResultValueFactory();
	}

	/**
	 * Create a string that represents a key of a unique invocation pattern for a built-in for a
	 * bridge/rule/built-in/arguments combination.
	 */
	@Override
	public String createInvocationPattern(SWRLBuiltInBridge bridge, String ruleName, int builtInIndex,
			boolean inConsequent, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
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

	@Override
	public IRI createIRI(String fullName) throws SWRLBuiltInException
	{
		try {
			return IRI.create(fullName);
		} catch (RuntimeException e) {
			throw new SWRLBuiltInException("error creating IRI from full name " + fullName + ": " + e.getMessage(), e);
		}
	}

	protected OWLNamedIndividual injectOWLNamedIndividualOfClass(OWLClass cls) throws SWRLBuiltInException
	{
		OWLNamedIndividual individual = getSWRLAPIOWLDataFactory().getInjectedOWLNamedIndividual();
		OWLDeclarationAxiom declarationAxiom = getSWRLAPIOWLDataFactory().getOWLIndividualDeclarationAxiom(individual);
		OWLClassAssertionAxiom classAssertionAxiom = getSWRLAPIOWLDataFactory().getOWLClassAssertionAxiom(cls, individual);
		getBuiltInBridge().getIRIResolver().recordOWLNamedIndividual(individual);
		getBuiltInBridge().injectOWLAxiom(declarationAxiom);
		getBuiltInBridge().injectOWLAxiom(classAssertionAxiom);

		return individual;
	}

	// Argument handling methods

	public void checkNumberOfArgumentsAtLeastOne(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		if (arguments.size() < 1)
			throw new InvalidSWRLBuiltInArgumentNumberException(1, 0, "at least");
	}

	@Override
	public void checkNumberOfArgumentsEqualTo(int expecting, int actual) throws InvalidSWRLBuiltInArgumentNumberException
	{
		if (expecting != actual)
			throw new InvalidSWRLBuiltInArgumentNumberException(expecting, actual);
	}

	@Override
	public void checkNumberOfArgumentsAtLeast(int expectingAtLeast, int actual)
			throws InvalidSWRLBuiltInArgumentNumberException
	{
		if (actual < expectingAtLeast)
			throw new InvalidSWRLBuiltInArgumentNumberException(expectingAtLeast, actual, "at least");
	}

	@Override
	public void checkNumberOfArgumentsAtMost(int expectingAtMost, int actual)
			throws InvalidSWRLBuiltInArgumentNumberException
	{
		if (actual > expectingAtMost)
			throw new InvalidSWRLBuiltInArgumentNumberException(expectingAtMost, actual, "at most");
	}

	@Override
	public void checkNumberOfArgumentsInRange(int expectingAtLeast, int expectingAtMost, int actual)
			throws InvalidSWRLBuiltInArgumentNumberException
	{
		if (actual > expectingAtMost || actual < expectingAtLeast)
			throw new InvalidSWRLBuiltInArgumentNumberException(expectingAtMost, actual, expectingAtLeast + " to");
	}

	@Override
	public void checkThatAllArgumentsAreLiterals(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
			checkThatArgumentIsALiteral(argumentNumber, arguments);
	}

	@Override
	public void checkThatAllArgumentsAreNumeric(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
			checkThatArgumentIsNumeric(argumentNumber, arguments);
	}

	@Override
	public void checkThatAllArgumentsAreIntegers(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
			checkThatArgumentIsAnInteger(argumentNumber, arguments);
	}

	@Override
	public boolean areAllArgumentsShorts(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
			if (!isArgumentAShort(argumentNumber, arguments))
				return false;
		return true;
	}

	@Override
	public boolean areAllArgumentsIntegers(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
			if (!isArgumentAnInteger(argumentNumber, arguments))
				return false;
		return true;
	}

	@Override
	public boolean areAllArgumentsLongs(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
			if (!isArgumentALong(argumentNumber, arguments))
				return false;
		return true;
	}

	@Override
	public boolean areAllArgumentsFloats(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
			if (!isArgumentAFloat(argumentNumber, arguments))
				return false;
		return true;
	}

	@Override
	public boolean areAllArgumentsDoubles(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
			if (!isArgumentADouble(argumentNumber, arguments))
				return false;
		return true;
	}

	@Override
	public boolean isArgumentConvertableToDouble(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		return (isArgumentNumeric(argumentNumber, arguments));
	}

	@Override
	public boolean isArgumentConvertableToFloat(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		return (isArgumentNumeric(argumentNumber, arguments) && isArgumentAShort(argumentNumber, arguments)
				&& isArgumentAnInteger(argumentNumber, arguments) && isArgumentALong(argumentNumber, arguments)
				&& isArgumentAFloat(
				argumentNumber, arguments));
	}

	@Override
	public boolean isArgumentConvertableToLong(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		return (isArgumentNumeric(argumentNumber, arguments) && isArgumentAShort(argumentNumber, arguments)
				&& isArgumentAnInteger(argumentNumber, arguments) && isArgumentALong(argumentNumber, arguments));
	}

	@Override
	public boolean isArgumentConvertableToInteger(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		return (isArgumentNumeric(argumentNumber, arguments) && isArgumentAShort(argumentNumber, arguments)
				&& isArgumentAnInteger(
				argumentNumber, arguments));
	}

	@Override
	public boolean isArgumentConvertableToShort(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		return (isArgumentNumeric(argumentNumber, arguments) && isArgumentAShort(argumentNumber, arguments));
	}

	@Override
	public boolean isShortMostPreciseArgument(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
			if (isArgumentAnInteger(argumentNumber, arguments) || isArgumentALong(argumentNumber, arguments)
					|| isArgumentAFloat(argumentNumber, arguments) || isArgumentADouble(argumentNumber, arguments))
				return false;
		return true;
	}

	@Override
	public boolean isIntegerMostPreciseArgument(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
			if (isArgumentALong(argumentNumber, arguments) || isArgumentAFloat(argumentNumber, arguments)
					|| isArgumentADouble(argumentNumber, arguments))
				return false;
		return true;
	}

	@Override
	public boolean isLongMostPreciseArgument(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
			if (isArgumentADouble(argumentNumber, arguments) || isArgumentAFloat(argumentNumber, arguments))
				return false;
		return true;
	}

	@Override
	public boolean isFloatMostPreciseArgument(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
			if (isArgumentADouble(argumentNumber, arguments))
				return false;
		return true;
	}

	@Override
	public boolean areAllArgumentsBooleans(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
			if (!isArgumentABoolean(argumentNumber, arguments))
				return false;
		return true;
	}

	@Override
	public boolean areAllArgumentLiterals(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
			if (!isArgumentALiteral(argumentNumber, arguments))
				return false;
		return true;
	}

	@Override
	public boolean areAllArgumentsNumeric(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
			if (!isArgumentNumeric(argumentNumber, arguments))
				return false;
		return true;
	}

	@Override
	public boolean areAllArgumentsStrings(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
			if (!isArgumentAString(argumentNumber, arguments))
				return false;
		return true;
	}

	@Override
	public boolean areAllArgumentsOfAnOrderedType(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
			if (!isArgumentOfAnOrderedType(argumentNumber, arguments))
				return false;
		return true;
	}

	@Override
	public void checkThatAllArgumentsAreFloats(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
			checkThatArgumentIsAFloat(argumentNumber, arguments);
	}

	@Override
	public void checkThatAllArgumentsAreStrings(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
			checkThatArgumentIsAString(argumentNumber, arguments);
	}

	@Override
	public void checkThatAllArgumentsAreOfAnOrderedType(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++)
			checkThatArgumentIsOfAnOrderedType(argumentNumber, arguments);
	}

	public void checkThatArgumentIsALiteral(SWRLBuiltInArgument argument) throws SWRLBuiltInException
	{
		if (!(argument instanceof SWRLLiteralBuiltInArgument))
			throw new InvalidSWRLBuiltInArgumentException(makeInvalidArgumentTypeMessage(argument, "data value"));
	}

	@Override
	public void checkThatArgumentIsNumeric(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		if (!isArgumentNumeric(argumentNumber, arguments))
			throw new InvalidSWRLBuiltInArgumentException(argumentNumber, makeInvalidArgumentTypeMessage(
					arguments.get(argumentNumber), "numeric"));
	}

	@Override
	public void checkThatArgumentIsOfAnOrderedType(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		if (!isArgumentOfAnOrderedType(argumentNumber, arguments))
			throw new InvalidSWRLBuiltInArgumentException(argumentNumber, makeInvalidArgumentTypeMessage(
					arguments.get(argumentNumber), "ordered type"));
	}

	@Override
	public boolean isArgumentOfAnOrderedType(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		return (isArgumentNumeric(argumentNumber, arguments) || isArgumentAString(argumentNumber, arguments));
	}

	@Override
	public boolean isArgumentAnIndividual(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		checkArgumentNumber(argumentNumber, arguments);

		return arguments.get(argumentNumber) instanceof SWRLNamedIndividualBuiltInArgument;
	}

	public boolean isArgumentADatatypeValue(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		checkArgumentNumber(argumentNumber, arguments);

		return arguments.get(argumentNumber) instanceof SWRLLiteralBuiltInArgument;
	}

	@Override
	public void checkThatArgumentIsAnIndividual(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		if (!isArgumentAnIndividual(argumentNumber, arguments)) {
			throw new InvalidSWRLBuiltInArgumentException(argumentNumber, makeInvalidArgumentTypeMessage(
					arguments.get(argumentNumber), "individual"));
		}
	}

	@Override
	public void checkThatArgumentIsALiteral(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		if (!isArgumentADatatypeValue(argumentNumber, arguments)) {
			throw new InvalidSWRLBuiltInArgumentException(argumentNumber, makeInvalidArgumentTypeMessage(
					arguments.get(argumentNumber), "data value"));
		}
	}

	@Override
	public IRI getArgumentAsAnIndividualIRI(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		checkThatArgumentIsAnIndividual(argumentNumber, arguments);

		return ((SWRLNamedIndividualBuiltInArgument)arguments.get(argumentNumber)).getIRI();
	}

	@Override
	public SWRLNamedIndividualBuiltInArgument getArgumentAsAnIndividual(int argumentNumber,
			List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		checkThatArgumentIsAnIndividual(argumentNumber, arguments);

		return (SWRLNamedIndividualBuiltInArgument)arguments.get(argumentNumber);
	}

	@Override
	public IRI getArgumentAsAClassIRI(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		checkThatArgumentIsAClass(argumentNumber, arguments);

		return ((SWRLClassBuiltInArgument)arguments.get(argumentNumber)).getIRI();
	}

	@Override
	public SWRLClassBuiltInArgument getArgumentAsAClass(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		checkThatArgumentIsAClass(argumentNumber, arguments);

		return (SWRLClassBuiltInArgument)arguments.get(argumentNumber);
	}

	public SWRLPropertyBuiltInArgument getArgumentAsAProperty(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		checkThatArgumentIsAProperty(argumentNumber, arguments);

		return (SWRLPropertyBuiltInArgument)arguments.get(argumentNumber);
	}

	@Override
	public SWRLObjectPropertyBuiltInArgument getArgumentAsAnObjectProperty(int argumentNumber,
			List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		checkThatArgumentIsAnObjectProperty(argumentNumber, arguments);

		return (SWRLObjectPropertyBuiltInArgument)arguments.get(argumentNumber);
	}

	@Override
	public SWRLDataPropertyBuiltInArgument getArgumentAsADataProperty(int argumentNumber,
			List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		checkThatArgumentIsADataProperty(argumentNumber, arguments);

		return (SWRLDataPropertyBuiltInArgument)arguments.get(argumentNumber);
	}

	@Override
	public IRI getArgumentAsAnIRI(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
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

	@Override
	public IRI getArgumentAsAPropertyIRI(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws
			SWRLBuiltInException
	{
		checkThatArgumentIsAProperty(argumentNumber, arguments);

		return ((SWRLPropertyBuiltInArgument)arguments.get(argumentNumber)).getIRI();
	}

	@Override
	public void checkArgumentNumber(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		if ((argumentNumber < 0) || (argumentNumber >= arguments.size()))
			throw new SWRLBuiltInException("(0-offset) argument number #" + argumentNumber + " is out of bounds");
	}

	@Override
	public boolean isArgumentNumeric(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		return isArgumentALiteral(argumentNumber, arguments) && getArgumentAsASWRLAPILiteral(argumentNumber, arguments)
				.isNumeric();
	}

	@Override
	public boolean isArgumentNonNumeric(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws
			SWRLBuiltInException
	{
		return isArgumentALiteral(argumentNumber, arguments) && !getArgumentAsASWRLAPILiteral(argumentNumber, arguments)
				.isNumeric();
	}

	@Override
	public void checkThatArgumentIsNonNumeric(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		if (!isArgumentNonNumeric(argumentNumber, arguments))
			throw new InvalidSWRLBuiltInArgumentException(argumentNumber, makeInvalidArgumentTypeMessage(
					arguments.get(argumentNumber), "non-numeric"));
	}

	// Integers

	@Override
	public void checkThatArgumentIsAnInteger(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		if (!isArgumentAnInteger(argumentNumber, arguments))
			throw new InvalidSWRLBuiltInArgumentException(argumentNumber, makeInvalidArgumentTypeMessage(
					arguments.get(argumentNumber), "integer"));
	}

	@Override
	public boolean isArgumentAnInteger(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws
			SWRLBuiltInException
	{
		return isArgumentALiteral(argumentNumber, arguments) && (getArgumentAsASWRLAPILiteral(argumentNumber, arguments)
				.isInteger());
	}

	@Override
	public int getArgumentAsAnInteger(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		return getArgumentAsASWRLAPILiteral(argumentNumber, arguments).getInteger(); // Will throw
		// DatatypeConversionException if
		// invalid.
	}

	@Override
	public int getArgumentAsAPositiveInteger(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		int i = getArgumentAsASWRLAPILiteral(argumentNumber, arguments).getInteger(); // Will throw
		// DatatypeConversionException if
		// invalid.

		if (i < 0)
			throw new InvalidSWRLBuiltInArgumentException(argumentNumber, makeInvalidArgumentTypeMessage(
					arguments.get(argumentNumber), "expecting positive integer"));
		return i;
	}

	// Shorts

	@Override
	public boolean isArgumentAShort(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		return isArgumentALiteral(argumentNumber, arguments) && (getArgumentAsASWRLAPILiteral(argumentNumber, arguments)
				.isShort());
	}

	@Override
	public short getArgumentAsAShort(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		return getArgumentAsASWRLAPILiteral(argumentNumber, arguments).getShort(); // Will throw DatatypeConversionException
		// if
		// invalid.
	}

	@Override
	public boolean isArgumentALiteral(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		checkThatArgumentIsBound(argumentNumber, arguments);

		return (arguments.get(argumentNumber) instanceof SWRLLiteralBuiltInArgument);
	}

	@Override
	public boolean isArgumentAProperty(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws
			SWRLBuiltInException
	{
		checkThatArgumentIsBound(argumentNumber, arguments);

		return (arguments.get(argumentNumber) instanceof SWRLPropertyBuiltInArgument);
	} // isArgumentAProperty

	@Override
	public boolean isArgumentADataProperty(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		checkThatArgumentIsBound(argumentNumber, arguments);

		return (arguments.get(argumentNumber) instanceof SWRLDataPropertyBuiltInArgument);
	}

	@Override
	public boolean isArgumentAnObjectProperty(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		checkThatArgumentIsBound(argumentNumber, arguments);

		return (arguments.get(argumentNumber) instanceof SWRLObjectPropertyBuiltInArgument);
	}

	@Override
	public void checkThatArgumentIsAProperty(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		if (!isArgumentAProperty(argumentNumber, arguments))
			throw new InvalidSWRLBuiltInArgumentException(argumentNumber, makeInvalidArgumentTypeMessage(
					arguments.get(argumentNumber), "property"));
	}

	@Override
	public void checkThatArgumentIsAnObjectProperty(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		if (!isArgumentAnObjectProperty(argumentNumber, arguments))
			throw new InvalidSWRLBuiltInArgumentException(argumentNumber, makeInvalidArgumentTypeMessage(
					arguments.get(argumentNumber), "object property"));
	}

	@Override
	public void checkThatArgumentIsADataProperty(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		if (!isArgumentADataProperty(argumentNumber, arguments))
			throw new InvalidSWRLBuiltInArgumentException(argumentNumber, makeInvalidArgumentTypeMessage(
					arguments.get(argumentNumber), "data property"));
	}

	@Override
	public void checkThatArgumentIsAClassPropertyOrIndividual(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		if (!isArgumentAClassPropertyOrIndividual(argumentNumber, arguments))
			throw new InvalidSWRLBuiltInArgumentException(argumentNumber, makeInvalidArgumentTypeMessage(
					arguments.get(argumentNumber), "class, property, or individual"));
	}

	@Override
	public boolean isArgumentAClassPropertyOrIndividual(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		return isArgumentAClass(argumentNumber, arguments) || isArgumentAProperty(argumentNumber, arguments)
				|| isArgumentAnIndividual(argumentNumber, arguments);
	}

	@Override
	public boolean isArgumentAClass(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		checkThatArgumentIsBound(argumentNumber, arguments);

		return (arguments.get(argumentNumber) instanceof SWRLClassBuiltInArgument);
	}

	@Override
	public void checkThatArgumentIsAClass(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		if (!isArgumentAClass(argumentNumber, arguments))
			throw new InvalidSWRLBuiltInArgumentException(argumentNumber, makeInvalidArgumentTypeMessage(
					arguments.get(argumentNumber), "class"));
	}

	@Override
	public OWLLiteral getArgumentAsAnOWLLiteral(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		checkThatArgumentIsALiteral(argumentNumber, arguments);

		SWRLLiteralBuiltInArgument argument = (SWRLLiteralBuiltInArgument)arguments.get(argumentNumber);

		return argument.getLiteral();
	}

	@Override
	public OWLLiteral getArgumentAsAnOWLLiteral(SWRLBuiltInArgument argument) throws SWRLBuiltInException
	{
		checkThatArgumentIsALiteral(argument);

		SWRLLiteralBuiltInArgument a = (SWRLLiteralBuiltInArgument)argument;

		return a.getLiteral();
	}

	// Longs

	@Override
	public void checkThatArgumentIsALong(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws
			SWRLBuiltInException
	{
		if (!isArgumentALong(argumentNumber, arguments))
			throw new InvalidSWRLBuiltInArgumentException(argumentNumber, makeInvalidArgumentTypeMessage(
					arguments.get(argumentNumber), "long"));
	}

	@Override
	public boolean isArgumentALong(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		return isArgumentALiteral(argumentNumber, arguments) && (getArgumentAsASWRLAPILiteral(argumentNumber, arguments)
				.isLong());
	}

	@Override
	public long getArgumentAsALong(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		return getArgumentAsASWRLAPILiteral(argumentNumber, arguments).getLong(); // Will throw DatatypeConversionException
		// if
		// invalid.
	}

	@Override
	public long getArgumentAsAPositiveLong(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		long l = getArgumentAsASWRLAPILiteral(argumentNumber, arguments).getLong(); // Will throw
		// DatatypeConversionException if
		// invalid.

		if (l < 0)
			throw new InvalidSWRLBuiltInArgumentException(argumentNumber, makeInvalidArgumentTypeMessage(
					arguments.get(argumentNumber), "expecting positive long"));

		return l;
	}

	// Floats

	@Override
	public void checkThatArgumentIsAFloat(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		if (!isArgumentAFloat(argumentNumber, arguments))
			throw new InvalidSWRLBuiltInArgumentException(argumentNumber, makeInvalidArgumentTypeMessage(
					arguments.get(argumentNumber), "float"));
	}

	@Override
	public boolean isArgumentAFloat(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		return isArgumentALiteral(argumentNumber, arguments) && (getArgumentAsASWRLAPILiteral(argumentNumber, arguments)
				.isFloat());
	}

	@Override
	public float getArgumentAsAFloat(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		return getArgumentAsASWRLAPILiteral(argumentNumber, arguments).getFloat(); // Will throw DatatypeConversionException
		// if
		// invalid.
	}

	@Override
	public float getArgumentAsAFloat(SWRLBuiltInArgument argument) throws SWRLBuiltInException
	{
		return getArgumentAsASWRLAPILiteral(argument).getFloat(); // Will throw DatatypeConversionException if invalid.
	}

	// Double

	@Override
	public void checkThatArgumentIsADouble(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		if (!isArgumentADouble(argumentNumber, arguments))
			throw new InvalidSWRLBuiltInArgumentException(argumentNumber, makeInvalidArgumentTypeMessage(
					arguments.get(argumentNumber), "double"));
	}

	@Override
	public boolean isArgumentADouble(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		return isArgumentALiteral(argumentNumber, arguments) && (getArgumentAsASWRLAPILiteral(argumentNumber, arguments)
				.isDouble());
	}

	@Override
	public double getArgumentAsADouble(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws
			SWRLBuiltInException
	{
		checkArgumentNumber(argumentNumber, arguments);

		return getArgumentAsADouble(arguments.get(argumentNumber));
	}

	@Override
	public double getArgumentAsADouble(SWRLBuiltInArgument argument) throws SWRLBuiltInException
	{
		return getArgumentAsASWRLAPILiteral(argument).getDouble(); // Will throw DatatypeConversionException if invalid.
	}

	// Booleans

	@Override
	public void checkThatArgumentIsABoolean(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		if (!isArgumentABoolean(argumentNumber, arguments))
			throw new InvalidSWRLBuiltInArgumentException(argumentNumber, makeInvalidArgumentTypeMessage(
					arguments.get(argumentNumber), "boolean"));
	}

	@Override
	public boolean isArgumentABoolean(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		return isArgumentALiteral(argumentNumber, arguments) && (getArgumentAsASWRLAPILiteral(argumentNumber, arguments)
				.isBoolean());
	}

	@Override
	public boolean getArgumentAsABoolean(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws
			SWRLBuiltInException
	{
		checkThatArgumentIsABoolean(argumentNumber, arguments);

		return getArgumentAsASWRLAPILiteral(argumentNumber, arguments).getBoolean();
	}

	// Strings

	@Override
	public void checkThatArgumentIsAString(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		if (!isArgumentAString(argumentNumber, arguments))
			throw new InvalidSWRLBuiltInArgumentException(argumentNumber, makeInvalidArgumentTypeMessage(
					arguments.get(argumentNumber), "string"));
	}

	@Override
	public boolean isArgumentAString(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		return isArgumentALiteral(argumentNumber, arguments) && getArgumentAsASWRLAPILiteral(argumentNumber, arguments)
				.isString();
	}

	@Override
	public String getArgumentAsAString(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws
			SWRLBuiltInException
	{
		checkThatArgumentIsAString(argumentNumber, arguments);

		return getArgumentAsASWRLAPILiteral(argumentNumber, arguments).getString();
	}

	// Time

	public void checkThatArgumentIsATime(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws
			SWRLBuiltInException
	{
		if (!isArgumentATime(argumentNumber, arguments))
			throw new InvalidSWRLBuiltInArgumentException(argumentNumber, makeInvalidArgumentTypeMessage(
					arguments.get(argumentNumber), "xsd:time"));
	}

	public boolean isArgumentATime(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		return isArgumentALiteral(argumentNumber, arguments) && getArgumentAsASWRLAPILiteral(argumentNumber, arguments)
				.isTime();
	}

	public XSDTime getArgumentAsATime(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		checkThatArgumentIsATime(argumentNumber, arguments);

		return getArgumentAsASWRLAPILiteral(argumentNumber, arguments).getTime();
	}

	// Date

	public void checkThatArgumentIsADate(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws
			SWRLBuiltInException
	{
		if (!isArgumentADate(argumentNumber, arguments))
			throw new InvalidSWRLBuiltInArgumentException(argumentNumber, makeInvalidArgumentTypeMessage(
					arguments.get(argumentNumber), "xsd:Date"));
	}

	public boolean isArgumentADate(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		return isArgumentALiteral(argumentNumber, arguments) && getArgumentAsASWRLAPILiteral(argumentNumber, arguments)
				.isDate();
	}

	public XSDDate getArgumentAsADate(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		checkThatArgumentIsADate(argumentNumber, arguments);

		return getArgumentAsASWRLAPILiteral(argumentNumber, arguments).getDate();
	}

	// DateTime

	public void checkThatArgumentIsADateTime(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		if (!isArgumentADateTime(argumentNumber, arguments))
			throw new InvalidSWRLBuiltInArgumentException(argumentNumber, makeInvalidArgumentTypeMessage(
					arguments.get(argumentNumber), "xsd:DateTime"));
	}

	public boolean isArgumentADateTime(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws
			SWRLBuiltInException
	{
		return isArgumentALiteral(argumentNumber, arguments) && getArgumentAsASWRLAPILiteral(argumentNumber, arguments)
				.isDateTime();
	}

	public XSDDateTime getArgumentAsADateTime(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		checkThatArgumentIsADateTime(argumentNumber, arguments);

		return getArgumentAsASWRLAPILiteral(argumentNumber, arguments).getDateTime();
	}

	// Duration

	public void checkThatArgumentIsADuration(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		if (!isArgumentADuration(argumentNumber, arguments))
			throw new InvalidSWRLBuiltInArgumentException(argumentNumber, makeInvalidArgumentTypeMessage(
					arguments.get(argumentNumber), "xsd:Duration"));
	}

	public boolean isArgumentADuration(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws
			SWRLBuiltInException
	{
		return isArgumentALiteral(argumentNumber, arguments) && getArgumentAsASWRLAPILiteral(argumentNumber, arguments)
				.isDuration();
	}

	public XSDDuration getArgumentAsADuration(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		checkThatArgumentIsADuration(argumentNumber, arguments);

		return getArgumentAsASWRLAPILiteral(argumentNumber, arguments).getDuration();
	}

	// Unbound argument processing methods.

	@Override
	public boolean hasUnboundArguments(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		for (SWRLBuiltInArgument argument : arguments)
			if (argument.isVariable() && argument.asVariable().isUnbound())
				return true;

		return false;
	}

	@Override
	public void checkThatAllArgumentsAreBound(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		if (hasUnboundArguments(arguments))
			throw new SWRLBuiltInException("all arguments must be bound");
	}

	@Override
	public void checkThatArgumentIsBound(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws
			SWRLBuiltInException
	{
		if (isUnboundArgument(argumentNumber, arguments))
			throw new SWRLBuiltInException("not expecting an unbound argument for (0-offset) argument #" + argumentNumber);
	}

	@Override
	public boolean isUnboundArgument(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		checkArgumentNumber(argumentNumber, arguments);

		return arguments.get(argumentNumber).isVariable() && arguments.get(argumentNumber).asVariable().isUnbound();
	}

	public boolean isBoundArgument(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		checkArgumentNumber(argumentNumber, arguments);

		return arguments.get(argumentNumber).isVariable() && arguments.get(argumentNumber).asVariable().isBound();
	}

	/**
	 * Get 0-offset position of first unbound argument; return -1 if no unbound arguments are found.
	 */
	@Override
	public int getFirstUnboundArgument(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		for (int index = 0; index < arguments.size(); index++)
			if (arguments.get(index).isVariable() && arguments.get(index).asVariable().isUnbound())
				return index;

		return -1;
	}

	@Override
	public void checkForUnboundArguments(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		checkForUnboundArguments(arguments, "built-in does not support variable binding - unbound argument '"
				+ getFirstUnboundArgument(arguments) + "'");
	}

	@Override
	public void checkForUnboundArguments(List<SWRLBuiltInArgument> arguments, String message) throws SWRLBuiltInException
	{
		if (hasUnboundArguments(arguments))
			throw new SWRLBuiltInException(message + " (0-offset) argument #" + getFirstUnboundArgument(arguments));
	}

	@Override
	public void checkThatArgumentsWereBoundVariables(List<SWRLBuiltInArgument> arguments, String message)
			throws SWRLBuiltInException
	{
		for (SWRLBuiltInArgument argument : arguments)
			if (!argument.wasBoundVariable())
				throw new SWRLBuiltInException(message + " " + argument);
	}

	@Override
	public void checkForUnboundNonFirstArguments(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		if (hasUnboundArguments(arguments.subList(1, arguments.size())))
			throw new SWRLBuiltInException("built-in supports variable binding only for the first argument - "
					+ "unbound variables used as other arguments");
	}

	@Override
	public String getVariablePrefixedName(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		checkArgumentNumber(argumentNumber, arguments);

		if (!arguments.get(argumentNumber).isVariable())
			throw new SWRLBuiltInException("internal error: attempt to get variable name of non-variable argument "
					+ argumentNumber);

		return arguments.get(argumentNumber).asVariable().getVariablePrefixedName();
	}

	private String makeInvalidArgumentTypeMessage(SWRLBuiltInArgument argument, String expectedTypeName)
			throws SWRLBuiltInException
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
	@Override
	public Object getArgumentAsAPropertyValue(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		SWRLBuiltInArgument argument;

		checkThatArgumentIsBound(argumentNumber, arguments);

		argument = arguments.get(argumentNumber);

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
			SWRLAPILiteral literal = getArgumentAsASWRLAPILiteral(argument);
			if (literal.isByte())
				return literal.getByte();
			else if (literal.isShort())
				return literal.getShort();
			else if (literal.isInteger())
				return literal.getInteger();
			else if (literal.isInt())
				return literal.getInteger();
			else if (literal.isLong())
				return literal.getLong();
			else if (literal.isFloat())
				return literal.getFloat();
			else if (literal.isDouble())
				return literal.getDouble();
			else if (literal.isString())
				return literal.getString();
			else
				throw new SWRLBuiltInException("literal with value " + literal.toString()
						+ " not supported - strings and numeric literals only");
		} else
			throw new SWRLBuiltInException("argument " + argument + " of unknown type " + argument.getClass());
	}

	@Override
	public void checkForUnboundArguments(String ruleName, String builtInName, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		for (SWRLBuiltInArgument argument : arguments) {
			if (argument.isVariable() && argument.asVariable().isUnbound())
				throw new SWRLBuiltInException("built-in " + builtInName + " in rule " + ruleName + " "
						+ "returned with unbound argument ?" + argument.asVariable().getVariableName());
			else if (argument.isMultiValueVariable() && argument.asMultiValueVariable().hasNoArguments())
				throw new SWRLBuiltInException("built-in " + builtInName + " in rule " + ruleName + " "
						+ "returned with empty multi-argument ?" + argument.asVariable().getVariableName());
		}
	}

	@Override
	public List<SWRLBuiltInArgument> cloneArguments(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		return new ArrayList<SWRLBuiltInArgument>(arguments);
	}

	@Override
	public boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			Collection<SWRLBuiltInArgument> resultArguments) throws SWRLBuiltInException
	{
		checkArgumentNumber(resultArgumentNumber, arguments);

		if (isUnboundArgument(resultArgumentNumber, arguments)) {
			IRI variableIRI = arguments.get(resultArgumentNumber).asVariable().getIRI();
			SWRLMultiValueVariableBuiltInArgument resultMultiArgument = createSWRLMultiValueVariableBuiltInArgument(
					variableIRI);
			for (SWRLBuiltInArgument argument : resultArguments)
				resultMultiArgument.addArgument(argument);
			arguments.get(resultArgumentNumber).asVariable().setBuiltInResult(resultMultiArgument);
			return !resultMultiArgument.hasNoArguments();
		} else {
			SWRLBuiltInArgument argument = arguments.get(resultArgumentNumber);
			return resultArguments.contains(argument);
		}
	}

	@Override
	public boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
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

	@Override
	public boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			SWRLLiteralBuiltInArgument resultArgument) throws SWRLBuiltInException
	{
		checkArgumentNumber(resultArgumentNumber, arguments);

		if (isUnboundArgument(resultArgumentNumber, arguments)) {
			arguments.get(resultArgumentNumber).asVariable().setBuiltInResult(resultArgument);
			return true;
		} else {
			SWRLAPILiteral argumentLiteral = getArgumentAsASWRLAPILiteral(resultArgumentNumber, arguments);
			SWRLAPILiteral resultArgumentLiteral = getArgumentAsASWRLAPILiteral(resultArgument);
			return argumentLiteral.equals(resultArgumentLiteral);
		}
	}

	@Override
	public boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			short resultArgument) throws SWRLBuiltInException
	{
		return processResultArgument(arguments, resultArgumentNumber, createLiteralBuiltInArgument(resultArgument));
	}

	@Override
	public boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			int resultArgument)
			throws SWRLBuiltInException
	{
		return processResultArgument(arguments, resultArgumentNumber, createLiteralBuiltInArgument(resultArgument));
	}

	@Override
	public boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			OWLLiteral resultArgument) throws SWRLBuiltInException
	{
		return processResultArgument(arguments, resultArgumentNumber, createLiteralBuiltInArgument(resultArgument));
	}

	@Override
	public boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			long resultArgument) throws SWRLBuiltInException
	{
		return processResultArgument(arguments, resultArgumentNumber, createLiteralBuiltInArgument(resultArgument));
	}

	@Override
	public boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			float resultArgument) throws SWRLBuiltInException
	{
		return processResultArgument(arguments, resultArgumentNumber, createLiteralBuiltInArgument(resultArgument));
	}

	@Override
	public boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			double resultArgument) throws SWRLBuiltInException
	{
		return processResultArgument(arguments, resultArgumentNumber, createLiteralBuiltInArgument(resultArgument));
	}

	@Override
	public boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			byte resultArgument) throws SWRLBuiltInException
	{
		return processResultArgument(arguments, resultArgumentNumber, createLiteralBuiltInArgument(resultArgument));
	}

	@Override
	public boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			String resultArgument) throws SWRLBuiltInException
	{
		return processResultArgument(arguments, resultArgumentNumber, createLiteralBuiltInArgument(resultArgument));
	}

	public boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			XSDTime resultArgument) throws SWRLBuiltInException
	{
		return processResultArgument(arguments, resultArgumentNumber, createLiteralBuiltInArgument(resultArgument));
	}

	public boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			XSDDate resultArgument) throws SWRLBuiltInException
	{
		return processResultArgument(arguments, resultArgumentNumber, createLiteralBuiltInArgument(resultArgument));
	}

	public boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			XSDDateTime resultArgument) throws SWRLBuiltInException
	{
		return processResultArgument(arguments, resultArgumentNumber, createLiteralBuiltInArgument(resultArgument));
	}

	public boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			XSDDuration resultArgument) throws SWRLBuiltInException
	{
		return processResultArgument(arguments, resultArgumentNumber, createLiteralBuiltInArgument(resultArgument));
	}

	@Override
	public SWRLClassBuiltInArgument createClassBuiltInArgument(OWLClass cls) throws SWRLBuiltInException
	{
		return getSWRLBuiltInArgumentFactory().getClassBuiltInArgument(cls);
	}

	@Override
	public SWRLNamedIndividualBuiltInArgument createIndividualBuiltInArgument(OWLNamedIndividual individual)
			throws SWRLBuiltInException
	{
		return getSWRLBuiltInArgumentFactory().getNamedIndividualBuiltInArgument(individual);
	}

	@Override
	public SWRLObjectPropertyBuiltInArgument createObjectPropertyBuiltInArgument(OWLObjectProperty property)
			throws SWRLBuiltInException
	{
		return getSWRLBuiltInArgumentFactory().getObjectPropertyBuiltInArgument(property);
	}

	@Override
	public SWRLDataPropertyBuiltInArgument createDataPropertyBuiltInArgument(OWLDataProperty property)
			throws SWRLBuiltInException
	{
		return getSWRLBuiltInArgumentFactory().getDataPropertyBuiltInArgument(property);
	}

	@Override
	public SWRLAnnotationPropertyBuiltInArgument createAnnotationPropertyBuiltInArgument(OWLAnnotationProperty property)
			throws SWRLBuiltInException
	{
		return getSWRLBuiltInArgumentFactory().getAnnotationPropertyBuiltInArgument(property);
	}

	@Override
	public SWRLDatatypeBuiltInArgument createDatatypeBuiltInArgument(OWLDatatype datatype) throws SWRLBuiltInException
	{
		return getSWRLBuiltInArgumentFactory().getDatatypeBuiltInArgument(datatype);
	}

	public SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(OWLLiteral literal) throws SWRLBuiltInException
	{
		return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(literal);
	}

	@Override
	public SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(String s) throws SWRLBuiltInException
	{
		return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(s);
	}

	@Override
	public SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(boolean b) throws SWRLBuiltInException
	{
		return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(b);
	}

	@Override
	public SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(int i) throws SWRLBuiltInException
	{
		return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(i);
	}

	@Override
	public SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(long l) throws SWRLBuiltInException
	{
		return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(l);
	}

	@Override
	public SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(float f) throws SWRLBuiltInException
	{
		return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(f);
	}

	@Override
	public SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(double d) throws SWRLBuiltInException
	{
		return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(d);
	}

	@Override
	public SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(short s) throws SWRLBuiltInException
	{
		return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(s);
	}

	@Override
	public SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(Byte b) throws SWRLBuiltInException
	{
		return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(b);
	}

	@Override
	public SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(XSDDate date) throws SWRLBuiltInException
	{
		return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(date);
	}

	@Override
	public SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(XSDTime time) throws SWRLBuiltInException
	{
		return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(time);
	}

	@Override
	public SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(XSDDateTime dateTime) throws SWRLBuiltInException
	{
		return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(dateTime);
	}

	@Override
	public SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(XSDDuration duration) throws SWRLBuiltInException
	{
		return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(duration);
	}

	@Override
	public SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(URI uri) throws SWRLBuiltInException
	{
		return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(uri);
	}

	@Override
	public SWRLMultiValueVariableBuiltInArgument createSWRLMultiValueVariableBuiltInArgument(IRI variableIRI)
			throws SWRLBuiltInException
	{
		return getSWRLBuiltInArgumentFactory().getMultiValueVariableBuiltInArgument(variableIRI);
	}

	@Override
	public SWRLMultiValueVariableBuiltInArgument createSWRLMultiValueVariableBuiltInArgument(IRI variableIRI,
			List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		return getSWRLBuiltInArgumentFactory().getMultiValueVariableBuiltInArgument(variableIRI, arguments);
	}

	public SQWRLCollectionVariableBuiltInArgument createSQWRLCollectionVariableBuiltInArgument(IRI variableIRI,
			String queryName, String collectionName, String collectionGroupID) throws SWRLBuiltInException
	{
		return getSWRLBuiltInArgumentFactory().getSQWRLCollectionVariableBuiltInArgument(variableIRI, queryName,
				collectionName, collectionGroupID);
	}

	protected SWRLAPIOWLOntology getSWRLAPIOWLOntology() throws SWRLBuiltInLibraryException
	{
		return getBuiltInBridge().getSWRLAPIOWLOntology();
	}

	protected SWRLAPIOWLDataFactory getSWRLAPIOWLDataFactory() throws SWRLBuiltInLibraryException
	{
		return getBuiltInBridge().getSWRLAPIOWLDataFactory();
	}

	private SWRLBuiltInArgumentFactory getSWRLBuiltInArgumentFactory() throws SWRLBuiltInLibraryException
	{
		return getBuiltInBridge().getSWRLAPIOWLDataFactory().getSWRLBuiltInArgumentFactory();
	}

	private SWRLAPILiteralFactory getSWRLAPILiteralFactory() throws SWRLBuiltInLibraryException
	{
		return getBuiltInBridge().getSWRLAPIOWLDataFactory().getSWRLAPILiteralFactory();
	}

	private SWRLAPILiteral getArgumentAsASWRLAPILiteral(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException
	{
		checkThatArgumentIsALiteral(argumentNumber, arguments);

		SWRLLiteralBuiltInArgument argument = (SWRLLiteralBuiltInArgument)arguments.get(argumentNumber);

		return getSWRLAPILiteralFactory().getSWRLAPILiteral(argument.getLiteral());
	}

	private SWRLAPILiteral getArgumentAsASWRLAPILiteral(SWRLBuiltInArgument argument) throws SWRLBuiltInException
	{
		checkThatArgumentIsALiteral(argument);

		SWRLLiteralBuiltInArgument a = (SWRLLiteralBuiltInArgument)argument;

		return getSWRLAPILiteralFactory().getSWRLAPILiteral(a.getLiteral());
	}

}
