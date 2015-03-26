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
		return new OWLDatatypeImpl(iri);
	}

	@Override
	public OWLDatatype getOWLBooleanDatatype()
	{
		IRI iri = XSDVocabulary.BOOLEAN.getIRI();
		return new OWLDatatypeImpl(iri);
	}

	@Override
	public OWLDatatype getOWLShortDatatype()
	{
		IRI iri = XSDVocabulary.SHORT.getIRI();
		return new OWLDatatypeImpl(iri);
	}

	@Override
	public OWLDatatype getOWLDoubleDatatype()
	{
		IRI iri = XSDVocabulary.DOUBLE.getIRI();
		return new OWLDatatypeImpl(iri);
	}

	@Override
	public OWLDatatype getOWLFloatDatatype()
	{
		IRI iri = XSDVocabulary.FLOAT.getIRI();
		return new OWLDatatypeImpl(iri);
	}

	@Override
	public OWLDatatype getOWLIntDatatype()
	{
		IRI iri = XSDVocabulary.INT.getIRI();
		return new OWLDatatypeImpl(iri);
	}

	@Override
	public OWLDatatype getOWLLongDatatype()
	{
		IRI iri = XSDVocabulary.LONG.getIRI();
		return new OWLDatatypeImpl(iri);
	}

	@Override
	public OWLDatatype getOWLStringDatatype()
	{
		IRI iri = XSDVocabulary.STRING.getIRI();
		return new OWLDatatypeImpl(iri);
	}

	@Override
	public OWLDatatype getOWLByteDatatype()
	{
		IRI iri = XSDVocabulary.BYTE.getIRI();
		return new OWLDatatypeImpl(iri);
	}

	@Override
	public OWLDatatype getOWLURIDatatype()
	{
		IRI iri = XSDVocabulary.ANY_URI.getIRI();
		return new OWLDatatypeImpl(iri);
	}

	@Override
	public OWLDatatype getOWLDateDatatype()
	{
		IRI iri = XSDVocabulary.DATE.getIRI();
		return new OWLDatatypeImpl(iri);
	}

	@Override
	public OWLDatatype getOWLTimeDatatype()
	{
		IRI iri = XSDVocabulary.TIME.getIRI();
		return new OWLDatatypeImpl(iri);
	}

	@Override
	public OWLDatatype getOWLDateTimeDatatype()
	{
		IRI iri = XSDVocabulary.DATE_TIME.getIRI();
		return new OWLDatatypeImpl(iri);
	}

	@Override
	public OWLDatatype getOWLDurationDatatype()
	{
		IRI iri = XSDVocabulary.DURATION.getIRI();
		return new OWLDatatypeImpl(iri);
	}
}
