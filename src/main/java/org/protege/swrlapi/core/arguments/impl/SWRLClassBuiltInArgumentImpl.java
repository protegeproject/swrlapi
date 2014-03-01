package org.protege.swrlapi.core.arguments.impl;

import java.net.URI;

import org.protege.swrlapi.core.arguments.SWRLClassBuiltInArgument;
import org.semanticweb.owlapi.model.OWLClass;

class SWRLClassBuiltInArgumentImpl extends SWRLNamedBuiltInArgumentImpl implements SWRLClassBuiltInArgument
{
	public SWRLClassBuiltInArgumentImpl(URI classURI, String prefixedName)
	{
		super(classURI, prefixedName);
	}

	public SWRLClassBuiltInArgumentImpl(OWLClass cls)
	{
		super(cls);
	}
}
