package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;
import dataflow.quals.Deterministic;
import dataflow.quals.SideEffectFree;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.literal.OWLLiteralComparator;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLAnnotationPropertyResultValue;
import org.swrlapi.sqwrl.values.SQWRLClassResultValue;
import org.swrlapi.sqwrl.values.SQWRLDataPropertyResultValue;
import org.swrlapi.sqwrl.values.SQWRLEntityResultValue;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.swrlapi.sqwrl.values.SQWRLNamedIndividualResultValue;
import org.swrlapi.sqwrl.values.SQWRLObjectPropertyResultValue;

import java.util.Comparator;

class DefaultSQWRLLiteralResultValue extends DefaultLiteral implements SQWRLLiteralResultValue
{
  private static final Comparator<@NonNull OWLLiteral> owlLiteralComparator = OWLLiteralComparator.COMPARATOR;

  private final String datatypePrefixedName;

  public DefaultSQWRLLiteralResultValue(@NonNull OWLLiteral literal, @NonNull String datatypePrefixedName)
  {
    super(literal);
    this.datatypePrefixedName = datatypePrefixedName;
  }

  @Override public String getDatatypePrefixedName()
  {
    return this.datatypePrefixedName;
  }

  @Override public boolean isEntity()
  {
    return false;
  }

  @Override public boolean isClass()
  {
    return false;
  }

  @Override public boolean isNamedIndividual()
  {
    return false;
  }

  @Override public boolean isObjectProperty()
  {
    return false;
  }

  @Override public boolean isDataProperty()
  {
    return false;
  }

  @Override public boolean isAnnotationProperty()
  {
    return false;
  }

  @Override public boolean isLiteral()
  {
    return true;
  }

  @NonNull @Override public SQWRLClassResultValue asEntityResult() throws SQWRLException
  {
    throw new SQWRLException(getClass().getName() + " is not a " + SQWRLEntityResultValue.class.getName());
  }

  @NonNull @Override public SQWRLClassResultValue asClassResult() throws SQWRLException
  {
    throw new SQWRLException(getClass().getName() + " is not a " + SQWRLClassResultValue.class.getName());
  }

  @Override public @NonNull SQWRLNamedIndividualResultValue asNamedIndividualResult() throws SQWRLException
  {
    throw new SQWRLException(getClass().getName() + " is not a " + SQWRLNamedIndividualResultValue.class.getName());
  }

  @NonNull @Override public SQWRLObjectPropertyResultValue asObjectPropertyResult() throws SQWRLException
  {
    throw new SQWRLException(getClass().getName() + " is not an " + SQWRLObjectPropertyResultValue.class.getName());
  }

  @NonNull @Override public SQWRLDataPropertyResultValue asDataPropertyResult() throws SQWRLException
  {
    throw new SQWRLException(getClass().getName() + " is not an " + SQWRLDataPropertyResultValue.class.getName());
  }

  @NonNull @Override public SQWRLAnnotationPropertyResultValue asAnnotationPropertyResult() throws SQWRLException
  {
    throw new SQWRLException(getClass().getName() + " is not an " + SQWRLAnnotationPropertyResultValue.class.getName());
  }

  @NonNull @Override public SQWRLLiteralResultValue asLiteralResult()
  {
    return this;
  }

  @SideEffectFree @Deterministic @Override public boolean equals(@Nullable Object obj)
  {
    if (this == obj)
      return true;
    if ((obj == null) || (obj.getClass() != this.getClass()))
      return false;
    DefaultSQWRLLiteralResultValue l = (DefaultSQWRLLiteralResultValue)obj;

    return owlLiteralComparator.compare(this.getOWLLiteral(), l.getOWLLiteral()) == 0;
  }

  @SideEffectFree @Deterministic @Override public int hashCode()
  {
    int hash = 98;
    hash = hash + (null == this.getOWLLiteral() ? 0 : this.getOWLLiteral().hashCode());
    return hash;
  }

  @SideEffectFree @Deterministic @Override public int compareTo(@NonNull SQWRLLiteralResultValue o)
  {
    if (o == null)
      throw new NullPointerException();

    return owlLiteralComparator.compare(this.getOWLLiteral(), o.getOWLLiteral());
  }
}
