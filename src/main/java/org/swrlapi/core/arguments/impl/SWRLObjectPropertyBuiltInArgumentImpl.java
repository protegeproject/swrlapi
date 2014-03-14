package org.swrlapi.core.arguments.impl;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;

class SWRLObjectPropertyBuiltInArgumentImpl extends SWRLPropertyBuiltInArgumentImpl implements
		SWRLObjectPropertyBuiltInArgument
{
	private static final long serialVersionUID = 1L;

	public SWRLObjectPropertyBuiltInArgumentImpl(IRI propertyIRI)
	{
		super(propertyIRI);
	}

	public SWRLObjectPropertyBuiltInArgumentImpl(OWLObjectProperty property)
	{
		super(property);
	}
}