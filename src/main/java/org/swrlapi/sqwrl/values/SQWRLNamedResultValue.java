package org.swrlapi.sqwrl.values;

import org.semanticweb.owlapi.model.IRI;

public interface SQWRLNamedResultValue extends SQWRLResultValue, Comparable<SQWRLNamedResultValue>
{
	IRI getIRI();

	String getPrefixedName();
}