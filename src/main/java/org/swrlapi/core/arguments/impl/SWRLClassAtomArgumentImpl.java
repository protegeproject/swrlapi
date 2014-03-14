package org.swrlapi.core.arguments.impl;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.swrlapi.core.arguments.SWRLClassAtomArgument;

class SWRLClassAtomArgumentImpl extends SWRLNamedAtomArgumentImpl implements SWRLClassAtomArgument
{
	private static final long serialVersionUID = 1L;

	public SWRLClassAtomArgumentImpl(IRI classIRI)
	{
		super(classIRI);
	}

	public SWRLClassAtomArgumentImpl(OWLClass cls)
	{
		super(cls);
	}
}
