package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLClassExpressionResultValue;

class DefaultSQWRLClassExpressionResultValue extends DefaultSQWRLExpressionResultValue implements
  SQWRLClassExpressionResultValue
{
  public DefaultSQWRLClassExpressionResultValue(@NonNull String rendering)
  {
    super(rendering);
  }

  @Override public boolean isClassExpression() { return true; }

  @Override public @NonNull SQWRLClassExpressionResultValue asClassExpressionResult() throws SQWRLException
  {
    return this;
  }
}
