package org.swrlapi.ext;

import java.net.URI;

import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.xsd.XSDDate;
import org.swrlapi.xsd.XSDDateTime;
import org.swrlapi.xsd.XSDDuration;
import org.swrlapi.xsd.XSDTime;

/**
 * A convenience factory to create OWLAPI OWL literals. The SWRLAPI also has a {@link SWRLAPILiteralFactory} that can be
 * used to create {@link SWRLAPILiteral} objects, which wrap {@link OWLLiteral}s to provide additional convenience
 * methods used be the SWRLAPI.
 * 
 * @see OWLLiteral, SWRLAPILiteralFactory
 * @see XSDDate, XSDTime, XSDDatetime, XSDDuration
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
