package org.swrlapi.sqwrl.values.impl;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.values.SQWRLAnnotationPropertyResultValue;

class SQWRLAnnotationPropertyResultValueImpl extends SQWRLPropertyResultValueImpl
		implements SQWRLAnnotationPropertyResultValue
{
	public SQWRLAnnotationPropertyResultValueImpl(IRI propertyIRI, String prefixedName)
	{
		super(propertyIRI, prefixedName);
	}

	@Override
	public boolean isAnnotationProperty() { return true; }
}
