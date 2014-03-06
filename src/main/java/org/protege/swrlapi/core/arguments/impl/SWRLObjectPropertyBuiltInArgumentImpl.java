package org.protege.swrlapi.core.arguments.impl;

import org.protege.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObjectProperty;

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