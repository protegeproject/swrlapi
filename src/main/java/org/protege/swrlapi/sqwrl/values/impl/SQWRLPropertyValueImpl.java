package org.protege.swrlapi.sqwrl.values.impl;

import java.net.URI;

import org.protege.swrlapi.sqwrl.values.SQWRLPropertyValue;
import org.protege.swrlapi.sqwrl.values.SQWRLResultValue;

abstract class SQWRLPropertyValueImpl extends SQWRLNamedResultValueImpl implements SQWRLPropertyValue
{
	protected SQWRLPropertyValueImpl(URI propertyURI, String prefixedName)
	{
		super(propertyURI, prefixedName);
	}

	// TODO fix
	@Override
	public int compareTo(SQWRLResultValue o)
	{
		return super.compareTo(o);
	}
}
