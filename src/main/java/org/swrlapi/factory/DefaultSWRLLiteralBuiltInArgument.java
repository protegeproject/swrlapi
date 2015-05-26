package org.swrlapi.factory;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

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

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;

class DefaultSWRLLiteralBuiltInArgument extends DefaultSWRLBuiltInArgument implements SWRLLiteralBuiltInArgument
{
	private static final long serialVersionUID = 1L;

	@NonNull
	private static final Comparator<OWLLiteral> owlLiteralComparator = OWLLiteralComparator.COMPARATOR;

	@NonNull
	private final OWLLiteral literal;

	public DefaultSWRLLiteralBuiltInArgument(@NonNull OWLLiteral literal)
	{
		this.literal = literal;
	}

	@NonNull
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

	@NonNull
	@Override
	public SWRLVariableBuiltInArgument asVariable()
	{
		throw new SWRLAPIException("Not a SWRLVariableBuiltInArgument");
	}

	@NonNull
	@Override
	public SWRLMultiValueVariableBuiltInArgument asMultiValueVariable()
	{
		throw new SWRLAPIException("Not a SWRLMultiVariableBuiltInArgument");
	}

	@NonNull
	@Override
	public SWRLLiteralBuiltInArgument asSWRLLiteralBuiltInArgument()
	{
		return this;
	}

	@NonNull
	@Override
	public SWRLNamedBuiltInArgument asSWRLNamedBuiltInArgument()
	{
		throw new SWRLAPIException("Not a SWRLNamedBuiltInArgument");
	}

	@Override
	public boolean equals(@Nullable Object o)
	{
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		DefaultSWRLLiteralBuiltInArgument that = (DefaultSWRLLiteralBuiltInArgument)o;

		return this.literal.equals(that.literal);
	}

	@Override
	public int hashCode()
	{
		return this.literal.hashCode();
	}

	@Override
	public int compareTo(@NonNull OWLObject o)
	{
		if (o == null)
			throw new NullPointerException();

		if (!(o instanceof SWRLLiteralBuiltInArgument))
			return -1;

		SWRLLiteralBuiltInArgument other = (SWRLLiteralBuiltInArgument)o;

		return owlLiteralComparator.compare(this.getLiteral(), other.getLiteral());
	}

	@Override
	public void accept(@NonNull SWRLObjectVisitor visitor)
	{
		visitor.visit(this);
	}

	@NonNull
	@Override
	public <O> O accept(@NonNull SWRLObjectVisitorEx<O> visitor)
	{
		return visitor.visit(this);
	}

	@Override
	public void accept(@NonNull OWLObjectVisitor visitor)
	{
		visitor.visit(this);
	}

	@NonNull
	@Override
	public <O> O accept(@NonNull OWLObjectVisitorEx<O> visitor)
	{
		return visitor.visit(this);
	}

	@Override
	public <T> T accept(@NonNull SWRLBuiltInArgumentVisitorEx<T> visitor)
	{
		return visitor.visit(this);
	}

	@Override
	public void accept(@NonNull SWRLBuiltInArgumentVisitor visitor)
	{
		visitor.visit(this);
	}

	@NonNull
	@Override
	public String toString()
	{
		return this.literal.getLiteral();
	}

	@NonNull
	@Override
	public Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature()
	{
		return Collections.emptySet(); // TODO Implement getAnnotationPropertiesInSignature
	}
}
