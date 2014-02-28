package org.protege.swrlapi.ext;

import java.net.URI;

import org.protege.owl.portability.model.OWLDatatypeAdapter;

public interface OWLDatatypeFactory
{
	OWLDatatypeAdapter getOWLDatatype(URI type);

	OWLDatatypeAdapter getOWLBooleanDatatype();

	OWLDatatypeAdapter getOWLShortDatatype();

	OWLDatatypeAdapter getOWLDoubleDatatype();

	OWLDatatypeAdapter getOWLFloatDatatype();

	OWLDatatypeAdapter getOWLIntegerDatatype();

	OWLDatatypeAdapter getOWLLongDatatype();

	OWLDatatypeAdapter getOWLStringDatatype();

	OWLDatatypeAdapter getOWLByteDatatype();

	OWLDatatypeAdapter getOWLURIDatatype();

	OWLDatatypeAdapter getOWLDateDatatype();

	OWLDatatypeAdapter getOWLTimeDatatype();

	OWLDatatypeAdapter getOWLDateTimeDatatype();

	OWLDatatypeAdapter getOWLDurationDatatype();
}
