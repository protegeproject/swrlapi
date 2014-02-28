package org.protege.swrlapi.sqwrl.values.impl;

import java.net.URI;

import org.protege.swrlapi.sqwrl.values.SQWRLClassValue;

class SQWRLClassValueImpl extends SQWRLNamedResultValueImpl implements SQWRLClassValue
{
	public SQWRLClassValueImpl(URI classURI, String prefixedName)
	{
		super(classURI, prefixedName);
	}
}
