package org.protege.swrlapi.core.arguments.impl;

import java.net.URI;

import org.protege.owl.portability.model.OWLObjectPropertyAdapter;
import org.protege.swrlapi.core.arguments.SWRLObjectPropertyAtomArgument;

class SWRLObjectPropertyAtomArgumentImpl extends SWRLPropertyAtomArgumentImpl implements SWRLObjectPropertyAtomArgument
{
	public SWRLObjectPropertyAtomArgumentImpl(URI propertyURI, String prefixedName)
	{
		super(propertyURI, prefixedName);
	}

	public SWRLObjectPropertyAtomArgumentImpl(OWLObjectPropertyAdapter property)
	{
		super(property);
	}
}