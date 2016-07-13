package org.swrlapi.builtins.arguments;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLDataProperty;

/**
 * Represents an OWL data property argument to a built-in atom.
 *
 * @see org.semanticweb.owlapi.model.OWLDataProperty
 */
public interface SWRLDataPropertyBuiltInArgument
  extends SWRLNamedBuiltInArgument, SWRLDataPropertyExpressionBuiltInArgument
{
  /**
   * @return An OWL data property
   */
  @NonNull OWLDataProperty getOWLDataProperty();
}
