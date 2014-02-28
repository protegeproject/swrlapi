package org.protege.swrlapi.sqwrl.values.impl;

import java.net.URI;

import org.protege.swrlapi.sqwrl.values.SQWRLDataPropertyValue;

class SQWRLDataPropertyValueImpl extends SQWRLPropertyValueImpl implements SQWRLDataPropertyValue
{
	public SQWRLDataPropertyValueImpl(URI propertyURI, String prefixedName)
	{
		super(propertyURI, prefixedName);
	}
}
