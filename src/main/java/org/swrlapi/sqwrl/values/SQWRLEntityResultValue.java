package org.swrlapi.sqwrl.values;

import org.semanticweb.owlapi.model.IRI;

public interface SQWRLEntityResultValue extends SQWRLResultValue, Comparable<SQWRLEntityResultValue>
{
	IRI getIRI();

	String getPrefixedName();

	String getShortName();
}