package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLClass;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitor;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;

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

  @NonNull @Override public <T> T accept(@NonNull SWRLBuiltInArgumentVisitorEx<T> visitor)
  {
    return visitor.visit(this);
  }

  @Override public void accept(@NonNull SWRLBuiltInArgumentVisitor visitor)
  {
    visitor.visit(this);
  }
}
