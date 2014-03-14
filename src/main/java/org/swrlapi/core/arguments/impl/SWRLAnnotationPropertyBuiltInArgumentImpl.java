package org.swrlapi.core.arguments.impl;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.swrlapi.core.arguments.SWRLAnnotationPropertyBuiltInArgument;

class SWRLAnnotationPropertyBuiltInArgumentImpl extends SWRLPropertyBuiltInArgumentImpl implements
		SWRLAnnotationPropertyBuiltInArgument
{
	private static final long serialVersionUID = 1L;

	public SWRLAnnotationPropertyBuiltInArgumentImpl(IRI propertyIRI)
	{
		super(propertyIRI);
	}

	public SWRLAnnotationPropertyBuiltInArgumentImpl(OWLAnnotationProperty property)
	{
		super(property);
	}
}
