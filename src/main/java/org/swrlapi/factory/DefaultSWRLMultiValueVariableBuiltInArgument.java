package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
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

  @NonNull private List<@NonNull SWRLBuiltInArgument> arguments;

  public DefaultSWRLMultiValueVariableBuiltInArgument(@NonNull IRI variableIRI)
  {
    super(variableIRI);
    this.arguments = new ArrayList<>();
  }

  public DefaultSWRLMultiValueVariableBuiltInArgument(@NonNull IRI variableIRI,
    @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
  {
    super(variableIRI);
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

  @Override public void setArguments(@NonNull List<@NonNull SWRLBuiltInArgument> arguments)
  {
    this.arguments = arguments;
  }

  @NonNull @Override public List<@NonNull SWRLBuiltInArgument> getArguments()
  {
    return this.arguments;
  }

  @Override public int getNumberOfArguments()
  {
    return this.arguments.size();
  }

  @Override public boolean hasArguments()
  {
    return this.arguments.size() != 0;
  }

  @Override public boolean hasNoArguments()
  {
    return this.arguments.size() == 0;
  }

  @NonNull @Override public <@NonNull T> T accept(@NonNull SWRLBuiltInArgumentVisitorEx<@NonNull T> visitor)
  {
    return visitor.visit(this);
  }

  @Override public void accept(@NonNull SWRLBuiltInArgumentVisitor visitor)
  {
    visitor.visit(this);
  }

  @Override public boolean equals(Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    if (!super.equals(o))
      return false;

    DefaultSWRLMultiValueVariableBuiltInArgument that = (DefaultSWRLMultiValueVariableBuiltInArgument)o;

    return arguments != null ? arguments.equals(that.arguments) : that.arguments == null;

  }

  @Override public int hashCode()
  {
    int result = super.hashCode();
    result = 31 * result + (arguments != null ? arguments.hashCode() : 0);
    return result;
  }
}
