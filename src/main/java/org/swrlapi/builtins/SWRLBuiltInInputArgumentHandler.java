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
	// Unbound argument handling
	boolean hasUnboundArguments(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	void checkThatAllArgumentsAreBound(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	void checkThatArgumentIsBound(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	boolean isUnboundArgument(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	void checkForUnboundArguments(String ruleName, String builtInName, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	/**
	 * Get 0-offset position of first unbound argument; return -1 if no unbound arguments are found.
	 */
	int getFirstUnboundArgument(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	void checkForUnboundArguments(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	void checkForUnboundArguments(List<SWRLBuiltInArgument> arguments, String message) throws SWRLBuiltInException;

	void checkThatArgumentsWereBoundVariables(List<SWRLBuiltInArgument> arguments, String message)
			throws SWRLBuiltInException;

	void checkForUnboundNonFirstArguments(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	// Argument counting
	void checkNumberOfArgumentsEqualTo(int expecting, int actual) throws InvalidSWRLBuiltInArgumentNumberException;

	void checkNumberOfArgumentsAtLeast(int expectingAtLeast, int actual) throws InvalidSWRLBuiltInArgumentNumberException;

	void checkNumberOfArgumentsAtMost(int expectingAtMost, int actual) throws InvalidSWRLBuiltInArgumentNumberException;

	void checkNumberOfArgumentsInRange(int expectingAtLeast, int expectingAtMost, int actual)
			throws InvalidSWRLBuiltInArgumentNumberException;

	void checkArgumentNumber(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	// Named argument handling
	IRI getArgumentAsAnIRI(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	void checkThatArgumentIsAClassPropertyOrIndividual(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	boolean isArgumentAClassPropertyOrIndividual(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	// Class argument handling
	IRI getArgumentAsAClassIRI(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	SWRLClassBuiltInArgument getArgumentAsAClass(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	boolean isArgumentAClass(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	void checkThatArgumentIsAClass(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	// Individual argument handling
	boolean isArgumentAnIndividual(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	void checkThatArgumentIsAnIndividual(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws
			SWRLBuiltInException;

	IRI getArgumentAsAnIndividualIRI(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	SWRLNamedIndividualBuiltInArgument getArgumentAsAnIndividual(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	// Property argument handling
	boolean isArgumentAProperty(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	boolean isArgumentAnObjectProperty(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws
			SWRLBuiltInException;

	boolean isArgumentADataProperty(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	IRI getArgumentAsAPropertyIRI(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	SWRLObjectPropertyBuiltInArgument getArgumentAsAnObjectProperty(int argumentNumber,
			List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	SWRLDataPropertyBuiltInArgument getArgumentAsADataProperty(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	void checkThatArgumentIsAProperty(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws
			SWRLBuiltInException;

	void checkThatArgumentIsAnObjectProperty(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	void checkThatArgumentIsADataProperty(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	// Data value argument handling
	void checkThatAllArgumentsAreLiterals(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	boolean areAllArgumentLiterals(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	void checkThatArgumentIsALiteral(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	boolean isArgumentALiteral(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	OWLLiteral getArgumentAsAnOWLLiteral(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws
			SWRLBuiltInException;

	OWLLiteral getArgumentAsAnOWLLiteral(SWRLBuiltInArgument argument) throws SWRLBuiltInException;

	// Boolean
	boolean areAllArgumentsBooleans(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	void checkThatArgumentIsABoolean(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	boolean isArgumentABoolean(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	boolean getArgumentAsABoolean(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	// Strings
	void checkThatAllArgumentsAreStrings(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	boolean isArgumentAString(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	String getArgumentAsAString(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	boolean areAllArgumentsStrings(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	void checkThatArgumentIsAString(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	// Ordered typed
	void checkThatArgumentIsOfAnOrderedType(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	boolean isArgumentOfAnOrderedType(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws
			SWRLBuiltInException;

	boolean areAllArgumentsOfAnOrderedType(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	// Numeric
	boolean isArgumentNumeric(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	boolean isArgumentNonNumeric(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	void checkThatAllArgumentsAreOfAnOrderedType(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	void checkThatArgumentIsNumeric(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	void checkThatAllArgumentsAreNumeric(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	boolean areAllArgumentsNumeric(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	void checkThatArgumentIsNonNumeric(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws
			SWRLBuiltInException;

	// Shorts
	boolean areAllArgumentsShorts(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	boolean isShortMostPreciseArgument(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	boolean isArgumentConvertableToShort(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws
			SWRLBuiltInException;

	boolean isArgumentAShort(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	short getArgumentAsAShort(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	void checkThatArgumentIsALong(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	// Integers
	boolean isIntegerMostPreciseArgument(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	void checkThatAllArgumentsAreIntegers(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	boolean areAllArgumentsIntegers(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	boolean isArgumentConvertableToInteger(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	void checkThatArgumentIsAnInteger(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws
			SWRLBuiltInException;

	boolean isArgumentAnInteger(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	int getArgumentAsAnInteger(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	int getArgumentAsAPositiveInteger(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws
			SWRLBuiltInException;

	// Longs
	boolean isLongMostPreciseArgument(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	boolean areAllArgumentsLongs(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	boolean isArgumentConvertableToLong(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws
			SWRLBuiltInException;

	boolean isArgumentALong(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	long getArgumentAsALong(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	long getArgumentAsAPositiveLong(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	// Floats
	boolean isFloatMostPreciseArgument(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	void checkThatAllArgumentsAreFloats(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	boolean areAllArgumentsFloats(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	boolean isArgumentConvertableToFloat(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws
			SWRLBuiltInException;

	void checkThatArgumentIsAFloat(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	boolean isArgumentAFloat(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	float getArgumentAsAFloat(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	float getArgumentAsAFloat(SWRLBuiltInArgument argument) throws SWRLBuiltInException;

	// Doubles
	boolean areAllArgumentsDoubles(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	boolean isArgumentConvertableToDouble(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	void checkThatArgumentIsADouble(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	boolean isArgumentADouble(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	double getArgumentAsADouble(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	double getArgumentAsADouble(SWRLBuiltInArgument argument) throws SWRLBuiltInException;

	// Random methods

	List<SWRLBuiltInArgument> cloneArguments(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	Object getArgumentAsAPropertyValue(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws
			SWRLBuiltInException;

	// Variable name handling
	String getVariablePrefixedName(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;
}
