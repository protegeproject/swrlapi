package org.swrlapi.core.arguments.impl;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.swrlapi.core.arguments.SWRLNamedIndividualBuiltInArgument;

class SWRLNamedIndividualBuiltInArgumentImpl extends SWRLNamedBuiltInArgumentImpl implements
		SWRLNamedIndividualBuiltInArgument
{
	private static final long serialVersionUID = 1L;

	public SWRLNamedIndividualBuiltInArgumentImpl(IRI individualIRI)
	{
		super(individualIRI);
	}

	public SWRLNamedIndividualBuiltInArgumentImpl(OWLNamedIndividual individual)
	{
		super(individual);
	}
}
