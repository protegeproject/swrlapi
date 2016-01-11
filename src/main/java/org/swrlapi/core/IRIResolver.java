package org.swrlapi.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.IRI;

/**
 * For simplicity, SWRL rule engine implementations will typically use the prefixed names of OWL named objects to name
 * their representation of those objects.
 *
 * @see org.swrlapi.core.SWRLAPIOWLOntology
 */
public interface IRIResolver
{
  void reset();

  /**
   * @param iri An IRI
   * @return The prefixed form of the IRI
   * @throws IllegalArgumentException If the IRI cannot be resolved to a prefixed name
   */
  @NonNull String iri2PrefixedName(@NonNull IRI iri);

  /**
   * @param prefixedName A prefixed name
   * @return The IRI resolved from the prefixed name
   * @throws IllegalArgumentException If the prefixed name cannot be resolved
   */
  @NonNull IRI prefixedName2IRI(@NonNull String prefixedName);
}
