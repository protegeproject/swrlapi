package org.protege.swrlapi.core.arguments.impl;

import java.net.URI;

import org.protege.owl.portability.model.OWLNamedObjectAdapter;
import org.protege.swrlapi.core.arguments.SWRLPropertyAtomArgument;

abstract class SWRLPropertyAtomArgumentImpl extends SWRLNamedAtomArgumentImpl implements SWRLPropertyAtomArgument
{
	protected SWRLPropertyAtomArgumentImpl(URI propertyURI, String prefixedName)
	{
		super(propertyURI, prefixedName);
	}

	protected SWRLPropertyAtomArgumentImpl(OWLNamedObjectAdapter property)
	{
		super(property);
	}
}