package org.swrlapi.core.impl;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.vocab.XSDVocabulary;
import org.swrlapi.core.SWRLAPIOWLDatatypeFactory;

import uk.ac.manchester.cs.owl.owlapi.OWLDatatypeImpl;

public class DefaultSWRLAPIOWLDatatypeFactory implements SWRLAPIOWLDatatypeFactory
{
	@Override
	public OWLDatatype getOWLDatatype(IRI iri)
	{
		OWLDatatype datatype = new OWLDatatypeImpl(iri);
		return datatype;
	}

	@Override
	public OWLDatatype getOWLBooleanDatatype()
	{
		IRI iri = XSDVocabulary.BOOLEAN.getIRI();
		OWLDatatype datatype = new OWLDatatypeImpl(iri);
		return datatype;
	}

	@Override
	public OWLDatatype getOWLShortDatatype()
	{
		IRI iri = XSDVocabulary.SHORT.getIRI();
		OWLDatatype datatype = new OWLDatatypeImpl(iri);
		return datatype;
	}

	@Override
	public OWLDatatype getOWLDoubleDatatype()
	{
		IRI iri = XSDVocabulary.DOUBLE.getIRI();
		OWLDatatype datatype = new OWLDatatypeImpl(iri);
		return datatype;
	}

	@Override
	public OWLDatatype getOWLFloatDatatype()
	{
		IRI iri = XSDVocabulary.FLOAT.getIRI();
		OWLDatatype datatype = new OWLDatatypeImpl(iri);
		return datatype;
	}

	@Override
	public OWLDatatype getOWLIntegerDatatype()
	{
		IRI iri = XSDVocabulary.INTEGER.getIRI();
		OWLDatatype datatype = new OWLDatatypeImpl(iri);
		return datatype;
	}

	@Override
	public OWLDatatype getOWLIntDatatype()
	{
		IRI iri = XSDVocabulary.INT.getIRI();
		OWLDatatype datatype = new OWLDatatypeImpl(iri);
		return datatype;
	}

	@Override
	public OWLDatatype getOWLLongDatatype()
	{
		IRI iri = XSDVocabulary.LONG.getIRI();
		OWLDatatype datatype = new OWLDatatypeImpl(iri);
		return datatype;
	}

	@Override
	public OWLDatatype getOWLStringDatatype()
	{
		IRI iri = XSDVocabulary.STRING.getIRI();
		OWLDatatype datatype = new OWLDatatypeImpl(iri);
		return datatype;
	}

	@Override
	public OWLDatatype getOWLByteDatatype()
	{
		IRI iri = XSDVocabulary.FLOAT.getIRI();
		OWLDatatype datatype = new OWLDatatypeImpl(iri);
		return datatype;
	}

	@Override
	public OWLDatatype getOWLURIDatatype()
	{
		IRI iri = XSDVocabulary.ANY_URI.getIRI();
		OWLDatatype datatype = new OWLDatatypeImpl(iri);
		return datatype;
	}

	@Override
	public OWLDatatype getOWLDateDatatype()
	{
		IRI iri = XSDVocabulary.DATE.getIRI();
		OWLDatatype datatype = new OWLDatatypeImpl(iri);
		return datatype;
	}

	@Override
	public OWLDatatype getOWLTimeDatatype()
	{
		IRI iri = XSDVocabulary.TIME.getIRI();
		OWLDatatype datatype = new OWLDatatypeImpl(iri);
		return datatype;
	}

	@Override
	public OWLDatatype getOWLDateTimeDatatype()
	{
		IRI iri = XSDVocabulary.DATE_TIME.getIRI();
		OWLDatatype datatype = new OWLDatatypeImpl(iri);
		return datatype;
	}

	@Override
	public OWLDatatype getOWLDurationDatatype()
	{
		IRI iri = XSDVocabulary.DURATION.getIRI();
		OWLDatatype datatype = new OWLDatatypeImpl(iri);
		return datatype;
	}
}
