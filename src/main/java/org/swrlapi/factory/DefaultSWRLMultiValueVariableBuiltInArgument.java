package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitor;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument;

import java.util.ArrayList;
import java.util.List;

/**
 * A class used to bind multiple arguments to a built-in argument
 */
class DefaultSWRLMultiValueVariableBuiltInArgument extends DefaultSWRLVariableBuiltInArgument
  implements SWRLMultiValueVariableBuiltInArgument
{
  private static final long serialVersionUID = 1L;

  @NonNull private List<SWRLBuiltInArgument> arguments;

  public DefaultSWRLMultiValueVariableBuiltInArgument(@NonNull IRI variableIRI, @NonNull String variablePrefixedName)
  {
    super(variableIRI, variablePrefixedName);
    this.arguments = new ArrayList<>();
  }

  public DefaultSWRLMultiValueVariableBuiltInArgument(@NonNull IRI variableIRI, @NonNull String variablePrefixedName,
    @NonNull List<SWRLBuiltInArgument> arguments)
  {
    super(variableIRI, variablePrefixedName);
    this.arguments = arguments;
  }

  @NonNull @Override public SWRLMultiValueVariableBuiltInArgument asMultiValueVariable()
  {
    return this;
  }

  @Override public void addArgument(@NonNull SWRLBuiltInArgument argument)
  {
    this.arguments.add(argument);
  }

  @Override public void setArguments(@NonNull List<SWRLBuiltInArgument> arguments)
  {
    this.arguments = arguments;
  }

  @NonNull @Override public List<SWRLBuiltInArgument> getArguments()
  {
    return this.arguments;
  }

  @Override public int getNumberOfArguments()
  {
    return this.arguments.size();
  }

  @Override public boolean hasNoArguments()
  {
    return this.arguments.size() == 0;
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
