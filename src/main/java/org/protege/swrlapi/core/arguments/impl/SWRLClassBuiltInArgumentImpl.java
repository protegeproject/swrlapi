package org.protege.swrlapi.core.arguments.impl;

import java.net.URI;

import org.protege.owl.portability.model.OWLClassAdapter;
import org.protege.swrlapi.core.arguments.SWRLClassBuiltInArgument;

class SWRLClassBuiltInArgumentImpl extends SWRLNamedBuiltInArgumentImpl implements SWRLClassBuiltInArgument
{
	public SWRLClassBuiltInArgumentImpl(URI classURI, String prefixedName)
	{
		super(classURI, prefixedName);
	}

	public SWRLClassBuiltInArgumentImpl(OWLClassAdapter cls)
	{
		super(cls);
	}
}
