package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Deterministic;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLAnnotationPropertyResultValue;
import org.swrlapi.sqwrl.values.SQWRLClassResultValue;
import org.swrlapi.sqwrl.values.SQWRLDataPropertyResultValue;
import org.swrlapi.sqwrl.values.SQWRLEntityResultValue;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.swrlapi.sqwrl.values.SQWRLNamedIndividualResultValue;
import org.swrlapi.sqwrl.values.SQWRLObjectPropertyResultValue;

abstract class DefaultSQWRLEntityResultValue implements SQWRLEntityResultValue
{
  @NonNull private final IRI iri;
  @NonNull private final String prefixedName;

  DefaultSQWRLEntityResultValue(@NonNull IRI iri, @NonNull String prefixedName)
  {
    this.iri = iri;
    this.prefixedName = prefixedName;
  }

  @NonNull @Override public IRI getIRI()
  {
    return this.iri;
  }

  @NonNull @Override public String getPrefixedName()
  {
    return this.prefixedName;
  }

  @NonNull @Override public String getShortName()
  {
    return this.prefixedName.startsWith(":") ? this.prefixedName.substring(1) : this.prefixedName;
  }

  @Override public boolean isEntity()
  {
    return true;
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
    return false;
  }

  @NonNull @Override public SQWRLEntityResultValue asEntityResult() throws SQWRLException
  {
    return this;
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

  @NonNull @Override public SQWRLLiteralResultValue asLiteralResult() throws SQWRLException
  {
    throw new SQWRLException(getClass().getName() + " is not a " + SQWRLLiteralResultValue.class.getName());
  }

  @SideEffectFree @Deterministic @Override public boolean equals(@Nullable Object obj)
  {
    if (this == obj)
      return true;
    if ((obj == null) || (obj.getClass() != this.getClass()))
      return false;
    DefaultSQWRLEntityResultValue n = (DefaultSQWRLEntityResultValue)obj;

    return this.iri.equals(n.iri);
  }

  @SideEffectFree @Deterministic @Override public int hashCode()
  {
    int hash = 298;
    hash = hash + (null == this.iri ? 0 : this.iri.hashCode());
    return hash;
  }

  @SideEffectFree @Deterministic @Override public int compareTo(@NonNull SQWRLEntityResultValue o)
  {
    if (o == null)
      throw new NullPointerException();

    return this.iri.compareTo(o.getIRI());
  }

  @SideEffectFree @NonNull @Override public String toString()
  {
    return this.prefixedName;
  }
}
