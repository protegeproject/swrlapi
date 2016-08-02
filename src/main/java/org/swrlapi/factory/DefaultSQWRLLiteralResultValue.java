package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Deterministic;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.literal.OWLLiteralComparator;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLAnnotationPropertyResultValue;
import org.swrlapi.sqwrl.values.SQWRLClassExpressionResultValue;
import org.swrlapi.sqwrl.values.SQWRLClassResultValue;
import org.swrlapi.sqwrl.values.SQWRLDataPropertyExpressionResultValue;
import org.swrlapi.sqwrl.values.SQWRLDataPropertyResultValue;
import org.swrlapi.sqwrl.values.SQWRLDatatypeResultValue;
import org.swrlapi.sqwrl.values.SQWRLEntityResultValue;
import org.swrlapi.sqwrl.values.SQWRLExpressionResultValue;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.swrlapi.sqwrl.values.SQWRLNamedIndividualResultValue;
import org.swrlapi.sqwrl.values.SQWRLObjectPropertyExpressionResultValue;
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

  @Override public boolean isLiteral()
  {
    return true;
  }

  @NonNull @Override public SQWRLLiteralResultValue asLiteralResult()
  {
    return this;
  }

  @Override public boolean isEntity()
  {
    return false;
  }

  @Override public boolean isExpression()
  {
    return false;
  }

  @Override public boolean isClass()
  {
    return false;
  }

  @Override public boolean isClassExpression()
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

  @Override public boolean isObjectPropertyExpression()
  {
    return false;
  }

  @Override public boolean isDataProperty()
  {
    return false;
  }

  @Override public boolean isDataPropertyExpression()
  {
    return false;
  }

  @Override public boolean isAnnotationProperty()
  {
    return false;
  }

  @Override public boolean isDatatype()
  {
    return false;
  }

  @NonNull @Override public SQWRLEntityResultValue asEntityResult() throws SQWRLException
  {
    throw new SQWRLException(getClass().getName() + " is not a " + SQWRLEntityResultValue.class.getName());
  }

  @Override public @NonNull SQWRLExpressionResultValue asExpressionResult() throws SQWRLException
  {
    throw new SQWRLException(getClass().getName() + " is not a " + SQWRLExpressionResultValue.class.getName());
  }

  @Override public @NonNull SQWRLClassResultValue asClassResult() throws SQWRLException
  {
    throw new SQWRLException(getClass().getName() + " is not a " + SQWRLClassResultValue.class.getName());
  }

  @Override public @NonNull SQWRLClassExpressionResultValue asClassExpressionResult() throws SQWRLException
  {
    throw new SQWRLException(getClass().getName() + " is not a " + SQWRLClassExpressionResultValue.class.getName());
  }

  @Override public @NonNull SQWRLNamedIndividualResultValue asNamedIndividualResult() throws SQWRLException
  {
    throw new SQWRLException(getClass().getName() + " is not a " + SQWRLNamedIndividualResultValue.class.getName());
  }

  @Override public @NonNull SQWRLObjectPropertyResultValue asObjectPropertyResult() throws SQWRLException
  {
    throw new SQWRLException(getClass().getName() + " is not an " + SQWRLObjectPropertyResultValue.class.getName());
  }

  @Override public @NonNull SQWRLObjectPropertyExpressionResultValue asObjectPropertyExpressionResult()
    throws SQWRLException
  {
    throw new SQWRLException(
      getClass().getName() + " is not an " + SQWRLObjectPropertyExpressionResultValue.class.getName());
  }

  @Override public @NonNull SQWRLDataPropertyResultValue asDataPropertyResult() throws SQWRLException
  {
    throw new SQWRLException(getClass().getName() + " is not an " + SQWRLDataPropertyResultValue.class.getName());
  }

  @Override public @NonNull SQWRLDataPropertyExpressionResultValue asDataPropertyExpressionResult()
    throws SQWRLException
  {
    throw new SQWRLException(
      getClass().getName() + " is not an " + SQWRLDataPropertyExpressionResultValue.class.getName());
  }

  @Override public @NonNull SQWRLAnnotationPropertyResultValue asAnnotationPropertyResult() throws SQWRLException
  {
    throw new SQWRLException(getClass().getName() + " is not an " + SQWRLAnnotationPropertyResultValue.class.getName());
  }

  @Override public @NonNull SQWRLDatatypeResultValue asDatatypeResult() throws SQWRLException
  {
    throw new SQWRLException(getClass().getName() + " is not a " + SQWRLDatatypeResultValue.class.getName());
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
