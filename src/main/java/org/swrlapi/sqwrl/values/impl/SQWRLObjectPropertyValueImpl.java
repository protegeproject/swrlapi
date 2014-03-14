package org.swrlapi.sqwrl.values.impl;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.values.SQWRLObjectPropertyValue;

class SQWRLObjectPropertyValueImpl extends SQWRLPropertyValueImpl implements SQWRLObjectPropertyValue
{
	public SQWRLObjectPropertyValueImpl(IRI propertyIRI, String prefixedName)
	{
		super(propertyIRI, prefixedName);
	}
}
