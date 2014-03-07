package org.protege.swrlapi.builtins;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.Collection;
import java.util.List;

import org.protege.swrlapi.core.SWRLBuiltInBridge;
import org.protege.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLClassBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLIndividualBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLLiteralBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLMultiArgument;
import org.protege.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;
import org.protege.swrlapi.exceptions.BuiltInException;
import org.protege.swrlapi.exceptions.InvalidBuiltInArgumentNumberException;
import org.protege.swrlapi.exceptions.SWRLBuiltInLibraryException;
import org.protege.swrlapi.ext.SWRLAPILiteral;
import org.protege.swrlapi.sqwrl.values.SQWRLResultValueFactory;
import org.protege.swrlapi.xsd.XSDDate;
import org.protege.swrlapi.xsd.XSDDateTime;
import org.protege.swrlapi.xsd.XSDDuration;
import org.protege.swrlapi.xsd.XSDTime;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;

/**
 * A class that defines methods that must be implemented by a built-in library. See <a
 * href="http://protege.cim3.net/cgi-bin/wiki.pl?SWRLBuiltInBridge">here</a> for documentation.
 * <p>
 * Also includes an array of methods for processing built-in arguments.
 * <p>
 * The class AbstractSWRLBuiltInLibrary provides an implementation of this interface.
 */
public interface SWRLBuiltInLibrary extends SWRLBuiltInContext
{
	String getLibraryName();

	// Reset library, discarding any internal state if any (e.g., caches).
	void reset() throws BuiltInException;

	// Method to invoke a built-in in the library. Invoked by {@link SWRLBuiltInLibraryManager}.
	boolean invokeBuiltInMethod(Method method, SWRLBuiltInBridge bridge, String ruleName, String prefix,
			String builtInMethodName, int builtInIndex, boolean isInConsequent, List<SWRLBuiltInArgument> arguments)
			throws BuiltInException;

	// Variable name handling
	String getVariableName(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

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

	void checkForNonVariableArguments(List<SWRLBuiltInArgument> arguments, String message) throws BuiltInException;

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

	SWRLIndividualBuiltInArgument getArgumentAsAnIndividual(int argumentNumber, List<SWRLBuiltInArgument> arguments)
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

	SWRLAPILiteral getArgumentAsASWRLAPILiteral(int argumentNumber, List<SWRLBuiltInArgument> arguments)
			throws BuiltInException;

	SWRLAPILiteral getArgumentAsASWRLAPILiteral(SWRLBuiltInArgument argument) throws BuiltInException;

	// IRI

	IRI createIRI(String fullName) throws BuiltInException;

	// Primitive type argument handling

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

	/**
	 * Create a string that represents a unique invocation pattern for a built-in for a bridge/rule/built-in/arguments
	 * combination.
	 */
	String createInvocationPattern(SWRLBuiltInBridge invokingBridge, String invokingRuleName, int invokingBuiltInIndex,
			boolean isInConsequent, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	List<SWRLBuiltInArgument> cloneArguments(List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	Object getArgumentAsAPropertyValue(int argumentNumber, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	// Result argument handling
	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			Collection<SWRLBuiltInArgument> resultArguments) throws BuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			SWRLBuiltInArgument resultArgument) throws BuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			SWRLLiteralBuiltInArgument resultArgument) throws BuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber, short resultArgument)
			throws BuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber, int resultArgument)
			throws BuiltInException;

	public boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			OWLLiteral resultArgument) throws BuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber, long resultArgument)
			throws BuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber, float resultArgument)
			throws BuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber, double resultArgument)
			throws BuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber, byte resultArgument)
			throws BuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber, String resultArgument)
			throws BuiltInException;

	// Argument creation handling
	SWRLClassBuiltInArgument createClassBuiltInArgument(IRI classIRI) throws BuiltInException;

	SWRLIndividualBuiltInArgument createIndividualBuiltInArgument(IRI individualIRI) throws BuiltInException;

	SWRLObjectPropertyBuiltInArgument createObjectPropertyBuiltInArgument(IRI propertyIRI) throws BuiltInException;

	SWRLDataPropertyBuiltInArgument createDataPropertyArgument(IRI propertyIRI) throws BuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(String s) throws BuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(boolean b) throws BuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(Byte b) throws BuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(short s) throws BuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(int i) throws BuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(long l) throws BuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(float f) throws BuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(double d) throws BuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(URI uri) throws BuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(XSDDate date) throws BuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(XSDTime time) throws BuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(XSDDateTime dateTime) throws BuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(XSDDuration duration) throws BuiltInException;

	SWRLAPILiteral createSWRLAPILiteral(String s) throws BuiltInException;

	SWRLAPILiteral createSWRLAPILiteral(boolean b) throws BuiltInException;

	SWRLAPILiteral createSWRLAPILiteral(Byte b) throws BuiltInException;

	SWRLAPILiteral createSWRLAPILiteral(short s) throws BuiltInException;

	SWRLAPILiteral createSWRLAPILiteral(int i) throws BuiltInException;

	SWRLAPILiteral createSWRLAPILiteral(long l) throws BuiltInException;

	SWRLAPILiteral createSWRLAPILiteral(float f) throws BuiltInException;

	SWRLAPILiteral createSWRLAPILiteral(double d) throws BuiltInException;

	SWRLAPILiteral createSWRLAPILiteral(URI uri) throws BuiltInException;

	SWRLAPILiteral createSWRLAPILiteral(XSDDate date) throws BuiltInException;

	SWRLAPILiteral createSWRLAPILiteral(XSDTime time) throws BuiltInException;

	SWRLAPILiteral createSWRLAPILiteral(XSDDateTime dateTime) throws BuiltInException;

	SWRLAPILiteral createSWRLAPILiteral(XSDDuration duration) throws BuiltInException;

	SWRLMultiArgument createMultiArgument() throws BuiltInException;

	SWRLMultiArgument createMultiArgument(List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	SQWRLResultValueFactory getSQWRLResultValueFactory() throws SWRLBuiltInLibraryException;
}
