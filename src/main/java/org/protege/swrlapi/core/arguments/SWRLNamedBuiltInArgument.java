package org.protege.swrlapi.core.arguments;

import org.semanticweb.owlapi.model.IRI;

public interface SWRLNamedBuiltInArgument extends SWRLBuiltInArgument
{
	IRI getIRI();

	String getPrefixedName();
}
