package org.swrlapi.core;

import java.net.URI;

import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.core.xsd.XSDDate;
import org.swrlapi.core.xsd.XSDDateTime;
import org.swrlapi.core.xsd.XSDDuration;
import org.swrlapi.core.xsd.XSDTime;
import org.swrlapi.exceptions.SQWRLLiteralException;

/**
 * The SWRLAPI's literal wraps an OWLAPI literal to provide additional convenience methods used by the SWRLAPI.
 *
 * @see org.semanticweb.owlapi.model.OWLLiteral
 */
public interface SWRLAPILiteral
{
	OWLLiteral getOWLLiteral();

	OWLDatatype getOWLDatatype();

	boolean isNumeric();

	boolean isBoolean();

	boolean isShort();

	boolean isInteger();

	boolean isInt();

	boolean isLong();

	boolean isFloat();

	boolean isDouble();

	boolean isString();

	boolean isByte();

	boolean isAnyURI();

	boolean isTime();

	boolean isDate();

	boolean isDateTime();

	boolean isDuration();

	String getString() throws SQWRLLiteralException;

	boolean getBoolean() throws SQWRLLiteralException;

	int getInteger() throws SQWRLLiteralException;

	long getLong() throws SQWRLLiteralException;

	float getFloat() throws SQWRLLiteralException;

	double getDouble() throws SQWRLLiteralException;

	short getShort() throws SQWRLLiteralException;

	byte getByte() throws SQWRLLiteralException;

	URI getAnyURI() throws SQWRLLiteralException;

	XSDTime getTime() throws SQWRLLiteralException;

	XSDDate getDate() throws SQWRLLiteralException;

	XSDDateTime getDateTime() throws SQWRLLiteralException;

	XSDDuration getDuration() throws SQWRLLiteralException;

	String getLiteral();

	boolean isComparable();

	boolean isQuotableType();

	@Override
	String toString();

	String toQuotedString();
}
