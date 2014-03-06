package org.protege.swrlapi.core.arguments.impl;

import org.protege.swrlapi.core.arguments.SWRLPropertyBuiltInArgument;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLNamedObject;

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