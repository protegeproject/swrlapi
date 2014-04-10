package org.swrlapi.core.arguments;

import org.semanticweb.owlapi.model.OWLDatatype;

/**
 * Represents an OWL datatype argument to a built-in atom.
 */
public interface SWRLDatatypeBuiltInArgument extends SWRLNamedBuiltInArgument
{
	OWLDatatype getOWLDatatype();
}
