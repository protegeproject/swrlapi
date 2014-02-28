package org.protege.swrlapi.core.arguments.impl;

import java.net.URI;

import org.protege.owl.portability.model.OWLClassAdapter;
import org.protege.swrlapi.core.arguments.SWRLClassAtomArgument;

class SWRLClassAtomArgumentImpl extends SWRLNamedAtomArgumentImpl implements SWRLClassAtomArgument
{
	public SWRLClassAtomArgumentImpl(URI classURI, String prefixedName)
	{
		super(classURI, prefixedName);
	}

	public SWRLClassAtomArgumentImpl(OWLClassAdapter cls)
	{
		super(cls);
	}
}
