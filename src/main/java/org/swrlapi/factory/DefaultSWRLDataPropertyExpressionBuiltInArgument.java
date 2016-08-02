package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitor;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.builtins.arguments.SWRLDataPropertyExpressionBuiltInArgument;
import org.swrlapi.exceptions.SWRLBuiltInException;

class DefaultSWRLDataPropertyExpressionBuiltInArgument extends DefaultSWRLBuiltInArgument
  implements SWRLDataPropertyExpressionBuiltInArgument
{
  private static final long serialVersionUID = 1L;

  private final OWLDataPropertyExpression propertyExpression;

  public DefaultSWRLDataPropertyExpressionBuiltInArgument(@NonNull OWLDataPropertyExpression propertyExpression)
  {
    this.propertyExpression = propertyExpression;
  }

  @NonNull @Override public OWLDataPropertyExpression getOWLDataPropertyExpression()
  {
    return this.propertyExpression;
  }

  @NonNull @Override public <@NonNull T> T accept(@NonNull SWRLBuiltInArgumentVisitorEx<@NonNull T> visitor)
  {
    return visitor.visit(this);
  }

  @Override public boolean isDataPropertyExpression()
  {
    return true;
  }

  @NonNull @Override public SWRLDataPropertyExpressionBuiltInArgument asSWRLDataPropertyExpressionBuiltInArgument()
    throws SWRLBuiltInException
  {
    return this;
  }

  @Override public void accept(@NonNull SWRLBuiltInArgumentVisitor visitor)
  {
    visitor.visit(this);
  }
}
