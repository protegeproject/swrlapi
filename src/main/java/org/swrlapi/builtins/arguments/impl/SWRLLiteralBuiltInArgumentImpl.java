package org.swrlapi.builtins.arguments.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.SWRLObjectVisitor;
import org.semanticweb.owlapi.model.SWRLObjectVisitorEx;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitor;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.core.OWLLiteralComparator;
import org.swrlapi.exceptions.SWRLAPIException;

class SWRLLiteralBuiltInArgumentImpl extends SWRLBuiltInArgumentImpl implements SWRLLiteralBuiltInArgument
{
	private static final long serialVersionUID = 1L;

	private static Comparator<OWLLiteral> owlLiteralComparator = OWLLiteralComparator.COMPARATOR;

	private final OWLLiteral literal;

	public SWRLLiteralBuiltInArgumentImpl(OWLLiteral literal)
	{
		this.literal = literal;
	}

	@Override
	public OWLLiteral getLiteral()
	{
		return this.literal;
	}

	@Override
	public boolean isVariable()
	{
		return false;
	}

	@Override
	public boolean isMultiValueVariable()
	{
		return false;
	}

	public @Override boolean isLiteral()
	{
		return false;
	}

	public @Override boolean isNamed()
	{
		return true;
	}

	@Override
	public SWRLVariableBuiltInArgument asVariable()
	{
		throw new SWRLAPIException("Not a SWRLVariableBuiltInArgument");
	}

	@Override
	public SWRLMultiValueVariableBuiltInArgument asMultiValueVariable()
	{
		throw new SWRLAPIException("Not a SWRLMultiVariableBuiltInArgument");
	}

	@Override
	public SWRLLiteralBuiltInArgument asSWRLLiteralBuiltInArgument()
	{
		return this;
	}

	@Override
	public SWRLNamedBuiltInArgument asSWRLNamedBuiltInArgument()
	{
		throw new SWRLAPIException("Not a SWRLNamedBuiltInArgument");
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		SWRLLiteralBuiltInArgumentImpl that = (SWRLLiteralBuiltInArgumentImpl)o;

		if (!literal.equals(that.literal)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode()
	{
		return literal.hashCode();
	}

	@Override
	public int compareTo(OWLObject o)
	{
		if (!(o instanceof SWRLLiteralBuiltInArgument))
			return -1;

		SWRLLiteralBuiltInArgument other = (SWRLLiteralBuiltInArgument)o;

		return owlLiteralComparator.compare(this.getLiteral(), other.getLiteral());
	}

	@Override
	public void accept(SWRLObjectVisitor visitor)
	{
		visitor.visit(this);
	}

	@Override
	public <O> O accept(SWRLObjectVisitorEx<O> visitor)
	{
		return visitor.visit(this);
	}

	@Override
	public void accept(OWLObjectVisitor visitor)
	{
		visitor.visit(this);
	}

	@Override
	public <O> O accept(OWLObjectVisitorEx<O> visitor)
	{
		return visitor.visit(this);
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

	@Override
	public String toString()
	{
		return this.literal.getLiteral();
	}

	@Nonnull
	@Override
	public Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature()
	{
		return Collections.emptySet(); // TODO OWLAPI V4.0.0 update
	}
}
