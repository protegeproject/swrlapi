package org.protege.swrlapi.sqwrl.values.impl;

import org.protege.swrlapi.sqwrl.values.SQWRLPropertyValue;
import org.protege.swrlapi.sqwrl.values.SQWRLResultValue;
import org.semanticweb.owlapi.model.IRI;

abstract class SQWRLPropertyValueImpl extends SQWRLNamedResultValueImpl implements SQWRLPropertyValue
{
	protected SQWRLPropertyValueImpl(IRI propertyIRI, String prefixedName)
	{
		super(propertyIRI, prefixedName);
	}

	// TODO fix
	@Override
	public int compareTo(SQWRLResultValue o)
	{
		return super.compareTo(o);
	}
}
