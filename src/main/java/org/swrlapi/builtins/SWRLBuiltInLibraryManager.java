package org.swrlapi.builtins;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument;
import org.swrlapi.exceptions.BuiltInException;
import org.swrlapi.exceptions.IncompatibleBuiltInClassException;
import org.swrlapi.exceptions.IncompatibleBuiltInMethodException;
import org.swrlapi.exceptions.SWRLBuiltInLibraryException;
import org.swrlapi.exceptions.UnresolvedBuiltInClassException;
import org.swrlapi.exceptions.UnresolvedBuiltInMethodException;

/**
 * This class manages the dynamic loading of SWRL built-in libraries and the invocation of built-ins in those libraries.
 * A library is identified by a prefix and this prefix is used to find and dynamically load a Java class implementing
 * the built-ins in this library. For example, the <a
 * href="http://protege.cim3.net/cgi-bin/wiki.pl?CoreSWRLBuiltIns">core SWRL built-in library</a> is identified by the
 * prefix swrlb; built-ins in this library can then be referred to in SWRL rules using this prefix followed by the
 * built-in name, e.g., swrlb:lessThanOrEqual.
 * <p>
 * See <a href="http://protege.cim3.net/cgi-bin/wiki.pl?SWRLBuiltInBridge">here</a> for documentation on defining these
 * built-in libraries.
 */
public abstract class SWRLBuiltInLibraryManager
{
	private static final String BuiltInLibraryPackageBaseName = "org.swrlapi.builtins.";

	// Holds instances of implementation classes defining built-in libraries
	private static final Map<String, SWRLBuiltInLibrary> builtInLibraries;
	private static final Map<String, Method> methods;

	static {
		builtInLibraries = new HashMap<String, SWRLBuiltInLibrary>();
		methods = new HashMap<String, Method>();
	}

	/**
	 * Invoke a SWRL built-in. This method is called from the invokeSWRLBuiltIn method in the
	 * {@link org.swrlapi.bridge.SWRLRuleEngineBridge} and should not be called directly from a rule engine. The built-in
	 * name should be the prefixed name of the built-in (e.g., swrlb:lessThanOrEqual).
	 * <p>
	 * For built-ins that evaluate to true, this method will return a list of argument patterns, one pattern for each
	 * combination of arguments that evaluates to true.
	 * <p>
	 * If the built-in evaluates to false, it will return an empty argument pattern list.
	 */
	public static List<List<SWRLBuiltInArgument>> invokeSWRLBuiltIn(SWRLBuiltInBridge bridge, String ruleName,
			String builtInName, int builtInIndex, boolean isInConsequent, List<SWRLBuiltInArgument> arguments)
			throws BuiltInException
	{
		String prefix = getPrefix(builtInName);
		String implementationClassName = getBuiltInLibraryImplementationClassName(prefix);
		String builtInMethodName = getBuiltInMethodName(builtInName);
		SWRLBuiltInLibrary library = loadBuiltInLibrary(bridge, ruleName, prefix, implementationClassName);
		Method method = resolveBuiltInMethod(ruleName, library, prefix, builtInMethodName);
		List<List<SWRLBuiltInArgument>> argumentPatterns = new ArrayList<List<SWRLBuiltInArgument>>();

		if (library.invokeBuiltInMethod(method, bridge, ruleName, prefix, builtInMethodName, builtInIndex, isInConsequent,
				arguments)) {

			if (hasUnboundArguments(arguments)) // Make sure the built-in has bound all of its arguments.
				throw new BuiltInException("built-in " + builtInName + "(index " + builtInIndex + ") in rule " + ruleName
						+ " returned with unbound arguments");

			processBoundArguments(arguments);

			for (List<SWRLBuiltInArgument> argumentPattern : generateBuiltInArgumentPattern(ruleName, builtInName,
					builtInIndex, arguments))
				argumentPatterns.add(argumentPattern);
		}

		return argumentPatterns;
	}

	private static void processBoundArguments(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		for (int argumentIndex = 0; argumentIndex < arguments.size(); argumentIndex++) {
			SWRLBuiltInArgument argument = arguments.get(argumentIndex);
			if (argument.isVariable() && argument.asVariable().hasBuiltInResult())
				arguments.set(argumentIndex, argument.asVariable().getBuiltInResult());
		}
	}

	private static SWRLBuiltInLibrary loadBuiltInLibrary(SWRLBuiltInBridge bridge, String ruleName, String prefix,
			String implementationClassName) throws SWRLBuiltInLibraryException
	{
		SWRLBuiltInLibrary library;

		if (builtInLibraries.containsKey(prefix)) { // Find the cached implementation.
			library = builtInLibraries.get(prefix);
		} else { // Implementation class not loaded - load it, cache it, and call its reset method.
			library = loadBuiltInLibraryImpl(ruleName, prefix, implementationClassName);
			builtInLibraries.put(prefix, library);
			invokeBuiltInLibraryResetMethod(bridge, library);
		}
		return library;
	}

	private static String getPrefix(String builtInName)
	{
		int hashIndex = builtInName.indexOf(':');

		if (hashIndex != -1) {
			return builtInName.substring(0, hashIndex);
		} else
			return ""; // No prefix - try the base built-ins package. Ordinarily, built-ins should not be located here.
	}

	private static String getBuiltInLibraryImplementationClassName(String prefix)
	{
		if (prefix.length() == 0)
			return BuiltInLibraryPackageBaseName + "SWRLBuiltInLibraryImpl";
		else {
			return BuiltInLibraryPackageBaseName + prefix + ".SWRLBuiltInLibraryImpl";
		}
	}

	private static String getBuiltInMethodName(String builtInName)
	{
		if (builtInName.indexOf(":") == -1)
			return builtInName;
		else
			return builtInName.substring(builtInName.indexOf(":") + 1, builtInName.length());
	}

	/**
	 * Invoke the reset() method for each registered built-in library.
	 */
	private static void invokeBuiltInLibraryResetMethod(SWRLBuiltInBridge bridge, SWRLBuiltInLibrary library)
			throws SWRLBuiltInLibraryException
	{
		try {
			library.invokeResetMethod(bridge);
		} catch (Exception e) {
			throw new SWRLBuiltInLibraryException("error calling reset method in built-in library " + library.getClass());
		}
	}

	public static void invokeAllBuiltInLibrariesResetMethod(SWRLBuiltInBridge bridge) throws SWRLBuiltInLibraryException
	{
		for (SWRLBuiltInLibrary library : builtInLibraries.values())
			invokeBuiltInLibraryResetMethod(bridge, library);
	}

	/**
	 * This method is called with a list of built-in arguments. Some argument positions may contain multi-arguments,
	 * indicating that there is more than one pattern. If the result has more than one multi-argument, each multi-argument
	 * must have the same number of elements.
	 */
	private static Set<List<SWRLBuiltInArgument>> generateBuiltInArgumentPattern(String ruleName, String builtInName,
			int builtInIndex, List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		List<Integer> multiValueBuiltInArgumentIndexes = getMultiValueBuiltInArgumentIndexes(arguments);
		Set<List<SWRLBuiltInArgument>> pattern = new HashSet<List<SWRLBuiltInArgument>>();

		if (multiValueBuiltInArgumentIndexes.isEmpty()) // No multi-arguments - generate a single pattern
			pattern.add(arguments);
		else { // Generate all possible patterns
			int firstMultiValueBuiltInArgumentIndex = multiValueBuiltInArgumentIndexes.get(0); // Pick the first
																																													// multi-argument.
			SWRLMultiValueVariableBuiltInArgument multiValueBuiltInArgument = getArgumentAsASWRLMultiValueBuiltInArgument(
					arguments, firstMultiValueBuiltInArgumentIndex);
			int numberOfArgumentsInMultiValueBuiltInArgument = multiValueBuiltInArgument.getNumberOfArguments();

			if (numberOfArgumentsInMultiValueBuiltInArgument < 1)
				throw new BuiltInException("empty multi-value argument for built-in " + builtInName + "(index " + builtInIndex
						+ ") in rule " + ruleName);

			for (int i = 1; i < multiValueBuiltInArgumentIndexes.size(); i++) {
				int multiValueBuiltInArgumentIndex = multiValueBuiltInArgumentIndexes.get(i);
				multiValueBuiltInArgument = getArgumentAsASWRLMultiValueBuiltInArgument(arguments,
						multiValueBuiltInArgumentIndex);
				if (numberOfArgumentsInMultiValueBuiltInArgument != multiValueBuiltInArgument.getNumberOfArguments())
					throw new BuiltInException("all multi-value arguments must have the same number of elements for built-in "
							+ builtInName + "(index " + builtInIndex + ") in rule " + ruleName);
			}

			for (int multiValueBuiltInArgumentArgumentIndex = 0; multiValueBuiltInArgumentArgumentIndex < numberOfArgumentsInMultiValueBuiltInArgument; multiValueBuiltInArgumentArgumentIndex++) {
				List<SWRLBuiltInArgument> argumentsPattern = generateArgumentsPattern(arguments,
						multiValueBuiltInArgumentArgumentIndex);
				pattern.add(argumentsPattern);
			}
		}
		return pattern;
	}

	private static SWRLMultiValueVariableBuiltInArgument getArgumentAsASWRLMultiValueBuiltInArgument(
			List<SWRLBuiltInArgument> arguments, int argumentIndex) throws BuiltInException
	{
		if (arguments.get(argumentIndex) instanceof SWRLMultiValueVariableBuiltInArgument)
			return (SWRLMultiValueVariableBuiltInArgument)arguments.get(argumentIndex);
		else
			throw new BuiltInException("expecting milti-argment for (0-indexed) argument #" + argumentIndex);
	}

	// Find indices of multi-arguments (if any) in a list of arguments.
	private static List<Integer> getMultiValueBuiltInArgumentIndexes(List<SWRLBuiltInArgument> arguments)
	{
		List<Integer> result = new ArrayList<Integer>();

		for (int i = 0; i < arguments.size(); i++)
			if (arguments.get(i) instanceof SWRLMultiValueVariableBuiltInArgument)
				result.add(Integer.valueOf(i));

		return result;
	}

	private static List<SWRLBuiltInArgument> generateArgumentsPattern(List<SWRLBuiltInArgument> arguments,
			int multiValueBuiltInArgumentArgumentIndex) throws BuiltInException
	{
		List<SWRLBuiltInArgument> result = new ArrayList<SWRLBuiltInArgument>();

		for (SWRLBuiltInArgument argument : arguments) {
			if (argument instanceof SWRLMultiValueVariableBuiltInArgument) {
				SWRLMultiValueVariableBuiltInArgument multiValueBuiltInArgument = (SWRLMultiValueVariableBuiltInArgument)argument;
				result.add(multiValueBuiltInArgument.getArguments().get(multiValueBuiltInArgumentArgumentIndex));
			} else
				result.add(argument);
		}

		return result;
	}

	private static Method resolveBuiltInMethod(String ruleName, SWRLBuiltInLibrary library, String prefix,
			String builtInMethodName) throws UnresolvedBuiltInMethodException
	{
		String key = prefix + ":" + builtInMethodName;

		if (methods.containsKey(key))
			return methods.get(key);
		else {
			try {
				Method method = library.getClass().getMethod(builtInMethodName, new Class[] { List.class });

				checkBuiltInMethodSignature(ruleName, prefix, builtInMethodName, method); // Check signature of method

				methods.put(key, method);

				return method;
			} catch (Exception e) {
				throw new UnresolvedBuiltInMethodException(ruleName, prefix, builtInMethodName, e.getMessage(), e);
			}
		}
	}

	// TODO Need to get constructor of library to catch exceptions it may throw.
	private static SWRLBuiltInLibrary loadBuiltInLibraryImpl(String ruleName, String prefix, String className)
			throws UnresolvedBuiltInClassException, IncompatibleBuiltInClassException, SWRLBuiltInLibraryException
	{
		Class<?> swrlBuiltInLibraryClass;
		SWRLBuiltInLibrary swrlBuiltInLibrary;

		try {
			swrlBuiltInLibraryClass = Class.forName(className);
		} catch (Exception e) {
			throw new UnresolvedBuiltInClassException(ruleName, prefix, e.getMessage(), e);
		}

		checkBuiltInMethodsClassCompatibility(ruleName, prefix, swrlBuiltInLibraryClass); // Check implementation class for
																																											// compatibility.

		try {
			swrlBuiltInLibrary = (SWRLBuiltInLibrary)swrlBuiltInLibraryClass.newInstance();
		} catch (InstantiationException e) {
			throw new IncompatibleBuiltInClassException(ruleName, prefix, className, e.getMessage(), e);
		} catch (ExceptionInInitializerError e) {
			throw new IncompatibleBuiltInClassException(ruleName, prefix, className, e.getMessage(), e);
		} catch (IllegalAccessException e) {
			throw new IncompatibleBuiltInClassException(ruleName, prefix, className, e.getMessage(), e);
		} catch (SecurityException e) {
			throw new IncompatibleBuiltInClassException(ruleName, prefix, className, e.getMessage(), e);
		}

		return swrlBuiltInLibrary;
	}

	private static void checkBuiltInMethodSignature(String ruleName, String prefix, String builtInURI, Method method)
			throws IncompatibleBuiltInMethodException
	{
		Class<?> exceptionTypes[];
		Type parameterTypes[];

		if (method.getReturnType() != Boolean.TYPE)
			throw new IncompatibleBuiltInMethodException(ruleName, prefix, builtInURI, "Java method must return a boolean");

		exceptionTypes = method.getExceptionTypes();

		if ((exceptionTypes.length != 1) || (exceptionTypes[0] != BuiltInException.class))
			throw new IncompatibleBuiltInMethodException(ruleName, prefix, builtInURI,
					"Java method must throw a single exception of type BuiltInException");

		parameterTypes = method.getGenericParameterTypes();

		if ((parameterTypes.length != 1) || (!(parameterTypes[0] instanceof ParameterizedType))
				|| (((ParameterizedType)parameterTypes[0]).getRawType() != List.class)
				|| (((ParameterizedType)parameterTypes[0]).getActualTypeArguments().length != 1)
				|| (((ParameterizedType)parameterTypes[0]).getActualTypeArguments()[0] != SWRLBuiltInArgument.class))
			throw new IncompatibleBuiltInMethodException(ruleName, prefix, builtInURI,
					"Java built-in method implementation must accept a single List of SWRLBuiltInArgument objects");
	}

	private static boolean hasUnboundArguments(List<SWRLBuiltInArgument> arguments)
	{
		for (SWRLBuiltInArgument argument : arguments)
			if (argument.isVariable() && argument.asVariable().isUnbound())
				return true;

		return false;
	}

	private static void checkBuiltInMethodsClassCompatibility(String ruleName, String prefix, Class<?> cls)
			throws IncompatibleBuiltInClassException
	{
		if (!SWRLBuiltInLibrary.class.isAssignableFrom(cls))
			throw new IncompatibleBuiltInClassException(ruleName, prefix, cls.getName(),
					"Java class does not extend SWRLBuiltInLibrary", null);
	}
}
