package org.swrlapi.factory.resolvers;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

/**
 * This class is used to keep track of OWL named individuals, typically by a rule engine implementation. OWL 2 RL-based
 * reasoners, for example, do not create new individual as a result of inference (they do reason with supplied
 * individual, though) so rather than translating native rule engine representations of individual back to
 * the OWLAPI representation when returning inferred axioms, the original named individuals supplied to the engine can
 * be tracked via an ID and recorded and retrieved using this class.
 *
 * @see org.semanticweb.owlapi.model.OWLNamedIndividual
 */
interface OWLNamedIndividualResolver
{
  void reset();

  void recordOWLNamedIndividual(@NonNull String individualID, @NonNull OWLNamedIndividual individual);

  boolean recordsOWLNamedIndividual(@NonNull OWLNamedIndividual individual);

  /**
   * @param individual An OWL named individual to resolve
   * @return The ID of the resolved individual
   * @throws IllegalArgumentException If the individual cannot be resolved
   */
  @NonNull String resolveOWLNamedIndividual(@NonNull OWLNamedIndividual individual);

  /**
   * @param individualID An individual ID
   * @return The resolved individual
   * @throws IllegalArgumentException If the individual ID cannot be resolved
   */
  @NonNull OWLNamedIndividual resolveOWLNamedIndividual(@NonNull String individualID);
}
