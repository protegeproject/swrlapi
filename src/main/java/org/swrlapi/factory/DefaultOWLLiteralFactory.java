package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.vocab.XSDVocabulary;
import org.swrlapi.literal.OWLLiteralValidator;
import org.swrlapi.literal.XSDDate;
import org.swrlapi.literal.XSDDateTime;
import org.swrlapi.literal.XSDDuration;
import org.swrlapi.literal.XSDTime;
import uk.ac.manchester.cs.owl.owlapi.OWLLiteralImpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.List;

class DefaultOWLLiteralFactory implements OWLLiteralFactory
{
  @NonNull private final OWLDatatypeFactory datatypeFactory;

  public DefaultOWLLiteralFactory()
  {
    this.datatypeFactory = SWRLAPIInternalFactory.createOWLDatatypeFactory();
  }

  @NonNull @Override public OWLLiteral getOWLLiteral(byte b)
  {
    return new OWLLiteralImpl("" + b, "", getOWLDatatypeFactory().getByteDatatype());
  }

  @NonNull @Override public OWLLiteral getOWLLiteral(short s)
  {
    return new OWLLiteralImpl("" + s, "", getOWLDatatypeFactory().getShortDatatype());
  }

  @NonNull @Override public OWLLiteral getOWLLiteral(int i)
  {
    return new OWLLiteralImpl("" + i, "", getOWLDatatypeFactory().getIntDatatype());
  }

  @NonNull @Override public OWLLiteral getOWLLiteral(long l)
  {
    return new OWLLiteralImpl("" + l, "", getOWLDatatypeFactory().getLongDatatype());
  }

  @NonNull @Override public OWLLiteral getOWLLiteral(float f)
  {
    return new OWLLiteralImpl("" + f, "", getOWLDatatypeFactory().getFloatDatatype());
  }

  @NonNull @Override public OWLLiteral getOWLLiteral(double d)
  {
    return new OWLLiteralImpl("" + d, "", getOWLDatatypeFactory().getDoubleDatatype());
  }

  @NonNull @Override public OWLLiteral getOWLLiteral(@NonNull BigDecimal d)
  {
    return new OWLLiteralImpl("" + d, "", getOWLDatatypeFactory().getDecimalDatatype());
  }

  @NonNull @Override public OWLLiteral getOWLLiteral(@NonNull BigInteger i)
  {
    return new OWLLiteralImpl("" + i, "", getOWLDatatypeFactory().getIntegerDatatype());
  }

  @NonNull @Override public OWLLiteral getOWLLiteral(@NonNull String s)
  {
    return new OWLLiteralImpl(s, "", getOWLDatatypeFactory().getStringDatatype());
  }

  @NonNull @Override public OWLLiteral getOWLLiteral(boolean b)
  {
    return new OWLLiteralImpl("" + b, "", getOWLDatatypeFactory().getBooleanDatatype());
  }

  @NonNull @Override public OWLLiteral getOWLLiteral(URI uri)
  {
    return new OWLLiteralImpl("" + uri, "", getOWLDatatypeFactory().getURIDatatype());
  }

  @NonNull @Override public OWLLiteral getOWLLiteral(@NonNull XSDDate date)
  {
    return new OWLLiteralImpl(date.getContent(), "", getOWLDatatypeFactory().getDateDatatype());
  }

  @NonNull @Override public OWLLiteral getOWLLiteral(@NonNull XSDTime time)
  {
    return new OWLLiteralImpl(time.getContent(), "", getOWLDatatypeFactory().getTimeDatatype());
  }

  @NonNull @Override public OWLLiteral getOWLLiteral(@NonNull XSDDateTime datetime)
  {
    return new OWLLiteralImpl(datetime.getContent(), "", getOWLDatatypeFactory().getDateTimeDatatype());
  }

  @NonNull @Override public OWLLiteral getOWLLiteral(@NonNull XSDDuration duration)
  {
    return new OWLLiteralImpl(duration.getContent(), "", getOWLDatatypeFactory().getDurationDatatype());
  }

  @NonNull @Override public OWLLiteral getOWLLiteral(@NonNull String literal, @NonNull OWLDatatype datatype)
  {
    validateOWLLiteral(literal, datatype);
    return new OWLLiteralImpl(literal, "", datatype);
  }

  @NonNull @Override public OWLLiteral createLeastNarrowNumericOWLLiteral(double value,
      @NonNull List<@NonNull OWLLiteral> inputLiterals)
  {
    if (isWidestNumericLiteralAByte(inputLiterals))
      return getOWLLiteral((byte)value);
    else if (isWidestNumericLiteralAShort(inputLiterals))
      return getOWLLiteral((short)value);
    else if (isWidestNumericLiteralAnInt(inputLiterals))
      return getOWLLiteral((int)value);
    else if (isWidestNumericLiteralALong(inputLiterals))
      return getOWLLiteral((long)value);
    else if (isWidestNumericLiteralAFloat(inputLiterals))
      return getOWLLiteral((float)value);
    else
      return getOWLLiteral(value);
  }

  private boolean isWidestNumericLiteralAByte(@NonNull List<@NonNull OWLLiteral> literals)
  {
    for (OWLLiteral literal : literals)
      if (isShort(literal) || isInt(literal) || isLong(literal) || isFloat(literal) || isDouble(literal))
        return false;
    return true;
  }

  private boolean isWidestNumericLiteralAShort(@NonNull List<@NonNull OWLLiteral> literals)
  {
    for (OWLLiteral literal : literals)
      if (isInt(literal) || isLong(literal) || isFloat(literal) || isDouble(literal))
        return false;
    return true;
  }

  private boolean isWidestNumericLiteralAnInt(@NonNull List<@NonNull OWLLiteral> literals)
  {
    for (OWLLiteral literal : literals)
      if (isLong(literal) || isFloat(literal) || isDouble(literal))
        return false;
    return true;
  }

  private boolean isWidestNumericLiteralALong(@NonNull List<@NonNull OWLLiteral> literals)
  {
    for (OWLLiteral literal : literals)
      if (isFloat(literal) || isDouble(literal))
        return false;
    return true;
  }

  private boolean isWidestNumericLiteralAFloat(@NonNull List<@NonNull OWLLiteral> literals)
  {
    for (OWLLiteral literal : literals)
      if (isDouble(literal))
        return false;
    return true;
  }

  private boolean isShort(@NonNull OWLLiteral literal)
  {
    return literal.getDatatype().getIRI().equals(XSDVocabulary.SHORT.getIRI());
  }

  private boolean isInt(@NonNull OWLLiteral literal)
  {
    return literal.getDatatype().getIRI().equals(XSDVocabulary.INT.getIRI());
  }

  private boolean isLong(@NonNull OWLLiteral literal)
  {
    return literal.getDatatype().getIRI().equals(XSDVocabulary.LONG.getIRI());
  }

  private boolean isFloat(@NonNull OWLLiteral literal)
  {
    return literal.getDatatype().isFloat();
  }

  private boolean isDouble(@NonNull OWLLiteral literal)
  {
    return literal.getDatatype().isDouble();
  }

  private void validateOWLLiteral(@NonNull String literal, @NonNull OWLDatatype datatype)
  {
    if (!OWLLiteralValidator.isValid(literal, datatype))
      throw new RuntimeException("literal value '" + literal + "' is not a valid " + datatype.getIRI());
  }

  @NonNull private OWLDatatypeFactory getOWLDatatypeFactory()
  {
    return this.datatypeFactory;
  }
}
