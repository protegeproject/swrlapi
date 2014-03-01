package org.protege.swrlapi.sqwrl.values.impl;

import org.protege.swrlapi.ext.impl.DefaultSWRLAPILiteral;
import org.protege.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.protege.swrlapi.sqwrl.values.SQWRLResultValue;
import org.semanticweb.owlapi.model.OWLLiteral;

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

	// TODO Not pleasant. See {@link OWLLiteralImpl#compareTo} and {@link SWRLAPILiteralImpl#compareTo}.
	@Override
	public int compareTo(SQWRLResultValue value)
	{
		return super.compareTo((SQWRLLiteralResultValue)value);
	}
}
