package org.protege.swrlapi.sqwrl.values;

import org.semanticweb.owlapi.model.IRI;

public interface SQWRLNamedResultValue extends SQWRLResultValue
{
	IRI getIRI();

	String getPrefixedName();
}