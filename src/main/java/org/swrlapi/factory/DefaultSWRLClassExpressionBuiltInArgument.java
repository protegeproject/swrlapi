package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitor;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.builtins.arguments.SWRLClassExpressionBuiltInArgument;
import org.swrlapi.exceptions.SWRLBuiltInException;

class DefaultSWRLClassExpressionBuiltInArgument extends DefaultSWRLBuiltInArgument
  implements SWRLClassExpressionBuiltInArgument
{
  private static final long serialVersionUID = 1L;

  private final OWLClassExpression classExpression;

  public DefaultSWRLClassExpressionBuiltInArgument(@NonNull OWLClassExpression classExpression)
  {
    this.classExpression = classExpression;
  }

  @NonNull @Override public OWLClassExpression getOWLClassExpression()
  {
    return this.classExpression;
  }

  @NonNull @Override public <@NonNull T> T accept(@NonNull SWRLBuiltInArgumentVisitorEx<@NonNull T> visitor)
  {
    return visitor.visit(this);
  }

  @Override public boolean isClassExpression()
  {
    return true;
  }

  @NonNull @Override public SWRLClassExpressionBuiltInArgument asSWRLClassExpressionBuiltInArgument()
    throws SWRLBuiltInException
  {
    return this;
  }

  @Override public void accept(@NonNull SWRLBuiltInArgumentVisitor visitor)
  {
    visitor.visit(this);
  }
}
