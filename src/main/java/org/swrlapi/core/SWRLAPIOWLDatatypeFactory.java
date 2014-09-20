package org.swrlapi.core;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDatatype;

/**
 * A convenience factory to create OWLAPI OWL datatypes.
 *
 * @see org.semanticweb.owlapi.model.OWLDatatype
 */
public interface SWRLAPIOWLDatatypeFactory
{
	OWLDatatype getOWLDatatype(IRI iri);

	OWLDatatype getOWLBooleanDatatype();

	OWLDatatype getOWLShortDatatype();

	OWLDatatype getOWLDoubleDatatype();

	OWLDatatype getOWLFloatDatatype();

	OWLDatatype getOWLIntDatatype();

	OWLDatatype getOWLLongDatatype();

	OWLDatatype getOWLStringDatatype();

	OWLDatatype getOWLByteDatatype();

	OWLDatatype getOWLURIDatatype();

	OWLDatatype getOWLDateDatatype();

	OWLDatatype getOWLTimeDatatype();

	OWLDatatype getOWLDateTimeDatatype();

	OWLDatatype getOWLDurationDatatype();
}
