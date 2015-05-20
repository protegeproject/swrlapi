package org.swrlapi.factory;

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
class DefaultSWRLMultiValueVariableBuiltInArgument extends DefaultSWRLVariableBuiltInArgument implements
    SWRLMultiValueVariableBuiltInArgument
{
  private static final long serialVersionUID = 1L;

  private List<SWRLBuiltInArgument> arguments;

  public DefaultSWRLMultiValueVariableBuiltInArgument(IRI variableIRI, String variablePrefixedName)
  {
    super(variableIRI, variablePrefixedName);
    this.arguments = new ArrayList<>();
  }

  public DefaultSWRLMultiValueVariableBuiltInArgument(IRI variableIRI, String variablePrefixedName,
    List<SWRLBuiltInArgument> arguments)
  {
    super(variableIRI, variablePrefixedName);
    this.arguments = arguments;
  }

  @Override
  public SWRLMultiValueVariableBuiltInArgument asMultiValueVariable()
  {
    return this;
  }

  @Override
  public void addArgument(SWRLBuiltInArgument argument)
  {
    this.arguments.add(argument);
  }

  @Override
  public void setArguments(List<SWRLBuiltInArgument> arguments)
  {
    this.arguments = arguments;
  }

  @Override
  public List<SWRLBuiltInArgument> getArguments()
  {
    return this.arguments;
  }

  @Override
  public int getNumberOfArguments()
  {
    return this.arguments.size();
  }

  @Override
  public boolean hasNoArguments()
  {
    return this.arguments.size() == 0;
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
