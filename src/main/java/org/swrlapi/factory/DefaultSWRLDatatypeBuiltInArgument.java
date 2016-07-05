package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitor;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.exceptions.SWRLBuiltInException;

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

  @Override public boolean isDatatype()
  {
    return true;
  }

  @NonNull @Override public SWRLDatatypeBuiltInArgument asSWRLDatatypeBuiltInArgument() throws SWRLBuiltInException
  {
    return this;
  }

  @Override public void accept(@NonNull SWRLBuiltInArgumentVisitor visitor)
  {
    visitor.visit(this);
  }
}
