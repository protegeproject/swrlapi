package org.protege.swrlapi.core.arguments.impl;

import org.protege.swrlapi.core.arguments.SWRLClassBuiltInArgument;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;

class SWRLClassBuiltInArgumentImpl extends SWRLNamedBuiltInArgumentImpl implements SWRLClassBuiltInArgument
{
	private static final long serialVersionUID = 6799830791612109948L;

	public SWRLClassBuiltInArgumentImpl(IRI classIRI, String prefixedName)
	{
		super(classIRI, prefixedName);
	}

	public SWRLClassBuiltInArgumentImpl(OWLClass cls)
	{
		super(cls);
	}
}
