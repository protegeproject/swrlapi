package org.swrlapi.factory;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.core.Literal;
import org.swrlapi.core.LiteralFactory;
import org.swrlapi.core.OWLLiteralFactory;
import org.swrlapi.core.xsd.XSDDate;
import org.swrlapi.core.xsd.XSDDateTime;
import org.swrlapi.core.xsd.XSDDuration;
import org.swrlapi.core.xsd.XSDTime;

import java.net.URI;

class DefaultLiteralFactory implements LiteralFactory
{
  private final OWLLiteralFactory owlLiteralFactory;

  public DefaultLiteralFactory()
  {
    this.owlLiteralFactory = SWRLAPIFactory.getOWLLiteralFactory();
  }

  @Override
  public Literal getLiteral(byte b)
  {
    return SWRLAPIFactory.getLiteral(getOWLLiteralFactory().getOWLLiteral(b));
  }

  @Override
  public Literal getLiteral(short s)
  {
    return SWRLAPIFactory.getLiteral(getOWLLiteralFactory().getOWLLiteral(s));
  }

  @Override
  public Literal getLiteral(float value)
  {
    return SWRLAPIFactory.getLiteral(getOWLLiteralFactory().getOWLLiteral(value));
  }

  @Override
  public Literal getLiteral(int value)
  {
    return SWRLAPIFactory.getLiteral(getOWLLiteralFactory().getOWLLiteral(value));
  }

  @Override
  public Literal getLiteral(double value)
  {
    return SWRLAPIFactory.getLiteral(getOWLLiteralFactory().getOWLLiteral(value));
  }

  @Override
  public Literal getLiteral(String value)
  {
    return SWRLAPIFactory.getLiteral(getOWLLiteralFactory().getOWLLiteral(value));
  }

  @Override
  public Literal getLiteral(boolean value)
  {
    return SWRLAPIFactory.getLiteral(getOWLLiteralFactory().getOWLLiteral(value));
  }

  @Override
  public Literal getLiteral(URI uri)
  {
    return SWRLAPIFactory.getLiteral(getOWLLiteralFactory().getOWLLiteral(uri));
  }

  @Override
  public Literal getLiteral(XSDDate date)
  {
    return SWRLAPIFactory.getLiteral(getOWLLiteralFactory().getOWLLiteral(date));
  }

  @Override
  public Literal getLiteral(XSDTime time)
  {
    return SWRLAPIFactory.getLiteral(getOWLLiteralFactory().getOWLLiteral(time));
  }

  @Override
  public Literal getLiteral(XSDDateTime datetime)
  {
    return SWRLAPIFactory.getLiteral(getOWLLiteralFactory().getOWLLiteral(datetime));
  }

  @Override
  public Literal getLiteral(XSDDuration duration)
  {
    return SWRLAPIFactory.getLiteral(getOWLLiteralFactory().getOWLLiteral(duration));
  }

  @Override
  public Literal getLiteral(OWLLiteral literal)
  {
    return SWRLAPIFactory.getLiteral(literal);
  }

  private OWLLiteralFactory getOWLLiteralFactory()
  {
    return this.owlLiteralFactory;
  }
}
