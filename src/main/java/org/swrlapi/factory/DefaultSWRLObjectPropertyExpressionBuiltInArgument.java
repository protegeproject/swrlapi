package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentType;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitor;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyExpressionBuiltInArgument;
import org.swrlapi.exceptions.SWRLBuiltInException;

class DefaultSWRLObjectPropertyExpressionBuiltInArgument extends DefaultSWRLBuiltInArgument
  implements SWRLObjectPropertyExpressionBuiltInArgument
{
  private static final long serialVersionUID = 1L;

  private final OWLObjectPropertyExpression propertyExpression;

  public DefaultSWRLObjectPropertyExpressionBuiltInArgument(@NonNull OWLObjectPropertyExpression propertyExpression)
  {
    this.propertyExpression = propertyExpression;
  }

  @NonNull @Override public SWRLBuiltInArgumentType<?> getSWRLBuiltInArgumentType()
  {
    return SWRLBuiltInArgumentType.OBJECT_PROPERTY_EXPRESSION;
  }

  @NonNull @Override public OWLObjectPropertyExpression getOWLObjectPropertyExpression()
  {
    return this.propertyExpression;
  }

  @NonNull @Override public <@NonNull T> T accept(@NonNull SWRLBuiltInArgumentVisitorEx<@NonNull T> visitor)
  {
    return visitor.visit(this);
  }

  @NonNull @Override public SWRLObjectPropertyExpressionBuiltInArgument asSWRLObjectPropertyExpressionBuiltInArgument()
    throws SWRLBuiltInException
  {
    return this;
  }

  @Override public void accept(@NonNull SWRLBuiltInArgumentVisitor visitor)
  {
    visitor.visit(this);
  }
}
