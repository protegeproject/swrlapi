package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitor;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.builtins.arguments.SWRLClassExpressionBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedBuiltInArgument;
import org.swrlapi.exceptions.SWRLAPIException;

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

  @Override public boolean isLiteral()
  {
    return false;
  }

  @Override public boolean isNamed()
  {
    return false;
  }

  @Override public @NonNull SWRLLiteralBuiltInArgument asSWRLLiteralBuiltInArgument()
  {
    throw new SWRLAPIException("Not a SWRLLiteralBuiltInArgument");
  }

  @Override public @NonNull SWRLNamedBuiltInArgument asSWRLNamedBuiltInArgument()
  {
    throw new SWRLAPIException("Not a SWRLNamedBuiltInArgument");
  }


  @Override public void accept(@NonNull SWRLBuiltInArgumentVisitor visitor)
  {
    visitor.visit(this);
  }
}
