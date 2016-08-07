package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentType;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitor;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.exceptions.SWRLBuiltInException;

class DefaultSWRLDataPropertyBuiltInArgument extends DefaultSWRLNamedBuiltInArgument
  implements SWRLDataPropertyBuiltInArgument
{
  private static final long serialVersionUID = 1L;

  public DefaultSWRLDataPropertyBuiltInArgument(@NonNull OWLDataProperty property)
  {
    super(property);
  }

  @NonNull @Override public SWRLBuiltInArgumentType<?> getSWRLBuiltInArgumentType()
  {
    return SWRLBuiltInArgumentType.DATA_PROPERTY;
  }

  @NonNull @Override public OWLDataProperty getOWLDataProperty()
  {
    return getOWLEntity().asOWLDataProperty();
  }

  @NonNull @Override public OWLDataPropertyExpression getOWLDataPropertyExpression()
  {
    return getOWLDataProperty();
  }

  @NonNull @Override public <@NonNull T> T accept(@NonNull SWRLBuiltInArgumentVisitorEx<@NonNull T> visitor)
  {
    return visitor.visit(this);
  }

  @NonNull @Override public SWRLDataPropertyBuiltInArgument asSWRLDataPropertyBuiltInArgument()
    throws SWRLBuiltInException
  {
    return this;
  }

  @NonNull @Override public SWRLDataPropertyBuiltInArgument asSWRLDataPropertyExpressionBuiltInArgument()
    throws SWRLBuiltInException
  {
    return this;
  }

  @Override public void accept(@NonNull SWRLBuiltInArgumentVisitor visitor)
  {
    visitor.visit(this);
  }
}
