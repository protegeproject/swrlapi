package org.swrlapi.builtins;

import checkers.nullness.quals.NonNull;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.exceptions.SWRLBuiltInLibraryException;

/**
 * Provides invocation context for invoked SWRL built-ins (such the name of invoking rule, whether the invocation is in
 * the consequent or the antecedent) and access to the invoking {@link org.swrlapi.builtins.SWRLBuiltInBridge}.
 *
 * @see org.swrlapi.builtins.SWRLBuiltInLibrary
 * @see org.swrlapi.builtins.SWRLBuiltInBridge
 */
public interface SWRLBuiltInContext
{
  /**
   * @return The invoking bridge
   * @throws SWRLBuiltInLibraryException If the method is called outside of a built-in invocation context
   */
  @NonNull SWRLBuiltInBridge getBuiltInBridge() throws SWRLBuiltInLibraryException;

  /**
   * @param bridge The associated SWRL rule engine bridge
   * @throws SWRLBuiltInLibraryException If the method is called outside of a built-in invocation context
   */
  void invokeResetMethod(@NonNull SWRLBuiltInBridge bridge) throws SWRLBuiltInLibraryException;

  /**
   * @return The invoking rule name
   * @throws SWRLBuiltInLibraryException If the method is called outside of a built-in invocation context
   */
  @NonNull String getInvokingRuleName() throws SWRLBuiltInLibraryException;

  /**
   * @return The index of the invoking built-in
   * @throws SWRLBuiltInLibraryException If the method is called outside of a built-in invocation context
   */
  int getInvokingBuiltInIndex() throws SWRLBuiltInLibraryException;

  /**
   * @throws SWRLBuiltInException If the invocation context is not a rule consequent
   */
  void checkThatInConsequent() throws SWRLBuiltInException;

  /**
   * @throws SWRLBuiltInException If the method is called outside of a built-in invocation context
   */
  void checkThatInAntecedent() throws SWRLBuiltInException;

  /**
   * @return True if the invocation is from a rule consequent
   * @throws SWRLBuiltInLibraryException If the method is called outside of a built-in invocation context
   */
  boolean getIsInConsequent() throws SWRLBuiltInLibraryException;
}
