package org.swrlapi.builtins.arguments;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;

/**
 * Represents an OWL annotation property argument to a SWRL built-in atom.
 *
 * @see org.semanticweb.owlapi.model.OWLAnnotationProperty
 */
public interface SWRLAnnotationPropertyBuiltInArgument extends SWRLNamedBuiltInArgument
{
  /**
   * @return An OWL annotation property
   */
  @NonNull OWLAnnotationProperty getOWLAnnotationProperty();
}
