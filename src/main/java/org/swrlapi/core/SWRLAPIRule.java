package org.swrlapi.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLRule;

import java.util.List;
import java.util.Set;

/**
 * SWRLAPI representation of a SWRL rule. It specializes the OWLAPI's {@link org.semanticweb.owlapi.model.SWRLRule} to
 * provide additional functionality. In particular, the SWRLAPI defines a new atom type for built-ins called
 * {@link org.swrlapi.core.SWRLAPIBuiltInAtom} (which specializes the OWLAPI
 * {@link org.semanticweb.owlapi.model.SWRLBuiltInAtom} type). This atom takes additional built-in argument types
 * represented by {@link org.swrlapi.builtins.arguments.SWRLBuiltInArgument}, which extends the OWLAPI
 * {@link org.semanticweb.owlapi.model.SWRLDArgument} class.
 * <p>
 * A SWRLAPI SWRL rules also have an optional rule name, which is stored in a label annotation property.
 * <p>
 * These SWRLAPI rules are extracted from an OWL ontology by the
 * {@link org.swrlapi.core.SWRLAPIOWLOntology#getSWRLRules()} method.
 *
 * A {@link org.swrlapi.core.SWRLRuleRenderer} can be used to display a rule.
 * 
 * @see org.swrlapi.core.SWRLAPIBuiltInAtom
 * @see org.swrlapi.builtins.arguments.SWRLBuiltInArgument
 * @see org.swrlapi.core.SWRLAPIOWLOntology
 * @see org.swrlapi.core.SWRLRuleRenderer
 */
public interface SWRLAPIRule extends SWRLRule
{
  /**
   * @return The name of the rule
   */
  String getRuleName();

  /**
   * @return A comment annotation associated with a rule
   */
  String getComment();

  /**
   * @return True if the rule is active
   */
  boolean isActive();

  /**
   *
   * @param active The active state
   */
  void setActive(boolean active);

  /**
   * @return True if this is a SQWRL query
   */
  boolean isSQWRLQuery();

  /**
   * @return A list of SWRL atoms
   */
  List<@NonNull SWRLAtom> getBodyAtoms();

  /**
   * @return A list of SWRL atoms
   */
  @NonNull List<@NonNull SWRLAtom> getHeadAtoms();

  /**
   * @return A list of atoms
   */
  @NonNull List<@NonNull SWRLAtom> getNonBuiltInAtomsFromBody();

  /**
   * @return A list of built-in atoms
   */
  @NonNull List<@NonNull SWRLAPIBuiltInAtom> getBuiltInAtomsFromBody();

  /**
   * @param builtInNames A set of built-in names
   * @return A list of built-in atoms
   */
  @NonNull List<@NonNull SWRLAPIBuiltInAtom> getBuiltInAtomsFromHead(Set<@NonNull String> builtInNames);

  /**
   * @return A list of atoms
   */
  @NonNull List<@NonNull SWRLAtom> getNonBuiltInAtomsFromHead();

  /**
   * @return A list of built-in atoms
   */
  @NonNull List<@NonNull SWRLAPIBuiltInAtom> getBuiltInAtomsFromHead();

  /**
   * @param builtInNames A set of built-in names
   * @return A list of built-in atoms
   */
  @NonNull List<@NonNull SWRLAPIBuiltInAtom> getBuiltInAtomsFromBody(@NonNull Set<@NonNull String> builtInNames);
}
