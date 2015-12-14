package org.swrlapi.builtins.arguments;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 * Represents an OWL object property argument to a built-in atom.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectProperty
 */
public interface SWRLObjectPropertyBuiltInArgument extends SWRLNamedBuiltInArgument
{
  /**
   * @return An OWL object property
   */
  @NonNull OWLObjectProperty getOWLObjectProperty();
}
