package org.swrlapi.builtins;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.exceptions.InvalidSWRLBuiltInArgumentNumberException;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.literal.XSDDate;
import org.swrlapi.literal.XSDDateTime;
import org.swrlapi.literal.XSDDuration;
import org.swrlapi.literal.XSDTime;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * Utility methods for dealing with input arguments to SWRL built-ins.
 *
 * @see org.swrlapi.builtins.AbstractSWRLBuiltInLibrary
 */
public interface SWRLBuiltInInputArgumentHandler
{
  /**
   * @param arguments The built-in arguments The built-in arguments
   * @return True if there are unbound arguments
   */
  // Unbound argument handling
  boolean hasUnboundArguments(@NonNull List<@NonNull SWRLBuiltInArgument> arguments);

  /**
   * @param arguments The built-in arguments
   * @throws SWRLBuiltInException If not all arguments are bound
   */
  void checkThatAllArgumentsAreBound(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @throws SWRLBuiltInException If the argument is not bound
   */
  void checkThatArgumentIsBound(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return True if the specified argument is unbound
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean isUnboundArgument(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param ruleName    The name of the rule
   * @param builtInName The name of the built-in
   * @param arguments   The built-in arguments
   * @throws SWRLBuiltInException If unbound arguments exist
   */
  void checkForUnboundArguments(@NonNull String ruleName, @NonNull String builtInName,
    @NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * Get 0-offset position of first unbound argument; return -1 if no unbound arguments are found.
   *
   * @param arguments The built-in arguments
   * @return The first unbound argument
   */
  int getFirstUnboundArgument(@NonNull List<@NonNull SWRLBuiltInArgument> arguments);

  /**
   * @param arguments The built-in arguments
   * @throws SWRLBuiltInException If at least one argument is unbound
   */
  void checkForUnboundArguments(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param arguments The built-in arguments
   * @param message   The exception message used if unbound arguments are found
   * @throws SWRLBuiltInException If at least one argument is unbound
   */
  void checkForUnboundArguments(@NonNull List<@NonNull SWRLBuiltInArgument> arguments, @NonNull String message)
    throws SWRLBuiltInException;

  /**
   * @param arguments The built-in arguments
   * @param message   The exception message used if non bound variables are found
   * @throws SWRLBuiltInException If not all argument are bound variables
   */
  void checkThatAllArgumentsAreBoundVariables(@NonNull List<@NonNull SWRLBuiltInArgument> arguments, @NonNull String message)
    throws SWRLBuiltInException;

  /**
   * @param arguments The built-in arguments
   * @throws SWRLBuiltInException If the check condition is met
   */
  void checkForUnboundNonFirstArguments(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param expecting Number of expected arguments
   * @param actual    Actual number of arguments
   * @throws InvalidSWRLBuiltInArgumentNumberException If the expected and actual number are not equal
   */
  void checkNumberOfArgumentsEqualTo(int expecting, int actual) throws InvalidSWRLBuiltInArgumentNumberException;

  /**
   * @param expectingAtLeast Minimum number of expected arguments
   * @param actual           Actual number of arguments
   * @throws InvalidSWRLBuiltInArgumentNumberException If the condition is not met
   */
  void checkNumberOfArgumentsAtLeast(int expectingAtLeast, int actual) throws InvalidSWRLBuiltInArgumentNumberException;

  /**
   * @param expectingAtMost Maximum number of expected arguments
   * @param actual          Actual number of arguments
   * @throws InvalidSWRLBuiltInArgumentNumberException If the condition is not met
   */
  void checkNumberOfArgumentsAtMost(int expectingAtMost, int actual) throws InvalidSWRLBuiltInArgumentNumberException;

  /**
   * @param expectingAtLeast Minimum number of expected arguments
   * @param expectingAtMost  Maximum number of expected arguments
   * @param actual           Actual number of arguments
   * @throws InvalidSWRLBuiltInArgumentNumberException If the condition is not met
   */
  void checkNumberOfArgumentsInRange(int expectingAtLeast, int expectingAtMost, int actual)
    throws InvalidSWRLBuiltInArgumentNumberException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @throws SWRLBuiltInException If the argument number is invalid
   */
  void checkArgumentNumber(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return An IRI representation of the specified argument
   * @throws SWRLBuiltInException If the specified argument cannot be converted to an IRI
   */
  @NonNull IRI getArgumentAsAnIRI(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @throws SWRLBuiltInException If the argument does not meet the check
   */
  void checkThatArgumentIsAClassPropertyOrIndividual(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return True is the argument is a class, property or individual
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean isArgumentAClassPropertyOrIndividual(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return The IRI of the specified class argument
   * @throws SWRLBuiltInException If an error occurs during IRI generation
   */
  @NonNull IRI getArgumentAsAClassIRI(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return A class built-in argument
   * @throws SWRLBuiltInException If an error occurs during argument generation
   */
  @NonNull SWRLClassBuiltInArgument getArgumentAsAClass(int argumentNumber,
    @NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return True if the specified argument is a class
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean isArgumentAClass(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @throws SWRLBuiltInException If the specified argument is not a class
   */
  void checkThatArgumentIsAClass(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return True if the specified argument is an individual
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean isArgumentAnOWLNamedIndividual(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @throws SWRLBuiltInException If the specified argument is not an individual
   */
  void checkThatArgumentIsANamedIndividual(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return The IRI of the OWL individual argument
   * @throws SWRLBuiltInException If the argument cannot be represented as an IRI
   */
  @NonNull IRI getArgumentAsANamedIndividualIRI(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return A named individual built-in argument
   * @throws SWRLBuiltInException If the argument cannot be represented as an individual built-in argument
   */
  @NonNull SWRLNamedIndividualBuiltInArgument getArgumentAsANamedIndividual(int argumentNumber,
    @NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return True if the specified argument is a property
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean isArgumentAProperty(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return True if the specified argument is an object property
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean isArgumentAnObjectProperty(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return True if the specified argument is a data property
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean isArgumentADataProperty(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return The IRI of the specified property
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  @NonNull IRI getArgumentAsAPropertyIRI(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return An object property built-in argument
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  @NonNull SWRLObjectPropertyBuiltInArgument getArgumentAsAnObjectProperty(int argumentNumber,
    @NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return A data property built-in argument
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  @NonNull SWRLDataPropertyBuiltInArgument getArgumentAsADataProperty(int argumentNumber,
    @NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @throws SWRLBuiltInException If the specified argument is not a property
   */
  void checkThatArgumentIsAProperty(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @throws SWRLBuiltInException If the specified argument is not an object property
   */
  void checkThatArgumentIsAnObjectProperty(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @throws SWRLBuiltInException If the specified argument is not a data property
   */
  void checkThatArgumentIsADataProperty(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param arguments The built-in arguments
   * @throws SWRLBuiltInException If not all arguments are literals
   */
  void checkThatAllArgumentsAreOWLLiterals(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param arguments The built-in arguments
   * @return True if all arguments are literals
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean areAllArgumentLiterals(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @throws SWRLBuiltInException If the specified argument is not a literal
   */
  void checkThatArgumentIsALiteral(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return True if the specified argument is a literal
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean isArgumentALiteral(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return An OWL literal
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  @NonNull OWLLiteral getArgumentAsAnOWLLiteral(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argument A built-in argument
   * @return An OWL literal
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  @NonNull OWLLiteral getArgumentAsAnOWLLiteral(@NonNull SWRLBuiltInArgument argument) throws SWRLBuiltInException;

  /**
   * @param arguments The built-in arguments
   * @return True is all arguments are booleans
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean areAllArgumentsBooleans(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  void checkThatArgumentIsABoolean(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return True if the specified argument is a boolean
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean isArgumentABoolean(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return A boolean argument value
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean getArgumentAsABoolean(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param arguments The built-in arguments
   * @throws SWRLBuiltInException If not all arguments are strings
   */
  void checkThatAllArgumentsAreStrings(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return True if the specified argument is a string
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean isArgumentAString(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return A string representation of an argument
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  @NonNull String getArgumentAsAString(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return A string representation of an argument
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  @NonNull String representArgumentAsAString(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param arguments The built-in arguments
   * @return True if all arguments are strings
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean areAllArgumentsStrings(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @throws SWRLBuiltInException If the specified argument is not a string
   */
  void checkThatArgumentIsAString(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @throws SWRLBuiltInException If the specified argument is not of an ordered type
   */
  void checkThatArgumentIsOfAnOrderedType(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return True if the specified argument is of an ordered type
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean isArgumentOfAnOrderedType(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param arguments The built-in arguments
   * @return True if all arguments are of an ordered type
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean areAllArgumentsOfAnOrderedType(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return True if the specified argument is numeric
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean isArgumentNumeric(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return True if the specified argument if not numeric
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean isArgumentNonNumeric(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param arguments The built-in arguments
   * @throws SWRLBuiltInException If not all arguments are of an ordered type
   */
  void checkThatAllArgumentsAreOfAnOrderedType(@NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @throws SWRLBuiltInException If the specified argument is not numeric
   */
  void checkThatArgumentIsNumeric(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param arguments The built-in arguments
   * @throws SWRLBuiltInException If not all arguments are numeric
   */
  void checkThatAllArgumentsAreNumeric(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param arguments The built-in arguments
   * @return True if all arguments are numeric
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean areAllArgumentsNumeric(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @throws SWRLBuiltInException If the specified argument is numeric
   */
  void checkThatArgumentIsNonNumeric(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param arguments The built-in arguments
   * @return True if all arguments are bytes
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean areAllArgumentsBytes(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param arguments The built-in arguments
   * @return True if a byte is the widest numeric argument
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean isWidestNumericArgumentAByte(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return True if the specified argument is convertible to a byte
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean isArgumentConvertibleToByte(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return True if the specified argument is a byte
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean isArgumentAByte(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return A byte representation of the specified argument
   * @throws SWRLBuiltInException If the argument is not convertible to a byte
   */
  short getArgumentAsAByte(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @throws SWRLBuiltInException If the argument is not a byte
   */
  void checkThatArgumentIsAByte(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param arguments The built-in arguments
   * @return True if all arguments are shorts
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean areAllArgumentsShorts(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param arguments The built-in arguments
   * @return True is a short is the widest numeric argument
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean isWidestNumericArgumentAShort(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return True if the specified argument is convertible to short
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean isArgumentConvertibleToShort(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return True is the specified argument is a short
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean isArgumentAShort(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return A short representation of the specified argument
   * @throws SWRLBuiltInException If the specified argument can not be converted to a short
   */
  short getArgumentAsAShort(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @throws SWRLBuiltInException If the specified argument is not a short
   */
  void checkThatArgumentIsAShort(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param arguments The built-in arguments
   * @return True if an int is the widest numeric argument
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean isWidestNumericArgumentAnInt(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param arguments The built-in arguments
   * @throws SWRLBuiltInException If not all arguments are integers
   */
  void checkThatAllArgumentsAreInts(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param arguments The built-in arguments
   * @return True if all arguments are ints
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean areAllArgumentsInts(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return True if the specified argument is convertible to an int
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean isArgumentConvertibleToInt(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @throws SWRLBuiltInException If the specifed argument is not an int
   */
  void checkThatArgumentIsAnInt(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return True if the specified argument is an int
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean isArgumentAnInt(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return An int representation of the specified argument
   * @throws SWRLBuiltInException If the specified argument cannot be converted to an int
   */
  int getArgumentAsAnInt(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return A positive int representation of the specified argument
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  int getArgumentAsAPositiveInt(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param arguments The built-in arguments
   * @return True is the widest numeric argument is a long
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean isWidestNumericArgumentALong(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param arguments The built-in arguments
   * @return True if all arguments are longs
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean areAllArgumentsLongs(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return True if the specified argument is convertible to a long
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean isArgumentConvertibleToLong(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return True if the argument is a long
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean isArgumentALong(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return A long representation of the specified argument
   * @throws SWRLBuiltInException If the specified argument cannot be converted to a long
   */
  long getArgumentAsALong(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return A positive long representation of the specified argument
   * @throws SWRLBuiltInException If the specified argument cannot be converted to a positive long
   */
  long getArgumentAsAPositiveLong(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @throws SWRLBuiltInException If the specified argument is not a long
   */
  void checkThatArgumentIsALong(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param arguments The built-in arguments
   * @return True if the widest numeric argument is a float
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean isWidestNumericArgumentAFloat(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param arguments The built-in arguments
   * @throws SWRLBuiltInException If not all arguments are floats
   */
  void checkThatAllArgumentsAreFloats(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param arguments The built-in arguments
   * @return True if all arguments are floats
   * @throws SWRLBuiltInException If the specified argument cannot be converted to a long
   */
  boolean areAllArgumentsFloats(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return True if the specified argument is convertible to a float
   * @throws SWRLBuiltInException If the specified argument cannot be converted to a long
   */
  boolean isArgumentConvertibleToFloat(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @throws SWRLBuiltInException If the specified argument is not a float
   */
  void checkThatArgumentIsAFloat(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return True if the specified argument is a float
   * @throws SWRLBuiltInException If the specified argument cannot be converted to a long
   */
  boolean isArgumentAFloat(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return A float representation of the specified argument
   * @throws SWRLBuiltInException If the specified argument cannot be converted to a float
   */
  float getArgumentAsAFloat(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argument The built-in argument
   * @return A float representation of the built-in argument
   * @throws SWRLBuiltInException If the argument cannot be converted to a float
   */
  float getArgumentAsAFloat(@NonNull SWRLBuiltInArgument argument) throws SWRLBuiltInException;

  /**
   * @param arguments The built-in arguments
   * @return True if all arguments are doubles
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean areAllArgumentsDoubles(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param arguments The built-in arguments
   * @return True if all arguments are xsd:decimals
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean areAllArgumentsDecimals(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param arguments The built-in arguments
   * @return True if all arguments are xsd:integers
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean areAllArgumentsIntegers(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return True if the specified argument is convertible to a double
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean isArgumentConvertibleToDouble(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @throws SWRLBuiltInException If the specified argument is not a double
   */
  void checkThatArgumentIsADouble(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return True if the specified argument is a double
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  boolean isArgumentADouble(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return A double representing the specified argument
   * @throws SWRLBuiltInException If the specified argument cannot be converted to a double
   */
  double getArgumentAsADouble(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argument A built-in argument
   * @return A double representation of the built-on argument
   * @throws SWRLBuiltInException If the built-in argument cannot be converted to a double
   */
  double getArgumentAsADouble(SWRLBuiltInArgument argument) throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return A decimal representing the specified argument
   * @throws SWRLBuiltInException If the specified argument cannot be converted to a double
   */
  @NonNull BigDecimal getArgumentAsADecimal(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argument A built-in argument
   * @return A decimal representation of the built-on argument
   * @throws SWRLBuiltInException If the built-in argument cannot be converted to a double
   */
  @NonNull BigDecimal getArgumentAsADecimal(SWRLBuiltInArgument argument) throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return An integer representing the specified argument
   * @throws SWRLBuiltInException If the specified argument cannot be converted to a double
   */
  @NonNull BigInteger getArgumentAsAnInteger(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argument A built-in argument
   * @return An integer representation of the built-on argument
   * @throws SWRLBuiltInException If the built-in argument cannot be converted to a double
   */
  @NonNull BigInteger getArgumentAsAnInteger(SWRLBuiltInArgument argument) throws SWRLBuiltInException;

  void checkThatArgumentIsATime(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  boolean isArgumentATime(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  @NonNull XSDTime getArgumentAsATime(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  void checkThatArgumentIsADate(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  boolean isArgumentADate(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  @NonNull XSDDate getArgumentAsADate(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  void checkThatArgumentIsADateTime(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  boolean isArgumentADateTime(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  @NonNull XSDDateTime getArgumentAsADateTime(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  void checkThatArgumentIsADuration(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  boolean isArgumentADuration(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  @NonNull XSDDuration getArgumentAsADuration(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  // Random methods

  /**
   * @param arguments The built-in arguments
   * @return A clone of the built-in argument list
   */
  @NonNull List<@NonNull SWRLBuiltInArgument> cloneArguments(List<@NonNull SWRLBuiltInArgument> arguments);

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return A property value
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  @NonNull Object getArgumentAsAPropertyValue(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;

  /**
   * @param argumentNumber The 0-based index of the argument
   * @param arguments      The built-in arguments
   * @return The prefixed name of a variable
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  @NonNull String getVariableName(int argumentNumber, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException;
}
