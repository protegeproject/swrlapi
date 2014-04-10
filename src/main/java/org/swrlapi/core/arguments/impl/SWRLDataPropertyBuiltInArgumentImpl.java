package org.swrlapi.core.arguments.impl;

import org.semanticweb.owlapi.model.OWLDataProperty;
import org.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;

class SWRLDataPropertyBuiltInArgumentImpl extends SWRLNamedBuiltInArgumentImpl implements
		SWRLDataPropertyBuiltInArgument
{
	private static final long serialVersionUID = 1L;

	public SWRLDataPropertyBuiltInArgumentImpl(OWLDataProperty property)
	{
		super(property);
	}

	@Override
	public OWLDataProperty getOWLDataProperty()
	{
		return getOWLEntity().asOWLDataProperty();
	}
}
