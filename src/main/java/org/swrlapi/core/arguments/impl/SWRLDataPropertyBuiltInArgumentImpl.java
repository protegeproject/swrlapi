package org.swrlapi.core.arguments.impl;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;

class SWRLDataPropertyBuiltInArgumentImpl extends SWRLPropertyBuiltInArgumentImpl implements
		SWRLDataPropertyBuiltInArgument
{
	private static final long serialVersionUID = 1L;

	public SWRLDataPropertyBuiltInArgumentImpl(IRI propertyIRI)
	{
		super(propertyIRI);
	}

	public SWRLDataPropertyBuiltInArgumentImpl(OWLDataProperty property)
	{
		super(property);
	}
}
