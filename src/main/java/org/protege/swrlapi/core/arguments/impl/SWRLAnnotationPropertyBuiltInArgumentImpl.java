package org.protege.swrlapi.core.arguments.impl;

import java.net.URI;

import org.protege.swrlapi.core.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;

class SWRLAnnotationPropertyBuiltInArgumentImpl extends SWRLPropertyBuiltInArgumentImpl implements
		SWRLAnnotationPropertyBuiltInArgument
{
	public SWRLAnnotationPropertyBuiltInArgumentImpl(URI propertyURI, String prefixedName)
	{
		super(propertyURI, prefixedName);
	}

	public SWRLAnnotationPropertyBuiltInArgumentImpl(OWLAnnotationProperty property)
	{
		super(property);
	}
}
