package org.protege.swrlapi.core.arguments.impl;

import org.protege.owl.portability.model.OWLLiteralAdapter;
import org.protege.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLLiteralBuiltInArgument;

class SWRLLiteralBuiltInArgumentImpl extends SWRLBuiltInArgumentImpl implements SWRLLiteralBuiltInArgument
{
	private final OWLLiteralAdapter literal;

	public SWRLLiteralBuiltInArgumentImpl(OWLLiteralAdapter literal)
	{
		this.literal = literal;
	}

	@Override
	public OWLLiteralAdapter getLiteral()
	{
		return literal;
	}

	@Override
	public String toString()
	{
		return literal.toString();
	}

	// TODO: fix
	@Override
	public int compareTo(SWRLBuiltInArgument argument)
	{
		return literal.compareTo(((SWRLLiteralBuiltInArgument)argument).getLiteral());
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		SWRLLiteralBuiltInArgumentImpl impl = (SWRLLiteralBuiltInArgumentImpl)obj;
		return (literal != null && impl.literal != null && literal.equals(impl.literal));
	}

	@Override
	public int hashCode()
	{
		int hash = 12;
		hash = hash + (null == literal ? 0 : literal.hashCode());
		return hash;
	}
}
