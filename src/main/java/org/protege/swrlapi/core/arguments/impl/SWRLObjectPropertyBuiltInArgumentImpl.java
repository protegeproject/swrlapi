package org.protege.swrlapi.core.arguments.impl;

import java.net.URI;

import org.protege.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;
import org.semanticweb.owlapi.model.OWLObjectProperty;

class SWRLObjectPropertyBuiltInArgumentImpl extends SWRLPropertyBuiltInArgumentImpl implements
		SWRLObjectPropertyBuiltInArgument
{
	public SWRLObjectPropertyBuiltInArgumentImpl(URI propertyURI, String prefixedName)
	{
		super(propertyURI, prefixedName);
	}

	public SWRLObjectPropertyBuiltInArgumentImpl(OWLObjectProperty property)
	{
		super(property);
	}
}