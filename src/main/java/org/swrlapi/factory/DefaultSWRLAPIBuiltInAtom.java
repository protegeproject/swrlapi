package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.core.SWRLAPIBuiltInAtom;
import org.swrlapi.exceptions.SWRLAPIException;
import uk.ac.manchester.cs.owl.owlapi.SWRLBuiltInAtomImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class DefaultSWRLAPIBuiltInAtom extends SWRLBuiltInAtomImpl implements SWRLAPIBuiltInAtom
{
  private static final long serialVersionUID = 1L;

  @NonNull private final String ruleName;
  @NonNull private final IRI builtInIRI;
  @NonNull private final String builtInPrefixedName;
  @NonNull private List<@NonNull SWRLBuiltInArgument> arguments;
  @NonNull private Set<@NonNull String> pathVariableNames = new HashSet<>();

  private boolean sqwrlCollectionResultsUsed = false;
  private int builtInIndex = -1; // Index of this built-in atom in rule body; left-to-right, first built-in index is 0,
  // second in 1, and so on

  public DefaultSWRLAPIBuiltInAtom(@NonNull String ruleName, @NonNull IRI builtInIRI,
    @NonNull String builtInPrefixedName, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
  {
    super(builtInIRI, new ArrayList<>(arguments));
    this.ruleName = ruleName;
    this.builtInIRI = builtInIRI;
    this.builtInPrefixedName = builtInPrefixedName;
    this.arguments = new ArrayList<>(arguments);
  }

  @NonNull @Override public String getRuleName()
  {
    return this.ruleName;
  }

  @Override public void setBuiltInArguments(@NonNull List<@NonNull SWRLBuiltInArgument> arguments)
  {
    this.arguments = arguments;
  }

  @NonNull @Override public String getBuiltInPrefixedName()
  {
    return this.builtInPrefixedName;
  }

  @NonNull @Override public IRI getBuiltInIRI()
  {
    return this.builtInIRI;
  }

  @NonNull @Override public List<@NonNull SWRLBuiltInArgument> getBuiltInArguments()
  {
    return Collections.unmodifiableList(this.arguments);
  }

  @Override public int getNumberOfArguments()
  {
    return this.arguments.size();
  }

  @Override public int getBuiltInIndex()
  {
    return this.builtInIndex;
  }

  @Override public void setBuiltInIndex(int builtInIndex)
  {
    this.builtInIndex = builtInIndex;
  }

  @NonNull @Override public Set<@NonNull String> getPathVariableNames()
  {
    return Collections.unmodifiableSet(this.pathVariableNames);
  }

  @Override public boolean hasPathVariables()
  {
    return !this.pathVariableNames.isEmpty();
  }

  @Override public boolean usesAtLeastOneVariableOf(@NonNull Set<@NonNull String> variableNames)
  {
    Set<@NonNull String> s = new HashSet<>(variableNames);

    s.retainAll(getArgumentsVariableNames());

    return !s.isEmpty();
  }

  @Override public boolean isArgumentAVariable(int argumentNumber)
  {
    checkArgumentNumber(argumentNumber);

    return this.arguments.get(argumentNumber) instanceof SWRLVariableBuiltInArgument;
  }

  @Override public boolean isArgumentUnbound(int argumentNumber)
  {
    checkArgumentNumber(argumentNumber);

    return this.arguments.get(argumentNumber).isVariable() && this.arguments.get(argumentNumber).asVariable()
      .isUnbound();
  }

  @Override public boolean hasUnboundArguments()
  {
    for (SWRLBuiltInArgument argument : this.arguments)
      if (argument.isVariable() && argument.asVariable().isUnbound())
        return true;
    return false;
  }

  @Override public boolean hasVariableArguments()
  {
    for (SWRLBuiltInArgument argument : this.arguments)
      if (argument.isVariable())
        return true;
    return false;
  }

  @NonNull @Override public Set<@NonNull String> getUnboundArgumentVariableNames()
  {
    Set<@NonNull String> unboundArgumentVariablePrefixNames = new HashSet<>();
    for (SWRLBuiltInArgument argument : this.arguments) {
      if (argument.isVariable() && argument.asVariable().isUnbound())
        unboundArgumentVariablePrefixNames.add(argument.asVariable().getVariableName());
    }
    return unboundArgumentVariablePrefixNames;
  }

  @NonNull @Override public String getArgumentVariableName(int argumentNumber)
  {
    checkArgumentNumber(argumentNumber);

    if (!this.arguments.get(argumentNumber).isVariable())
      throw new SWRLAPIException("expecting a variable for (0-offset) argument #" + argumentNumber);

    return this.arguments.get(argumentNumber).asVariable().getVariableName();
  }

  @NonNull @Override public List<@NonNull String> getArgumentsVariableNames()
  {
    List<@NonNull String> argumentsVariablePrefixNames = new ArrayList<>();
    for (SWRLBuiltInArgument argument : this.arguments) {
      if (argument.isVariable())
        argumentsVariablePrefixNames.add(argument.asVariable().getVariableName());
    }
    return argumentsVariablePrefixNames;
  }

  @NonNull @Override public List<@NonNull String> getArgumentsVariableNamesExceptFirst()
  {
    List<@NonNull String> result = new ArrayList<>();
    int argumentCount = 0;

    for (SWRLBuiltInArgument argument : this.arguments)
      if (argument.isVariable() && argumentCount++ != 0)
        result.add(argument.asVariable().getVariableName());

    return Collections.unmodifiableList(result);
  }

  @Override public void addArguments(@NonNull List<@NonNull SWRLBuiltInArgument> additionalArguments)
  {
    this.arguments.addAll(additionalArguments);
  }

  @Override public void setPathVariableNames(@NonNull Set<@NonNull String> variableNames)
  {
    this.pathVariableNames = new HashSet<>(variableNames);
  }

  @Override public void setUsesSQWRLCollectionResults()
  {
    this.sqwrlCollectionResultsUsed = true;
  }

  @Override public boolean usesSQWRLCollectionResults()
  {
    return this.sqwrlCollectionResultsUsed;
  }

  private void checkArgumentNumber(int argumentNumber)
  {
    if (argumentNumber < 0 || argumentNumber > this.arguments.size())
      throw new SWRLAPIException("invalid (0-offset) argument #" + argumentNumber);
  }

  @Override public boolean equals(Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    if (!super.equals(o))
      return false;

    DefaultSWRLAPIBuiltInAtom that = (DefaultSWRLAPIBuiltInAtom)o;

    if (sqwrlCollectionResultsUsed != that.sqwrlCollectionResultsUsed)
      return false;
    if (builtInIndex != that.builtInIndex)
      return false;
    if (ruleName != null ? !ruleName.equals(that.ruleName) : that.ruleName != null)
      return false;
    if (builtInIRI != null ? !builtInIRI.equals(that.builtInIRI) : that.builtInIRI != null)
      return false;
    if (builtInPrefixedName != null ?
      !builtInPrefixedName.equals(that.builtInPrefixedName) :
      that.builtInPrefixedName != null)
      return false;
    if (arguments != null ? !arguments.equals(that.arguments) : that.arguments != null)
      return false;
    return pathVariableNames != null ?
      pathVariableNames.equals(that.pathVariableNames) :
      that.pathVariableNames == null;

  }

  @Override public int hashCode()
  {
    int result = super.hashCode();
    result = 31 * result + (ruleName != null ? ruleName.hashCode() : 0);
    result = 31 * result + (builtInIRI != null ? builtInIRI.hashCode() : 0);
    result = 31 * result + (builtInPrefixedName != null ? builtInPrefixedName.hashCode() : 0);
    result = 31 * result + (arguments != null ? arguments.hashCode() : 0);
    result = 31 * result + (pathVariableNames != null ? pathVariableNames.hashCode() : 0);
    result = 31 * result + (sqwrlCollectionResultsUsed ? 1 : 0);
    result = 31 * result + builtInIndex;
    return result;
  }
}
