package org.protege.swrlapi.ext.impl;

import java.net.URI;
import java.net.URISyntaxException;

import org.protege.swrlapi.ext.OWLDatatypeFactory;
import org.semanticweb.owlapi.model.OWLDatatype;

import uk.ac.manchester.cs.owl.owlapi.OWLDatatypeImpl;

public class DefaultOWLDatatypeFactory implements OWLDatatypeFactory
{

	@Override
	public OWLDatatype getOWLDatatype(URI uri)
	{
		OWLDatatype datatype = new OWLDatatypeImpl(uri, XSDNames.Prefixed.BOOLEAN);
		return datatype;
	}

	@Override
	public OWLDatatype getOWLBooleanDatatype()
	{
		try {
			URI uri = new URI(XSDNames.Full.BOOLEAN);
			OWLDatatype datatype = new OWLDatatypeImpl(uri, XSDNames.Prefixed.BOOLEAN);
			return datatype;
		} catch (URISyntaxException e) {
			return null;
		}
	}

	@Override
	public OWLDatatype getOWLShortDatatype()
	{
		try {
			URI uri = new URI(XSDNames.Full.SHORT);
			OWLDatatype datatype = new OWLDatatypeImpl(uri, XSDNames.Prefixed.SHORT);
			return datatype;
		} catch (URISyntaxException e) {
			return null;
		}
	}

	@Override
	public OWLDatatype getOWLDoubleDatatype()
	{
		try {
			URI uri = new URI(XSDNames.Full.DOUBLE);
			OWLDatatype datatype = new OWLDatatypeImpl(uri, XSDNames.Prefixed.DOUBLE);
			return datatype;
		} catch (URISyntaxException e) {
			return null;
		}
	}

	@Override
	public OWLDatatype getOWLFloatDatatype()
	{
		try {
			URI uri = new URI(XSDNames.Full.FLOAT);
			OWLDatatype datatype = new OWLDatatypeImpl(uri, XSDNames.Prefixed.FLOAT);
			return datatype;
		} catch (URISyntaxException e) {
			return null;
		}
	}

	@Override
	public OWLDatatype getOWLIntegerDatatype()
	{
		try {
			URI uri = new URI(XSDNames.Full.INTEGER);
			OWLDatatype datatype = new OWLDatatypeImpl(uri, XSDNames.Prefixed.INTEGER);
			return datatype;
		} catch (URISyntaxException e) {
			return null;
		}
	}

	@Override
	public OWLDatatype getOWLLongDatatype()
	{
		try {
			URI uri = new URI(XSDNames.Full.LONG);
			OWLDatatype datatype = new OWLDatatypeImpl(uri, XSDNames.Prefixed.LONG);
			return datatype;
		} catch (URISyntaxException e) {
			return null;
		}
	}

	@Override
	public OWLDatatype getOWLStringDatatype()
	{
		try {
			URI uri = new URI(XSDNames.Full.STRING);
			OWLDatatype datatype = new OWLDatatypeImpl(uri, XSDNames.Prefixed.STRING);
			return datatype;
		} catch (URISyntaxException e) {
			return null;
		}
	}

	@Override
	public OWLDatatype getOWLByteDatatype()
	{
		try {
			URI uri = new URI(XSDNames.Full.FLOAT);
			OWLDatatype datatype = new OWLDatatypeImpl(uri, XSDNames.Prefixed.BYTE);
			return datatype;
		} catch (URISyntaxException e) {
			return null;
		}
	}

	@Override
	public OWLDatatype getOWLURIDatatype()
	{
		try {
			URI uri = new URI(XSDNames.Full.ANY_URI);
			OWLDatatype datatype = new OWLDatatypeImpl(uri, XSDNames.Prefixed.ANY_URI);
			return datatype;
		} catch (URISyntaxException e) {
			return null;
		}
	}

	@Override
	public OWLDatatype getOWLDateDatatype()
	{
		try {
			URI uri = new URI(XSDNames.Full.DATE);
			OWLDatatype datatype = new OWLDatatypeImpl(uri, XSDNames.Prefixed.DATE);
			return datatype;
		} catch (URISyntaxException e) {
			return null;
		}
	}

	@Override
	public OWLDatatype getOWLTimeDatatype()
	{
		try {
			URI uri = new URI(XSDNames.Full.TIME);
			OWLDatatype datatype = new OWLDatatypeImpl(uri, XSDNames.Prefixed.TIME);
			return datatype;
		} catch (URISyntaxException e) {
			return null;
		}
	}

	@Override
	public OWLDatatype getOWLDateTimeDatatype()
	{
		try {
			URI uri = new URI(XSDNames.Full.DATE_TIME);
			OWLDatatype datatype = new OWLDatatypeImpl(uri, XSDNames.Prefixed.DATE_TIME);
			return datatype;
		} catch (URISyntaxException e) {
			return null;
		}
	}

	@Override
	public OWLDatatype getOWLDurationDatatype()
	{
		try {
			URI uri = new URI(XSDNames.Full.DURATION);
			OWLDatatype datatype = new OWLDatatypeImpl(uri, XSDNames.Prefixed.DURATION);
			return datatype;
		} catch (URISyntaxException e) {
			return null;
		}
	}

}
