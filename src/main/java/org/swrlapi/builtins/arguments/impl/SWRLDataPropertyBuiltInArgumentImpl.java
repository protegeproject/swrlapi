package org.swrlapi.builtins.arguments.impl;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitor;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;

import javax.annotation.Nonnull;

import java.util.Collections;
import java.util.Set;

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

	@Override
	public <T> T accept(SWRLBuiltInArgumentVisitorEx<T> visitor)
	{
		return visitor.visit(this);
	}

	@Override
	public void accept(SWRLBuiltInArgumentVisitor visitor)
	{
		visitor.visit(this);
	}

	@Nonnull @Override public Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature()
	{
		return Collections.emptySet(); // TODO OWLAPI V4.0.0 update
	}
}
