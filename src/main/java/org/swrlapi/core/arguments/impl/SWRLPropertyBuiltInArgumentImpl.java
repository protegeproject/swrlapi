package org.swrlapi.core.arguments.impl;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLNamedObject;
import org.swrlapi.core.arguments.SWRLPropertyBuiltInArgument;

abstract class SWRLPropertyBuiltInArgumentImpl extends SWRLNamedBuiltInArgumentImpl implements
		SWRLPropertyBuiltInArgument
{
	private static final long serialVersionUID = 1L;

	protected SWRLPropertyBuiltInArgumentImpl(IRI propertyIRI)
	{
		super(propertyIRI);
	}

	protected SWRLPropertyBuiltInArgumentImpl(OWLNamedObject property)
	{
		super(property);
	}
}