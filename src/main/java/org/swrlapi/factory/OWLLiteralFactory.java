package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.literal.Literal;
import org.swrlapi.literal.XSDDate;
import org.swrlapi.literal.XSDDateTime;
import org.swrlapi.literal.XSDDuration;
import org.swrlapi.literal.XSDTime;

import java.math.BigDecimal;
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
 * @see org.swrlapi.literal.XSDDate
 * @see org.swrlapi.literal.XSDTime
 * @see org.swrlapi.literal.XSDDateTime
 * @see org.swrlapi.literal.XSDDuration
 */
public interface OWLLiteralFactory
{
  @NonNull OWLLiteral getOWLLiteral(byte b);

  @NonNull OWLLiteral getOWLLiteral(short s);

  @NonNull OWLLiteral getOWLLiteral(int value);

  @NonNull OWLLiteral getOWLLiteral(long value);

  @NonNull OWLLiteral getOWLLiteral(float value);

  @NonNull OWLLiteral getOWLLiteral(double value);

  @NonNull OWLLiteral getOWLLiteral(@NonNull BigDecimal value);

  @NonNull OWLLiteral getOWLLiteral(@NonNull String value);

  @NonNull OWLLiteral getOWLLiteral(boolean value);

  @NonNull OWLLiteral getOWLLiteral(@NonNull URI uri);

  @NonNull OWLLiteral getOWLLiteral(@NonNull XSDDate date);

  @NonNull OWLLiteral getOWLLiteral(@NonNull XSDTime time);

  @NonNull OWLLiteral getOWLLiteral(@NonNull XSDDateTime datetime);

  @NonNull OWLLiteral getOWLLiteral(@NonNull XSDDuration duration);

  @NonNull OWLLiteral getOWLLiteral(@NonNull String literal, @NonNull OWLDatatype datatype);

  @NonNull OWLLiteral createLeastNarrowNumericOWLLiteral(double value, @NonNull List<@NonNull OWLLiteral> inputLiterals);
}
