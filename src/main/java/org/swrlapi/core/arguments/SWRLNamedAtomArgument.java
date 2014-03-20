package org.swrlapi.core.arguments;

import org.semanticweb.owlapi.model.IRI;

/**
 * Represents an OWL named entity argument to an atom.
 */
public interface SWRLNamedAtomArgument extends SWRLAtomArgument
{
	IRI getIRI();
}
