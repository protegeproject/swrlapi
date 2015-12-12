package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitor;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;

class DefaultSWRLDatatypeBuiltInArgument extends DefaultSWRLNamedBuiltInArgument implements SWRLDatatypeBuiltInArgument
{
  private static final long serialVersionUID = 1L;

  public DefaultSWRLDatatypeBuiltInArgument(@NonNull OWLDatatype datatype)
  {
    super(datatype);
  }

  @NonNull @Override public OWLDatatype getOWLDatatype()
  {
    return getOWLEntity().asOWLDatatype();
  }

  @NonNull @Override public <@NonNull T> T accept(@NonNull SWRLBuiltInArgumentVisitorEx<@NonNull T> visitor)
  {
    return visitor.visit(this);
  }

  @Override public void accept(@NonNull SWRLBuiltInArgumentVisitor visitor)
  {
    visitor.visit(this);
  }
}
