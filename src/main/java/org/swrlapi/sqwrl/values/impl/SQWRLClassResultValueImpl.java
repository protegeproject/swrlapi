package org.swrlapi.sqwrl.values.impl;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.values.SQWRLClassResultValue;

class SQWRLClassResultValueImpl extends SQWRLEntityResultValueImpl implements SQWRLClassResultValue
{
	public SQWRLClassResultValueImpl(IRI classIRI, String prefixedName)
	{
		super(classIRI, prefixedName);
	}

	@Override
	public boolean isClass() { return true; }
}
