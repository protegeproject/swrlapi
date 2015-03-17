package org.swrlapi.builtins.arguments.impl;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitor;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;

import javax.annotation.Nonnull;

import java.util.Collections;
import java.util.Set;

class SWRLClassBuiltInArgumentImpl extends SWRLNamedBuiltInArgumentImpl implements SWRLClassBuiltInArgument
{
	private static final long serialVersionUID = 1L;

	public SWRLClassBuiltInArgumentImpl(OWLClass cls)
	{
		super(cls);
	}

	@Override
	public OWLClass getOWLClass()
	{
		return getOWLEntity().asOWLClass();
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
