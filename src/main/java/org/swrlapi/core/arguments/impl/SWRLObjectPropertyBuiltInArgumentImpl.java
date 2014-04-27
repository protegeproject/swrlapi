package org.swrlapi.core.arguments.impl;

import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.core.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;

class SWRLObjectPropertyBuiltInArgumentImpl extends SWRLNamedBuiltInArgumentImpl implements
		SWRLObjectPropertyBuiltInArgument
{
	private static final long serialVersionUID = 1L;

	public SWRLObjectPropertyBuiltInArgumentImpl(OWLObjectProperty property)
	{
		super(property);
	}

	@Override
	public OWLObjectProperty getOWLObjectProperty()
	{
		return getOWLEntity().asOWLObjectProperty();
	}

	@Override
	public <T> T accept(SWRLBuiltInArgumentVisitorEx<T> visitor)
	{
		return visitor.visit(this);
	}
}