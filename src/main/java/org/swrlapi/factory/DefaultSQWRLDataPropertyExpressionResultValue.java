package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLDataPropertyExpressionResultValue;

class DefaultSQWRLDataPropertyExpressionResultValue extends DefaultSQWRLExpressionResultValue
  implements SQWRLDataPropertyExpressionResultValue
{
  public DefaultSQWRLDataPropertyExpressionResultValue(@NonNull String rendering)
  {
    super(rendering);
  }

  @Override public boolean isDataPropertyExpression() { return true; }

  @Override public @NonNull SQWRLDataPropertyExpressionResultValue asDataPropertyExpressionResult()
    throws SQWRLException
  {
    return this;
  }
}
