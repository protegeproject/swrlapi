package org.protege.swrlapi.core.arguments.impl;

import java.net.URI;

import org.protege.swrlapi.core.arguments.SWRLPropertyBuiltInArgument;
import org.semanticweb.owlapi.model.OWLNamedObject;

abstract class SWRLPropertyBuiltInArgumentImpl extends SWRLNamedBuiltInArgumentImpl implements
		SWRLPropertyBuiltInArgument
{
	private static final long serialVersionUID = 1483948640425764795L;

	protected SWRLPropertyBuiltInArgumentImpl(URI propertyURI, String prefixedName)
	{
		super(propertyURI, prefixedName);
	}

	protected SWRLPropertyBuiltInArgumentImpl(OWLNamedObject property)
	{
		super(property);
	}
}