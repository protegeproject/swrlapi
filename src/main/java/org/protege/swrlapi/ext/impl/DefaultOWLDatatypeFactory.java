package org.protege.swrlapi.ext.impl;

import java.net.URI;
import java.net.URISyntaxException;

import org.protege.owl.portability.model.OWLDatatypeAdapter;
import org.protege.owl.portability.model.XSDNames;
import org.protege.owl.portability.model.impl.OWLDatatypeAdapterImpl;
import org.protege.swrlapi.ext.OWLDatatypeFactory;

public class DefaultOWLDatatypeFactory implements OWLDatatypeFactory
{

	@Override
	public OWLDatatypeAdapter getOWLDatatype(URI uri)
	{
		OWLDatatypeAdapter datatype = new OWLDatatypeAdapterImpl(uri, XSDNames.Prefixed.BOOLEAN);
		return datatype;
	}

	@Override
	public OWLDatatypeAdapter getOWLBooleanDatatype()
	{
		try {
			URI uri = new URI(XSDNames.Full.BOOLEAN);
			OWLDatatypeAdapter datatype = new OWLDatatypeAdapterImpl(uri, XSDNames.Prefixed.BOOLEAN);
			return datatype;
		} catch (URISyntaxException e) {
			return null;
		}
	}

	@Override
	public OWLDatatypeAdapter getOWLShortDatatype()
	{
		try {
			URI uri = new URI(XSDNames.Full.SHORT);
			OWLDatatypeAdapter datatype = new OWLDatatypeAdapterImpl(uri, XSDNames.Prefixed.SHORT);
			return datatype;
		} catch (URISyntaxException e) {
			return null;
		}
	}

	@Override
	public OWLDatatypeAdapter getOWLDoubleDatatype()
	{
		try {
			URI uri = new URI(XSDNames.Full.DOUBLE);
			OWLDatatypeAdapter datatype = new OWLDatatypeAdapterImpl(uri, XSDNames.Prefixed.DOUBLE);
			return datatype;
		} catch (URISyntaxException e) {
			return null;
		}
	}

	@Override
	public OWLDatatypeAdapter getOWLFloatDatatype()
	{
		try {
			URI uri = new URI(XSDNames.Full.FLOAT);
			OWLDatatypeAdapter datatype = new OWLDatatypeAdapterImpl(uri, XSDNames.Prefixed.FLOAT);
			return datatype;
		} catch (URISyntaxException e) {
			return null;
		}
	}

	@Override
	public OWLDatatypeAdapter getOWLIntegerDatatype()
	{
		try {
			URI uri = new URI(XSDNames.Full.INTEGER);
			OWLDatatypeAdapter datatype = new OWLDatatypeAdapterImpl(uri, XSDNames.Prefixed.INTEGER);
			return datatype;
		} catch (URISyntaxException e) {
			return null;
		}
	}

	@Override
	public OWLDatatypeAdapter getOWLLongDatatype()
	{
		try {
			URI uri = new URI(XSDNames.Full.LONG);
			OWLDatatypeAdapter datatype = new OWLDatatypeAdapterImpl(uri, XSDNames.Prefixed.LONG);
			return datatype;
		} catch (URISyntaxException e) {
			return null;
		}
	}

	@Override
	public OWLDatatypeAdapter getOWLStringDatatype()
	{
		try {
			URI uri = new URI(XSDNames.Full.STRING);
			OWLDatatypeAdapter datatype = new OWLDatatypeAdapterImpl(uri, XSDNames.Prefixed.STRING);
			return datatype;
		} catch (URISyntaxException e) {
			return null;
		}
	}

	@Override
	public OWLDatatypeAdapter getOWLByteDatatype()
	{
		try {
			URI uri = new URI(XSDNames.Full.FLOAT);
			OWLDatatypeAdapter datatype = new OWLDatatypeAdapterImpl(uri, XSDNames.Prefixed.BYTE);
			return datatype;
		} catch (URISyntaxException e) {
			return null;
		}
	}

	@Override
	public OWLDatatypeAdapter getOWLURIDatatype()
	{
		try {
			URI uri = new URI(XSDNames.Full.ANY_URI);
			OWLDatatypeAdapter datatype = new OWLDatatypeAdapterImpl(uri, XSDNames.Prefixed.ANY_URI);
			return datatype;
		} catch (URISyntaxException e) {
			return null;
		}
	}

	@Override
	public OWLDatatypeAdapter getOWLDateDatatype()
	{
		try {
			URI uri = new URI(XSDNames.Full.DATE);
			OWLDatatypeAdapter datatype = new OWLDatatypeAdapterImpl(uri, XSDNames.Prefixed.DATE);
			return datatype;
		} catch (URISyntaxException e) {
			return null;
		}
	}

	@Override
	public OWLDatatypeAdapter getOWLTimeDatatype()
	{
		try {
			URI uri = new URI(XSDNames.Full.TIME);
			OWLDatatypeAdapter datatype = new OWLDatatypeAdapterImpl(uri, XSDNames.Prefixed.TIME);
			return datatype;
		} catch (URISyntaxException e) {
			return null;
		}
	}

	@Override
	public OWLDatatypeAdapter getOWLDateTimeDatatype()
	{
		try {
			URI uri = new URI(XSDNames.Full.DATE_TIME);
			OWLDatatypeAdapter datatype = new OWLDatatypeAdapterImpl(uri, XSDNames.Prefixed.DATE_TIME);
			return datatype;
		} catch (URISyntaxException e) {
			return null;
		}
	}

	@Override
	public OWLDatatypeAdapter getOWLDurationDatatype()
	{
		try {
			URI uri = new URI(XSDNames.Full.DURATION);
			OWLDatatypeAdapter datatype = new OWLDatatypeAdapterImpl(uri, XSDNames.Prefixed.DURATION);
			return datatype;
		} catch (URISyntaxException e) {
			return null;
		}
	}

}
