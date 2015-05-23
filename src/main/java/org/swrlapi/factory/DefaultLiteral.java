package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.vocab.XSDVocabulary;
import org.swrlapi.core.Literal;
import org.swrlapi.core.xsd.XSDDate;
import org.swrlapi.core.xsd.XSDDateTime;
import org.swrlapi.core.xsd.XSDDuration;
import org.swrlapi.core.xsd.XSDTime;
import org.swrlapi.exceptions.LiteralException;

import java.net.URI;

class DefaultLiteral implements Literal
{
  @NonNull private final OWLLiteral literal;

  public DefaultLiteral(@NonNull OWLLiteral literal)
  {
    this.literal = literal;
  }

  @NonNull @Override
  public OWLLiteral getOWLLiteral()
  {
    return this.literal;
  }

  @Override
  public boolean isNumeric()
  {
    return isByte() || isShort() || isInt() || isLong() || isFloat() || isDouble();
  }

  @Override
  public boolean isByte()
  {
    return this.literal.getDatatype().getIRI().equals(XSDVocabulary.BYTE.getIRI());
  }

  @Override
  public boolean isShort()
  {
    return this.literal.getDatatype().getIRI().equals(XSDVocabulary.SHORT.getIRI());
  }

  @Override
  public boolean isInt()
  {
    return this.literal.getDatatype().getIRI().equals(XSDVocabulary.INT.getIRI());
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
  public boolean isString()
  {
    return this.literal.getDatatype().isString();
  }

  @Override
  public boolean isBoolean()
  {
    return this.literal.getDatatype().isBoolean();
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
  public boolean isComparable()
  {
    return isNumeric() || isString() || isTime() || isDate() || isDateTime() || isDuration();
  }

  @Override
  public boolean isQuotableType()
  {
    return isString() || isTime() || isDate() || isDateTime() || isDuration();
  }

  @NonNull @Override
  public String getString() throws LiteralException
  {
    if (!isString())
      throw new LiteralException("cannot convert value of type " + this.literal.getDatatype() + " to String");

    return getOWLLiteral().getLiteral();
  }

  @Override
  public boolean getBoolean() throws LiteralException
  {
    if (!isBoolean())
      throw new LiteralException("cannot convert value of type " + this.literal.getDatatype() + " to boolean");

    return Boolean.parseBoolean(this.literal.getLiteral());
  }

  @Override
  public byte getByte() throws LiteralException
  {
    try {
      if (!isByte())
        throw new LiteralException("cannot convert value of type " + this.literal.getDatatype() + " to "
            + XSDVocabulary.BYTE);
      return Byte.parseByte(this.literal.getLiteral());
    } catch (NumberFormatException e) {
      throw new LiteralException("cannot convert value " + this.literal.getLiteral() + " of type "
          + this.literal.getDatatype() + " to byte");
    }
  }

  @Override
  public short getShort() throws LiteralException
  {
    try {
      if (isShort())
        return Short.parseShort(this.literal.getLiteral());
      else if (isByte())
        return Byte.parseByte(this.literal.getLiteral());
      else
        throw new LiteralException("cannot convert value of type " + this.literal.getDatatype() + " to short");
    } catch (NumberFormatException e) {
      throw new LiteralException("cannot convert value " + this.literal.getLiteral() + " of type "
          + this.literal.getDatatype() + " to short");
    }
  }

  @Override
  public int getInt() throws LiteralException
  {
    try {
      if (isInt())
        return Integer.parseInt(this.literal.getLiteral());
      else if (isShort())
        return Short.parseShort(this.literal.getLiteral());
      else if (isByte())
        return Byte.parseByte(this.literal.getLiteral());
      else
        throw new LiteralException("cannot convert value of type " + this.literal.getDatatype() + " to int");
    } catch (NumberFormatException e) {
      throw new LiteralException("cannot convert value " + this.literal.getLiteral() + " of type "
          + this.literal.getDatatype() + " to integer");
    }
  }

  @Override
  public long getLong() throws LiteralException
  {
    try {
      if (isLong())
        return Long.parseLong(this.literal.getLiteral());
      else if (isInt())
        return Integer.parseInt(this.literal.getLiteral());
      else if (isShort())
        return Short.parseShort(this.literal.getLiteral());
      else if (isByte())
        return Byte.parseByte(this.literal.getLiteral());
      else
        throw new LiteralException("cannot convert value of type " + this.literal.getDatatype() + " to long");
    } catch (NumberFormatException e) {
      throw new LiteralException("cannot convert value " + this.literal.getLiteral() + " of type "
          + this.literal.getDatatype() + " to long");
    }
  }

  // Some precision loss possible going from integer and long to float. See:
  // http://www.particle.kth.se/~lindsey/JavaCourse/Book/Part1/Java/Chapter02/castsMixing.html
  @Override
  public float getFloat() throws LiteralException
  {
    try {
      if (isFloat())
        return Float.parseFloat(this.literal.getLiteral());
      else if (isDouble())
        return Float.parseFloat(this.literal.getLiteral());
      else if (isInt())
        return Integer.parseInt(this.literal.getLiteral());
      else if (isLong())
        return Long.parseLong(this.literal.getLiteral());
      else if (isShort())
        return Short.parseShort(this.literal.getLiteral());
      else if (isByte())
        return Byte.parseByte(this.literal.getLiteral());
      else
        throw new LiteralException("cannot convert value of type " + this.literal.getDatatype() + " to float");
    } catch (NumberFormatException e) {
      throw new LiteralException("cannot convert value " + this.literal.getLiteral() + " of type "
          + this.literal.getDatatype() + " to float");
    }
  }

  // Some precision loss possible going from long to double. See:
  // http://www.particle.kth.se/~lindsey/JavaCourse/Book/Part1/Java/Chapter02/castsMixing.html
  @Override
  public double getDouble() throws LiteralException
  {
    try {
      if (isDouble())
        return Double.parseDouble(this.literal.getLiteral());
      else if (isFloat())
        return Float.parseFloat(this.literal.getLiteral());
      else if (isInt())
        return Integer.parseInt(this.literal.getLiteral());
      else if (isLong())
        return Long.parseLong(this.literal.getLiteral());
      else if (isShort())
        return Short.parseShort(this.literal.getLiteral());
      else if (isByte())
        return Byte.parseByte(this.literal.getLiteral());
      else
        throw new LiteralException("cannot convert value of type " + this.literal.getDatatype() + " to double");
    } catch (NumberFormatException e) {
      throw new LiteralException("cannot convert value " + this.literal.getLiteral() + " of type "
          + this.literal.getDatatype() + " to double");
    }
  }

  @NonNull @Override
  public URI getAnyURI() throws LiteralException
  {
    try {
      if (!isAnyURI())
        throw new LiteralException("cannot convert value of type " + this.literal.getDatatype() + " to "
            + XSDVocabulary.ANY_URI);
      return URI.create(this.literal.getLiteral());
    } catch (IllegalArgumentException e) {
      throw new LiteralException("cannot convert value " + this.literal.getLiteral() + " of type "
          + this.literal.getDatatype() + " to URI");
    }
  }

  @NonNull @Override
  public XSDTime getTime() throws LiteralException
  {
    if (!isTime())
      throw new LiteralException("cannot convert value of type " + this.literal.getDatatype() + " to "
          + XSDVocabulary.TIME);
    return new XSDTime(this.literal.getLiteral());
  }

  @NonNull @Override
  public XSDDate getDate() throws LiteralException
  {
    if (!isDate())
      throw new LiteralException("cannot convert value of type " + this.literal.getDatatype() + " to "
          + XSDVocabulary.DATE);
    return new XSDDate(this.literal.getLiteral());
  }

  @NonNull @Override
  public XSDDateTime getDateTime() throws LiteralException
  {
    if (!isDateTime())
      throw new LiteralException("cannot convert value of type " + this.literal.getDatatype() + " to "
          + XSDVocabulary.DATE_TIME);
    return new XSDDateTime(this.literal.getLiteral());
  }

  @NonNull @Override
  public XSDDuration getDuration() throws LiteralException
  {
    if (!isDuration())
      throw new LiteralException("cannot convert value of type " + this.literal.getDatatype() + " to "
          + XSDVocabulary.DURATION.getPrefixedName());

    return new XSDDuration(this.literal.getLiteral());
  }

  @NonNull @Override
  public String getValue()
  {
    return this.literal.getLiteral();
  }

  @NonNull @Override
  public OWLDatatype getOWLDatatype()
  {
    return this.literal.getDatatype();
  }

  @NonNull @Override
  public String toString()
  {
    return this.literal.toString();
  }

  @NonNull @Override
  public String toQuotedString()
  {
    // Escape non-escaped double quote characters; for humans: [^\\]" -> \\"
    return "\"" + this.literal.toString().replaceAll("[~\\\\]\"", "\\\\\"") + "\"";
  }

  @Override
  public boolean equals(@Nullable Object obj)
  {
    if (this == obj)
      return true;
    if ((obj == null) || (obj.getClass() != this.getClass()))
      return false;
    DefaultLiteral impl = (DefaultLiteral)obj;
    return (this.literal != null && impl.literal != null && this.literal.equals(impl.literal));
  }

  @Override
  public int hashCode()
  {
    int hash = 95;
    hash = hash + (null == this.literal ? 0 : this.literal.hashCode());
    return hash;
  }
}
