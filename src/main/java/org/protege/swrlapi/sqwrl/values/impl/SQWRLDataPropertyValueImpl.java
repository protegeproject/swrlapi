package org.protege.swrlapi.sqwrl.values.impl;

import org.protege.swrlapi.sqwrl.values.SQWRLDataPropertyValue;
import org.semanticweb.owlapi.model.IRI;

class SQWRLDataPropertyValueImpl extends SQWRLPropertyValueImpl implements SQWRLDataPropertyValue
{
	public SQWRLDataPropertyValueImpl(IRI propertyIRI, String prefixedName)
	{
		super(propertyIRI, prefixedName);
	}
}
