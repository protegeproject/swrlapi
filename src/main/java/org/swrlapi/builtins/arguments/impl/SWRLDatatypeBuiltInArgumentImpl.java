package org.swrlapi.builtins.arguments.impl;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitor;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;

import javax.annotation.Nonnull;

import java.util.Collections;
import java.util.Set;

class SWRLDatatypeBuiltInArgumentImpl extends SWRLNamedBuiltInArgumentImpl implements SWRLDatatypeBuiltInArgument
{
	private static final long serialVersionUID = 1L;

	public SWRLDatatypeBuiltInArgumentImpl(OWLDatatype datatype)
	{
		super(datatype);
	}

	@Override
	public OWLDatatype getOWLDatatype()
	{
		return getOWLEntity().asOWLDatatype();
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
