package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitor;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;

class DefaultSWRLNamedIndividualBuiltInArgument extends DefaultSWRLNamedBuiltInArgument
  implements SWRLNamedIndividualBuiltInArgument
{
  private static final long serialVersionUID = 1L;

  public DefaultSWRLNamedIndividualBuiltInArgument(OWLNamedIndividual individual)
  {
    super(individual);
  }

  @NonNull @Override public OWLNamedIndividual getOWLNamedIndividual()
  {
    return getOWLEntity().asOWLNamedIndividual();
  }

  @Override public <@NonNull T> T accept(@NonNull SWRLBuiltInArgumentVisitorEx<@NonNull T> visitor)
  {
    return visitor.visit(this);
  }

  @Override public void accept(@NonNull SWRLBuiltInArgumentVisitor visitor)
  {
    visitor.visit(this);
  }
}
