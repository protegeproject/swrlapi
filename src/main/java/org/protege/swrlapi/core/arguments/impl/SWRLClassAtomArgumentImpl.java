package org.protege.swrlapi.core.arguments.impl;

import org.protege.swrlapi.core.arguments.SWRLClassAtomArgument;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;

class SWRLClassAtomArgumentImpl extends SWRLNamedAtomArgumentImpl implements SWRLClassAtomArgument
{
	private static final long serialVersionUID = -1835060093639991034L;

	public SWRLClassAtomArgumentImpl(IRI classIRI, String prefixedName)
	{
		super(classIRI, prefixedName);
	}

	public SWRLClassAtomArgumentImpl(OWLClass cls)
	{
		super(cls);
	}
}
