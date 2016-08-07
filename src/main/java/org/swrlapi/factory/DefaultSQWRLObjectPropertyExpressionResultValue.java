package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLObjectPropertyExpressionResultValue;

class DefaultSQWRLObjectPropertyExpressionResultValue extends DefaultSQWRLExpressionResultValue
  implements SQWRLObjectPropertyExpressionResultValue
{
  public DefaultSQWRLObjectPropertyExpressionResultValue(@NonNull String rendering)
  {
    super(rendering);
  }

  @Override public boolean isObjectPropertyExpression() { return true; }

  @NonNull @Override public SQWRLObjectPropertyExpressionResultValue asObjectPropertyExpressionResult()
    throws SQWRLException
  {
    return this;
  }
}
