package org.swrlapi.sqwrl.values.impl;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.ext.impl.DefaultSWRLAPILiteral;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.swrlapi.sqwrl.values.SQWRLResultValue;

/**
 * Implementation of a data value object that represents Java and XML Schema primitive data literals.
 */
class SQWRLLiteralResultValueImpl extends DefaultSWRLAPILiteral implements SQWRLLiteralResultValue
{
	public SQWRLLiteralResultValueImpl(OWLLiteral literal)
	{
		super(literal);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		SQWRLLiteralResultValueImpl l = (SQWRLLiteralResultValueImpl)obj;
		return super.equals(l);
	}

	/**
	 * TODO Not pleasant and probably incorrect. What about a SQWRLNamedResultValue? See
	 * {@link DefaultSWRLAPILiteral#compareTo(org.swrlapi.ext.SWRLAPILiteral)}.
	 */
	@Override
	public int compareTo(SQWRLResultValue value)
	{
		return super.compareTo((SQWRLLiteralResultValue)value);
	}
}
