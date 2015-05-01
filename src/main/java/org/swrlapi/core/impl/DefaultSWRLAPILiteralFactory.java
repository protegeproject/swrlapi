package org.swrlapi.core.impl;

import java.net.URI;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.core.OWLLiteralFactory;
import org.swrlapi.core.SWRLAPILiteral;
import org.swrlapi.core.SWRLAPILiteralFactory;
import org.swrlapi.core.xsd.XSDDate;
import org.swrlapi.core.xsd.XSDDateTime;
import org.swrlapi.core.xsd.XSDDuration;
import org.swrlapi.core.xsd.XSDTime;

public class DefaultSWRLAPILiteralFactory implements SWRLAPILiteralFactory
{
  private final OWLLiteralFactory owlLiteralFactory;

  public DefaultSWRLAPILiteralFactory()
  {
    this.owlLiteralFactory = new DefaultOWLLiteralFactory();
  }

  @Override
  public SWRLAPILiteral getSWRLAPILiteral(byte b)
  {
    return new DefaultSWRLAPILiteral(getOWLLiteralFactory().getOWLLiteral(b));
  }

  @Override
  public SWRLAPILiteral getSWRLAPILiteral(short s)
  {
    return new DefaultSWRLAPILiteral(getOWLLiteralFactory().getOWLLiteral(s));
  }

  @Override
  public SWRLAPILiteral getSWRLAPILiteral(float value)
  {
    return new DefaultSWRLAPILiteral(getOWLLiteralFactory().getOWLLiteral(value));
  }

  @Override
  public SWRLAPILiteral getSWRLAPILiteral(int value)
  {
    return new DefaultSWRLAPILiteral(getOWLLiteralFactory().getOWLLiteral(value));
  }

  @Override
  public SWRLAPILiteral getSWRLAPILiteral(double value)
  {
    return new DefaultSWRLAPILiteral(getOWLLiteralFactory().getOWLLiteral(value));
  }

  @Override
  public SWRLAPILiteral getSWRLAPILiteral(String value)
  {
    return new DefaultSWRLAPILiteral(getOWLLiteralFactory().getOWLLiteral(value));
  }

  @Override
  public SWRLAPILiteral getSWRLAPILiteral(boolean value)
  {
    return new DefaultSWRLAPILiteral(getOWLLiteralFactory().getOWLLiteral(value));
  }

  @Override
  public SWRLAPILiteral getSWRLAPILiteral(URI uri)
  {
    return new DefaultSWRLAPILiteral(getOWLLiteralFactory().getOWLLiteral(uri));
  }

  @Override
  public SWRLAPILiteral getSWRLAPILiteral(XSDDate date)
  {
    return new DefaultSWRLAPILiteral(getOWLLiteralFactory().getOWLLiteral(date));
  }

  @Override
  public SWRLAPILiteral getSWRLAPILiteral(XSDTime time)
  {
    return new DefaultSWRLAPILiteral(getOWLLiteralFactory().getOWLLiteral(time));
  }

  @Override
  public SWRLAPILiteral getSWRLAPILiteral(XSDDateTime datetime)
  {
    return new DefaultSWRLAPILiteral(getOWLLiteralFactory().getOWLLiteral(datetime));
  }

  @Override
  public SWRLAPILiteral getSWRLAPILiteral(XSDDuration duration)
  {
    return new DefaultSWRLAPILiteral(getOWLLiteralFactory().getOWLLiteral(duration));
  }

  @Override
  public SWRLAPILiteral getSWRLAPILiteral(OWLLiteral literal)
  {
    return new DefaultSWRLAPILiteral(literal);
  }

  private OWLLiteralFactory getOWLLiteralFactory()
  {
    return this.owlLiteralFactory;
  }
}
