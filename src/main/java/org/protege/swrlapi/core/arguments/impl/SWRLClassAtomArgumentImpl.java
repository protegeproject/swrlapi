package org.protege.swrlapi.core.arguments.impl;

import java.net.URI;

import org.protege.swrlapi.core.arguments.SWRLClassAtomArgument;
import org.semanticweb.owlapi.model.OWLClass;

class SWRLClassAtomArgumentImpl extends SWRLNamedAtomArgumentImpl implements SWRLClassAtomArgument
{
	public SWRLClassAtomArgumentImpl(URI classURI, String prefixedName)
	{
		super(classURI, prefixedName);
	}

	public SWRLClassAtomArgumentImpl(OWLClass cls)
	{
		super(cls);
	}
}
