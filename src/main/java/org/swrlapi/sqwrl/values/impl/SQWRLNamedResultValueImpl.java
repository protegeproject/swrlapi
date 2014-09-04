package org.swrlapi.sqwrl.values.impl;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.exceptions.SWRLAPIException;
import org.swrlapi.exceptions.SWRLAPIInternalException;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.swrlapi.sqwrl.values.SQWRLNamedResultValue;

abstract class SQWRLNamedResultValueImpl implements SQWRLNamedResultValue
{
	private final IRI iri;
	private final String prefixedName;

	protected SQWRLNamedResultValueImpl(IRI iri, String prefixedName)
	{
		this.iri = iri;
		this.prefixedName = prefixedName;
	}

	@Override
	public IRI getIRI()
	{
		return iri;
	}

	@Override
	public String getPrefixedName()
	{
		return this.prefixedName;
	}

	@Override
	public boolean isNamed()
	{
		return true;
	}

	@Override
	public boolean isLiteral()
	{
		return false;
	}

	@Override
	public SQWRLLiteralResultValue asLiteralResult()
	{
		throw new SWRLAPIException(getClass().getName() + " is not a " + SQWRLLiteralResultValue.class.getName());
	}

	@Override
	public SQWRLNamedResultValue asNamedResult()
	{
		return this;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		SQWRLNamedResultValueImpl n = (SQWRLNamedResultValueImpl)obj;

		return this.iri.equals(n.iri);
	}

	@Override
	public int hashCode()
	{
		int hash = 298;
		hash = hash + (null == this.iri ? 0 : this.iri.hashCode());
		return hash;
	}

	@Override
	public int compareTo(SQWRLNamedResultValue o)
	{
		return iri.compareTo(o.getIRI());
	}

	@Override
	public String toString()
	{
		return prefixedName;
	}
}
