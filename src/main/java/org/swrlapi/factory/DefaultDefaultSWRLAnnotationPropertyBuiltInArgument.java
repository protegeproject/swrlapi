package org.swrlapi.factory;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitor;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;

class DefaultDefaultSWRLAnnotationPropertyBuiltInArgument extends DefaultSWRLNamedBuiltInArgument
  implements SWRLAnnotationPropertyBuiltInArgument
{
  private static final long serialVersionUID = 1L;

  public DefaultDefaultSWRLAnnotationPropertyBuiltInArgument(OWLAnnotationProperty property)
  {
    super(property);
  }

  @Override public OWLAnnotationProperty getOWLAnnotationProperty()
  {
    return getOWLEntity().asOWLAnnotationProperty();
  }

  @Override public <T> T accept(SWRLBuiltInArgumentVisitorEx<T> visitor)
  {
    return visitor.visit(this);
  }

  @Override public void accept(SWRLBuiltInArgumentVisitor visitor)
  {
    visitor.visit(this);
  }
}
