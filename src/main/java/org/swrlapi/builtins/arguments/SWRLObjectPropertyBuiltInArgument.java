package org.swrlapi.builtins.arguments;

import checkers.nullness.quals.NonNull;
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
