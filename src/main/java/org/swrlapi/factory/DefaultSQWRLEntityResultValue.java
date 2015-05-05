package org.swrlapi.factory;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLAnnotationPropertyResultValue;
import org.swrlapi.sqwrl.values.SQWRLClassResultValue;
import org.swrlapi.sqwrl.values.SQWRLDataPropertyResultValue;
import org.swrlapi.sqwrl.values.SQWRLIndividualResultValue;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.swrlapi.sqwrl.values.SQWRLEntityResultValue;
import org.swrlapi.sqwrl.values.SQWRLObjectPropertyResultValue;

abstract class DefaultSQWRLEntityResultValue implements SQWRLEntityResultValue
{
  private final IRI iri;
  private final String prefixedName;

  protected DefaultSQWRLEntityResultValue(IRI iri, String prefixedName)
  {
    this.iri = iri;
    this.prefixedName = prefixedName;
  }

  @Override
  public IRI getIRI()
  {
    return this.iri;
  }

  @Override
  public String getPrefixedName()
  {
    return this.prefixedName;
  }

  @Override
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

  @Override
  public SQWRLEntityResultValue asEntityResult() throws SQWRLException
  {
    return this;
  }

  @Override
  public SQWRLClassResultValue asClassResult() throws SQWRLException
  {
    throw new SQWRLException(getClass().getName() + " is not a " + SQWRLClassResultValue.class.getName());
  }

  @Override
  public SQWRLIndividualResultValue asIndividualResult() throws SQWRLException
  {
    throw new SQWRLException(getClass().getName() + " is not a " + SQWRLIndividualResultValue.class.getName());
  }

  @Override
  public SQWRLObjectPropertyResultValue asObjectPropertyResult() throws SQWRLException
  {
    throw new SQWRLException(getClass().getName() + " is not an " + SQWRLObjectPropertyResultValue.class.getName());
  }

  @Override
  public SQWRLDataPropertyResultValue asDataPropertyResult() throws SQWRLException
  {
    throw new SQWRLException(getClass().getName() + " is not an " + SQWRLDataPropertyResultValue.class.getName());
  }

  @Override
  public SQWRLAnnotationPropertyResultValue asAnnotationPropertyResult() throws SQWRLException
  {
    throw new SQWRLException(getClass().getName() + " is not an " + SQWRLAnnotationPropertyResultValue.class.getName());
  }

  @Override
  public SQWRLLiteralResultValue asLiteralResult() throws SQWRLException
  {
    throw new SQWRLException(getClass().getName() + " is not a " + SQWRLLiteralResultValue.class.getName());
  }

  @Override
  public boolean equals(Object obj)
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
  public int compareTo(SQWRLEntityResultValue o)
  {
    return this.iri.compareTo(o.getIRI());
  }

  @Override
  public String toString()
  {
    return this.prefixedName;
  }
}
