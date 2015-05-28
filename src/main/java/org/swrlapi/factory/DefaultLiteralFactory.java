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
    this.owlLiteralFactory = SWRLAPIFactory.createOWLLiteralFactory();
  }

  @NonNull @Override
  public Literal getLiteral(byte b)
  {
    return SWRLAPIFactory.createLiteral(getOWLLiteralFactory().getOWLLiteral(b));
  }

  @NonNull @Override
  public Literal getLiteral(short s)
  {
    return SWRLAPIFactory.createLiteral(getOWLLiteralFactory().getOWLLiteral(s));
  }

  @NonNull @Override
  public Literal getLiteral(float value)
  {
    return SWRLAPIFactory.createLiteral(getOWLLiteralFactory().getOWLLiteral(value));
  }

  @NonNull @Override
  public Literal getLiteral(int value)
  {
    return SWRLAPIFactory.createLiteral(getOWLLiteralFactory().getOWLLiteral(value));
  }

  @NonNull @Override
  public Literal getLiteral(double value)
  {
    return SWRLAPIFactory.createLiteral(getOWLLiteralFactory().getOWLLiteral(value));
  }

  @NonNull @Override
  public Literal getLiteral(@NonNull String value)
  {
    return SWRLAPIFactory.createLiteral(getOWLLiteralFactory().getOWLLiteral(value));
  }

  @NonNull @Override
  public Literal getLiteral(boolean value)
  {
    return SWRLAPIFactory.createLiteral(getOWLLiteralFactory().getOWLLiteral(value));
  }

  @NonNull @Override
  public Literal getLiteral(@NonNull URI uri)
  {
    return SWRLAPIFactory.createLiteral(getOWLLiteralFactory().getOWLLiteral(uri));
  }

  @NonNull @Override
  public Literal getLiteral(@NonNull XSDDate date)
  {
    return SWRLAPIFactory.createLiteral(getOWLLiteralFactory().getOWLLiteral(date));
  }

  @NonNull @Override
  public Literal getLiteral(@NonNull XSDTime time)
  {
    return SWRLAPIFactory.createLiteral(getOWLLiteralFactory().getOWLLiteral(time));
  }

  @NonNull @Override
  public Literal getLiteral(@NonNull XSDDateTime datetime)
  {
    return SWRLAPIFactory.createLiteral(getOWLLiteralFactory().getOWLLiteral(datetime));
  }

  @NonNull @Override
  public Literal getLiteral(@NonNull XSDDuration duration)
  {
    return SWRLAPIFactory.createLiteral(getOWLLiteralFactory().getOWLLiteral(duration));
  }

  @NonNull @Override
  public Literal getLiteral(@NonNull OWLLiteral literal)
  {
    return SWRLAPIFactory.createLiteral(literal);
  }

  @NonNull private OWLLiteralFactory getOWLLiteralFactory()
  {
    return this.owlLiteralFactory;
  }
}
