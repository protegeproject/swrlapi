package org.protege.swrlapi.ext;

import java.net.URI;

import org.protege.swrlapi.xsd.XSDDate;
import org.protege.swrlapi.xsd.XSDDateTime;
import org.protege.swrlapi.xsd.XSDDuration;
import org.protege.swrlapi.xsd.XSDTime;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;

/**
 * A factory to create OWLAPI OWL literals.
 */
public interface OWLLiteralFactory
{
	OWLLiteral getOWLLiteral(String literal, OWLDatatype datatype);

	OWLLiteral getOWLLiteral(String value);

	OWLLiteral getOWLLiteral(boolean value);

	OWLLiteral getOWLLiteral(double value);

	OWLLiteral getOWLLiteral(float value);

	OWLLiteral getOWLLiteral(int value);

	OWLLiteral getOWLLiteral(long value);

	OWLLiteral getOWLLiteral(byte b);

	OWLLiteral getOWLLiteral(short s);

	OWLLiteral getOWLLiteral(URI uri);

	OWLLiteral getOWLLiteral(XSDDate date);

	OWLLiteral getOWLLiteral(XSDTime time);

	OWLLiteral getOWLLiteral(XSDDateTime datetime);

	OWLLiteral getOWLLiteral(XSDDuration duration);
}
