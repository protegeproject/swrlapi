package org.swrlapi.sqwrl.values.impl;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.values.SQWRLDataPropertyValue;

class SQWRLDataPropertyValueImpl extends SQWRLPropertyValueImpl implements SQWRLDataPropertyValue
{
	public SQWRLDataPropertyValueImpl(IRI propertyIRI, String prefixedName)
	{
		super(propertyIRI, prefixedName);
	}
}
