package org.swrlapi.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.exceptions.SWRLBuiltInException;

import java.util.List;
import java.util.Set;

/**
 * The SWRLAPI's built-in atom extends the OWLAPI's built-in atom with additional functionality. In addition to the
 * {@link SWRLBuiltInArgument} class, this interface is the SWRLAPI's primary OWLAPI extension point.
 *
 * @see org.swrlapi.builtins.arguments.SWRLBuiltInArgument
 */
public interface SWRLAPIBuiltInAtom extends SWRLBuiltInAtom
{
  /**
   * @return The prefixed name of the built-in
   */
  String getBuiltInPrefixedName();

  /**
   * @return The IRI of the built-in
   */
  @NonNull IRI getBuiltInIRI();

  /**
   * @return The 0-based index of the built-in in the enclosing rule
   */
  int getBuiltInIndex();

  /**
   * @param builtInIndex The 0-based index of the built-in in the enclosing rule
   */
  void setBuiltInIndex(int builtInIndex);

  /**
   * @param variableNames A set of variable names
   * @return True if the built-in uses at lease one of the supplied variables
   */
  boolean usesAtLeastOneVariableOf(Set<@NonNull String> variableNames) throws SWRLBuiltInException;

  /**
   * @return A list of built-in arguments
   */
  @NonNull List<@NonNull SWRLBuiltInArgument> getBuiltInArguments();

  /**
   * @return The number of built-in arguments
   */
  int getNumberOfArguments();

  /**
   * @param argumentNumber A argument index
   * @return If the specified argument is a variable
   */
  boolean isArgumentAVariable(int argumentNumber);

  /**
   * @param argumentNumber A argument index
   * @return If the specified argument is unbound
   */
  boolean isArgumentUnbound(int argumentNumber) throws SWRLBuiltInException;

  /**
   * @return True if at least once argument is unbound
   */
  boolean hasUnboundArguments() throws SWRLBuiltInException;

  /**
   * @return True if at least one argument is a variable
   */
  boolean hasVariableArguments();

  /**
   * @return The names of the unbound variable arguments
   */
  @NonNull Set<@NonNull String> getUnboundArgumentVariableNames() throws SWRLBuiltInException;

  /**
   * @param argumentNumber An argument index
   * @return A variable name
   */
  String getArgumentVariableName(int argumentNumber) throws SWRLBuiltInException;

  /**
   * @return A list of variable names
   */
  @NonNull List<@NonNull String> getArgumentsVariableNames() throws SWRLBuiltInException;

  /**
   * @return A list of variable names
   */
  @NonNull List<@NonNull String> getArgumentsVariableNamesExceptFirst() throws SWRLBuiltInException;

  /**
   * @return The name of the rule
   */
  String getRuleName();

  // SQWRL-related methods

  /**
   * @param arguments A list of built-in arguments
   */
  void setBuiltInArguments(@NonNull List<@NonNull SWRLBuiltInArgument> arguments);

  /**
   * @param additionalArguments A list of built-in arguments
   */
  void addArguments(@NonNull List<@NonNull SWRLBuiltInArgument> additionalArguments);

  /**
   * @param variableNames A set of variable names
   */
  void setPathVariableNames(Set<@NonNull String> variableNames);

  /**
   * @return True if the built-in has at least one path variable
   */
  boolean hasPathVariables();

  /**
   * Indicates variables that this built-in depends on (directly or indirectly)
   *
   * @return A list of variable names
   */
  @NonNull Set<@NonNull String> getPathVariableNames();

  /**
   * Indicate that the built-in uses a SQWRL collection result
   */
  void setUsesSQWRLCollectionResults();

  /**
   * @return True if the built-in uses a SQWRL collection result
   */
  boolean usesSQWRLCollectionResults();
}
