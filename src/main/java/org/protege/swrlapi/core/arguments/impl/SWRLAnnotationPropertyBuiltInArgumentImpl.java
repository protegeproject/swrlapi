package org.protege.swrlapi.core.arguments.impl;

import java.net.URI;

import org.protege.owl.portability.model.OWLAnnotationPropertyAdapter;
import org.protege.swrlapi.core.arguments.SWRLAnnotationPropertyBuiltInArgument;

class SWRLAnnotationPropertyBuiltInArgumentImpl extends SWRLPropertyBuiltInArgumentImpl implements
		SWRLAnnotationPropertyBuiltInArgument
{
	public SWRLAnnotationPropertyBuiltInArgumentImpl(URI propertyURI, String prefixedName)
	{
		super(propertyURI, prefixedName);
	}

	public SWRLAnnotationPropertyBuiltInArgumentImpl(OWLAnnotationPropertyAdapter property)
	{
		super(property);
	}
}
