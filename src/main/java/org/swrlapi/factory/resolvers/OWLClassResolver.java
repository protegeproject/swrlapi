package org.swrlapi.factory.resolvers;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLClass;

/**
 * This class is used to keep track of OWL classes, typically by a rule engine implementation. OWL 2 RL-based
 * reasoners, for example, do not create new classes as a result of inference (they do reason with supplied
 * classes, though) so rather than translating native rule engine representations of classes back to
 * the OWLAPI representation when returning inferred axioms, the original classes supplied to the engine can be
 * tracked via an ID and recorded and retrieved using this class.
 *
 * @see org.semanticweb.owlapi.model.OWLClass
 */
interface OWLClassResolver
{
  void reset();

  void recordOWLClass(@NonNull String classID, @NonNull OWLClass cls);

  boolean recordsOWLClass(@NonNull OWLClass cls);

  /**
   *
   * @param cls A class to resolve
   * @return The ID of the resolved class
   * @throws IllegalArgumentException If the class cannot be resolved
   */
  @NonNull String resolveOWLClass(@NonNull OWLClass cls);

  /**
   *
   * @param classID A class ID
   * @return The resolved class
   * @throws IllegalArgumentException If the class ID cannot be resolved
   */
  @NonNull OWLClass resolveOWLClass(@NonNull String classID);
}
