package org.protege.swrlapi.sqwrl.values.impl;

import org.protege.swrlapi.sqwrl.values.SQWRLClassValue;
import org.semanticweb.owlapi.model.IRI;

class SQWRLClassValueImpl extends SQWRLNamedResultValueImpl implements SQWRLClassValue
{
	public SQWRLClassValueImpl(IRI classIRI, String prefixedName)
	{
		super(classIRI, prefixedName);
	}
}
