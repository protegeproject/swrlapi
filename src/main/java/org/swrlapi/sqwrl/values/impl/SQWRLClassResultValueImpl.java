package org.swrlapi.sqwrl.values.impl;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.values.SQWRLClassResultValue;

class SQWRLClassResultValueImpl extends SQWRLNamedResultValueImpl implements SQWRLClassResultValue
{
	public SQWRLClassResultValueImpl(IRI classIRI, String prefixedName)
	{
		super(classIRI, prefixedName);
	}
}
