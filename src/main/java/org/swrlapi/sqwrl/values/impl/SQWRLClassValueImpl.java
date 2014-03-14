package org.swrlapi.sqwrl.values.impl;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.values.SQWRLClassValue;

class SQWRLClassValueImpl extends SQWRLNamedResultValueImpl implements SQWRLClassValue
{
	public SQWRLClassValueImpl(IRI classIRI, String prefixedName)
	{
		super(classIRI, prefixedName);
	}
}
