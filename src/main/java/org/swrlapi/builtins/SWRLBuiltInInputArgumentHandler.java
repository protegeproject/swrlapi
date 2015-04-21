package org.swrlapi.builtins;

import java.util.List;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.exceptions.InvalidSWRLBuiltInArgumentNumberException;
import org.swrlapi.exceptions.SWRLBuiltInException;

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
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	// Unbound argument handling
	boolean hasUnboundArguments(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param arguments The built-in arguments
	 * @throws SWRLBuiltInException If not all arguments are bound
	 */
	void checkThatAllArgumentsAreBound(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @throws SWRLBuiltInException If the argument is not bound
	 */
	void checkThatArgumentIsBound(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return True if the specified argument is unbound
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean isUnboundArgument(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param ruleName The name of the rule
	 * @param builtInName The name of the built-in
	 * @param arguments The built-in arguments
	 * @throws SWRLBuiltInException If unbound arguments exist
	 */
	void checkForUnboundArguments(String ruleName, String builtInName, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	/**
	 * Get 0-offset position of first unbound argument; return -1 if no unbound arguments are found.
	 * 
	 * @param arguments The built-in arguments
	 * @return The first unbound argument
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	int getFirstUnboundArgument(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param arguments The built-in arguments
	 * @throws SWRLBuiltInException If at least one argument is unbound
	 */
	void checkForUnboundArguments(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param arguments The built-in arguments
	 * @param message The exception message used if unbound arguments are found
	 * @throws SWRLBuiltInException If at least one argument is unbound
	 */
	void checkForUnboundArguments(List<SWRLBuiltInArgument> arguments, String message) throws SWRLBuiltInException;

	/**
	 * @param arguments The built-in arguments
	 * @param message The exception message used if non bound variables are found
	 * @throws SWRLBuiltInException If not all argument are bound variables
	 */
	void checkThatArgumentsWereBoundVariables(List<SWRLBuiltInArgument> arguments, String message)
			throws SWRLBuiltInException;

	/**
	 * @param arguments The built-in arguments
	 * @throws SWRLBuiltInException If the check condition is met
	 */
	void checkForUnboundNonFirstArguments(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param expecting Number of expected arguments
	 * @param actual Actual number of arguments
	 * @throws InvalidSWRLBuiltInArgumentNumberException If the expect and actual number are not equal
	 */
	void checkNumberOfArgumentsEqualTo(int expecting, int actual) throws InvalidSWRLBuiltInArgumentNumberException;

	/**
	 * @param expectingAtLeast Minimum number of expected arguments
	 * @param actual Actual number of arguments
	 * @throws InvalidSWRLBuiltInArgumentNumberException If the condition is not met
	 */
	void checkNumberOfArgumentsAtLeast(int expectingAtLeast, int actual) throws InvalidSWRLBuiltInArgumentNumberException;

	/**
	 * @param expectingAtMost Maximum number of expected arguments
	 * @param actual Actual number of arguments
	 * @throws InvalidSWRLBuiltInArgumentNumberException If the condition is not met
	 */
	void checkNumberOfArgumentsAtMost(int expectingAtMost, int actual) throws InvalidSWRLBuiltInArgumentNumberException;

	/**
	 * @param expectingAtLeast Minimum number of expected arguments
	 * @param expectingAtMost Maximum number of expected arguments
	 * @param actual Actual number of arguments
	 * @throws InvalidSWRLBuiltInArgumentNumberException If the condition is not met
	 */
	void checkNumberOfArgumentsInRange(int expectingAtLeast, int expectingAtMost, int actual)
			throws InvalidSWRLBuiltInArgumentNumberException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @throws SWRLBuiltInException If the argument number is invalid
	 */
	void checkArgumentNumber(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return An IRI representation of the specified argument
	 * @throws SWRLBuiltInException If the specified argument cannot be converted to an IRI
	 */
	IRI getArgumentAsAnIRI(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @throws SWRLBuiltInException If the argument does not meet the check
	 */
	void checkThatArgumentIsAClassPropertyOrIndividual(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return True is the argument is a class, property or individual
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean isArgumentAClassPropertyOrIndividual(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return The IRI of the specified class argument
	 * @throws SWRLBuiltInException If an error occurs during IRI generation
	 */
	IRI getArgumentAsAClassIRI(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return A class built-in argument
	 * @throws SWRLBuiltInException If an error occurs during argument generation
	 */
	SWRLClassBuiltInArgument getArgumentAsAClass(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return True if the specified argument is a class
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean isArgumentAClass(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @throws SWRLBuiltInException If the specified argument is not a class
	 */
	void checkThatArgumentIsAClass(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return True if the specified argument is an individual
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean isArgumentAnIndividual(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @throws SWRLBuiltInException If the specified argument is not an individual
	 */
	void checkThatArgumentIsAnIndividual(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return The IRI of the OWL individual argument
	 * @throws SWRLBuiltInException If the argument cannot be represented as an IRI
	 */
	IRI getArgumentAsAnIndividualIRI(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return A named individual built-in argument
	 * @throws SWRLBuiltInException If the argument cannot be represented as an individual built-in argument
	 */
	SWRLNamedIndividualBuiltInArgument getArgumentAsAnIndividual(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return True if the specified argument is a property
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean isArgumentAProperty(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return True if the specified argument is an object property
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean isArgumentAnObjectProperty(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return True if the specified argument is a data property
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean isArgumentADataProperty(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return The IRI of the specified property
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	IRI getArgumentAsAPropertyIRI(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return An object property built-in argument
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	SWRLObjectPropertyBuiltInArgument getArgumentAsAnObjectProperty(int argumentNumber,
			List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return A data property built-in argument
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	SWRLDataPropertyBuiltInArgument getArgumentAsADataProperty(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @throws SWRLBuiltInException If the specified argument is not a property
	 */
	void checkThatArgumentIsAProperty(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @throws SWRLBuiltInException If the specified argument is not an object property
	 */
	void checkThatArgumentIsAnObjectProperty(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @throws SWRLBuiltInException If the specified argument is not a data property
	 */
	void checkThatArgumentIsADataProperty(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	/**
	 * @param arguments The built-in arguments
	 * @throws SWRLBuiltInException If not all arguments are literals
	 */
	void checkThatAllArgumentsAreLiterals(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param arguments The built-in arguments
	 * @return True if all arguments are literals
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean areAllArgumentLiterals(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @throws SWRLBuiltInException If the specified argument is not a literal
	 */
	void checkThatArgumentIsALiteral(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return True if the specified argument is a literal
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean isArgumentALiteral(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return An OWL literal
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	OWLLiteral getArgumentAsAnOWLLiteral(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	/**
	 * @param argument A built-in argument
	 * @return An OWL literal
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	OWLLiteral getArgumentAsAnOWLLiteral(SWRLBuiltInArgument argument) throws SWRLBuiltInException;

	/**
	 * @param arguments The built-in arguments
	 * @return True is all arguments are booleans
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean areAllArgumentsBooleans(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	void checkThatArgumentIsABoolean(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return True if the specified argument is a boolean
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean isArgumentABoolean(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return A boolean argument value
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean getArgumentAsABoolean(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param arguments The built-in arguments
	 * @throws SWRLBuiltInException If not all arguments are strings
	 */
	void checkThatAllArgumentsAreStrings(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return True if the specified argument is a string
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean isArgumentAString(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return A string representation of an argument
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	String getArgumentAsAString(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param arguments The built-in arguments
	 * @return True if all arguments are strings
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean areAllArgumentsStrings(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @throws SWRLBuiltInException If the specified argument is not a string
	 */
	void checkThatArgumentIsAString(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @throws SWRLBuiltInException If the specified argument is not of an ordered type
	 */
	void checkThatArgumentIsOfAnOrderedType(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return True if the specified argument is of an ordered type
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean isArgumentOfAnOrderedType(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	/**
	 * @param arguments The built-in arguments
	 * @return True if all arguments are of an ordered type
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean areAllArgumentsOfAnOrderedType(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return True if the specified argument is numeric
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean isArgumentNumeric(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return True if the specified argument if not numeric
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean isArgumentNonNumeric(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param arguments The built-in arguments
	 * @throws SWRLBuiltInException If not all arguments are of an ordered type
	 */
	void checkThatAllArgumentsAreOfAnOrderedType(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @throws SWRLBuiltInException If the specified argument is not numeric
	 */
	void checkThatArgumentIsNumeric(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param arguments The built-in arguments
	 * @throws SWRLBuiltInException If not all arguments are numeric
	 */
	void checkThatAllArgumentsAreNumeric(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param arguments The built-in arguments
	 * @return True if all arguments are numeric
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean areAllArgumentsNumeric(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @throws SWRLBuiltInException If the specified argument is numeric
	 */
	void checkThatArgumentIsNonNumeric(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	/**
	 * @param arguments The built-in arguments
	 * @return True if all arguments are bytes
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean areAllArgumentsBytes(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param arguments The built-in arguments
	 * @return True if a byte is the widest numeric argument
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean isWidestNumericArgumentAByte(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return True if the specified argument is convertable to a byte
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean isArgumentConvertableToByte(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return True if the specified argument is a byte
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean isArgumentAByte(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return A byte representation of the specified argument
	 * @throws SWRLBuiltInException If the argument is not convertable to a byte
	 */
	short getArgumentAsAByte(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @throws SWRLBuiltInException If the argument is not a byte
	 */
	void checkThatArgumentIsAByte(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param arguments The built-in arguments
	 * @return True if all arguments are shorts
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean areAllArgumentsShorts(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param arguments The built-in arguments
	 * @return True is a short is the widest numeric argument
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean isWidestNumericArgumentAShort(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return True if the specified argument is convertable to short
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean isArgumentConvertableToShort(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return True is the specified argument is a short
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean isArgumentAShort(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return A short representation of the specified argument
	 * @throws SWRLBuiltInException If the specified argument can not be converted to a short
	 */
	short getArgumentAsAShort(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @throws SWRLBuiltInException If the specified argument is not a short
	 */
	void checkThatArgumentIsAShort(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param arguments The built-in arguments
	 * @return True if an int is the widest numeric argument
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean isWidestNumericArgumentAnInt(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param arguments The built-in arguments
	 * @throws SWRLBuiltInException If not all arguments are integers
	 */
	void checkThatAllArgumentsAreIntegers(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param arguments The built-in arguments
	 * @return True if all arguments are integers
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean areAllArgumentsIntegers(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return True if the specified argument is convertable to an int
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean isArgumentConvertableToInteger(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @throws SWRLBuiltInException If the specifed argument is not an int
	 */
	void checkThatArgumentIsAnInt(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return True if the specified argument is an int
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean isArgumentAnInt(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return An int representation of the specified argument
	 * @throws SWRLBuiltInException If the specified argument cannot be converted to an int
	 */
	int getArgumentAsAnInt(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return A positive int representation of the specified argument
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	int getArgumentAsAPositiveInt(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param arguments The built-in arguments
	 * @return True is the widest numeric argument is a long
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean isWidestNumericArgumentALong(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param arguments The built-in arguments
	 * @return True if all arguments are longs
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean areAllArgumentsLongs(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return True if the specified argument is convertable to a long
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean isArgumentConvertableToLong(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return True if the argument is a long
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean isArgumentALong(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return A long representation of the specified argument
	 * @throws SWRLBuiltInException If the specified argument cannot be converted to a long
	 */
	long getArgumentAsALong(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return A positive long representation of the specified argument
	 * @throws SWRLBuiltInException If the specified argument cannot be converted to a positive long
	 */
	long getArgumentAsAPositiveLong(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @throws SWRLBuiltInException If the specified argument is not a long
	 */
	void checkThatArgumentIsALong(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param arguments The built-in arguments
	 * @return True if the widest numeric argument is a float
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean isWidestNumericArgumentAFloat(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param arguments The built-in arguments
	 * @throws SWRLBuiltInException If not all arguments are floats
	 */
	void checkThatAllArgumentsAreFloats(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param arguments The built-in arguments
	 * @return True if all arguments are floats
	 * @throws SWRLBuiltInException If the specified argument cannot be converted to a long
	 */
	boolean areAllArgumentsFloats(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return True if the specified argument is convertable to a float
	 * @throws SWRLBuiltInException If the specified argument cannot be converted to a long
	 */
	boolean isArgumentConvertableToFloat(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @throws SWRLBuiltInException If the specified argument is not a float
	 */
	void checkThatArgumentIsAFloat(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return True if the specified argument is a float
	 * @throws SWRLBuiltInException If the specified argument cannot be converted to a long
	 */
	boolean isArgumentAFloat(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return A float representation of the specified argument
	 * @throws SWRLBuiltInException If the specified argument cannot be converted to a float
	 */
	float getArgumentAsAFloat(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argument The built-in argument
	 * @return A float representation of the built-in argument
	 * @throws SWRLBuiltInException If the argument cannot be converted to a float
	 */
	float getArgumentAsAFloat(SWRLBuiltInArgument argument) throws SWRLBuiltInException;

	/**
	 * @param arguments The built-in arguments
	 * @return True if all arguments are doubles
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean areAllArgumentsDoubles(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return True if the specified argument is convertable to a double
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean isArgumentConvertableToDouble(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @throws SWRLBuiltInException If the specified argument is not a double
	 */
	void checkThatArgumentIsADouble(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return True if the specified argument is a double
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean isArgumentADouble(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return A double representing the specified argument
	 * @throws SWRLBuiltInException If the specified argument cannot be converted to a double
	 */
	double getArgumentAsADouble(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argument A built-in argument
	 * @return A double representation of the built-on argument
	 * @throws SWRLBuiltInException If the built-in argument cannot be converted to a double
	 */
	double getArgumentAsADouble(SWRLBuiltInArgument argument) throws SWRLBuiltInException;

	// Random methods

	/**
	 * @param arguments The built-in arguments
	 * @return A clone of the built-in argument list
	 * @throws SWRLBuiltInException If an error occurs during cloning
	 */
	List<SWRLBuiltInArgument> cloneArguments(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return A property value
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	Object getArgumentAsAPropertyValue(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	/**
	 * @param argumentNumber The 0-based index of the argument
	 * @param arguments The built-in arguments
	 * @return The prefixed name of a variable
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	String getVariablePrefixedName(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;
}
