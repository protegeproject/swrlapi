package org.swrlapi.builtins.arguments.impl;

import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitor;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;

class SWRLObjectPropertyBuiltInArgumentImpl extends SWRLNamedBuiltInArgumentImpl implements
    SWRLObjectPropertyBuiltInArgument
{
  private static final long serialVersionUID = 1L;

  public SWRLObjectPropertyBuiltInArgumentImpl(OWLObjectProperty property)
  {
    super(property);
  }

  @Override
  public OWLObjectProperty getOWLObjectProperty()
  {
    return getOWLEntity().asOWLObjectProperty();
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