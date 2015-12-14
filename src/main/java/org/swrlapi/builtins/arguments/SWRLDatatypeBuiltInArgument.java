package org.swrlapi.builtins.arguments;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLDatatype;

/**
 * Represents an OWL datatype argument to a built-in atom.
 *
 * @see org.semanticweb.owlapi.model.OWLDatatype
 */
public interface SWRLDatatypeBuiltInArgument extends SWRLNamedBuiltInArgument
{
  /**
   * @return An OWL datatype
   */
  @NonNull OWLDatatype getOWLDatatype();
}
