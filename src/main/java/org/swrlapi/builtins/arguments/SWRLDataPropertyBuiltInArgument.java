package org.swrlapi.builtins.arguments;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLDataProperty;

/**
 * Represents an OWL data property argument to a built-in atom.
 *
 * @see org.semanticweb.owlapi.model.OWLDataProperty
 */
public interface SWRLDataPropertyBuiltInArgument extends SWRLNamedBuiltInArgument
{
  /**
   * @return An OWL data property
   */
  @NonNull OWLDataProperty getOWLDataProperty();
}
