package org.swrlapi.factory;

import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.vocab.XSDVocabulary;
import org.swrlapi.core.OWLDatatypeFactory;
import org.swrlapi.core.OWLLiteralFactory;
import org.swrlapi.core.OWLLiteralValidator;
import org.swrlapi.core.xsd.XSDDate;
import org.swrlapi.core.xsd.XSDDateTime;
import org.swrlapi.core.xsd.XSDDuration;
import org.swrlapi.core.xsd.XSDTime;
import uk.ac.manchester.cs.owl.owlapi.OWLLiteralImpl;

import java.net.URI;
import java.util.List;

public class DefaultOWLLiteralFactory implements OWLLiteralFactory
{
  private final OWLDatatypeFactory datatypeFactory;

  public DefaultOWLLiteralFactory()
  {
    this.datatypeFactory = SWRLAPIFactory.getOWLDatatypeFactory();
  }

  @Override
  public OWLLiteral getOWLLiteral(byte b)
  {
    return new OWLLiteralImpl("" + b, "", getOWLDatatypeFactory().getByteDatatype());
  }

  @Override
  public OWLLiteral getOWLLiteral(short s)
  {
    return new OWLLiteralImpl("" + s, "", getOWLDatatypeFactory().getShortDatatype());
  }

  @Override
  public OWLLiteral getOWLLiteral(int i)
  {
    return new OWLLiteralImpl("" + i, "", getOWLDatatypeFactory().getIntDatatype());
  }

  @Override
  public OWLLiteral getOWLLiteral(long l)
  {
    return new OWLLiteralImpl("" + l, "", getOWLDatatypeFactory().getLongDatatype());
  }

  @Override
  public OWLLiteral getOWLLiteral(float f)
  {
    return new OWLLiteralImpl("" + f, "", getOWLDatatypeFactory().getFloatDatatype());
  }

  @Override
  public OWLLiteral getOWLLiteral(double d)
  {
    return new OWLLiteralImpl("" + d, "", getOWLDatatypeFactory().getDoubleDatatype());
  }

  @Override
  public OWLLiteral getOWLLiteral(String s)
  {
    return new OWLLiteralImpl(s, "", getOWLDatatypeFactory().getStringDatatype());
  }

  @Override
  public OWLLiteral getOWLLiteral(boolean b)
  {
    return new OWLLiteralImpl("" + b, "", getOWLDatatypeFactory().getBooleanDatatype());
  }

  @Override
  public OWLLiteral getOWLLiteral(URI uri)
  {
    return new OWLLiteralImpl("" + uri, "", getOWLDatatypeFactory().getURIDatatype());
  }

  @Override
  public OWLLiteral getOWLLiteral(XSDDate date)
  {
    return new OWLLiteralImpl(date.getContent(), "", getOWLDatatypeFactory().getDateDatatype());
  }

  @Override
  public OWLLiteral getOWLLiteral(XSDTime time)
  {
    return new OWLLiteralImpl(time.getContent(), "", getOWLDatatypeFactory().getTimeDatatype());
  }

  @Override
  public OWLLiteral getOWLLiteral(XSDDateTime datetime)
  {
    return new OWLLiteralImpl(datetime.getContent(), "", getOWLDatatypeFactory().getDateTimeDatatype());
  }

  @Override
  public OWLLiteral getOWLLiteral(XSDDuration duration)
  {
    return new OWLLiteralImpl(duration.getContent(), "", getOWLDatatypeFactory().getDurationDatatype());
  }

  @Override
  public OWLLiteral getOWLLiteral(String literal, OWLDatatype datatype)
  {
    validateOWLLiteral(literal, datatype);
    return new OWLLiteralImpl(literal, "", datatype);
  }

  @Override
  public OWLLiteral createLeastNarrowNumericOWLLiteral(double value, List<OWLLiteral> inputLiterals)
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

  private boolean isWidestNumericLiteralAByte(List<OWLLiteral> literals)
  {
    for (OWLLiteral literal : literals)
      if (isShort(literal) || isInt(literal) || isLong(literal) || isFloat(literal) || isDouble(literal))
        return false;
    return true;
  }

  private boolean isWidestNumericLiteralAShort(List<OWLLiteral> literals)
  {
    for (OWLLiteral literal : literals)
      if (isInt(literal) || isLong(literal) || isFloat(literal) || isDouble(literal))
        return false;
    return true;
  }

  private boolean isWidestNumericLiteralAnInt(List<OWLLiteral> literals)
  {
    for (OWLLiteral literal : literals)
      if (isLong(literal) || isFloat(literal) || isDouble(literal))
        return false;
    return true;
  }

  private boolean isWidestNumericLiteralALong(List<OWLLiteral> literals)
  {
    for (OWLLiteral literal : literals)
      if (isFloat(literal) || isDouble(literal))
        return false;
    return true;
  }

  private boolean isWidestNumericLiteralAFloat(List<OWLLiteral> literals)
  {
    for (OWLLiteral literal : literals)
      if (isDouble(literal))
        return false;
    return true;
  }

  private boolean isShort(OWLLiteral literal)
  {
    return literal.getDatatype().getIRI().equals(XSDVocabulary.SHORT.getIRI());
  }

  private boolean isInt(OWLLiteral literal)
  {
    return literal.getDatatype().getIRI().equals(XSDVocabulary.INT.getIRI());
  }

  private boolean isLong(OWLLiteral literal)
  {
    return literal.getDatatype().getIRI().equals(XSDVocabulary.LONG.getIRI());
  }

  private boolean isFloat(OWLLiteral literal)
  {
    return literal.getDatatype().isFloat();
  }

  private boolean isDouble(OWLLiteral literal)
  {
    return literal.getDatatype().isDouble();
  }

  private void validateOWLLiteral(String literal, OWLDatatype datatype)
  {
    if (!OWLLiteralValidator.isValid(literal, datatype))
      throw new RuntimeException("literal value " + literal + " is not a valid " + datatype.getIRI());
  }

  private OWLDatatypeFactory getOWLDatatypeFactory()
  {
    return this.datatypeFactory;
  }
}
