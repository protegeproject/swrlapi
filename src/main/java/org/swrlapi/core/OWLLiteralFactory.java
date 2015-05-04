package org.swrlapi.core;

import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.core.xsd.XSDDate;
import org.swrlapi.core.xsd.XSDDateTime;
import org.swrlapi.core.xsd.XSDDuration;
import org.swrlapi.core.xsd.XSDTime;

import java.net.URI;
import java.util.List;

/**
 * A convenience factory to create OWLAPI OWL literals.
 * 
 * The SWRLAPI also has a {@link LiteralFactory} that can be used to create
 * {@link Literal} objects, which wrap {@link org.semanticweb.owlapi.model.OWLLiteral}s to
 * provide additional convenience methods used be the SWRLAPI.
 * 
 * @see org.semanticweb.owlapi.model.OWLLiteral
 * @see LiteralFactory
 * @see org.swrlapi.core.xsd.XSDDate
 * @see org.swrlapi.core.xsd.XSDTime
 * @see org.swrlapi.core.xsd.XSDDateTime
 * @see org.swrlapi.core.xsd.XSDDuration
 */
public interface OWLLiteralFactory
{
  OWLLiteral getOWLLiteral(byte b);

  OWLLiteral getOWLLiteral(short s);

  OWLLiteral getOWLLiteral(int value);

  OWLLiteral getOWLLiteral(long value);

  OWLLiteral getOWLLiteral(float value);

  OWLLiteral getOWLLiteral(double value);

  OWLLiteral getOWLLiteral(String value);

  OWLLiteral getOWLLiteral(boolean value);

  OWLLiteral getOWLLiteral(URI uri);

  OWLLiteral getOWLLiteral(XSDDate date);

  OWLLiteral getOWLLiteral(XSDTime time);

  OWLLiteral getOWLLiteral(XSDDateTime datetime);

  OWLLiteral getOWLLiteral(XSDDuration duration);

  OWLLiteral getOWLLiteral(String literal, OWLDatatype datatype);

  OWLLiteral createLeastNarrowNumericOWLLiteral(double value, List<OWLLiteral> inputLiterals);
}
