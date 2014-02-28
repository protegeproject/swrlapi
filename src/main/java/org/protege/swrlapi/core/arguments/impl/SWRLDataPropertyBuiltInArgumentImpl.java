package org.protege.swrlapi.core.arguments.impl;

import java.net.URI;

import org.protege.owl.portability.model.OWLDataPropertyAdapter;
import org.protege.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;

class SWRLDataPropertyBuiltInArgumentImpl extends SWRLPropertyBuiltInArgumentImpl implements
		SWRLDataPropertyBuiltInArgument
{
	public SWRLDataPropertyBuiltInArgumentImpl(URI propertyURI, String prefixedName)
	{
		super(propertyURI, prefixedName);
	}

	public SWRLDataPropertyBuiltInArgumentImpl(OWLDataPropertyAdapter property)
	{
		super(property);
	}
}
