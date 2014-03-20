package org.swrlapi.core.arguments;

import org.semanticweb.owlapi.model.IRI;

/**
 * Represents an OWL named entity argument to a non built-in atom. Distinct from a {@link SWRLNamedBuiltInArgument},
 * which represents an OWL named entity argument to a built-in atom.
 */
public interface SWRLNamedAtomArgument extends SWRLAtomArgument
{
	IRI getIRI();

	String getPrefixedName();
}
