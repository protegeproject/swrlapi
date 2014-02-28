package org.protege.swrlapi.sqwrl.values.impl;

import java.net.URI;

import org.protege.swrlapi.sqwrl.values.SQWRLIndividualValue;

class SQWRLIndividualValueImpl extends SQWRLNamedResultValueImpl implements SQWRLIndividualValue
{
	public SQWRLIndividualValueImpl(URI individualURI, String prefixedName)
	{
		super(individualURI, prefixedName);
	}
}
