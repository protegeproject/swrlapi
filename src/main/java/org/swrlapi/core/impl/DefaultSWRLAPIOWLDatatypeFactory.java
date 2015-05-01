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
  public OWLDatatype getBooleanDatatype()
  {
    IRI iri = XSDVocabulary.BOOLEAN.getIRI();
    return new OWLDatatypeImpl(iri);
  }

  @Override
  public OWLDatatype getShortDatatype()
  {
    IRI iri = XSDVocabulary.SHORT.getIRI();
    return new OWLDatatypeImpl(iri);
  }

  @Override
  public OWLDatatype getDoubleDatatype()
  {
    IRI iri = XSDVocabulary.DOUBLE.getIRI();
    return new OWLDatatypeImpl(iri);
  }

  @Override
  public OWLDatatype getFloatDatatype()
  {
    IRI iri = XSDVocabulary.FLOAT.getIRI();
    return new OWLDatatypeImpl(iri);
  }

  @Override
  public OWLDatatype getIntDatatype()
  {
    IRI iri = XSDVocabulary.INT.getIRI();
    return new OWLDatatypeImpl(iri);
  }

  @Override
  public OWLDatatype getLongDatatype()
  {
    IRI iri = XSDVocabulary.LONG.getIRI();
    return new OWLDatatypeImpl(iri);
  }

  @Override
  public OWLDatatype getStringDatatype()
  {
    IRI iri = XSDVocabulary.STRING.getIRI();
    return new OWLDatatypeImpl(iri);
  }

  @Override
  public OWLDatatype getByteDatatype()
  {
    IRI iri = XSDVocabulary.BYTE.getIRI();
    return new OWLDatatypeImpl(iri);
  }

  @Override
  public OWLDatatype getURIDatatype()
  {
    IRI iri = XSDVocabulary.ANY_URI.getIRI();
    return new OWLDatatypeImpl(iri);
  }

  @Override
  public OWLDatatype getDateDatatype()
  {
    IRI iri = XSDVocabulary.DATE.getIRI();
    return new OWLDatatypeImpl(iri);
  }

  @Override
  public OWLDatatype getTimeDatatype()
  {
    IRI iri = XSDVocabulary.TIME.getIRI();
    return new OWLDatatypeImpl(iri);
  }

  @Override
  public OWLDatatype getDateTimeDatatype()
  {
    IRI iri = XSDVocabulary.DATE_TIME.getIRI();
    return new OWLDatatypeImpl(iri);
  }

  @Override
  public OWLDatatype getDurationDatatype()
  {
    IRI iri = XSDVocabulary.DURATION.getIRI();
    return new OWLDatatypeImpl(iri);
  }
}
