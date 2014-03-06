package org.protege.swrlapi.core.arguments.impl;

import org.protege.swrlapi.core.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;

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
