package org.swrlapi.core.impl;

import java.net.URI;

import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.core.OWLLiteralFactory;
import org.swrlapi.core.SWRLAPIOWLDatatypeFactory;
import org.swrlapi.core.xsd.XSDDate;
import org.swrlapi.core.xsd.XSDDateTime;
import org.swrlapi.core.xsd.XSDDuration;
import org.swrlapi.core.xsd.XSDTime;

import uk.ac.manchester.cs.owl.owlapi.OWLLiteralImpl;

public class DefaultOWLLiteralFactory implements OWLLiteralFactory
{
	private final SWRLAPIOWLDatatypeFactory datatypeFactory;

	public DefaultOWLLiteralFactory()
	{
		this.datatypeFactory = new DefaultSWRLAPIOWLDatatypeFactory();
	}

	@Override
	public OWLLiteral getOWLLiteral(byte b)
	{
		return new OWLLiteralImpl("" + b, "", getOWLDatatypeFactory().getOWLByteDatatype());
	}

	@Override
	public OWLLiteral getOWLLiteral(short s)
	{
		return new OWLLiteralImpl("" + s, "", getOWLDatatypeFactory().getOWLShortDatatype());
	}

	@Override
	public OWLLiteral getOWLLiteral(int i)
	{
		return new OWLLiteralImpl("" + i, "", getOWLDatatypeFactory().getOWLIntDatatype());
	}

	@Override
	public OWLLiteral getOWLLiteral(long l)
	{
		return new OWLLiteralImpl("" + l, "", getOWLDatatypeFactory().getOWLLongDatatype());
	}

	@Override
	public OWLLiteral getOWLLiteral(float f)
	{
		return new OWLLiteralImpl("" + f, "", getOWLDatatypeFactory().getOWLFloatDatatype());
	}

	@Override
	public OWLLiteral getOWLLiteral(double d)
	{
		return new OWLLiteralImpl("" + d, "", getOWLDatatypeFactory().getOWLDoubleDatatype());
	}

	@Override
	public OWLLiteral getOWLLiteral(String s)
	{
		return new OWLLiteralImpl(s, "", getOWLDatatypeFactory().getOWLStringDatatype());
	}

	@Override
	public OWLLiteral getOWLLiteral(boolean b)
	{
		return new OWLLiteralImpl("" + b, "", getOWLDatatypeFactory().getOWLBooleanDatatype());
	}

	@Override
	public OWLLiteral getOWLLiteral(URI uri)
	{
		return new OWLLiteralImpl("" + uri, "", getOWLDatatypeFactory().getOWLURIDatatype());
	}

	@Override
	public OWLLiteral getOWLLiteral(XSDDate date)
	{
		return new OWLLiteralImpl(date.getContent(), "", getOWLDatatypeFactory().getOWLDateDatatype());
	}

	@Override
	public OWLLiteral getOWLLiteral(XSDTime time)
	{
		return new OWLLiteralImpl(time.getContent(), "", getOWLDatatypeFactory().getOWLTimeDatatype());
	}

	@Override
	public OWLLiteral getOWLLiteral(XSDDateTime datetime)
	{
		return new OWLLiteralImpl(datetime.getContent(), "", getOWLDatatypeFactory().getOWLDateTimeDatatype());
	}

	@Override
	public OWLLiteral getOWLLiteral(XSDDuration duration)
	{
		return new OWLLiteralImpl(duration.getContent(), "", getOWLDatatypeFactory().getOWLDurationDatatype());
	}

	@Override
	public OWLLiteral getOWLLiteral(String literal, OWLDatatype datatype)
	{
		return new OWLLiteralImpl(literal, "", datatype);
	}

	private SWRLAPIOWLDatatypeFactory getOWLDatatypeFactory()
	{
		return this.datatypeFactory;
	}
}
