package org.protege.swrlapi.ext;

import java.net.URI;

import org.protege.swrlapi.exceptions.SQWRLLiteralException;
import org.protege.swrlapi.xsd.XSDDate;
import org.protege.swrlapi.xsd.XSDDateTime;
import org.protege.swrlapi.xsd.XSDDuration;
import org.protege.swrlapi.xsd.XSDTime;
import org.semanticweb.owlapi.model.OWLLiteral;

/**
 * The SWRLAPI's literal extends the OWLAPI's literal with additional functionality.
 */
public interface SWRLAPILiteral extends Comparable<SWRLAPILiteral>
{
	OWLLiteral getOWLLiteral();

	boolean isNumeric();

	boolean isBoolean();

	boolean isShort();

	boolean isInteger();

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
