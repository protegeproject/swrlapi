package org.swrlapi.core.arguments.impl;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.swrlapi.core.arguments.SWRLAnnotationPropertyBuiltInArgument;

class SWRLAnnotationPropertyBuiltInArgumentImpl extends SWRLNamedBuiltInArgumentImpl implements
		SWRLAnnotationPropertyBuiltInArgument
{
	private static final long serialVersionUID = 1L;

	public SWRLAnnotationPropertyBuiltInArgumentImpl(OWLAnnotationProperty property)
	{
		super(property);
	}

	@Override
	public OWLAnnotationProperty getOWLAnnotationProperty()
	{
		return getOWLEntity().asOWLAnnotationProperty();
	}
}
