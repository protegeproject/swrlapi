package org.swrlapi.builtins;

import checkers.nullness.quals.NonNull;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument;
import org.swrlapi.exceptions.IncompatibleBuiltInMethodException;
import org.swrlapi.exceptions.IncompatibleSWRLBuiltInClassException;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.exceptions.SWRLBuiltInLibraryException;
import org.swrlapi.exceptions.UnresolvedSWRLBuiltInClassException;
import org.swrlapi.exceptions.UnresolvedSWRLBuiltInMethodException;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class manages the dynamic loading of SWRL built-in libraries and the invocation of built-ins in those libraries.
 * A library is identified by a prefix and this prefix is used to find and dynamically load a Java class implementing
 * the built-ins in this library.
 *
 * @see org.swrlapi.builtins.SWRLBuiltInLibrary
 */
public class SWRLBuiltInLibraryManager
{
  private static final String BuiltInLibraryPackageBaseName = "org.swrlapi.builtins.";

  // Holds instances of implementation classes defining built-in libraries
  @NonNull private final Map<String, SWRLBuiltInLibrary> builtInLibraries;
  @NonNull private final Map<String, Method> methods;

  public SWRLBuiltInLibraryManager()
  {
    this.builtInLibraries = new HashMap<>();
    this.methods = new HashMap<>();
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
   *
   * @param bridge         The built-in bridge
   * @param ruleName       The name of the invoking rule
   * @param builtInName    The invoked built-in
   * @param builtInIndex   The 0-based index of the invoked built-in
   * @param isInConsequent If the built-in in the consequent?
   * @param arguments      The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an exception occurs during invocation
   */
  @NonNull public List<List<SWRLBuiltInArgument>> invokeSWRLBuiltIn(@NonNull SWRLBuiltInBridge bridge,
    @NonNull String ruleName, @NonNull String builtInName, int builtInIndex, boolean isInConsequent,
    @NonNull List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    String prefix = getPrefix(builtInName);
    String implementationClassName = getBuiltInLibraryImplementationClassName(prefix);
    String builtInMethodName = getBuiltInMethodName(builtInName);
    SWRLBuiltInLibrary library = loadBuiltInLibrary(bridge, ruleName, prefix, implementationClassName);
    Method method = resolveBuiltInMethod(ruleName, library, prefix, builtInMethodName);
    List<List<SWRLBuiltInArgument>> argumentPatterns = new ArrayList<>();

    if (library.invokeBuiltInMethod(method, bridge, ruleName, prefix, builtInMethodName, builtInIndex, isInConsequent,
      arguments)) {

      if (hasUnboundArguments(arguments)) // Make sure the built-in has bound all of its arguments.
        throw new SWRLBuiltInException("built-in " + builtInName + "(index " + builtInIndex + ") in rule " + ruleName
          + " returned with unbound arguments");

      processBoundArguments(arguments);

      argumentPatterns.addAll(generateBuiltInArgumentPattern(ruleName, builtInName, builtInIndex, arguments).stream()
        .collect(Collectors.toList()));
    }

    return argumentPatterns;
  }

  private void processBoundArguments(@NonNull List<SWRLBuiltInArgument> arguments)
  {
    for (int argumentIndex = 0; argumentIndex < arguments.size(); argumentIndex++) {
      SWRLBuiltInArgument argument = arguments.get(argumentIndex);
      if (argument.isVariable() && argument.asVariable().hasBuiltInResult())
        arguments.set(argumentIndex, argument.asVariable().getBuiltInResult());
    }
  }

  private SWRLBuiltInLibrary loadBuiltInLibrary(@NonNull SWRLBuiltInBridge bridge, @NonNull String ruleName,
    @NonNull String prefix, @NonNull String implementationClassName) throws SWRLBuiltInLibraryException
  {
    SWRLBuiltInLibrary library;

    if (this.builtInLibraries.containsKey(prefix)) { // Find the cached implementation.
      library = this.builtInLibraries.get(prefix);
    } else { // Implementation class not loaded - load it, cache it, and call its reset method.
      library = loadBuiltInLibraryImpl(ruleName, prefix, implementationClassName);
      this.builtInLibraries.put(prefix, library);
      invokeBuiltInLibraryResetMethod(bridge, library);
    }
    return library;
  }

  @NonNull private String getPrefix(@NonNull String builtInName)
  {
    int hashIndex = builtInName.indexOf(':');

    if (hashIndex != -1) {
      return builtInName.substring(0, hashIndex);
    } else
      return ""; // No prefix - try the base built-ins package. Ordinarily, built-ins should not be located here.
  }

  @NonNull private String getBuiltInLibraryImplementationClassName(@NonNull String prefix)
  {
    if (prefix.length() == 0)
      return BuiltInLibraryPackageBaseName + "SWRLBuiltInLibraryImpl";
    else {
      return BuiltInLibraryPackageBaseName + prefix + ".SWRLBuiltInLibraryImpl";
    }
  }

  @NonNull private String getBuiltInMethodName(@NonNull String builtInName)
  {
    if (!builtInName.contains(":"))
      return builtInName;
    else
      return builtInName.substring(builtInName.indexOf(":") + 1, builtInName.length());
  }

  /**
   * Invoke the reset() method for each registered built-in library.
   */
  private void invokeBuiltInLibraryResetMethod(@NonNull SWRLBuiltInBridge bridge,
    @NonNull SWRLBuiltInLibrary library) throws SWRLBuiltInLibraryException
  {
    try {
      library.invokeResetMethod(bridge);
    } catch (Exception e) {
      throw new SWRLBuiltInLibraryException("error calling reset method in built-in library " + library.getClass());
    }
  }

  public void invokeAllBuiltInLibrariesResetMethod(@NonNull SWRLBuiltInBridge bridge)
    throws SWRLBuiltInLibraryException
  {
    for (SWRLBuiltInLibrary library : this.builtInLibraries.values())
      invokeBuiltInLibraryResetMethod(bridge, library);
  }

  /**
   * This method is called with a list of built-in arguments. Some argument positions may contain multi-arguments,
   * indicating that there is more than one pattern. If the result has more than one multi-argument, each multi-argument
   * must have the same number of elements.
   */
  @NonNull Set<List<SWRLBuiltInArgument>> generateBuiltInArgumentPattern(@NonNull String ruleName,
    @NonNull String builtInName, int builtInIndex, @NonNull List<SWRLBuiltInArgument> arguments)
    throws SWRLBuiltInException
  {
    List<Integer> multiValueBuiltInArgumentIndexes = getMultiValueBuiltInArgumentIndexes(arguments);
    Set<List<SWRLBuiltInArgument>> pattern = new HashSet<>();

    if (multiValueBuiltInArgumentIndexes.isEmpty()) // No multi-arguments - generate a single pattern
      pattern.add(arguments);
    else { // Generate all possible patterns
      int firstMultiValueBuiltInArgumentIndex = multiValueBuiltInArgumentIndexes.get(0); // Pick the first
      // multi-argument.
      SWRLMultiValueVariableBuiltInArgument multiValueBuiltInArgument = getArgumentAsASWRLMultiValueBuiltInArgument(
        arguments, firstMultiValueBuiltInArgumentIndex);
      int numberOfArgumentsInMultiValueBuiltInArgument = multiValueBuiltInArgument.getNumberOfArguments();

      if (numberOfArgumentsInMultiValueBuiltInArgument < 1)
        throw new SWRLBuiltInException(
          "empty multi-value argument for built-in " + builtInName + "(index " + builtInIndex + ") in rule "
            + ruleName);

      for (int i = 1; i < multiValueBuiltInArgumentIndexes.size(); i++) {
        int multiValueBuiltInArgumentIndex = multiValueBuiltInArgumentIndexes.get(i);
        multiValueBuiltInArgument = getArgumentAsASWRLMultiValueBuiltInArgument(arguments,
          multiValueBuiltInArgumentIndex);
        if (numberOfArgumentsInMultiValueBuiltInArgument != multiValueBuiltInArgument.getNumberOfArguments())
          throw new SWRLBuiltInException(
            "all multi-value arguments must have the same number of elements for built-in " + builtInName + "(index "
              + builtInIndex + ") in rule " + ruleName);
      }

      for (int multiValueBuiltInArgumentArgumentIndex = 0; multiValueBuiltInArgumentArgumentIndex
        < numberOfArgumentsInMultiValueBuiltInArgument; multiValueBuiltInArgumentArgumentIndex++) {
        List<SWRLBuiltInArgument> argumentsPattern = generateArgumentsPattern(arguments,
          multiValueBuiltInArgumentArgumentIndex);
        pattern.add(argumentsPattern);
      }
    }
    return pattern;
  }

  @NonNull private SWRLMultiValueVariableBuiltInArgument getArgumentAsASWRLMultiValueBuiltInArgument(
    @NonNull List<SWRLBuiltInArgument> arguments, int argumentIndex) throws SWRLBuiltInException
  {
    if (arguments.get(argumentIndex) instanceof SWRLMultiValueVariableBuiltInArgument)
      return (SWRLMultiValueVariableBuiltInArgument)arguments.get(argumentIndex);
    else
      throw new SWRLBuiltInException("expecting milti-argment for (0-indexed) argument #" + argumentIndex);
  }

  // Find indices of multi-arguments (if any) in a list of arguments.
  @NonNull private List<Integer> getMultiValueBuiltInArgumentIndexes(
    @NonNull List<SWRLBuiltInArgument> arguments)
  {
    List<Integer> result = new ArrayList<>();

    for (int i = 0; i < arguments.size(); i++)
      if (arguments.get(i) instanceof SWRLMultiValueVariableBuiltInArgument)
        result.add(i);

    return result;
  }

  @NonNull private List<SWRLBuiltInArgument> generateArgumentsPattern(
    @NonNull List<SWRLBuiltInArgument> arguments, int multiValueBuiltInArgumentArgumentIndex)
  {
    List<SWRLBuiltInArgument> result = new ArrayList<>();

    for (SWRLBuiltInArgument argument : arguments) {
      if (argument instanceof SWRLMultiValueVariableBuiltInArgument) {
        SWRLMultiValueVariableBuiltInArgument multiValueBuiltInArgument = (SWRLMultiValueVariableBuiltInArgument)argument;
        result.add(multiValueBuiltInArgument.getArguments().get(multiValueBuiltInArgumentArgumentIndex));
      } else
        result.add(argument);
    }

    return result;
  }

  private Method resolveBuiltInMethod(@NonNull String ruleName, @NonNull SWRLBuiltInLibrary library,
    @NonNull String prefix, @NonNull String builtInMethodName) throws UnresolvedSWRLBuiltInMethodException
  {
    String key = prefix + ":" + builtInMethodName;

    if (methods.containsKey(key))
      return methods.get(key);
    else {
      try {
        Method method = library.getClass().getMethod(builtInMethodName, List.class);

        checkBuiltInMethodSignature(ruleName, prefix, builtInMethodName, method); // Check signature of method

        methods.put(key, method);

        return method;
      } catch (Exception e) {
        throw new UnresolvedSWRLBuiltInMethodException(ruleName, prefix, builtInMethodName, e.getMessage(), e);
      }
    }
  }

  // TODO Need to get constructor of library to catch exceptions it may throw.
  private SWRLBuiltInLibrary loadBuiltInLibraryImpl(@NonNull String ruleName, @NonNull String prefix,
    @NonNull String className) throws SWRLBuiltInLibraryException
  {
    Class<?> swrlBuiltInLibraryClass;
    SWRLBuiltInLibrary swrlBuiltInLibrary;

    try {
      swrlBuiltInLibraryClass = Class.forName(className);
    } catch (Exception e) {
      throw new UnresolvedSWRLBuiltInClassException(ruleName, prefix, e.getMessage(), e);
    }

    checkBuiltInMethodsClassCompatibility(ruleName, prefix, swrlBuiltInLibraryClass); // Check implementation class for
    // compatibility.

    try {
      swrlBuiltInLibrary = (SWRLBuiltInLibrary)swrlBuiltInLibraryClass.newInstance();
    } catch (@NonNull InstantiationException | ExceptionInInitializerError | SecurityException | IllegalAccessException e) {
      throw new IncompatibleSWRLBuiltInClassException(ruleName, prefix, className, e.getMessage(), e);
    }

    return swrlBuiltInLibrary;
  }

  private void checkBuiltInMethodSignature(@NonNull String ruleName, @NonNull String prefix,
    @NonNull String builtInURI, @NonNull Method method) throws IncompatibleBuiltInMethodException
  {
    Class<?> exceptionTypes[];
    Type parameterTypes[];

    if (method.getReturnType() != Boolean.TYPE)
      throw new IncompatibleBuiltInMethodException(ruleName, prefix, builtInURI, "Java method must return a boolean");

    exceptionTypes = method.getExceptionTypes();

    if ((exceptionTypes.length != 1) || (exceptionTypes[0] != SWRLBuiltInException.class))
      throw new IncompatibleBuiltInMethodException(ruleName, prefix, builtInURI,
        "Java method must throw a single exception of type BuiltInException");

    parameterTypes = method.getGenericParameterTypes();

    if ((parameterTypes.length != 1) || (!(parameterTypes[0] instanceof ParameterizedType)) || (
      ((ParameterizedType)parameterTypes[0]).getRawType() != List.class) || (
      ((ParameterizedType)parameterTypes[0]).getActualTypeArguments().length != 1) || (
      ((ParameterizedType)parameterTypes[0]).getActualTypeArguments()[0] != SWRLBuiltInArgument.class))
      throw new IncompatibleBuiltInMethodException(ruleName, prefix, builtInURI,
        "Java built-in method implementation must accept a single List of SWRLBuiltInArgument objects");
  }

  private boolean hasUnboundArguments(@NonNull List<SWRLBuiltInArgument> arguments)
  {
    for (SWRLBuiltInArgument argument : arguments)
      if (argument.isVariable() && argument.asVariable().isUnbound())
        return true;

    return false;
  }

  private void checkBuiltInMethodsClassCompatibility(@NonNull String ruleName, @NonNull String prefix,
    @NonNull Class<?> cls) throws IncompatibleSWRLBuiltInClassException
  {
    if (!SWRLBuiltInLibrary.class.isAssignableFrom(cls))
      throw new IncompatibleSWRLBuiltInClassException(ruleName, prefix, cls.getName(),
        "Java class does not extend SWRLBuiltInLibrary", null);
  }
}
