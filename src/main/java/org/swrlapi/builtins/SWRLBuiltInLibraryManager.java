package org.swrlapi.builtins;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument;
import org.swrlapi.exceptions.IncompatibleBuiltInMethodException;
import org.swrlapi.exceptions.IncompatibleSWRLBuiltInClassException;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.exceptions.SWRLBuiltInLibraryException;
import org.swrlapi.exceptions.UnresolvedSWRLBuiltInClassException;
import org.swrlapi.exceptions.UnresolvedSWRLBuiltInMethodException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

/**
 * This class manages the dynamic loading of SWRL built-in libraries and the invocation of built-ins in those libraries.
 * A library is identified by a prefix and this prefix is used to loadExternalSWRLBuiltInLibraries and dynamically
 * load a Java class implementing the built-ins in this library.
 *
 * @see org.swrlapi.builtins.SWRLBuiltInLibrary
 */
public class SWRLBuiltInLibraryManager
{
  private static final Logger log = LoggerFactory.getLogger(SWRLBuiltInLibraryManager.class);

  private static final String SWRLBuiltInLibraryPackageBaseName = "org.swrlapi.builtins";
  private static final String SWRLBuiltInLibraryImplementationClassName = "SWRLBuiltInLibraryImpl";
  private static final String[] preCannedSWRLBuiltInLibraryPrefixNames = { "swrlb", "sqwrl", "swrlx", "swrlm", "abox",
    "tbox", "rbox", "temporal" };
  private static final Set<String> preCannedSWRLBuiltInLibraryPrefixes = new HashSet<>(
    Arrays.asList(preCannedSWRLBuiltInLibraryPrefixNames));

  @NonNull private final Map<@NonNull IRI, @NonNull String> swrlBuiltInIRI2PrefixedName = new HashMap<>();
  @NonNull private final Map<@NonNull String, @NonNull IRI> swrlBuiltInPrefixedName2IRI = new HashMap<>();

  // Map of built-in library prefix name to SWRLBuiltInLibrary instance
  @NonNull private final Map<@NonNull String, @NonNull SWRLBuiltInLibrary> swrlBuiltInLibraryImplementations;

  // Map of prefix:methodName to method implementation
  @NonNull private final Map<@NonNull String, @NonNull Method> swrlBuiltInMethods;

  public SWRLBuiltInLibraryManager()
  {
    this.swrlBuiltInLibraryImplementations = new HashMap<>();
    this.swrlBuiltInMethods = new HashMap<>();

    loadInternalSWRLBuiltInLibraries(preCannedSWRLBuiltInLibraryPrefixes);
  }

  public void loadExternalSWRLBuiltInLibraries(@NonNull File swrlBuiltInLibraryDirectory)
  {
    if (swrlBuiltInLibraryDirectory.exists() && swrlBuiltInLibraryDirectory.isDirectory()) {
      File[] swrlBuiltInLibrarySubDirectories = swrlBuiltInLibraryDirectory.listFiles();
      for (File builtInLibrarySubDirectory : swrlBuiltInLibrarySubDirectories) {
        if (builtInLibrarySubDirectory.isDirectory()) {
          File swrlBuiltInLibrarySubDirectory = builtInLibrarySubDirectory;
          String swrlBuiltInLibrarySubDirectoryName = swrlBuiltInLibrarySubDirectory.getName();
          try {
            URL swrlBuiltInLibrarySubDirectoryURL = new URL(
              "file:" + swrlBuiltInLibrarySubDirectory.getCanonicalPath() + "/"); // the trailing slash is important for the classloader (otherwise it expects a file and throws a FNFE)
            URLClassLoader classLoader = new URLClassLoader(new URL[] { swrlBuiltInLibrarySubDirectoryURL },
              this.getClass().getClassLoader());
            SWRLBuiltInLibrary swrlBuiltInLibrary = instantiateSWRLBuiltInLibraryImplementation(
              swrlBuiltInLibrarySubDirectoryName, classLoader);
            String swrlBuiltInLibraryPrefix = swrlBuiltInLibrary.getPrefix();
            if (preCannedSWRLBuiltInLibraryPrefixes.contains(swrlBuiltInLibraryPrefix)) {
              log.warn("External built-in library prefix " + swrlBuiltInLibraryPrefix
                + " collides with in-built library prefix");
            } else if (swrlBuiltInLibraryPrefix.equals(swrlBuiltInLibrarySubDirectoryName)) {
              log.info("Loading external SWRL built-in library " + swrlBuiltInLibraryPrefix);
              registerSWRLBuiltIns(swrlBuiltInLibraryPrefix, swrlBuiltInLibrary.getNamespace(),
                swrlBuiltInLibrary.getBuiltInNames());
              this.swrlBuiltInLibraryImplementations.put(swrlBuiltInLibraryPrefix, swrlBuiltInLibrary);
            } else
              log.warn("Invalid sub-directory name " + swrlBuiltInLibrarySubDirectoryName
                + " for SWRL built-in library with prefix " + swrlBuiltInLibraryPrefix
                + " - sub-directory and prefix should match!");
          } catch (IOException e) {
            log.warn(
              "Internal error processing SWRL built-in directory " + swrlBuiltInLibraryDirectory.getAbsolutePath());
          }
        } else
          log.warn("SWRL built-in directory " + swrlBuiltInLibraryDirectory.getAbsolutePath()
            + " does not exist or is not a directory!");
      }
    }
  }

  private void loadInternalSWRLBuiltInLibraries(Set<@NonNull String> swrlBuiltInLibraryPrefixes)
  {
    for (String swrlBuiltInLibraryPrefix : swrlBuiltInLibraryPrefixes) {
      SWRLBuiltInLibrary swrlBuiltInLibrary = instantiateSWRLBuiltInLibraryImplementation(swrlBuiltInLibraryPrefix, SWRLBuiltInLibraryManager.class.getClassLoader());

      registerSWRLBuiltIns(swrlBuiltInLibrary.getPrefix(), swrlBuiltInLibrary.getNamespace(),
        swrlBuiltInLibrary.getBuiltInNames());
      this.swrlBuiltInLibraryImplementations.put(swrlBuiltInLibraryPrefix, swrlBuiltInLibrary);
    }
  }

  public boolean isSWRLBuiltInIRI(@NonNull IRI iri)
  {
    return swrlBuiltInIRI2PrefixedName.containsKey(iri);
  }

  public boolean isSWRLBuiltIn(@NonNull String prefixedName)
  {
    return swrlBuiltInPrefixedName2IRI.containsKey(prefixedName);
  }

  public Optional<@NonNull IRI> swrlBuiltInPrefixedName2IRI(@NonNull String prefixedName)
  {
    if (this.swrlBuiltInPrefixedName2IRI.containsKey(prefixedName))
      return Optional.of(this.swrlBuiltInPrefixedName2IRI.get(prefixedName));
    else
      return Optional.empty();
  }

  public Optional<@NonNull String> swrlBuiltInIRI2PrefixedName(@NonNull IRI iri)
  {
    if (this.swrlBuiltInIRI2PrefixedName.containsKey(iri))
      return Optional.of(this.swrlBuiltInIRI2PrefixedName.get(iri));
    else
      return Optional.empty();
  }

  @NonNull public Set<@NonNull IRI> getSWRLBuiltInIRIs()
  {
    return Collections.unmodifiableSet(swrlBuiltInIRI2PrefixedName.keySet());
  }

  /**
   * Invoke a SWRL built-in. This method is called from the
   * {@link org.swrlapi.bridge.SWRLRuleEngineBridge#invokeSWRLBuiltIn} method in the
   * {@link org.swrlapi.bridge.SWRLRuleEngineBridge} and should not be called directly from a rule engine. The built-in
   * name should be the prefixed name of the built-in (e.g., swrlb:lessThanOrEqual).
   * <p>
   * For built-ins that evaluate to true, this method will return a list of argument patterns, one pattern for each
   * combination of arguments that evaluates to true.
   * <p>
   * If the built-in evaluates to false, it will return an empty argument pattern list.
   *
   * @param bridge                    The built-in bridge
   * @param ruleName                  The name of the invoking rule
   * @param builtInMethodPrefixedName The invoked built-in method
   * @param builtInIndex              The 0-based index of the invoked built-in
   * @param isInConsequent            If the built-in in the consequent?
   * @param arguments                 The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an exception occurs during invocation
   */
  @NonNull public List<@NonNull List<@NonNull SWRLBuiltInArgument>> invokeSWRLBuiltIn(@NonNull SWRLBuiltInBridge bridge,
    @NonNull String ruleName, @NonNull String builtInMethodPrefixedName, int builtInIndex, boolean isInConsequent,
    @NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    String prefix = getPrefixFromPrefixedName(builtInMethodPrefixedName);
    String builtInMethodName = getBuiltInMethodNameFromPrefixedName(builtInMethodPrefixedName);
    SWRLBuiltInLibrary swrlBuiltInLibrary = getSWRLBuiltInLibraryImplementation(prefix);
    Method method = resolveSWRLBuiltInMethod(ruleName, swrlBuiltInLibrary, prefix, builtInMethodName);
    List<@NonNull List<@NonNull SWRLBuiltInArgument>> argumentPatterns = new ArrayList<>();

    if (swrlBuiltInLibrary
      .invokeBuiltInMethod(method, bridge, ruleName, prefix, builtInMethodName, builtInIndex, isInConsequent,
        arguments)) {

      if (hasUnboundArguments(arguments)) // Make sure the built-in has bound all of its arguments.
        throw new SWRLBuiltInException(
          "built-in " + builtInMethodPrefixedName + "(index " + builtInIndex + ") in rule " + ruleName
            + " returned with unbound arguments");

      processBoundArguments(arguments);

      argumentPatterns.addAll(
        generateBuiltInArgumentPattern(ruleName, builtInMethodPrefixedName, builtInIndex, arguments).stream()
          .collect(Collectors.toList()));
    }

    return argumentPatterns;
  }

  private void processBoundArguments(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    for (int argumentIndex = 0; argumentIndex < arguments.size(); argumentIndex++) {
      SWRLBuiltInArgument argument = arguments.get(argumentIndex);
      if (argument.isVariable() && argument.asVariable().hasBuiltInResult()) {
        Optional<SWRLBuiltInArgument> builtInResult = argument.asVariable().getBuiltInResult();

        if (builtInResult.isPresent())
          arguments.set(argumentIndex, builtInResult.get());
      }
    }
  }

  @NonNull private SWRLBuiltInLibrary getSWRLBuiltInLibraryImplementation(@NonNull String prefix)
    throws SWRLBuiltInLibraryException
  {
    if (this.swrlBuiltInLibraryImplementations.containsKey(prefix)) // Find the cached implementation
      return this.swrlBuiltInLibraryImplementations.get(prefix);
    else
      throw new SWRLBuiltInLibraryException("could not find built-in library for prefix " + prefix);
  }

  /**
   * Invoke the reset() method for each registered built-in library.
   */
  private void invokeBuiltInLibraryResetMethod(@NonNull SWRLBuiltInBridge bridge, @NonNull SWRLBuiltInLibrary library)
    throws SWRLBuiltInLibraryException
  {
    try {
      library.invokeResetMethod(bridge);
    } catch (Exception e) {
      throw new SWRLBuiltInLibraryException("error calling reset method in built-in library " + library.getClass());
    }
  }

  public void invokeAllBuiltInLibrariesResetMethod(@NonNull SWRLBuiltInBridge bridge) throws SWRLBuiltInLibraryException
  {
    for (SWRLBuiltInLibrary library : this.swrlBuiltInLibraryImplementations.values())
      invokeBuiltInLibraryResetMethod(bridge, library);
  }

  /**
   * This method is called with a list of built-in arguments. Some argument positions may contain multi-arguments,
   * indicating that there is more than one pattern. If the result has more than one multi-argument, each multi-argument
   * must have the same number of elements.
   */
  @NonNull private Set<@NonNull List<@NonNull SWRLBuiltInArgument>> generateBuiltInArgumentPattern(
    @NonNull String ruleName, @NonNull String builtInName, int builtInIndex,
    @NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    List<@NonNull Integer> multiValueBuiltInArgumentIndexes = getMultiValueBuiltInArgumentIndexes(arguments);
    Set<@NonNull List<@NonNull SWRLBuiltInArgument>> pattern = new HashSet<>();

    if (multiValueBuiltInArgumentIndexes.isEmpty()) // No multi-arguments - generate a single pattern
      pattern.add(arguments);
    else { // Generate all possible patterns
      int firstMultiValueBuiltInArgumentIndex = multiValueBuiltInArgumentIndexes.get(0); // Pick first multi-argument
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
        List<@NonNull SWRLBuiltInArgument> argumentsPattern = generateArgumentsPattern(arguments,
          multiValueBuiltInArgumentArgumentIndex);
        pattern.add(argumentsPattern);
      }
    }
    return pattern;
  }

  @NonNull private SWRLMultiValueVariableBuiltInArgument getArgumentAsASWRLMultiValueBuiltInArgument(
    @NonNull List<@NonNull SWRLBuiltInArgument> arguments, int argumentIndex) throws SWRLBuiltInException
  {
    if (arguments.get(argumentIndex) instanceof SWRLMultiValueVariableBuiltInArgument)
      return (SWRLMultiValueVariableBuiltInArgument)arguments.get(argumentIndex);
    else
      throw new SWRLBuiltInException("expecting milti-argment for (0-indexed) argument #" + argumentIndex);
  }

  // Find indices of multi-arguments (if any) in a list of arguments.
  @NonNull private List<@NonNull Integer> getMultiValueBuiltInArgumentIndexes(
    @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
  {
    List<@NonNull Integer> result = new ArrayList<>();

    for (int i = 0; i < arguments.size(); i++)
      if (arguments.get(i) instanceof SWRLMultiValueVariableBuiltInArgument)
        result.add(i);

    return result;
  }

  @NonNull private List<@NonNull SWRLBuiltInArgument> generateArgumentsPattern(
    @NonNull List<@NonNull SWRLBuiltInArgument> arguments, int multiValueBuiltInArgumentArgumentIndex)
  {
    List<@NonNull SWRLBuiltInArgument> result = new ArrayList<>();

    for (SWRLBuiltInArgument argument : arguments) {
      if (argument instanceof SWRLMultiValueVariableBuiltInArgument) {
        SWRLMultiValueVariableBuiltInArgument multiValueBuiltInArgument = (SWRLMultiValueVariableBuiltInArgument)argument;
        result.add(multiValueBuiltInArgument.getArguments().get(multiValueBuiltInArgumentArgumentIndex));
      } else
        result.add(argument);
    }

    return result;
  }

  @NonNull private Method resolveSWRLBuiltInMethod(@NonNull String ruleName, @NonNull SWRLBuiltInLibrary library,
    @NonNull String prefix, @NonNull String builtInMethodName) throws UnresolvedSWRLBuiltInMethodException
  {
    String key = prefix + ":" + builtInMethodName;

    if (swrlBuiltInMethods.containsKey(key))
      return swrlBuiltInMethods.get(key);
    else {
      try {
        Method method = library.getClass().getMethod(builtInMethodName, List.class);

        checkSWRLBuiltInMethodSignature(ruleName, prefix, builtInMethodName, method); // Check signature of method

        swrlBuiltInMethods.put(key, method);

        return method;
      } catch (Exception e) {
        throw new UnresolvedSWRLBuiltInMethodException(ruleName, prefix, builtInMethodName,
          e.getMessage() != null ? e.getMessage() : "", e);
      }
    }
  }

  @NonNull private SWRLBuiltInLibrary instantiateSWRLBuiltInLibraryImplementation(
    @NonNull String swrlBuiltInLibraryPrefix, ClassLoader classLoader) throws SWRLBuiltInLibraryException
  {
    Class<?> swrlBuiltInLibraryImplementationClass;
    String swrlBuiltInLibraryImplementationClassName =
      SWRLBuiltInLibraryPackageBaseName + "." + swrlBuiltInLibraryPrefix + "."
        + SWRLBuiltInLibraryImplementationClassName;

    try {
      swrlBuiltInLibraryImplementationClass = Class.forName(swrlBuiltInLibraryImplementationClassName, true, classLoader);
    } catch (Exception e) {
      throw new UnresolvedSWRLBuiltInClassException(swrlBuiltInLibraryImplementationClassName,
        e.getMessage() != null ? e.getMessage() : "", e);
    }

    // Check implementation class for compatibility
    checkSWRLBuiltInLibraryImplementationClassCompatibility(swrlBuiltInLibraryImplementationClassName,
      swrlBuiltInLibraryImplementationClass);

    try { // TODO Need to get constructor of library to catch exceptions it may throw
      return (SWRLBuiltInLibrary)swrlBuiltInLibraryImplementationClass.getDeclaredConstructor().newInstance();
    } catch (@NonNull InstantiationException | ExceptionInInitializerError | SecurityException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
      throw new IncompatibleSWRLBuiltInClassException(swrlBuiltInLibraryImplementationClassName,
        e.getMessage() != null ? e.getMessage() : "", e);
    }
  }

  private void checkSWRLBuiltInMethodSignature(@NonNull String ruleName, @NonNull String prefix,
    @NonNull String builtInURI, @NonNull Method method) throws IncompatibleBuiltInMethodException
  {
    if (method.getReturnType() != Boolean.TYPE)
      throw new IncompatibleBuiltInMethodException(ruleName, prefix, builtInURI, "Java method must return a boolean");

    Class<?>[] exceptionTypes = method.getExceptionTypes();

    if ((exceptionTypes.length != 1) || (exceptionTypes[0] != SWRLBuiltInException.class))
      throw new IncompatibleBuiltInMethodException(ruleName, prefix, builtInURI,
        "Java method must throw a single exception of type BuiltInException");

    Type[] parameterTypes = method.getGenericParameterTypes();

    if ((parameterTypes.length != 1) || (!(parameterTypes[0] instanceof ParameterizedType)) || (
      ((ParameterizedType)parameterTypes[0]).getRawType() != List.class) || (
      ((ParameterizedType)parameterTypes[0]).getActualTypeArguments().length != 1) || (
      ((ParameterizedType)parameterTypes[0]).getActualTypeArguments()[0] != SWRLBuiltInArgument.class))
      throw new IncompatibleBuiltInMethodException(ruleName, prefix, builtInURI,
        "Java built-in method implementation must accept a single List of SWRLBuiltInArgument objects");
  }

  private void checkSWRLBuiltInLibraryImplementationClassCompatibility(@NonNull String prefix, @NonNull Class<?> cls)
    throws IncompatibleSWRLBuiltInClassException
  {
    if (!SWRLBuiltInLibrary.class.isAssignableFrom(cls))
      throw new IncompatibleSWRLBuiltInClassException(cls.getName(),
        "Java class does not extend " + SWRLBuiltInLibrary.class.getCanonicalName());
  }

  private boolean hasUnboundArguments(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    for (SWRLBuiltInArgument argument : arguments)
      if (argument.isVariable() && argument.asVariable().isUnbound())
        return true;

    return false;
  }

  @NonNull private Optional<String> extractSWRLBuiltInLibraryPrefixFromClassName(
    @NonNull String swrlBuiltInLibraryImplementationClassName)
  {
    if (swrlBuiltInLibraryImplementationClassName.length() > (SWRLBuiltInLibraryPackageBaseName.length()
      + SWRLBuiltInLibraryImplementationClassName.length())) {
      String swrlBuiltInLibraryPrefix = swrlBuiltInLibraryImplementationClassName
        .substring(SWRLBuiltInLibraryPackageBaseName.length(),
          swrlBuiltInLibraryImplementationClassName.length() - SWRLBuiltInLibraryImplementationClassName.length());

      if (isValidJavaIdentifier(swrlBuiltInLibraryPrefix))
        return Optional.of(swrlBuiltInLibraryPrefix);
      else {
        log.warn("Invalid SWRL built-in library implementation prefix " + swrlBuiltInLibraryImplementationClassName);
        return Optional.empty();
      }
    } else {
      log.warn("Invalid SWRL built-in library implementation class name " + swrlBuiltInLibraryImplementationClassName);
      return Optional.empty();
    }
  }

  private void registerSWRLBuiltIns(@NonNull String swrlBuiltInLibraryPrefix,
    @NonNull String swrlBuiltInLibraryNamespace, @NonNull Set<@NonNull String> builtInNames)
  {
    for (String builtInName : builtInNames)
      registerSWRLBuiltIn(swrlBuiltInLibraryPrefix, swrlBuiltInLibraryNamespace, builtInName);
  }

  private void registerSWRLBuiltIn(@NonNull String swrlBuiltInLibraryPrefix,
    @NonNull String swrlBuiltInLibraryNamespace, @NonNull String builtInName)
  {
    IRI builtInIRI = IRI.create(swrlBuiltInLibraryNamespace, builtInName);
    String builtInPrefixedName = swrlBuiltInLibraryPrefix + ":" + builtInName;
    this.swrlBuiltInIRI2PrefixedName.put(builtInIRI, builtInPrefixedName);
    this.swrlBuiltInPrefixedName2IRI.put(builtInPrefixedName, builtInIRI);
  }

  @NonNull private String getPrefixFromPrefixedName(@NonNull String builtInPrefixedName)
  {
    int hashIndex = builtInPrefixedName.indexOf(':');

    if (hashIndex != -1) {
      return builtInPrefixedName.substring(0, hashIndex);
    } else
      return ""; // No prefix - try the base built-ins package. Ordinarily, built-ins should not be located here.
  }

  @NonNull private String getSWRLBuiltInLibraryImplementationClassName(@NonNull String prefix)
  {
    if (prefix.length() == 0)
      return SWRLBuiltInLibraryPackageBaseName + SWRLBuiltInLibraryImplementationClassName;
    else {
      return SWRLBuiltInLibraryPackageBaseName + "." + prefix + "." + SWRLBuiltInLibraryImplementationClassName;
    }
  }

  @NonNull private String getBuiltInMethodNameFromPrefixedName(@NonNull String builtInPrefixedName)
  {
    if (!builtInPrefixedName.contains(":"))
      return builtInPrefixedName;
    else
      return builtInPrefixedName.substring(builtInPrefixedName.indexOf(":") + 1, builtInPrefixedName.length());
  }

  @NonNull private String getClassNameFromEntry(@NonNull ZipEntry entry)
  {
    StringBuilder className = new StringBuilder();
    for (String part : entry.getName().split("/")) {
      if (className.length() != 0)
        className.append(".");
      className.append(part);
      if (part.endsWith(".class"))
        className.setLength(className.length() - ".class".length());
    }
    return className.toString();
  }

  private static boolean isValidJavaIdentifier(@NonNull String s)
  {
    if (s == null || s.length() == 0)
      return false;

    char[] c = s.toCharArray();
    if (!Character.isJavaIdentifierStart(c[0]))
      return false;

    for (int i = 1; i < c.length; i++) {
      if (!Character.isJavaIdentifierPart(c[i]))
        return false;
    }

    return true;
  }
}
