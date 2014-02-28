package org.protege.swrlapi.ext;

import java.net.URI;

import org.protege.owl.portability.model.OWLDatatypeAdapter;
import org.protege.owl.portability.model.OWLLiteralAdapter;
import org.protege.swrlapi.xsd.XSDDate;
import org.protege.swrlapi.xsd.XSDDateTime;
import org.protege.swrlapi.xsd.XSDDuration;
import org.protege.swrlapi.xsd.XSDTime;

public interface OWLLiteralFactory
{
	OWLLiteralAdapter getOWLLiteral(String literal, OWLDatatypeAdapter datatype);

	OWLLiteralAdapter getOWLLiteral(String value);

	OWLLiteralAdapter getOWLLiteral(boolean value);

	OWLLiteralAdapter getOWLLiteral(double value);

	OWLLiteralAdapter getOWLLiteral(float value);

	OWLLiteralAdapter getOWLLiteral(int value);

	OWLLiteralAdapter getOWLLiteral(long value);

	OWLLiteralAdapter getOWLLiteral(byte b);

	OWLLiteralAdapter getOWLLiteral(short s);

	OWLLiteralAdapter getOWLLiteral(URI uri);

	OWLLiteralAdapter getOWLLiteral(XSDDate date);

	OWLLiteralAdapter getOWLLiteral(XSDTime time);

	OWLLiteralAdapter getOWLLiteral(XSDDateTime datetime);

	OWLLiteralAdapter getOWLLiteral(XSDDuration duration);
}
