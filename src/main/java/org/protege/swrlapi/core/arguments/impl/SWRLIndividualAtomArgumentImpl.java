package org.protege.swrlapi.core.arguments.impl;

import java.net.URI;

import org.protege.swrlapi.core.arguments.SWRLIndividualAtomArgument;

class SWRLIndividualAtomArgumentImpl extends SWRLNamedAtomArgumentImpl implements SWRLIndividualAtomArgument
{
	public SWRLIndividualAtomArgumentImpl(URI individualURI, String prefixedName)
	{
		super(individualURI, prefixedName);
	}
}
