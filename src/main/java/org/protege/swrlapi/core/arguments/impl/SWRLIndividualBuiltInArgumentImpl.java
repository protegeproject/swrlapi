package org.protege.swrlapi.core.arguments.impl;

import java.net.URI;

import org.protege.swrlapi.core.arguments.SWRLIndividualBuiltInArgument;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

class SWRLIndividualBuiltInArgumentImpl extends SWRLNamedBuiltInArgumentImpl implements SWRLIndividualBuiltInArgument
{
	public SWRLIndividualBuiltInArgumentImpl(URI individualURI, String prefixedName)
	{
		super(individualURI, prefixedName);
	}

	public SWRLIndividualBuiltInArgumentImpl(OWLNamedIndividual individual)
	{
		super(individual);
	}
}
