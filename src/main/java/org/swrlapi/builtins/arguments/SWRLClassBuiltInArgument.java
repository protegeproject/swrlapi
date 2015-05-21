package org.swrlapi.builtins.arguments;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLClass;

/**
 * Interface representing OWL class arguments to SWRL built-ins
 *
 * @see org.semanticweb.owlapi.model.OWLClass
 */
public interface SWRLClassBuiltInArgument extends SWRLNamedBuiltInArgument
{
  /**
   * @return An OWL class
   */
  @NonNull OWLClass getOWLClass();
}
