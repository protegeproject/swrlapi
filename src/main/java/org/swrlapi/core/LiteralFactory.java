package org.swrlapi.core;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.core.xsd.XSDDate;
import org.swrlapi.core.xsd.XSDDateTime;
import org.swrlapi.core.xsd.XSDDuration;
import org.swrlapi.core.xsd.XSDTime;

import java.net.URI;

/**
 * Factory for constructing SWRLAPI-based literals, which wrap {@link org.semanticweb.owlapi.model.OWLLiteral}s to provide
 * additional convenience methods used by the SWRLAPI.
 *
 * @see Literal
 * @see org.semanticweb.owlapi.model.OWLLiteral
 */
public interface LiteralFactory
{
  Literal getLiteral(byte b);

  Literal getLiteral(short s);

  Literal getLiteral(int value);

  Literal getLiteral(float value);

  Literal getLiteral(double value);

  Literal getLiteral(String value);

  Literal getLiteral(boolean value);

  Literal getLiteral(URI uri);

  Literal getLiteral(XSDDate date);

  Literal getLiteral(XSDTime time);

  Literal getLiteral(XSDDateTime datetime);

  Literal getLiteral(XSDDuration duration);

  Literal getLiteral(OWLLiteral literal);
}
