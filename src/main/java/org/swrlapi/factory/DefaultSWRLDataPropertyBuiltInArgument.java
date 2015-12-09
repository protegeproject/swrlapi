package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitor;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;

class DefaultSWRLDataPropertyBuiltInArgument extends DefaultSWRLNamedBuiltInArgument
  implements SWRLDataPropertyBuiltInArgument
{
  private static final long serialVersionUID = 1L;

  public DefaultSWRLDataPropertyBuiltInArgument(@NonNull OWLDataProperty property)
  {
    super(property);
  }

  @NonNull @Override public OWLDataProperty getOWLDataProperty()
  {
    return getOWLEntity().asOWLDataProperty();
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
