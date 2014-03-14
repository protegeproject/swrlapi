package org.swrlapi.sqwrl.values.impl;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.values.SQWRLAnnotationPropertyValue;

class SQWRLAnnotationPropertyValueImpl extends SQWRLPropertyValueImpl implements SQWRLAnnotationPropertyValue
{
	public SQWRLAnnotationPropertyValueImpl(IRI propertyIRI, String prefixedName)
	{
		super(propertyIRI, prefixedName);
	}
}
