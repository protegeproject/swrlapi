package org.protege.swrlapi.core.arguments.impl;

import org.protege.swrlapi.core.arguments.SWRLIndividualBuiltInArgument;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

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
