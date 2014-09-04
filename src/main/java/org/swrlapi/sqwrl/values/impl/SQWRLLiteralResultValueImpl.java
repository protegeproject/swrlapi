package org.swrlapi.sqwrl.values.impl;

import java.util.Comparator;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.core.OWLLiteralComparator;
import org.swrlapi.core.impl.DefaultSWRLAPILiteral;
import org.swrlapi.exceptions.SWRLAPIException;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.swrlapi.sqwrl.values.SQWRLNamedResultValue;

/**
 * Implementation of a data value object that represents Java and XML Schema primitive data literals.
 */
class SQWRLLiteralResultValueImpl extends DefaultSWRLAPILiteral implements SQWRLLiteralResultValue
{
	private static Comparator<OWLLiteral> owlLiteralComparator = OWLLiteralComparator.COMPARATOR;

	public SQWRLLiteralResultValueImpl(OWLLiteral literal)
	{
		super(literal);
	}

	@Override
	public boolean isNamed()
	{
		return false;
	}

	@Override
	public boolean isLiteral()
	{
		return true;
	}

	@Override
	public SQWRLLiteralResultValue asLiteralResult()
	{
		return this;
	}

	@Override
	public SQWRLNamedResultValue asNamedResult()
	{
		throw new SWRLAPIException(getClass().getName() + " is not a " + SQWRLNamedResultValue.class.getName());
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		SQWRLLiteralResultValueImpl l = (SQWRLLiteralResultValueImpl)obj;

		return owlLiteralComparator.compare(this.getOWLLiteral(), l.getOWLLiteral()) == 0;
	}

	@Override
	public int hashCode()
	{
		int hash = 98;
		hash = hash + (null == this.getOWLLiteral() ? 0 : this.getOWLLiteral().hashCode());
		return hash;
	}

	@Override
	public int compareTo(SQWRLLiteralResultValue o)
	{
		return owlLiteralComparator.compare(this.getOWLLiteral(), o.getOWLLiteral());
	}
}
