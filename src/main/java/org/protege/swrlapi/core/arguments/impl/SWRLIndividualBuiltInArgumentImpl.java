package org.protege.swrlapi.core.arguments.impl;

import java.net.URI;

import org.protege.owl.portability.model.OWLNamedIndividualAdapter;
import org.protege.swrlapi.core.arguments.SWRLIndividualBuiltInArgument;

class SWRLIndividualBuiltInArgumentImpl extends SWRLNamedBuiltInArgumentImpl implements SWRLIndividualBuiltInArgument
{
	public SWRLIndividualBuiltInArgumentImpl(URI individualURI, String prefixedName)
	{
		super(individualURI, prefixedName);
	}

	public SWRLIndividualBuiltInArgumentImpl(OWLNamedIndividualAdapter individual)
	{
		super(individual);
	}
}
