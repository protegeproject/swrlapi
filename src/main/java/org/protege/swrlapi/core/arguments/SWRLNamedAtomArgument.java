package org.protege.swrlapi.core.arguments;

import org.semanticweb.owlapi.model.IRI;

public interface SWRLNamedAtomArgument extends SWRLAtomArgument
{
	IRI getIRI();

	String getPrefixedName();
}
