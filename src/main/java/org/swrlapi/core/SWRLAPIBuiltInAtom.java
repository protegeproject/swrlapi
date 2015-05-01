package org.swrlapi.core;

import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;

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
  IRI getBuiltInIRI();

  /**
   * @return The 0-based index of the built-in in the enclosing rule
   */
  int getBuiltInIndex();

  /**
   * @param builtInIndex The 0-based index of the built-in in the enclosing rule
   */
  void setBuiltInIndex(int builtInIndex);

  /**
   * @param variablePrefixedNames A set of prefixed variable names
   * @return True if the built-in uses at lease one of the supplied variables
   */
  boolean usesAtLeastOneVariableOf(Set<String> variablePrefixedNames);

  /**
   * @return A list of built-in arguments
   */
  List<SWRLBuiltInArgument> getBuiltInArguments();

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
  boolean isArgumentUnbound(int argumentNumber);

  /**
   * @return True if at least once argument is unbound
   */
  boolean hasUnboundArguments();

  /**
   * @return True if at least one argument is a variable
   */
  boolean hasVariableArguments();

  /**
   * @return The prefixed names of the unbound variable arguments
   */
  Set<String> getUnboundArgumentVariablePrefixedNames();

  /**
   * @param argumentNumber An argument index
   * @return A prefixed variable name
   */
  String getArgumentVariablePrefixedName(int argumentNumber);

  /**
   * @return A list of variable prefixed names
   */
  List<String> getArgumentsVariablePrefixedNames();

  /**
   * @return A list of variable prefixed names
   */
  List<String> getArgumentsVariableNamesExceptFirst();

  /**
   * @return The name of the rule
   */
  String getRuleName();

  // SQWRL-related methods

  /**
   * @param arguments A list of built-in arguments
   */
  void setBuiltInArguments(List<SWRLBuiltInArgument> arguments);

  /**
   * @param additionalArguments A list of built-in arguments
   */
  void addArguments(List<SWRLBuiltInArgument> additionalArguments);

  /**
   * @param variablePrefixedNames A set of variable prefixed-names
   */
  void setPathVariablePrefixedNames(Set<String> variablePrefixedNames);

  /**
   * @return True if the built-in has at least one path variable
   */
  boolean hasPathVariables();

  /**
   * Indicates variables that this built-in atom depends on (directly or indirectly)
   * 
   * @return A list of variable prefixed names
   */
  Set<String> getPathVariablePrefixedNames();

  /**
   * Indicate that the built-in uses a SQWRL collection result
   */
  void setUsesSQWRLCollectionResults();

  /**
   * @return True if the built-in uses a SQWRL collection result
   */
  boolean usesSQWRLCollectionResults();
}
