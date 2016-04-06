package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Deterministic;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.vocab.XSDVocabulary;
import org.swrlapi.exceptions.LiteralException;
import org.swrlapi.literal.Literal;
import org.swrlapi.literal.OWLLiteralComparator;
import org.swrlapi.literal.XSDDate;
import org.swrlapi.literal.XSDDateTime;
import org.swrlapi.literal.XSDDuration;
import org.swrlapi.literal.XSDTime;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;

class DefaultLiteral implements Literal
{
  @NonNull private final OWLLiteral literal;

  public DefaultLiteral(@NonNull OWLLiteral literal)
  {
    this.literal = literal;
  }

  @NonNull @Override public OWLLiteral getOWLLiteral()
  {
    return this.literal;
  }

  @Override public boolean isNumeric()
  {
    return OWLLiteralComparator.isNumeric(literal);
  }

  @Override public boolean isByte()
  {
    return this.literal.getDatatype().getIRI().equals(XSDVocabulary.BYTE.getIRI());
  }

  @Override public boolean isShort()
  {
    return this.literal.getDatatype().getIRI().equals(XSDVocabulary.SHORT.getIRI());
  }

  @Override public boolean isInt()
  {
    return this.literal.getDatatype().getIRI().equals(XSDVocabulary.INT.getIRI());
  }

  @Override public boolean isLong()
  {
    return this.literal.getDatatype().getIRI().equals(XSDVocabulary.LONG.getIRI());
  }

  @Override public boolean isFloat()
  {
    return this.literal.getDatatype().isFloat();
  }

  @Override public boolean isDouble()
  {
    return this.literal.getDatatype().isDouble();
  }

  @Override public boolean isDecimal()
  {
    return this.literal.getDatatype().getIRI().equals(XSDVocabulary.DECIMAL.getIRI());
  }

  @Override public boolean isInteger()
  {
    return this.literal.getDatatype().getIRI().equals(XSDVocabulary.INTEGER.getIRI());
  }

  @Override public boolean isNegativeInteger()
  {
    return this.literal.getDatatype().getIRI().equals(XSDVocabulary.NEGATIVE_INTEGER.getIRI());
  }

  @Override public boolean isPositiveInteger()
  {
    return this.literal.getDatatype().getIRI().equals(XSDVocabulary.POSITIVE_INTEGER.getIRI());
  }

  @Override public boolean isNonNegativeInteger()
  {
    return this.literal.getDatatype().getIRI().equals(XSDVocabulary.NON_NEGATIVE_INTEGER.getIRI());
  }

  @Override public boolean isNonPositiveInteger()
  {
    return this.literal.getDatatype().getIRI().equals(XSDVocabulary.NON_POSITIVE_INTEGER.getIRI());
  }

  @Override public boolean isUnsignedLong()
  {
    return this.literal.getDatatype().getIRI().equals(XSDVocabulary.UNSIGNED_LONG.getIRI());
  }

  @Override public boolean isUnsignedInt()
  {
    return this.literal.getDatatype().getIRI().equals(XSDVocabulary.UNSIGNED_INT.getIRI());
  }

  @Override public boolean isUnsignedShort()
  {
    return this.literal.getDatatype().getIRI().equals(XSDVocabulary.UNSIGNED_SHORT.getIRI());
  }

  @Override public boolean isUnsignedByte()
  {
    return this.literal.getDatatype().getIRI().equals(XSDVocabulary.UNSIGNED_BYTE.getIRI());
  }

  @Override public boolean isString()
  {
    return this.literal.getDatatype().isString();
  }

  @Override public boolean isBoolean()
  {
    return this.literal.getDatatype().isBoolean();
  }

  @Override public boolean isAnyURI()
  {
    return this.literal.getDatatype().getIRI().equals(XSDVocabulary.ANY_URI.getIRI());
  }

  @Override public boolean isTime()
  {
    return this.literal.getDatatype().getIRI().equals(XSDVocabulary.TIME.getIRI());
  }

  @Override public boolean isDate()
  {
    return this.literal.getDatatype().getIRI().equals(XSDVocabulary.DATE.getIRI());
  }

  @Override public boolean isDateTime()
  {
    return this.literal.getDatatype().getIRI().equals(XSDVocabulary.DATE_TIME.getIRI());
  }

  @Override public boolean isDuration()
  {
    return this.literal.getDatatype().getIRI().equals(XSDVocabulary.DURATION.getIRI());
  }

  @Override public boolean isComparable()
  {
    return isNumeric() || isString() || isTime() || isDate() || isDateTime() || isDuration();
  }

  @Override public boolean isQuotableType()
  {
    return isString() || isTime() || isDate() || isDateTime() || isDuration();
  }

  @NonNull @Override public String getString() throws LiteralException
  {
    if (!isString())
      throw new LiteralException(
        "cannot convert value of type " + this.literal.getDatatype() + " to " + XSDVocabulary.STRING.getPrefixedName());

    return getOWLLiteral().getLiteral();
  }

  @Override public boolean getBoolean() throws LiteralException
  {
    if (!isBoolean())
      throw new LiteralException(
        "cannot convert value of type " + this.literal.getDatatype() + " to " + XSDVocabulary.BOOLEAN
          .getPrefixedName());

    return Boolean.parseBoolean(this.literal.getLiteral());
  }

  @Override public byte getByte() throws LiteralException
  {
    try {
      if (!isByte())
        throw new LiteralException(
          "cannot convert value of type " + this.literal.getDatatype() + " to " + XSDVocabulary.BYTE.getPrefixedName());
      return Byte.parseByte(this.literal.getLiteral());
    } catch (NumberFormatException e) {
      throw new LiteralException(
        "cannot convert value " + this.literal.getLiteral() + " of type " + this.literal.getDatatype()
          + XSDVocabulary.BYTE.getPrefixedName());
    }
  }

  @Override public short getShort() throws LiteralException
  {
    try {
      if (isShort())
        return Short.parseShort(this.literal.getLiteral());
      else if (isByte())
        return Byte.parseByte(this.literal.getLiteral());
      else
        throw new LiteralException(
          "cannot convert value of type " + this.literal.getDatatype() + " to " + XSDVocabulary.SHORT
            .getPrefixedName());
    } catch (NumberFormatException e) {
      throw new LiteralException(
        "cannot convert value " + this.literal.getLiteral() + " of type " + this.literal.getDatatype() + " to "
          + XSDVocabulary.SHORT.getPrefixedName());
    }
  }

  @Override public int getInt() throws LiteralException
  {
    try {
      if (isInt())
        return Integer.parseInt(this.literal.getLiteral());
      else if (isShort())
        return Short.parseShort(this.literal.getLiteral());
      else if (isByte())
        return Byte.parseByte(this.literal.getLiteral());
      else
        throw new LiteralException(
          "cannot convert value of type " + this.literal.getDatatype() + " to " + XSDVocabulary.INT.getPrefixedName());
    } catch (NumberFormatException e) {
      throw new LiteralException(
        "cannot convert value " + this.literal.getLiteral() + " of type " + this.literal.getDatatype() + " to "
          + XSDVocabulary.INT.getPrefixedName());
    }
  }

  @Override public long getLong() throws LiteralException
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
        throw new LiteralException(
          "cannot convert value of type " + this.literal.getDatatype() + " to " + XSDVocabulary.LONG.getPrefixedName());
    } catch (NumberFormatException e) {
      throw new LiteralException(
        "cannot convert value " + this.literal.getLiteral() + " of type " + this.literal.getDatatype() + " to "
          + XSDVocabulary.LONG.getPrefixedName());
    }
  }

  // Some precision loss possible going from integer and long to float. See:
  // http://www.particle.kth.se/~lindsey/JavaCourse/Book/Part1/Java/Chapter02/castsMixing.html
  @Override public float getFloat() throws LiteralException
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
        throw new LiteralException(
          "cannot convert value of type " + this.literal.getDatatype() + " to " + XSDVocabulary.FLOAT
            .getPrefixedName());
    } catch (NumberFormatException e) {
      throw new LiteralException(
        "cannot convert value " + this.literal.getLiteral() + " of type " + this.literal.getDatatype() + " to "
          + XSDVocabulary.FLOAT);
    }
  }

  // Some precision loss possible going from long to double. See:
  // http://www.particle.kth.se/~lindsey/JavaCourse/Book/Part1/Java/Chapter02/castsMixing.html
  @Override public double getDouble() throws LiteralException
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
        throw new LiteralException(
          "cannot convert value of type " + this.literal.getDatatype() + " to " + XSDVocabulary.DOUBLE
            .getPrefixedName());
    } catch (NumberFormatException e) {
      throw new LiteralException(
        "cannot convert value " + this.literal.getLiteral() + " of type " + this.literal.getDatatype() + " to "
          + XSDVocabulary.DOUBLE);
    }
  }

  @NonNull @Override public BigDecimal getDecimal() throws LiteralException
  {
    try {
      if (isNumeric())
        return new BigDecimal(this.literal.getLiteral());
      else
        throw new LiteralException(
          "cannot convert value of type " + this.literal.getDatatype() + " to " + XSDVocabulary.DECIMAL
            .getPrefixedName());
    } catch (NumberFormatException e) {
      throw new LiteralException(
        "cannot convert value " + this.literal.getLiteral() + " of type " + this.literal.getDatatype() + " to "
          + XSDVocabulary.DECIMAL.getPrefixedName());
    }
  }

  @NonNull @Override public BigInteger getInteger() throws LiteralException
  {
    try {
      if (isNumeric())
        return new BigInteger(this.literal.getLiteral());
      else
        throw new LiteralException(
          "cannot convert value of type " + this.literal.getDatatype() + " to " + XSDVocabulary.INTEGER
            .getPrefixedName());
    } catch (NumberFormatException e) {
      throw new LiteralException(
        "cannot convert value " + this.literal.getLiteral() + " of type " + this.literal.getDatatype() + " to "
          + XSDVocabulary.INTEGER.getPrefixedName());
    }
  }

  @NonNull @Override public BigInteger getNonNegativeInteger() throws LiteralException
  {
    try {
      if (isNumeric()) {
        BigInteger i = new BigInteger(this.literal.getLiteral());
        if (i.signum() != -1)
          return i;
        else
          throw new LiteralException(
            "invalid value " + getValue() + " for " + XSDVocabulary.NON_NEGATIVE_INTEGER.getPrefixedName());
      } else
        throw new LiteralException(
          "cannot convert value of type " + this.literal.getDatatype() + " to " + XSDVocabulary.NON_NEGATIVE_INTEGER
            .getPrefixedName());
    } catch (NumberFormatException e) {
      throw new LiteralException(
        "cannot convert value " + this.literal.getLiteral() + " of type " + this.literal.getDatatype() + " to "
          + XSDVocabulary.NON_NEGATIVE_INTEGER.getPrefixedName());
    }
  }

  @NonNull @Override public BigInteger getNonPositiveInteger() throws LiteralException
  {
    try {
      if (isNumeric()) {
        BigInteger i = new BigInteger(this.literal.getLiteral());
        if (i.signum() != 1)
          return i;
        else
          throw new LiteralException(
            "invalid value " + getValue() + " for " + XSDVocabulary.NON_POSITIVE_INTEGER.getPrefixedName());
      } else
        throw new LiteralException(
          "cannot convert value of type " + this.literal.getDatatype() + " to " + XSDVocabulary.NON_POSITIVE_INTEGER
            .getPrefixedName());
    } catch (NumberFormatException e) {
      throw new LiteralException(
        "cannot convert value " + this.literal.getLiteral() + " of type " + this.literal.getDatatype() + " to "
          + XSDVocabulary.NON_POSITIVE_INTEGER.getPrefixedName());
    }
  }

  @NonNull @Override public BigInteger getNegativeInteger() throws LiteralException
  {
    try {
      if (isNumeric()) {
        BigInteger i = new BigInteger(this.literal.getLiteral());
        if (i.signum() == -1)
          return i;
        else
          throw new LiteralException(
            "invalid value " + getValue() + " for " + XSDVocabulary.NEGATIVE_INTEGER.getPrefixedName());
      } else
        throw new LiteralException(
          "cannot convert value of type " + this.literal.getDatatype() + " to " + XSDVocabulary.NEGATIVE_INTEGER
            .getPrefixedName());
    } catch (NumberFormatException e) {
      throw new LiteralException(
        "cannot convert value " + this.literal.getLiteral() + " of type " + this.literal.getDatatype() + " to "
          + XSDVocabulary.NEGATIVE_INTEGER.getPrefixedName());
    }
  }

  @NonNull @Override public BigInteger getPositiveInteger() throws LiteralException
  {
    try {
      if (isNumeric()) {
        BigInteger i = new BigInteger(this.literal.getLiteral());
        if (i.signum() == 1)
          return i;
        else
          throw new LiteralException(
            "invalid value " + getValue() + " for " + XSDVocabulary.POSITIVE_INTEGER.getPrefixedName());
      } else
        throw new LiteralException(
          "cannot convert value of type " + this.literal.getDatatype() + " to " + XSDVocabulary.POSITIVE_INTEGER
            .getPrefixedName());
    } catch (NumberFormatException e) {
      throw new LiteralException(
        "cannot convert value " + this.literal.getLiteral() + " of type " + this.literal.getDatatype() + " to "
          + XSDVocabulary.POSITIVE_INTEGER.getPrefixedName());
    }
  }

  @Override public long getUnsignedLong() throws LiteralException
  {
    try {
      if (isNumeric()) {
        long l = Long.parseLong(this.literal.getLiteral());
        if (l >= 0)
          return l;
        else
          throw new LiteralException(
            "negative value " + getValue() + " for " + XSDVocabulary.UNSIGNED_LONG.getPrefixedName());
      } else
        throw new LiteralException(
          "cannot convert value of type " + this.literal.getDatatype() + " to " + XSDVocabulary.UNSIGNED_LONG
            .getPrefixedName());
    } catch (NumberFormatException e) {
      throw new LiteralException(
        "cannot convert value " + this.literal.getLiteral() + " of type " + this.literal.getDatatype() + " to "
          + XSDVocabulary.UNSIGNED_LONG.getPrefixedName());
    }
  }

  @Override public long getUnsignedInt() throws LiteralException
  {
    try {
      if (isNumeric()) {
        long l = Long.parseLong(this.literal.getLiteral());
        if (l >= 0)
          return l;
        else
          throw new LiteralException(
            "negative value " + getValue() + " for " + XSDVocabulary.UNSIGNED_INT.getPrefixedName());
      } else
        throw new LiteralException(
          "cannot convert value of type " + this.literal.getDatatype() + " to " + XSDVocabulary.UNSIGNED_INT
            .getPrefixedName());
    } catch (NumberFormatException e) {
      throw new LiteralException(
        "cannot convert value " + this.literal.getLiteral() + " of type " + this.literal.getDatatype() + " to "
          + XSDVocabulary.UNSIGNED_INT.getPrefixedName());
    }
  }

  @Override public int getUnsignedShort() throws LiteralException
  {
    try {
      if (isNumeric()) {
        int i = Integer.parseInt(this.literal.getLiteral());
        if (i >= 0)
          return i;
        else
          throw new LiteralException(
            "negative value " + getValue() + " for " + XSDVocabulary.UNSIGNED_SHORT.getPrefixedName());
      } else
        throw new LiteralException(
          "cannot convert value of type " + this.literal.getDatatype() + " to " + XSDVocabulary.UNSIGNED_SHORT
            .getPrefixedName());
    } catch (NumberFormatException e) {
      throw new LiteralException(
        "cannot convert value " + this.literal.getLiteral() + " of type " + this.literal.getDatatype() + " to "
          + XSDVocabulary.UNSIGNED_SHORT.getPrefixedName());
    }
  }

  @Override public short getUnsignedByte() throws LiteralException
  {
    try {
      if (isNumeric()) {
        short s = Short.parseShort(this.literal.getLiteral());
        if (s >= 0)
          return s;
        else
          throw new LiteralException(
            "negative value " + getValue() + " for " + XSDVocabulary.UNSIGNED_BYTE.getPrefixedName());
      } else
        throw new LiteralException(
          "cannot convert value of type " + this.literal.getDatatype() + " to " + XSDVocabulary.UNSIGNED_BYTE
            .getPrefixedName());
    } catch (NumberFormatException e) {
      throw new LiteralException(
        "cannot convert value " + this.literal.getLiteral() + " of type " + this.literal.getDatatype() + " to "
          + XSDVocabulary.UNSIGNED_BYTE.getPrefixedName());
    }
  }

  @NonNull @Override public URI getAnyURI() throws LiteralException
  {
    try {
      if (!isAnyURI())
        throw new LiteralException(
          "cannot convert value of type " + this.literal.getDatatype() + " to " + XSDVocabulary.ANY_URI
            .getPrefixedName());
      return URI.create(this.literal.getLiteral());
    } catch (IllegalArgumentException e) {
      throw new LiteralException(
        "cannot convert value " + this.literal.getLiteral() + " of type " + this.literal.getDatatype() + " to "
          + XSDVocabulary.ANY_URI.getPrefixedName());
    }
  }

  @NonNull @Override public XSDTime getTime() throws LiteralException
  {
    if (!isTime())
      throw new LiteralException(
        "cannot convert value of type " + this.literal.getDatatype() + " to " + XSDVocabulary.TIME.getPrefixedName());
    return new XSDTime(this.literal.getLiteral());
  }

  @NonNull @Override public XSDDate getDate() throws LiteralException
  {
    if (!isDate())
      throw new LiteralException(
        "cannot convert value of type " + this.literal.getDatatype() + " to " + XSDVocabulary.DATE.getPrefixedName());
    return new XSDDate(this.literal.getLiteral());
  }

  @NonNull @Override public XSDDateTime getDateTime() throws LiteralException
  {
    if (!isDateTime())
      throw new LiteralException(
        "cannot convert value of type " + this.literal.getDatatype() + " to " + XSDVocabulary.DATE_TIME
          .getPrefixedName());
    return new XSDDateTime(this.literal.getLiteral());
  }

  @NonNull @Override public XSDDuration getDuration() throws LiteralException
  {
    if (!isDuration())
      throw new LiteralException(
        "cannot convert value of type " + this.literal.getDatatype() + " to " + XSDVocabulary.DURATION
          .getPrefixedName());

    return new XSDDuration(this.literal.getLiteral());
  }

  @NonNull @Override public String getValue()
  {
    return this.literal.getLiteral();
  }

  @NonNull @Override public OWLDatatype getOWLDatatype()
  {
    return this.literal.getDatatype();
  }

  @NonNull @Override public String getOWLDatatypeName()
  {
    return getOWLDatatype().toString();
  }

  @SideEffectFree @NonNull @Override public String toString()
  {
    return this.literal.toString();
  }

  @NonNull @Override public String toQuotedString()
  {
    // Escape non-escaped double quote characters; for humans: [^\\]" -> \\"
    return "\"" + this.literal.toString().replaceAll("[~\\\\]\"", "\\\\\"") + "\"";
  }

  @SideEffectFree @Deterministic @Override public boolean equals(@Nullable Object obj)
  {
    if (this == obj)
      return true;
    if ((obj == null) || (obj.getClass() != this.getClass()))
      return false;
    DefaultLiteral impl = (DefaultLiteral)obj;
    return (this.literal != null && impl.literal != null && this.literal.equals(impl.literal));
  }

  @SideEffectFree @Deterministic @Override public int hashCode()
  {
    int hash = 95;
    hash = hash + (null == this.literal ? 0 : this.literal.hashCode());
    return hash;
  }
}
