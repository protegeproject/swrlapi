package org.swrlapi.factory;

import org.semanticweb.owlapi.model.OWLClass;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitor;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;

class DefaultSWRLClassBuiltInArgument extends DefaultSWRLNamedBuiltInArgument implements SWRLClassBuiltInArgument
{
  private static final long serialVersionUID = 1L;

  public DefaultSWRLClassBuiltInArgument(OWLClass cls)
  {
    super(cls);
  }

  @Override
  public OWLClass getOWLClass()
  {
    return getOWLEntity().asOWLClass();
  }

  @Override
  public <T> T accept(SWRLBuiltInArgumentVisitorEx<T> visitor)
  {
    return visitor.visit(this);
  }

  @Override
  public void accept(SWRLBuiltInArgumentVisitor visitor)
  {
    visitor.visit(this);
  }
}
