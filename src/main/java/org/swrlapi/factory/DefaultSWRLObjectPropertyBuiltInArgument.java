package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitor;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.exceptions.SWRLBuiltInException;

class DefaultSWRLObjectPropertyBuiltInArgument extends DefaultSWRLNamedBuiltInArgument
  implements SWRLObjectPropertyBuiltInArgument
{
  private static final long serialVersionUID = 1L;

  public DefaultSWRLObjectPropertyBuiltInArgument(@NonNull OWLObjectProperty property)
  {
    super(property);
  }

  @NonNull @Override public OWLObjectProperty getOWLObjectProperty()
  {
    return getOWLEntity().asOWLObjectProperty();
  }

  @Override @NonNull public <@NonNull T> T accept(@NonNull SWRLBuiltInArgumentVisitorEx<@NonNull T> visitor)
  {
    return visitor.visit(this);
  }

  @Override public boolean isObjectProperty()
  {
    return true;
  }

  @Override public @NonNull SWRLObjectPropertyBuiltInArgument asSWRLObjectPropertyBuiltInArgument()
    throws SWRLBuiltInException
  {
    return this;
  }

  @Override public void accept(@NonNull SWRLBuiltInArgumentVisitor visitor)
  {
    visitor.visit(this);
  }
}