package org.swrlapi.core.arguments.impl;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.swrlapi.core.arguments.SWRLClassBuiltInArgument;

class SWRLClassBuiltInArgumentImpl extends SWRLNamedBuiltInArgumentImpl implements SWRLClassBuiltInArgument
{
	private static final long serialVersionUID = 1L;

	public SWRLClassBuiltInArgumentImpl(IRI classIRI)
	{
		super(classIRI);
	}

	public SWRLClassBuiltInArgumentImpl(OWLClass cls)
	{
		super(cls);
	}
}
