package org.swrlapi.builtins;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.exceptions.SWRLBuiltInException;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * Defines a base interface for a SWRL built-in library. All built-in library implementation must implement this
 * interface.
 * <p>
 * The class {@link org.swrlapi.builtins.AbstractSWRLBuiltInLibrary} provides a default implementation of this interface
 * and provides and array of methods for dealing with built-in arguments.
 *
 * @see org.swrlapi.builtins.AbstractSWRLBuiltInLibrary
 * @see org.swrlapi.builtins.SWRLBuiltInContext
 * @see org.swrlapi.builtins.SWRLBuiltInBridge
 */
public interface SWRLBuiltInLibrary extends SWRLBuiltInContext
{
  @NonNull String getPrefix();

  /**
   * @return The name of the built-in library
   */
  @NonNull String getNamespace();

  @NonNull Set<@NonNull String> getBuiltInNames();

  @NonNull public Set<@NonNull IRI> getBuiltInIRIs();

  /**
   * Reset library, discarding any internal state if any (e.g., caches)
   */
  void reset();

  /**
   * Method to invoke a built-in in the library. Invoked by {@link SWRLBuiltInLibraryManager}.
   *
   * @param method            The built-in method
   * @param bridge            The built-in bridge
   * @param ruleName          The invoking rule name
   * @param prefix            The prefix of the built-in name
   * @param builtInMethodName The built-in name fragment
   * @param builtInIndex      The 0-based index of the built-in in the rule
   * @param isInConsequent    Is the built-in in the rule consequent
   * @param arguments         The arguments to the built-in
   * @return The return value from the built-in predicate
   * @throws SWRLBuiltInException If the parameters are invalid or if there is an error during built-in invocation
   */
  boolean invokeBuiltInMethod(@NonNull Method method, @NonNull SWRLBuiltInBridge bridge, @NonNull String ruleName,
    @NonNull String prefix, @NonNull String builtInMethodName, int builtInIndex, boolean isInConsequent,
    @NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;
}
