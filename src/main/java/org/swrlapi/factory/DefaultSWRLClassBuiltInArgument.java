package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLClass;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitor;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.exceptions.SWRLBuiltInException;

class DefaultSWRLClassBuiltInArgument extends DefaultSWRLNamedBuiltInArgument implements SWRLClassBuiltInArgument
{
  private static final long serialVersionUID = 1L;

  public DefaultSWRLClassBuiltInArgument(@NonNull OWLClass cls)
  {
    super(cls);
  }

  @NonNull @Override public OWLClass getOWLClass()
  {
    return getOWLEntity().asOWLClass();
  }

  @NonNull @Override public <@NonNull T> T accept(@NonNull SWRLBuiltInArgumentVisitorEx<@NonNull T> visitor)
  {
    return visitor.visit(this);
  }

  @Override public boolean isClass()
  {
    return true;
  }

  @NonNull @Override public SWRLClassBuiltInArgument asSWRLClassBuiltInArgument() throws SWRLBuiltInException
  {
    return this;
  }

  @Override public void accept(@NonNull SWRLBuiltInArgumentVisitor visitor)
  {
    visitor.visit(this);
  }
}
