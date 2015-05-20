package org.swrlapi.builtins;

import java.lang.reflect.Method;
import java.util.List;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.exceptions.SWRLBuiltInLibraryException;
import org.swrlapi.sqwrl.values.SQWRLResultValueFactory;

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
  /**
   * @return The name of the built-in library
   */
  String getLibraryName();

  /**
   * Reset library, discarding any internal state if any (e.g., caches).
   *
   */
  void reset();

  /**
   * Method to invoke a built-in in the library. Invoked by {@link SWRLBuiltInLibraryManager}.
   * 
   * @param method The name of the built-in method
   * @param bridge The built-in bridge
   * @param ruleName The invoking rule name
   * @param prefix The prefix of the built-in name
   * @param builtInMethodName The built-in name fragment
   * @param builtInIndex The 0-based index of the built-in in the rule
   * @param isInConsequent Is the built-in in the rule consequent
   * @param arguments The arguments to the built-in
   * @return The return value from the built-in predicate
   * @throws SWRLBuiltInException If the parameters are invalid or if there is an error during built-in invocation
   */
  boolean invokeBuiltInMethod(Method method, SWRLBuiltInBridge bridge, String ruleName, String prefix,
      String builtInMethodName, int builtInIndex, boolean isInConsequent, List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException;

  /**
   * Create a string that represents a unique invocation pattern for a built-in for a bridge/rule/built-in/arguments
   * combination.
   * 
   * @param invokingBridge The built-in bridge invoking the built-in
   * @param invokingRuleName The name of the rule invoking the built-in
   * @param invokingBuiltInIndex The 0-based index of the built-in in the rule
   * @param isInConsequent Is the built-in in the rule consequent
   * @param arguments The arguments to the built-in
   * @return A unique pattern for the invocation
   * @throws SWRLBuiltInException If the parameters are invalid or if there is an error during pattern generation
   */
  String createInvocationPattern(SWRLBuiltInBridge invokingBridge, String invokingRuleName, int invokingBuiltInIndex,
      boolean isInConsequent, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @return A SQWRL result value factory
   * @throws SWRLBuiltInLibraryException If an error occurs during factory retrieval
   */
  SQWRLResultValueFactory getSQWRLResultValueFactory() throws SWRLBuiltInLibraryException;

  /**
   * Convenience method to create an IRI from a full name
   * 
   * @param fullName The full name
   * @return An IRI generated from the full name
   * @throws SWRLBuiltInException If an error occurs during IRI generation
   */
  IRI createIRI(String fullName) throws SWRLBuiltInException;
}
