package org.swrlapi.factory;

import org.semanticweb.owlapi.model.OWLDataProperty;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitor;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;

class DefaultSWRLDataPropertyBuiltInArgument extends DefaultSWRLNamedBuiltInArgument implements
    SWRLDataPropertyBuiltInArgument
{
  private static final long serialVersionUID = 1L;

  public DefaultSWRLDataPropertyBuiltInArgument(OWLDataProperty property)
  {
    super(property);
  }

  @Override
  public OWLDataProperty getOWLDataProperty()
  {
    return getOWLEntity().asOWLDataProperty();
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
