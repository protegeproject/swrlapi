package org.swrlapi.factory;

import org.semanticweb.owlapi.model.OWLDatatype;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitor;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;

class DefaultSWRLDatatypeBuiltInArgument extends DefaultSWRLNamedBuiltInArgument implements SWRLDatatypeBuiltInArgument
{
  private static final long serialVersionUID = 1L;

  public DefaultSWRLDatatypeBuiltInArgument(OWLDatatype datatype)
  {
    super(datatype);
  }

  @Override
  public OWLDatatype getOWLDatatype()
  {
    return getOWLEntity().asOWLDatatype();
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
