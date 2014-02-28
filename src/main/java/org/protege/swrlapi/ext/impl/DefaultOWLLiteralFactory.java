package org.protege.swrlapi.ext.impl;

import java.net.URI;

import org.protege.owl.portability.model.OWLDatatypeAdapter;
import org.protege.owl.portability.model.OWLLiteralAdapter;
import org.protege.owl.portability.model.impl.OWLLiteralAdapterImpl;
import org.protege.swrlapi.ext.OWLDatatypeFactory;
import org.protege.swrlapi.ext.OWLLiteralFactory;
import org.protege.swrlapi.xsd.XSDDate;
import org.protege.swrlapi.xsd.XSDDateTime;
import org.protege.swrlapi.xsd.XSDDuration;
import org.protege.swrlapi.xsd.XSDTime;

public class DefaultOWLLiteralFactory implements OWLLiteralFactory
{
	private final OWLDatatypeFactory datatypeFactory;

	public DefaultOWLLiteralFactory(OWLDatatypeFactory datatypeFactory)
	{
		this.datatypeFactory = datatypeFactory;
	}

	@Override
	public OWLLiteralAdapter getOWLLiteral(boolean b)
	{
		return new OWLLiteralAdapterImpl(b, getOWLDatatypeFactory().getOWLBooleanDatatype());
	}

	@Override
	public OWLLiteralAdapter getOWLLiteral(short s)
	{
		return new OWLLiteralAdapterImpl(s, getOWLDatatypeFactory().getOWLShortDatatype());
	}

	@Override
	public OWLLiteralAdapter getOWLLiteral(double d)
	{
		return new OWLLiteralAdapterImpl(d, getOWLDatatypeFactory().getOWLDoubleDatatype());
	}

	@Override
	public OWLLiteralAdapter getOWLLiteral(float f)
	{
		return new OWLLiteralAdapterImpl(f, getOWLDatatypeFactory().getOWLFloatDatatype());
	}

	@Override
	public OWLLiteralAdapter getOWLLiteral(int i)
	{
		return new OWLLiteralAdapterImpl(i, getOWLDatatypeFactory().getOWLIntegerDatatype());
	}

	@Override
	public OWLLiteralAdapter getOWLLiteral(long l)
	{
		return new OWLLiteralAdapterImpl(l, getOWLDatatypeFactory().getOWLLongDatatype());
	}

	@Override
	public OWLLiteralAdapter getOWLLiteral(String s)
	{
		return new OWLLiteralAdapterImpl(s, getOWLDatatypeFactory().getOWLStringDatatype());
	}

	@Override
	public OWLLiteralAdapter getOWLLiteral(byte b)
	{
		return new OWLLiteralAdapterImpl(b, getOWLDatatypeFactory().getOWLByteDatatype());
	}

	@Override
	public OWLLiteralAdapter getOWLLiteral(URI uri)
	{
		return new OWLLiteralAdapterImpl(uri, getOWLDatatypeFactory().getOWLURIDatatype());
	}

	@Override
	public OWLLiteralAdapter getOWLLiteral(XSDDate date)
	{
		return new OWLLiteralAdapterImpl(date, getOWLDatatypeFactory().getOWLDateDatatype());
	}

	@Override
	public OWLLiteralAdapter getOWLLiteral(XSDTime time)
	{
		return new OWLLiteralAdapterImpl(time, getOWLDatatypeFactory().getOWLTimeDatatype());
	}

	@Override
	public OWLLiteralAdapter getOWLLiteral(XSDDateTime datetime)
	{
		return new OWLLiteralAdapterImpl(datetime, getOWLDatatypeFactory().getOWLDateTimeDatatype());
	}

	@Override
	public OWLLiteralAdapter getOWLLiteral(XSDDuration duration)
	{
		return new OWLLiteralAdapterImpl(duration, getOWLDatatypeFactory().getOWLDurationDatatype());
	}

	@Override
	public OWLLiteralAdapter getOWLLiteral(String literal, OWLDatatypeAdapter datatype)
	{
		return new OWLLiteralAdapterImpl(literal, datatype);
	}

	private OWLDatatypeFactory getOWLDatatypeFactory()
	{
		return this.datatypeFactory;
	}
}
