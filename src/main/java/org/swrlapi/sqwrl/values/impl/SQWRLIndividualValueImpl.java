package org.swrlapi.sqwrl.values.impl;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.values.SQWRLIndividualValue;

class SQWRLIndividualValueImpl extends SQWRLNamedResultValueImpl implements SQWRLIndividualValue
{
	public SQWRLIndividualValueImpl(IRI individualIRI, String prefixedName)
	{
		super(individualIRI, prefixedName);
	}
}
