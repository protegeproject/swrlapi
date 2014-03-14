package org.swrlapi.sqwrl.values.impl;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.values.SQWRLPropertyValue;
import org.swrlapi.sqwrl.values.SQWRLResultValue;

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
