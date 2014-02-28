package org.protege.swrlapi.core.arguments.impl;

import java.net.URI;

import org.protege.owl.portability.model.OWLDataPropertyAdapter;
import org.protege.swrlapi.core.arguments.SWRLDataPropertyAtomArgument;

class SWRLDataPropertyAtomArgumentImpl extends SWRLPropertyAtomArgumentImpl implements SWRLDataPropertyAtomArgument
{
	public SWRLDataPropertyAtomArgumentImpl(URI propertyURI, String prefixedName)
	{
		super(propertyURI, prefixedName);
	}

	public SWRLDataPropertyAtomArgumentImpl(OWLDataPropertyAdapter property)
	{
		super(property);
	}
}