package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
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
  @NonNull private final OWLLiteralFactory owlLiteralFactory;

  public DefaultLiteralFactory()
  {
    this.owlLiteralFactory = SWRLAPIFactory.getOWLLiteralFactory();
  }

  @NonNull @Override
  public Literal getLiteral(byte b)
  {
    return SWRLAPIFactory.getLiteral(getOWLLiteralFactory().getOWLLiteral(b));
  }

  @NonNull @Override
  public Literal getLiteral(short s)
  {
    return SWRLAPIFactory.getLiteral(getOWLLiteralFactory().getOWLLiteral(s));
  }

  @NonNull @Override
  public Literal getLiteral(float value)
  {
    return SWRLAPIFactory.getLiteral(getOWLLiteralFactory().getOWLLiteral(value));
  }

  @NonNull @Override
  public Literal getLiteral(int value)
  {
    return SWRLAPIFactory.getLiteral(getOWLLiteralFactory().getOWLLiteral(value));
  }

  @NonNull @Override
  public Literal getLiteral(double value)
  {
    return SWRLAPIFactory.getLiteral(getOWLLiteralFactory().getOWLLiteral(value));
  }

  @NonNull @Override
  public Literal getLiteral(String value)
  {
    return SWRLAPIFactory.getLiteral(getOWLLiteralFactory().getOWLLiteral(value));
  }

  @NonNull @Override
  public Literal getLiteral(boolean value)
  {
    return SWRLAPIFactory.getLiteral(getOWLLiteralFactory().getOWLLiteral(value));
  }

  @NonNull @Override
  public Literal getLiteral(@NonNull URI uri)
  {
    return SWRLAPIFactory.getLiteral(getOWLLiteralFactory().getOWLLiteral(uri));
  }

  @NonNull @Override
  public Literal getLiteral(@NonNull XSDDate date)
  {
    return SWRLAPIFactory.getLiteral(getOWLLiteralFactory().getOWLLiteral(date));
  }

  @NonNull @Override
  public Literal getLiteral(@NonNull XSDTime time)
  {
    return SWRLAPIFactory.getLiteral(getOWLLiteralFactory().getOWLLiteral(time));
  }

  @NonNull @Override
  public Literal getLiteral(@NonNull XSDDateTime datetime)
  {
    return SWRLAPIFactory.getLiteral(getOWLLiteralFactory().getOWLLiteral(datetime));
  }

  @NonNull @Override
  public Literal getLiteral(@NonNull XSDDuration duration)
  {
    return SWRLAPIFactory.getLiteral(getOWLLiteralFactory().getOWLLiteral(duration));
  }

  @NonNull @Override
  public Literal getLiteral(@NonNull OWLLiteral literal)
  {
    return SWRLAPIFactory.getLiteral(literal);
  }

  @NonNull private OWLLiteralFactory getOWLLiteralFactory()
  {
    return this.owlLiteralFactory;
  }
}
