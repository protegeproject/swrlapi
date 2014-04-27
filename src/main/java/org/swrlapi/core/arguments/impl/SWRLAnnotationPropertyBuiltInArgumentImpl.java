package org.swrlapi.core.arguments.impl;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.swrlapi.core.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLBuiltInArgumentVisitorEx;

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

	@Override
	public <T> T accept(SWRLBuiltInArgumentVisitorEx<T> visitor)
	{
		return visitor.visit(this);
	}
}
