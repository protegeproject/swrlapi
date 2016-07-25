package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
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
import org.swrlapi.sqwrl.values.SQWRLResultValue;

abstract class DefaultSQWRLResultValue implements SQWRLResultValue
{
  DefaultSQWRLResultValue()
  {
  }

  @Override public boolean isEntity()
  {
    return false;
  }

  @Override public boolean isExpression()
  {
    return false;
  }

  @Override public boolean isLiteral()
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

  @Override public @NonNull SQWRLLiteralResultValue asLiteralResult() throws SQWRLException
  {
    throw new SQWRLException(getClass().getName() + " is not a " + SQWRLLiteralResultValue.class.getName());
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
}
