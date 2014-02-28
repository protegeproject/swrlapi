package org.protege.swrlapi.core.arguments.impl;

import org.protege.owl.portability.model.OWLLiteralAdapter;
import org.protege.swrlapi.core.arguments.SWRLAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLLiteralAtomArgument;

class SWRLLiteralAtomArgumentImpl implements SWRLLiteralAtomArgument
{
	private final OWLLiteralAdapter literal;

	public SWRLLiteralAtomArgumentImpl(OWLLiteralAdapter literal)
	{
		this.literal = literal;
	}

	@Override
	public OWLLiteralAdapter getLiteral()
	{
		return this.literal;
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

	// TODO: fix
	public int compareTo(SWRLAtomArgument argument)
	{
		return this.literal.compareTo(((SWRLLiteralAtomArgument)argument).getLiteral());
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		SWRLLiteralAtomArgumentImpl impl = (SWRLLiteralAtomArgumentImpl)obj;
		return (getLiteral() == impl.getLiteral() || (getLiteral() != null && getLiteral().equals(impl.getLiteral())));
	}

	@Override
	public int hashCode()
	{
		int hash = 123;
		hash = hash + (null == getLiteral() ? 0 : getLiteral().hashCode());
		return hash;
	}
}
