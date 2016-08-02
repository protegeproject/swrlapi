package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitor;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.exceptions.SWRLBuiltInException;

class DefaultSWRLAnnotationPropertyBuiltInArgument extends DefaultSWRLNamedBuiltInArgument
  implements SWRLAnnotationPropertyBuiltInArgument
{
  private static final long serialVersionUID = 1L;

  public DefaultSWRLAnnotationPropertyBuiltInArgument(@NonNull OWLAnnotationProperty property)
  {
    super(property);
  }

  @NonNull @Override public OWLAnnotationProperty getOWLAnnotationProperty()
  {
    return getOWLEntity().asOWLAnnotationProperty();
  }

  @NonNull @Override public <@NonNull T> T accept(@NonNull SWRLBuiltInArgumentVisitorEx<@NonNull T> visitor)
  {
    return visitor.visit(this);
  }

  @Override public boolean isAnnotationProperty()
  {
    return true;
  }

  @NonNull @Override public SWRLAnnotationPropertyBuiltInArgument asSWRLAnnotationPropertyBuiltInArgument()
    throws SWRLBuiltInException
  {
    return this;
  }

  @Override public void accept(@NonNull SWRLBuiltInArgumentVisitor visitor)
  {
    visitor.visit(this);
  }
}
