package org.swrlapi.builtins.arguments;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

/**
 * Represents an OWL named individual argument to a built-in atom.
 *
 * @see org.semanticweb.owlapi.model.OWLNamedIndividual
 */
public interface SWRLNamedIndividualBuiltInArgument extends SWRLNamedBuiltInArgument
{
  /**
   * @return An OWL named individual
   */
  @NonNull OWLNamedIndividual getOWLNamedIndividual();
}
