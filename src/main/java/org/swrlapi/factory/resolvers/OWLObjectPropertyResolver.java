package org.swrlapi.factory.resolvers;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 * This class is used to keep track of OWL object properties, typically by a rule engine implementation. OWL 2 RL-based
 * reasoners, for example, do not create new object properties as a result of inference (they do reason with supplied
 * object properties, though) so rather than translating native rule engine representations of properties back to
 * the OWLAPI representation when returning inferred axioms, the original properties supplied to the engine can be
 * tracked via an ID and recorded and retrieved using this class.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectProperty
 */
interface OWLObjectPropertyResolver
{
  void reset();

  void recordOWLObjectProperty(@NonNull String propertyID, @NonNull OWLObjectProperty property);

  boolean recordsOWLObjectProperty(@NonNull OWLObjectProperty property);

  /**
   *
   * @param property An object property to resolve
   * @return The ID of the resolved object property
   * @throws IllegalArgumentException If the object property cannot be resolved
   */
  @NonNull String resolveOWLObjectProperty(@NonNull OWLObjectProperty property);

  /**
   *
   * @param propertyID An object property ID
   * @return The resolved object property
   * @throws IllegalArgumentException If the property ID cannot be resolved
   */
  @NonNull OWLObjectProperty resolveOWLObjectProperty(@NonNull String propertyID);
}
