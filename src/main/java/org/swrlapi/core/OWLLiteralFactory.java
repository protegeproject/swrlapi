package org.swrlapi.core;

import checkers.nullness.quals.NonNull;
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
  @NonNull OWLLiteral getOWLLiteral(byte b);

  @NonNull OWLLiteral getOWLLiteral(short s);

  @NonNull OWLLiteral getOWLLiteral(int value);

  @NonNull OWLLiteral getOWLLiteral(long value);

  @NonNull OWLLiteral getOWLLiteral(float value);

  @NonNull OWLLiteral getOWLLiteral(double value);

  @NonNull OWLLiteral getOWLLiteral(String value);

  @NonNull OWLLiteral getOWLLiteral(boolean value);

  @NonNull OWLLiteral getOWLLiteral(@NonNull URI uri);

  @NonNull OWLLiteral getOWLLiteral(@NonNull XSDDate date);

  @NonNull OWLLiteral getOWLLiteral(@NonNull XSDTime time);

  @NonNull OWLLiteral getOWLLiteral(@NonNull XSDDateTime datetime);

  @NonNull OWLLiteral getOWLLiteral(@NonNull XSDDuration duration);

  @NonNull OWLLiteral getOWLLiteral(@NonNull String literal, @NonNull OWLDatatype datatype);

  @NonNull OWLLiteral createLeastNarrowNumericOWLLiteral(double value, @NonNull List<OWLLiteral> inputLiterals);
}
