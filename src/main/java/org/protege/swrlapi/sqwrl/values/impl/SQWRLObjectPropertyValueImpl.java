package org.protege.swrlapi.sqwrl.values.impl;

import org.protege.swrlapi.sqwrl.values.SQWRLObjectPropertyValue;
import org.semanticweb.owlapi.model.IRI;

class SQWRLObjectPropertyValueImpl extends SQWRLPropertyValueImpl implements SQWRLObjectPropertyValue
{
	public SQWRLObjectPropertyValueImpl(IRI propertyIRI, String prefixedName)
	{
		super(propertyIRI, prefixedName);
	}
}
