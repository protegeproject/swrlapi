package org.protege.swrlapi.sqwrl.values.impl;

import org.protege.swrlapi.sqwrl.values.SQWRLAnnotationPropertyValue;
import org.semanticweb.owlapi.model.IRI;

class SQWRLAnnotationPropertyValueImpl extends SQWRLPropertyValueImpl implements SQWRLAnnotationPropertyValue
{
	public SQWRLAnnotationPropertyValueImpl(IRI propertyIRI, String prefixedName)
	{
		super(propertyIRI, prefixedName);
	}
}
