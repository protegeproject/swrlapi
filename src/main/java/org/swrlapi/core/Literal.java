package org.swrlapi.core;

import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.core.xsd.XSDDate;
import org.swrlapi.core.xsd.XSDDateTime;
import org.swrlapi.core.xsd.XSDDuration;
import org.swrlapi.core.xsd.XSDTime;
import org.swrlapi.exceptions.LiteralException;

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

  byte getByte() throws LiteralException;

  short getShort() throws LiteralException;

  int getInt() throws LiteralException;

  long getLong() throws LiteralException;

  float getFloat() throws LiteralException;

  double getDouble() throws LiteralException;

  String getString() throws LiteralException;

  boolean getBoolean() throws LiteralException;

  URI getAnyURI() throws LiteralException;

  XSDTime getTime() throws LiteralException;

  XSDDate getDate() throws LiteralException;

  XSDDateTime getDateTime() throws LiteralException;

  XSDDuration getDuration() throws LiteralException;

  String getValue();

  boolean isComparable();

  boolean isQuotableType();

  @Override
  String toString();

  String toQuotedString();

  OWLLiteral getOWLLiteral();

  OWLDatatype getOWLDatatype();
}
