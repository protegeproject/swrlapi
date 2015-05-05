package org.swrlapi.factory;

import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitor;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;

class DefaultSWRLNamedIndividualBuiltInArgument extends DefaultSWRLNamedBuiltInArgument implements
    SWRLNamedIndividualBuiltInArgument
{
  private static final long serialVersionUID = 1L;

  public DefaultSWRLNamedIndividualBuiltInArgument(OWLNamedIndividual individual)
  {
    super(individual);
  }

  @Override
  public OWLNamedIndividual getOWLNamedIndividual()
  {
    return getOWLEntity().asOWLNamedIndividual();
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
