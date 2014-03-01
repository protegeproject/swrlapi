package org.protege.swrlapi.core.arguments.impl;

import java.net.URI;

import org.protege.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;
import org.semanticweb.owlapi.model.OWLDataProperty;

class SWRLDataPropertyBuiltInArgumentImpl extends SWRLPropertyBuiltInArgumentImpl implements
		SWRLDataPropertyBuiltInArgument
{
	public SWRLDataPropertyBuiltInArgumentImpl(URI propertyURI, String prefixedName)
	{
		super(propertyURI, prefixedName);
	}

	public SWRLDataPropertyBuiltInArgumentImpl(OWLDataProperty property)
	{
		super(property);
	}
}
