package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Deterministic;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLEntityResultValue;

abstract class DefaultSQWRLEntityResultValue extends DefaultSQWRLResultValue implements SQWRLEntityResultValue
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

  @NonNull @Override public SQWRLEntityResultValue asEntityResult() throws SQWRLException
  {
    return this;
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

   @NonNull @SideEffectFree @Override public String toString()
  {
    return this.prefixedName;
  }
}
