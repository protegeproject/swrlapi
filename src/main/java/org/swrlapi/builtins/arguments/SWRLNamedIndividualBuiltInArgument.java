package org.swrlapi.builtins.arguments;

import checkers.nullness.quals.NonNull;
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
