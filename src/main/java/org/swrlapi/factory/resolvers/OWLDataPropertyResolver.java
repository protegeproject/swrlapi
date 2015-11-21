package org.swrlapi.factory.resolvers;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLDataProperty;

/**
 * This class is used to keep track of OWL data properties, typically by a rule engine implementation. OWL 2 RL-based
 * reasoners, for example, do not create new data properties as a result of inference (they do reason with supplied
 * data properties, though) so rather than translating native rule engine representations of properties back to
 * the OWLAPI representation when returning inferred axioms, the original properties supplied to the engine can be
 * tracked via an ID and recorded and retrieved using this class.
 *
 * @see org.semanticweb.owlapi.model.OWLDataProperty
 */
interface OWLDataPropertyResolver
{
  void reset();

  void recordOWLDataProperty(@NonNull String propertyID, @NonNull OWLDataProperty property);

  boolean recordsOWLDataProperty(@NonNull OWLDataProperty property);

  /**
   *
   * @param property A data property to resolve
   * @return The ID of the resolved data property
   * @throws IllegalArgumentException If the data property cannot be resolved
   */
  @NonNull String resolveOWLDataProperty(@NonNull OWLDataProperty property);

  /**
   *
   * @param propertyID An data property ID
   * @return The resolved data property
   * @throws IllegalArgumentException If the property ID cannot be resolved
   */
  @NonNull OWLDataProperty resolveOWLDataProperty(@NonNull String propertyID);
}