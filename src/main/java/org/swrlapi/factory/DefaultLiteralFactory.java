package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.literal.Literal;
import org.swrlapi.literal.XSDDate;
import org.swrlapi.literal.XSDDateTime;
import org.swrlapi.literal.XSDDuration;
import org.swrlapi.literal.XSDTime;

import java.net.URI;

class DefaultLiteralFactory implements LiteralFactory
{
  @NonNull private final OWLLiteralFactory owlLiteralFactory;

  public DefaultLiteralFactory()
  {
    this.owlLiteralFactory = SWRLAPIInternalFactory.createOWLLiteralFactory();
  }

  @NonNull @Override public Literal getLiteral(byte b)
  {
    return SWRLAPIInternalFactory.createLiteral(getOWLLiteralFactory().getOWLLiteral(b));
  }

  @NonNull @Override public Literal getLiteral(short s)
  {
    return SWRLAPIInternalFactory.createLiteral(getOWLLiteralFactory().getOWLLiteral(s));
  }

  @NonNull @Override public Literal getLiteral(float value)
  {
    return SWRLAPIInternalFactory.createLiteral(getOWLLiteralFactory().getOWLLiteral(value));
  }

  @NonNull @Override public Literal getLiteral(int value)
  {
    return SWRLAPIInternalFactory.createLiteral(getOWLLiteralFactory().getOWLLiteral(value));
  }

  @NonNull @Override public Literal getLiteral(double value)
  {
    return SWRLAPIInternalFactory.createLiteral(getOWLLiteralFactory().getOWLLiteral(value));
  }

  @NonNull @Override public Literal getLiteral(@NonNull String value)
  {
    return SWRLAPIInternalFactory.createLiteral(getOWLLiteralFactory().getOWLLiteral(value));
  }

  @NonNull @Override public Literal getLiteral(boolean value)
  {
    return SWRLAPIInternalFactory.createLiteral(getOWLLiteralFactory().getOWLLiteral(value));
  }

  @NonNull @Override public Literal getLiteral(@NonNull URI uri)
  {
    return SWRLAPIInternalFactory.createLiteral(getOWLLiteralFactory().getOWLLiteral(uri));
  }

  @NonNull @Override public Literal getLiteral(@NonNull XSDDate date)
  {
    return SWRLAPIInternalFactory.createLiteral(getOWLLiteralFactory().getOWLLiteral(date));
  }

  @NonNull @Override public Literal getLiteral(@NonNull XSDTime time)
  {
    return SWRLAPIInternalFactory.createLiteral(getOWLLiteralFactory().getOWLLiteral(time));
  }

  @NonNull @Override public Literal getLiteral(@NonNull XSDDateTime datetime)
  {
    return SWRLAPIInternalFactory.createLiteral(getOWLLiteralFactory().getOWLLiteral(datetime));
  }

  @NonNull @Override public Literal getLiteral(@NonNull XSDDuration duration)
  {
    return SWRLAPIInternalFactory.createLiteral(getOWLLiteralFactory().getOWLLiteral(duration));
  }

  @NonNull @Override public Literal getLiteral(@NonNull OWLLiteral literal)
  {
    return SWRLAPIInternalFactory.createLiteral(literal);
  }

  @NonNull private OWLLiteralFactory getOWLLiteralFactory()
  {
    return this.owlLiteralFactory;
  }
}
