package org.swrlapi.core.arguments.impl;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.swrlapi.core.arguments.SWRLIndividualBuiltInArgument;

class SWRLIndividualBuiltInArgumentImpl extends SWRLNamedBuiltInArgumentImpl implements SWRLIndividualBuiltInArgument
{
	private static final long serialVersionUID = 1L;

	public SWRLIndividualBuiltInArgumentImpl(IRI individualIRI)
	{
		super(individualIRI);
	}

	public SWRLIndividualBuiltInArgumentImpl(OWLNamedIndividual individual)
	{
		super(individual);
	}
}
