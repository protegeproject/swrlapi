package org.protege.swrlapi.sqwrl.values.impl;

import java.net.URI;

import org.protege.swrlapi.sqwrl.values.SQWRLNamedResultValue;
import org.protege.swrlapi.sqwrl.values.SQWRLResultValue;

abstract class SQWRLNamedResultValueImpl implements SQWRLNamedResultValue
{
	private final URI classURI;
	private final String prefixedName;

	protected SQWRLNamedResultValueImpl(URI classURI, String prefixedName)
	{
		this.classURI = classURI;
		this.prefixedName = prefixedName;
	}

	@Override
	public URI getURI()
	{
		return classURI;
	}

	@Override
	public String getPrefixedName()
	{
		return prefixedName;
	}

	// TODO: fix
	@Override
	public int compareTo(SQWRLResultValue o)
	{
		return classURI.compareTo(((SQWRLNamedResultValueImpl)o).getURI());
	}

	@Override
	public String toString()
	{
		return getPrefixedName();
	}
}
