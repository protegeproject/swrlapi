package org.protege.swrlapi.sqwrl.values.impl;

import java.net.URI;

import org.protege.swrlapi.sqwrl.values.SQWRLAnnotationPropertyValue;

class SQWRLAnnotationPropertyValueImpl extends SQWRLPropertyValueImpl implements SQWRLAnnotationPropertyValue
{
	public SQWRLAnnotationPropertyValueImpl(URI propertyURI, String prefixedName)
	{
		super(propertyURI, prefixedName);
	}
}
