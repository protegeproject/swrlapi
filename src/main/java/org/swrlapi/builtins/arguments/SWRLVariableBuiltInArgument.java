package org.swrlapi.builtins.arguments;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.swrlapi.exceptions.SWRLBuiltInException;

/**
 * Represents a variable argument to a SWRL built-in atom.
 *
 * @see org.semanticweb.owlapi.model.SWRLVariable
 */
public interface SWRLVariableBuiltInArgument extends SWRLBuiltInArgument, SWRLVariable
{
  /**
   * @return The prefixed name of the variable
   */
  @NonNull String getVariablePrefixedName();

  /**
   * @return The name of the variable stripped of a ":" if it is a local prefixed name
   */
  @NonNull String getVariableName();

  @Override
  boolean isVariable();

  /**
   * @return True if the variable has a built-in result attached
   */
  boolean hasBuiltInResult();

  /**
   * @return A SWRL built-in result
   */
  @Nullable SWRLBuiltInArgument getBuiltInResult();

  /**
   * @param builtInResult A SWRL built-in result
   * @throws SWRLBuiltInException If the variable is bound
   */
  void setBuiltInResult(SWRLBuiltInArgument builtInResult) throws SWRLBuiltInException;

  /**
   * @return True if the variable is unbound
   */
  boolean isUnbound();

  /**
   * @return True if the variable is bound
   */
  boolean isBound();

  /**
   * Set the variable as unbound
   */
  void setUnbound();

  /**
   * The set variable as bound
   */
  void setBound();
}
