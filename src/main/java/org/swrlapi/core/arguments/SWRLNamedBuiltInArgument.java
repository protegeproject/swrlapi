package org.swrlapi.core.arguments;

import org.semanticweb.owlapi.model.IRI;

/**
 * Represents an OWL named entity argument to a built-in atom. Distinct from a {@link SWRLNamedAtomArgument}, which
 * represents an OWL named entity argument to a non built-in atom.
 */
public interface SWRLNamedBuiltInArgument extends SWRLBuiltInArgument
{
	IRI getIRI();

	String getPrefixedName();
}
