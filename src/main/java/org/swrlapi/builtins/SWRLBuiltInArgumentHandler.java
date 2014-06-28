package org.swrlapi.builtins;

import java.util.List;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.exceptions.BuiltInException;
import org.swrlapi.exceptions.InvalidBuiltInArgumentNumberException;

public interface SWRLBuiltInArgumentHandler
{
	// Unbound argument handling
	boolean hasUnboundArguments(List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	void checkThatAllArgumentsAreBound(List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	void checkThatArgumentIsBound(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	boolean isUnboundArgument(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	void checkForUnboundArguments(String ruleName, String builtInName, List<SWRLBuiltInArgument> arguments)
			throws BuiltInException;

	/**
	 * Get 0-offset position of first unbound argument; return -1 if no unbound arguments are found.
	 */
	int getFirstUnboundArgument(List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	void checkForUnboundArguments(List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	void checkForUnboundArguments(List<SWRLBuiltInArgument> arguments, String message) throws BuiltInException;

	void checkThatArgumentsWereBoundVariables(List<SWRLBuiltInArgument> arguments, String message)
			throws BuiltInException;

	void checkForUnboundNonFirstArguments(List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	// Argument counting
	void checkNumberOfArgumentsEqualTo(int expecting, int actual) throws InvalidBuiltInArgumentNumberException;

	void checkNumberOfArgumentsAtLeast(int expectingAtLeast, int actual) throws InvalidBuiltInArgumentNumberException;

	void checkNumberOfArgumentsAtMost(int expectingAtMost, int actual) throws InvalidBuiltInArgumentNumberException;

	void checkNumberOfArgumentsInRange(int expectingAtLeast, int expectingAtMost, int actual)
			throws InvalidBuiltInArgumentNumberException;

	void checkArgumentNumber(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	// Named argument handling
	IRI getArgumentAsAnIRI(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	void checkThatArgumentIsAClassPropertyOrIndividual(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws BuiltInException;

	boolean isArgumentAClassPropertyOrIndividual(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws BuiltInException;

	// Class argument handling
	IRI getArgumentAsAClassIRI(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	SWRLClassBuiltInArgument getArgumentAsAClass(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws BuiltInException;

	boolean isArgumentAClass(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	void checkThatArgumentIsAClass(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	// Individual argument handling
	boolean isArgumentAnIndividual(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	void checkThatArgumentIsAnIndividual(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	IRI getArgumentAsAnIndividualIRI(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	SWRLNamedIndividualBuiltInArgument getArgumentAsAnIndividual(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws BuiltInException;

	// Property argument handling
	boolean isArgumentAProperty(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	boolean isArgumentAnObjectProperty(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	boolean isArgumentADataProperty(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	IRI getArgumentAsAPropertyIRI(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	SWRLObjectPropertyBuiltInArgument getArgumentAsAnObjectProperty(int argumentNumber,
			List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	SWRLDataPropertyBuiltInArgument getArgumentAsADataProperty(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws BuiltInException;

	void checkThatArgumentIsAProperty(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	void checkThatArgumentIsAnObjectProperty(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws BuiltInException;

	void checkThatArgumentIsADataProperty(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws BuiltInException;

	// Data value argument handling
	void checkThatAllArgumentsAreLiterals(List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	boolean areAllArgumentLiterals(List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	void checkThatArgumentIsALiteral(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	boolean isArgumentALiteral(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	OWLLiteral getArgumentAsAnOWLLiteral(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	OWLLiteral getArgumentAsAnOWLLiteral(SWRLBuiltInArgument argument) throws BuiltInException;

	// Boolean
	boolean areAllArgumentsBooleans(List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	void checkThatArgumentIsABoolean(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	boolean isArgumentABoolean(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	boolean getArgumentAsABoolean(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	// Strings
	void checkThatAllArgumentsAreStrings(List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	boolean isArgumentAString(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	String getArgumentAsAString(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	boolean areAllArgumentsStrings(List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	void checkThatArgumentIsAString(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	// Ordered typed
	void checkThatArgumentIsOfAnOrderedType(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws BuiltInException;

	boolean isArgumentOfAnOrderedType(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	boolean areAllArgumentsOfAnOrderedType(List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	// Numeric
	boolean isArgumentNumeric(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	boolean isArgumentNonNumeric(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	void checkThatAllArgumentsAreOfAnOrderedType(List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	void checkThatArgumentIsNumeric(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	void checkThatAllArgumentsAreNumeric(List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	boolean areAllArgumentsNumeric(List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	void checkThatArgumentIsNonNumeric(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	// Shorts
	boolean areAllArgumentsShorts(List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	boolean isShortMostPreciseArgument(List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	boolean isArgumentConvertableToShort(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	boolean isArgumentAShort(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	short getArgumentAsAShort(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	void checkThatArgumentIsALong(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	// Integers
	boolean isIntegerMostPreciseArgument(List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	void checkThatAllArgumentsAreIntegers(List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	boolean areAllArgumentsIntegers(List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	boolean isArgumentConvertableToInteger(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws BuiltInException;

	void checkThatArgumentIsAnInteger(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	boolean isArgumentAnInteger(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	int getArgumentAsAnInteger(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	int getArgumentAsAPositiveInteger(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	// Longs
	boolean isLongMostPreciseArgument(List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	boolean areAllArgumentsLongs(List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	boolean isArgumentConvertableToLong(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	boolean isArgumentALong(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	long getArgumentAsALong(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	long getArgumentAsAPositiveLong(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	// Floats
	boolean isFloatMostPreciseArgument(List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	void checkThatAllArgumentsAreFloats(List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	boolean areAllArgumentsFloats(List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	boolean isArgumentConvertableToFloat(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	void checkThatArgumentIsAFloat(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	boolean isArgumentAFloat(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	float getArgumentAsAFloat(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	float getArgumentAsAFloat(SWRLBuiltInArgument argument) throws BuiltInException;

	// Doubles
	boolean areAllArgumentsDoubles(List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	boolean isArgumentConvertableToDouble(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws BuiltInException;

	void checkThatArgumentIsADouble(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	boolean isArgumentADouble(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	double getArgumentAsADouble(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	double getArgumentAsADouble(SWRLBuiltInArgument argument) throws BuiltInException;

	// Random methods

	List<SWRLBuiltInArgument> cloneArguments(List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	Object getArgumentAsAPropertyValue(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	// Variable name handling
	String getVariablePrefixedName(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;
}
