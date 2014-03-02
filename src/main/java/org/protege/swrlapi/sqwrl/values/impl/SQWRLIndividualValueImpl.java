package org.protege.swrlapi.sqwrl.values.impl;

import org.protege.swrlapi.sqwrl.values.SQWRLIndividualValue;
import org.semanticweb.owlapi.model.IRI;

class SQWRLIndividualValueImpl extends SQWRLNamedResultValueImpl implements SQWRLIndividualValue
{
	public SQWRLIndividualValueImpl(IRI individualIRI, String prefixedName)
	{
		super(individualIRI, prefixedName);
	}
}
