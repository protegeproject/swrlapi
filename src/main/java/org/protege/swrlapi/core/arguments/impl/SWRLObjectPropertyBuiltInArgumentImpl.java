package org.protege.swrlapi.core.arguments.impl;

import java.net.URI;

import org.protege.owl.portability.model.OWLObjectPropertyAdapter;
import org.protege.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;

class SWRLObjectPropertyBuiltInArgumentImpl extends SWRLPropertyBuiltInArgumentImpl implements
		SWRLObjectPropertyBuiltInArgument
{
	public SWRLObjectPropertyBuiltInArgumentImpl(URI propertyURI, String prefixedName)
	{
		super(propertyURI, prefixedName);
	}

	public SWRLObjectPropertyBuiltInArgumentImpl(OWLObjectPropertyAdapter property)
	{
		super(property);
	}
}