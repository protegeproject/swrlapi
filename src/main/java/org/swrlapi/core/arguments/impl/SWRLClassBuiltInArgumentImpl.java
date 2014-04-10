package org.swrlapi.core.arguments.impl;

import org.semanticweb.owlapi.model.OWLClass;
import org.swrlapi.core.arguments.SWRLClassBuiltInArgument;

class SWRLClassBuiltInArgumentImpl extends SWRLNamedBuiltInArgumentImpl implements SWRLClassBuiltInArgument
{
	private static final long serialVersionUID = 1L;

	public SWRLClassBuiltInArgumentImpl(OWLClass cls)
	{
		super(cls);
	}

	@Override
	public OWLClass getOWLClass()
	{
		return getOWLEntity().asOWLClass();
	}
}
