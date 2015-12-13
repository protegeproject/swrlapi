package org.swrlapi.builtins;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.exceptions.SWRLBuiltInLibraryException;
import org.swrlapi.factory.SQWRLResultValueFactory;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Defines a base interface for a SWRL built-in library. All built-in library implementation must implement this
 * interface.
 * <p/>
 * The class {@link org.swrlapi.builtins.AbstractSWRLBuiltInLibrary} provides a default implementation of this interface
 * and provides and array of methods for dealing with built-in arguments.
 *
 * @see org.swrlapi.builtins.AbstractSWRLBuiltInLibrary
 * @see org.swrlapi.builtins.SWRLBuiltInContext
 * @see org.swrlapi.builtins.SWRLBuiltInBridge
 */
public interface SWRLBuiltInLibrary extends SWRLBuiltInContext
{
  /**
   * @return The name of the built-in library
   */
  @NonNull String getLibraryName();

  /**
   * Reset library, discarding any internal state if any (e.g., caches).
   */
  void reset();

  /**
   * Method to invoke a built-in in the library. Invoked by {@link SWRLBuiltInLibraryManager}.
   *
   * @param method            The name of the built-in method
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

  /**
   * Create a string that represents a unique invocation pattern for a built-in for a bridge/rule/built-in/arguments
   * combination.
   *
   * @param invokingBridge       The built-in bridge invoking the built-in
   * @param invokingRuleName     The name of the rule invoking the built-in
   * @param invokingBuiltInIndex The 0-based index of the built-in in the rule
   * @param isInConsequent       Is the built-in in the rule consequent
   * @param arguments            The arguments to the built-in
   * @return A unique pattern for the invocation
   * @throws SWRLBuiltInException If the parameters are invalid or if there is an error during pattern generation
   */
  @NonNull String createInvocationPattern(@NonNull SWRLBuiltInBridge invokingBridge, @NonNull String invokingRuleName,
      int invokingBuiltInIndex, boolean isInConsequent, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException;

  /**
   * @return A SQWRL result value factory
   * @throws SWRLBuiltInLibraryException If an error occurs during factory retrieval
   */
  @NonNull SQWRLResultValueFactory getSQWRLResultValueFactory() throws SWRLBuiltInLibraryException;

  /**
   * Convenience method to create an IRI from a full name
   *
   * @param fullName The full name
   * @return An IRI generated from the full name
   * @throws SWRLBuiltInException If an error occurs during IRI generation
   */
  @NonNull IRI createIRI(@NonNull String fullName) throws SWRLBuiltInException;
}
