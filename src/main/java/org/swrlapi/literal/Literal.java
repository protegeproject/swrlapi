package org.swrlapi.literal;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.exceptions.LiteralException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;

/**
 * Wraps an OWLAPI literal to provide additional convenience methods used by the SWRLAPI.
 *
 * @see org.semanticweb.owlapi.model.OWLLiteral
 */
public interface Literal
{
  boolean isNumeric();

  boolean isByte();

  boolean isShort();

  boolean isInt();

  boolean isLong();

  boolean isFloat();

  boolean isDouble();

  boolean isString();

  boolean isBoolean();

  boolean isAnyURI();

  boolean isTime();

  boolean isDate();

  boolean isDateTime();

  boolean isDuration();

  boolean isDecimal();

  boolean isInteger();

  boolean isNegativeInteger();

  boolean isNonNegativeInteger();

  boolean isNonPositiveInteger();

  boolean isUnsignedLong();

  boolean isUnsignedInt();

  boolean isUnsignedShort();

  boolean isUnsignedByte();

  byte getByte() throws LiteralException;

  short getShort() throws LiteralException;

  int getInt() throws LiteralException;

  long getLong() throws LiteralException;

  float getFloat() throws LiteralException;

  double getDouble() throws LiteralException;

  boolean getBoolean() throws LiteralException;

  @NonNull String getString() throws LiteralException;

  @NonNull URI getAnyURI() throws LiteralException;

  @NonNull XSDTime getTime() throws LiteralException;

  @NonNull XSDDate getDate() throws LiteralException;

  @NonNull XSDDateTime getDateTime() throws LiteralException;

  @NonNull XSDDuration getDuration() throws LiteralException;

  @NonNull BigDecimal getDecimal() throws LiteralException;

  @NonNull BigInteger getInteger() throws LiteralException;

  @NonNull String getValue();

  boolean isComparable();

  boolean isQuotableType();

  @SideEffectFree @NonNull @Override String toString();

  @NonNull String toQuotedString();

  @NonNull OWLLiteral getOWLLiteral();

  @NonNull OWLDatatype getOWLDatatype();
}
