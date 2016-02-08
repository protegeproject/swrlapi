package org.swrlapi.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;

import java.util.Optional;

/**
 * For simplicity, SWRL rule engine implementations will typically use the prefixed names of OWL entities to name
 * their representation of those objects. This interface provides resolving services for IRI to prefix name mapping
 * and vice versa.
 *
 * @see org.swrlapi.core.SWRLAPIOWLOntology
 */
public interface IRIResolver
{
  void reset();

  /**
   * @param prefixedName A prefixed name
   * @return The IRI resolved from the prefixed name
   */
  @NonNull Optional<@NonNull IRI> prefixedName2IRI(@NonNull String prefixedName);

  /**
   * @param iri An IRI
   * @return The prefixed form of the IRI
   */
  @NonNull Optional<@NonNull String> iri2PrefixedName(@NonNull IRI iri);


  /**
   * @param iri An OWL entity IRI
   * @return The short form of the IRI
   */
  @NonNull Optional<@NonNull String> iri2ShortForm(@NonNull IRI iri);

  /**
   *
   * @param ontology The ontology from which to extract prefixes
   */
  void updatePrefixes(@NonNull OWLOntology ontology);

  /**
   *
   * @param prefix A prefix
   * @param namespace A namespace
   */
  void setPrefix(@NonNull String prefix, @NonNull String namespace);
}
