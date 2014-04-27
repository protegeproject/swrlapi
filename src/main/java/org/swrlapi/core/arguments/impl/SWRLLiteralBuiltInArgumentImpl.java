package org.swrlapi.core.arguments.impl;

import java.util.Comparator;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.SWRLObjectVisitor;
import org.semanticweb.owlapi.model.SWRLObjectVisitorEx;
import org.swrlapi.core.OWLLiteralComparator;
import org.swrlapi.core.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.core.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.core.arguments.SWRLMultiValueVariableBuiltInArgument;
import org.swrlapi.core.arguments.SWRLVariableBuiltInArgument;

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

	@Override
	public SWRLVariableBuiltInArgument asVariable()
	{
		throw new RuntimeException("Not a SWRLVariableBuiltInArgument");
	}

	@Override
	public SWRLMultiValueVariableBuiltInArgument asMultiValueVariable()
	{
		throw new RuntimeException("Not a SWRLMultiVariableBuiltInArgument");
	}

	@Override
	public <T> T accept(SWRLBuiltInArgumentVisitorEx<T> visitor)
	{
		return visitor.visit(this);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		SWRLLiteralBuiltInArgumentImpl impl = (SWRLLiteralBuiltInArgumentImpl)obj;
		return (getLiteral() == impl.getLiteral() || (getLiteral() != null && getLiteral().equals(impl.getLiteral())));
	}

	@Override
	public int hashCode()
	{
		int hash = 12;
		hash = hash + (null == getLiteral() ? 0 : getLiteral().hashCode());
		return hash;
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
}
