package org.swrlapi.core;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDatatype;

/**
 * A convenience factory to create a core set of OWLAPI OWL datatypes.
 *
 * @see org.semanticweb.owlapi.model.OWLDatatype
 */
public interface SWRLAPIOWLDatatypeFactory
{
	OWLDatatype getOWLDatatype(IRI iri);

	OWLDatatype getByteDatatype();

	OWLDatatype getShortDatatype();

	OWLDatatype getDoubleDatatype();

	OWLDatatype getFloatDatatype();

	OWLDatatype getIntDatatype();

	OWLDatatype getLongDatatype();

	OWLDatatype getStringDatatype();

	OWLDatatype getBooleanDatatype();

	OWLDatatype getURIDatatype();

	OWLDatatype getDateDatatype();

	OWLDatatype getTimeDatatype();

	OWLDatatype getDateTimeDatatype();

	OWLDatatype getDurationDatatype();
}
