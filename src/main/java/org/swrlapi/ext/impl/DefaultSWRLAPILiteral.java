package org.swrlapi.ext.impl;

import java.net.URI;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.vocab.XSDVocabulary;
import org.swrlapi.exceptions.SQWRLLiteralException;
import org.swrlapi.ext.SWRLAPILiteral;
import org.swrlapi.xsd.XSDDate;
import org.swrlapi.xsd.XSDDateTime;
import org.swrlapi.xsd.XSDDuration;
import org.swrlapi.xsd.XSDTime;

import uk.ac.manchester.cs.owl.owlapi.OWLLiteralImpl;

public class DefaultSWRLAPILiteral implements SWRLAPILiteral
{
	private final OWLLiteral literal;

	public DefaultSWRLAPILiteral(OWLLiteral literal)
	{
		this.literal = literal;
	}

	@Override
	public OWLLiteral getOWLLiteral()
	{
		return this.literal;
	}

	@Override
	public boolean isInteger()
	{
		return this.literal.getDatatype().isInteger();
	}

	@Override
	public boolean isLong()
	{
		return this.literal.getDatatype().getIRI().equals(XSDVocabulary.LONG.getIRI());
	}

	@Override
	public boolean isFloat()
	{
		return this.literal.getDatatype().isFloat();
	}

	@Override
	public boolean isDouble()
	{
		return this.literal.getDatatype().isDouble();
	}

	@Override
	public boolean isShort()
	{
		return this.literal.getDatatype().getIRI().equals(XSDVocabulary.SHORT.getIRI());
	}

	@Override
	public boolean isBoolean()
	{
		return this.literal.getDatatype().isBoolean();
	}

	@Override
	public boolean isByte()
	{
		return this.literal.getDatatype().getIRI().equals(XSDVocabulary.BYTE.getIRI());
	}

	@Override
	public boolean isAnyURI()
	{
		return this.literal.getDatatype().getIRI().equals(XSDVocabulary.ANY_URI.getIRI());
	}

	@Override
	public boolean isTime()
	{
		return this.literal.getDatatype().getIRI().equals(XSDVocabulary.TIME.getIRI());
	}

	@Override
	public boolean isDate()
	{
		return this.literal.getDatatype().getIRI().equals(XSDVocabulary.DATE.getIRI());
	}

	@Override
	public boolean isDateTime()
	{
		return this.literal.getDatatype().getIRI().equals(XSDVocabulary.DATE_TIME.getIRI());
	}

	@Override
	public boolean isDuration()
	{
		return this.literal.getDatatype().getIRI().equals(XSDVocabulary.DURATION.getIRI());
	}

	@Override
	public boolean isString()
	{
		return this.literal.getDatatype().isString();
	}

	@Override
	public boolean isNumeric()
	{
		return isByte() || isShort() || isInteger() || isLong() || isFloat() || isDouble();
	}

	@Override
	public boolean isComparable()
	{
		return isNumeric() || isString() || isTime() || isDate() || isDateTime() || isDuration();
	}

	@Override
	public boolean isQuotableType()
	{
		return isString() || isTime() || isDate() || isDateTime() || isDuration();
	}

	@Override
	public String getString() throws SQWRLLiteralException
	{
		if (!isString())
			throw new SQWRLLiteralException("cannot convert value of type " + this.literal.getDatatype() + " to String");
		return getOWLLiteral().getLiteral();
	}

	@Override
	public boolean getBoolean() throws SQWRLLiteralException
	{
		if (!isBoolean())
			throw new SQWRLLiteralException("cannot convert value of type " + this.literal.getDatatype() + " to boolean");

		return Boolean.parseBoolean(literal.getLiteral());
	}

	@Override
	public short getShort() throws SQWRLLiteralException
	{
		try {
			return Short.parseShort(literal.getLiteral());
		} catch (NumberFormatException e) {
			throw new SQWRLLiteralException("cannot convert value " + literal.getLiteral() + " of type "
					+ this.literal.getDatatype() + " to short");
		}
	}

	@Override
	public int getInteger() throws SQWRLLiteralException
	{
		try {
			if (isInteger())
				return Integer.parseInt(literal.getLiteral());
			else if (isShort())
				return Short.parseShort(literal.getLiteral());
			else
				throw new SQWRLLiteralException("cannot convert value of type " + this.literal.getDatatype() + " to int");
		} catch (NumberFormatException e) {
			throw new SQWRLLiteralException("cannot convert value " + literal.getLiteral() + " of type "
					+ this.literal.getDatatype() + " to integer");
		}
	}

	@Override
	public long getLong() throws SQWRLLiteralException
	{
		try {
			if (isLong())
				return Long.parseLong(literal.getLiteral());
			else if (isInteger())
				return Integer.parseInt(literal.getLiteral());
			else if (isShort())
				return Short.parseShort(literal.getLiteral());
			else
				throw new SQWRLLiteralException("cannot convert value of type " + this.literal.getDatatype() + " to long");
		} catch (NumberFormatException e) {
			throw new SQWRLLiteralException("cannot convert value " + literal.getLiteral() + " of type "
					+ this.literal.getDatatype() + " to long");
		}
	}

	// Some precision loss possible going from integer and long to float. See:
	// http://www.particle.kth.se/~lindsey/JavaCourse/Book/Part1/Java/Chapter02/castsMixing.html
	@Override
	public float getFloat() throws SQWRLLiteralException
	{
		try {
			if (isFloat())
				return Float.parseFloat(literal.getLiteral());
			else if (isDouble())
				return Float.parseFloat(literal.getLiteral());
			else if (isInteger())
				return Integer.parseInt(literal.getLiteral());
			else if (isLong())
				return Long.parseLong(literal.getLiteral());
			else if (isShort())
				return Short.parseShort(literal.getLiteral());
			else
				throw new SQWRLLiteralException("cannot convert value of type " + this.literal.getDatatype() + " to float");
		} catch (NumberFormatException e) {
			throw new SQWRLLiteralException("cannot convert value " + literal.getLiteral() + " of type "
					+ this.literal.getDatatype() + " to float");
		}
	}

	// Some precision loss possible going from long to double. See:
	// http://www.particle.kth.se/~lindsey/JavaCourse/Book/Part1/Java/Chapter02/castsMixing.html
	@Override
	public double getDouble() throws SQWRLLiteralException
	{
		try {
			if (isDouble())
				return Double.parseDouble(literal.getLiteral());
			else if (isFloat())
				return Float.parseFloat(literal.getLiteral());
			else if (isInteger())
				return Integer.parseInt(literal.getLiteral());
			else if (isLong())
				return Long.parseLong(literal.getLiteral());
			else if (isShort())
				return Short.parseShort(literal.getLiteral());
			else
				throw new SQWRLLiteralException("cannot convert value of type " + this.literal.getDatatype() + " to double");
		} catch (NumberFormatException e) {
			throw new SQWRLLiteralException("cannot convert value " + literal.getLiteral() + " of type "
					+ this.literal.getDatatype() + " to double");
		}
	}

	@Override
	public byte getByte() throws SQWRLLiteralException
	{
		try {
			if (!isByte())
				throw new SQWRLLiteralException("cannot convert value of type " + this.literal.getDatatype() + " to "
						+ XSDVocabulary.BYTE);
			return Byte.parseByte(literal.getLiteral());
		} catch (NumberFormatException e) {
			throw new SQWRLLiteralException("cannot convert value " + literal.getLiteral() + " of type "
					+ this.literal.getDatatype() + " to byte");
		}
	}

	@Override
	public URI getAnyURI() throws SQWRLLiteralException
	{
		try {
			if (!isAnyURI())
				throw new SQWRLLiteralException("cannot convert value of type " + this.literal.getDatatype() + " to "
						+ XSDVocabulary.ANY_URI);
			return URI.create(literal.getLiteral());
		} catch (IllegalArgumentException e) {
			throw new SQWRLLiteralException("cannot convert value " + literal.getLiteral() + " of type "
					+ this.literal.getDatatype() + " to URI");
		}
	}

	@Override
	public XSDTime getTime() throws SQWRLLiteralException
	{
		if (!isTime())
			throw new SQWRLLiteralException("cannot convert value of type " + this.literal.getDatatype() + " to "
					+ XSDVocabulary.TIME);
		return new XSDTime(literal.getLiteral());
	}

	@Override
	public XSDDate getDate() throws SQWRLLiteralException
	{
		if (!isDate())
			throw new SQWRLLiteralException("cannot convert value of type " + this.literal.getDatatype() + " to "
					+ XSDVocabulary.DATE);
		return new XSDDate(literal.getLiteral());
	}

	@Override
	public XSDDateTime getDateTime() throws SQWRLLiteralException
	{
		if (!isDateTime())
			throw new SQWRLLiteralException("cannot convert value of type " + this.literal.getDatatype() + " to "
					+ XSDVocabulary.DATE_TIME);
		;
		return new XSDDateTime(literal.getLiteral());
	}

	@Override
	public XSDDuration getDuration() throws SQWRLLiteralException
	{
		if (!isDuration())
			throw new SQWRLLiteralException("cannot convert value of type " + this.literal.getDatatype() + " to "
					+ XSDVocabulary.DURATION.getShortName());
		return new XSDDuration(literal.getLiteral());
	}

	@Override
	public String getLiteral()
	{
		return this.literal.getLiteral();
	}

	@Override
	public String toString()
	{
		return this.literal.toString();
	}

	@Override
	public String toQuotedString()
	{
		// Escape non-escaped double quote characters; for humans: [^\\]" -> \\"
		return "\"" + this.literal.toString().replaceAll("[~\\\\]\"", "\\\\\"") + "\"";
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		DefaultSWRLAPILiteral impl = (DefaultSWRLAPILiteral)obj;
		return (this.literal != null && impl.literal != null && this.literal.equals(impl.literal));
	}

	@Override
	public int hashCode()
	{
		int hash = 95;
		hash = hash + (null == this.literal ? 0 : this.literal.toString().hashCode());
		return hash;
	}

	/**
	 * TODO This is incorrect. Fix. See also {@link SQWRLLiteralResultValueImpl#compareTo} and
	 * {@link OWLLiteralImpl#compareTo}.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int compareTo(SWRLAPILiteral literal)
	{
		return ((Comparable)this.literal).compareTo(literal.getOWLLiteral());
	}
}
