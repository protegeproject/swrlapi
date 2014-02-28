package org.protege.swrlapi.core.arguments.impl;

import java.net.URI;

import org.protege.owl.portability.model.OWLNamedObjectAdapter;
import org.protege.swrlapi.core.arguments.SWRLPropertyBuiltInArgument;

abstract class SWRLPropertyBuiltInArgumentImpl extends SWRLNamedBuiltInArgumentImpl implements
		SWRLPropertyBuiltInArgument
{
	protected SWRLPropertyBuiltInArgumentImpl(URI propertyURI, String prefixedName)
	{
		super(propertyURI, prefixedName);
	}

	protected SWRLPropertyBuiltInArgumentImpl(OWLNamedObjectAdapter property)
	{
		super(property);
	}
}