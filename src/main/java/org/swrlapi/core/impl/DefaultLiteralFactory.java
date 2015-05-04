package org.swrlapi.core.impl;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.core.Literal;
import org.swrlapi.core.LiteralFactory;
import org.swrlapi.core.OWLLiteralFactory;
import org.swrlapi.core.SWRLAPIFactory;
import org.swrlapi.core.xsd.XSDDate;
import org.swrlapi.core.xsd.XSDDateTime;
import org.swrlapi.core.xsd.XSDDuration;
import org.swrlapi.core.xsd.XSDTime;

import java.net.URI;

public class DefaultLiteralFactory implements LiteralFactory
{
  private final OWLLiteralFactory owlLiteralFactory;

  public DefaultLiteralFactory()
  {
    this.owlLiteralFactory = SWRLAPIFactory.getOWLLiteralFactory();
  }

  @Override
  public Literal getLiteral(byte b)
  {
    return new DefaultLiteral(getOWLLiteralFactory().getOWLLiteral(b));
  }

  @Override
  public Literal getLiteral(short s)
  {
    return new DefaultLiteral(getOWLLiteralFactory().getOWLLiteral(s));
  }

  @Override
  public Literal getLiteral(float value)
  {
    return new DefaultLiteral(getOWLLiteralFactory().getOWLLiteral(value));
  }

  @Override
  public Literal getLiteral(int value)
  {
    return new DefaultLiteral(getOWLLiteralFactory().getOWLLiteral(value));
  }

  @Override
  public Literal getLiteral(double value)
  {
    return new DefaultLiteral(getOWLLiteralFactory().getOWLLiteral(value));
  }

  @Override
  public Literal getLiteral(String value)
  {
    return new DefaultLiteral(getOWLLiteralFactory().getOWLLiteral(value));
  }

  @Override
  public Literal getLiteral(boolean value)
  {
    return new DefaultLiteral(getOWLLiteralFactory().getOWLLiteral(value));
  }

  @Override
  public Literal getLiteral(URI uri)
  {
    return new DefaultLiteral(getOWLLiteralFactory().getOWLLiteral(uri));
  }

  @Override
  public Literal getLiteral(XSDDate date)
  {
    return new DefaultLiteral(getOWLLiteralFactory().getOWLLiteral(date));
  }

  @Override
  public Literal getLiteral(XSDTime time)
  {
    return new DefaultLiteral(getOWLLiteralFactory().getOWLLiteral(time));
  }

  @Override
  public Literal getLiteral(XSDDateTime datetime)
  {
    return new DefaultLiteral(getOWLLiteralFactory().getOWLLiteral(datetime));
  }

  @Override
  public Literal getLiteral(XSDDuration duration)
  {
    return new DefaultLiteral(getOWLLiteralFactory().getOWLLiteral(duration));
  }

  @Override
  public Literal getLiteral(OWLLiteral literal)
  {
    return new DefaultLiteral(literal);
  }

  private OWLLiteralFactory getOWLLiteralFactory()
  {
    return this.owlLiteralFactory;
  }
}
