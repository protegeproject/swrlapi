package org.swrlapi.core.arguments.impl;

import java.util.Comparator;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObject;
import org.swrlapi.core.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.core.arguments.SWRLMultiValueVariableBuiltInArgument;
import org.swrlapi.core.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.ext.impl.DefaultSWRLAPILiteral;

class SWRLLiteralBuiltInArgumentImpl extends SWRLBuiltInArgumentImpl implements SWRLLiteralBuiltInArgument
{
	private static final long serialVersionUID = 1L;

	private static Comparator<String> naturalOrderComparator = NaturalOrderComparator.NUMERICAL_ORDER;

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
	public String toDisplayText()
	{
		return this.literal.toString();
	}

	@Override
	public String toString()
	{
		return toDisplayText();
	}

	/**
	 * TODO This is not correct. We really need a way of mapping to engine to deal with underlying types. See also
	 * {@link DefaultSWRLAPILiteral} and {@link SQWRLLiteralResultValueImpl}.
	 */
	public int compareTo(SWRLLiteralBuiltInArgument o)
	{
		OWLLiteral otherOWLLiteral = o.getLiteral();

		int diff = naturalOrderComparator.compare(this.literal.getLiteral(), otherOWLLiteral.getLiteral());
		if (diff != 0)
			return diff;

		diff = this.literal.getDatatype().compareTo(otherOWLLiteral.getDatatype());
		if (diff != 0)
			return diff;

		return this.literal.getLang().compareTo(otherOWLLiteral.getLang());
	}

	@Override
	public int compareTo(OWLObject o)
	{
		if (!(o instanceof SWRLLiteralBuiltInArgument))
			return -1;

		SWRLLiteralBuiltInArgument other = (SWRLLiteralBuiltInArgument)o;

		return compareTo(other);
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
}
