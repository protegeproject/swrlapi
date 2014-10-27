package org.swrlapi.builtins.arguments.impl;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitor;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;

import javax.annotation.Nonnull;
import java.util.Set;

class SWRLNamedIndividualBuiltInArgumentImpl extends SWRLNamedBuiltInArgumentImpl implements
		SWRLNamedIndividualBuiltInArgument
{
	private static final long serialVersionUID = 1L;

	public SWRLNamedIndividualBuiltInArgumentImpl(OWLNamedIndividual individual)
	{
		super(individual);
	}

	@Override
	public OWLNamedIndividual getOWLNamedIndividual()
	{
		return getOWLEntity().asOWLNamedIndividual();
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
		return null; // TODO OWLAPI V4.0.0 update
	}
}
