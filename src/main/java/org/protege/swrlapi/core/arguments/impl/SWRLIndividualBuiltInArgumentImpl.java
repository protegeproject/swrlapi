package org.protege.swrlapi.core.arguments.impl;

import org.protege.swrlapi.core.arguments.SWRLIndividualBuiltInArgument;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

class SWRLIndividualBuiltInArgumentImpl extends SWRLNamedBuiltInArgumentImpl implements SWRLIndividualBuiltInArgument
{
	private static final long serialVersionUID = 5242701530881210633L;

	public SWRLIndividualBuiltInArgumentImpl(IRI individualIRI, String prefixedName)
	{
		super(individualIRI, prefixedName);
	}

	public SWRLIndividualBuiltInArgumentImpl(OWLNamedIndividual individual)
	{
		super(individual);
	}
}
