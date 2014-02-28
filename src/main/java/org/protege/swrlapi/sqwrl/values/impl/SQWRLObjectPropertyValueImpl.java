package org.protege.swrlapi.sqwrl.values.impl;

import java.net.URI;

import org.protege.swrlapi.sqwrl.values.SQWRLObjectPropertyValue;

class SQWRLObjectPropertyValueImpl extends SQWRLPropertyValueImpl implements SQWRLObjectPropertyValue
{
	public SQWRLObjectPropertyValueImpl(URI propertyURI, String prefixedName)
	{
		super(propertyURI, prefixedName);
	}
}
