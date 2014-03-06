package org.protege.swrlapi.core.arguments.impl;

import org.protege.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataProperty;

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
