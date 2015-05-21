package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;
import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLAnnotationPropertyResultValue;
import org.swrlapi.sqwrl.values.SQWRLClassResultValue;
import org.swrlapi.sqwrl.values.SQWRLDataPropertyResultValue;
import org.swrlapi.sqwrl.values.SQWRLEntityResultValue;
import org.swrlapi.sqwrl.values.SQWRLIndividualResultValue;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
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

  @NonNull @Override
  public IRI getIRI()
  {
    return this.iri;
  }

  @NonNull @Override
  public String getPrefixedName()
  {
    return this.prefixedName;
  }

  @NonNull @Override
  public String getShortName()
  {
    return this.prefixedName.startsWith(":") ? this.prefixedName.substring(1) : this.prefixedName;
  }

  @Override
  public boolean isEntity()
  {
    return true;
  }

  @Override
  public boolean isClass()
  {
    return false;
  }

  @Override
  public boolean isIndividual()
  {
    return false;
  }

  @Override
  public boolean isObjectProperty()
  {
    return false;
  }

  @Override
  public boolean isDataProperty()
  {
    return false;
  }

  @Override
  public boolean isAnnotationProperty()
  {
    return false;
  }

  @Override
  public boolean isLiteral()
  {
    return false;
  }

  @NonNull @Override
  public SQWRLEntityResultValue asEntityResult() throws SQWRLException
  {
    return this;
  }

  @NonNull @Override
  public SQWRLClassResultValue asClassResult() throws SQWRLException
  {
    throw new SQWRLException(getClass().getName() + " is not a " + SQWRLClassResultValue.class.getName());
  }

  @NonNull @Override
  public SQWRLIndividualResultValue asIndividualResult() throws SQWRLException
  {
    throw new SQWRLException(getClass().getName() + " is not a " + SQWRLIndividualResultValue.class.getName());
  }

  @NonNull @Override
  public SQWRLObjectPropertyResultValue asObjectPropertyResult() throws SQWRLException
  {
    throw new SQWRLException(getClass().getName() + " is not an " + SQWRLObjectPropertyResultValue.class.getName());
  }

  @NonNull @Override
  public SQWRLDataPropertyResultValue asDataPropertyResult() throws SQWRLException
  {
    throw new SQWRLException(getClass().getName() + " is not an " + SQWRLDataPropertyResultValue.class.getName());
  }

  @NonNull @Override
  public SQWRLAnnotationPropertyResultValue asAnnotationPropertyResult() throws SQWRLException
  {
    throw new SQWRLException(getClass().getName() + " is not an " + SQWRLAnnotationPropertyResultValue.class.getName());
  }

  @NonNull @Override
  public SQWRLLiteralResultValue asLiteralResult() throws SQWRLException
  {
    throw new SQWRLException(getClass().getName() + " is not a " + SQWRLLiteralResultValue.class.getName());
  }

  @Override
  public boolean equals(@Nullable Object obj)
  {
    if (this == obj)
      return true;
    if ((obj == null) || (obj.getClass() != this.getClass()))
      return false;
    DefaultSQWRLEntityResultValue n = (DefaultSQWRLEntityResultValue)obj;

    return this.iri.equals(n.iri);
  }

  @Override
  public int hashCode()
  {
    int hash = 298;
    hash = hash + (null == this.iri ? 0 : this.iri.hashCode());
    return hash;
  }

  @Override
  public int compareTo(@NonNull SQWRLEntityResultValue o)
  {
    if (o == null)
      throw new NullPointerException();

    return this.iri.compareTo(o.getIRI());
  }

  @NonNull @Override
  public String toString()
  {
    return this.prefixedName;
  }
}
