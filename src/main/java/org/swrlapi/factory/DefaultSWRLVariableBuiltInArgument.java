package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Deterministic;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.SWRLObjectVisitor;
import org.semanticweb.owlapi.model.SWRLObjectVisitorEx;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentType;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitor;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.exceptions.SWRLAPIException;
import org.swrlapi.exceptions.SWRLBuiltInException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

class DefaultSWRLVariableBuiltInArgument extends DefaultSWRLBuiltInArgument implements SWRLVariableBuiltInArgument
{
  // There is an equals methods defined for this class.
  private static final long serialVersionUID = 1L;

  @NonNull private final IRI variableIRI;
  @NonNull private final String variableName;

  @Nullable private SWRLBuiltInArgument builtInResult; // Used to store result of binding for unbound arguments
  private boolean isBound;

  public DefaultSWRLVariableBuiltInArgument(@NonNull IRI variableIRI)
  {
    this.variableIRI = variableIRI;

    com.google.common.base.Optional<String> remainder = variableIRI.getRemainder();

    if (remainder.isPresent())
      variableName = remainder.get();
    else
      throw new IllegalArgumentException("SWRL variable with IRI " + variableIRI + " has no remainder");

    this.builtInResult = null;
    this.isBound = true;
  }

  @NonNull @Override public SWRLBuiltInArgumentType<?> getSWRLBuiltInArgumentType()
  {
    return SWRLBuiltInArgumentType.VARIABLE;
  }

  @NonNull @Override public IRI getIRI()
  {
    return this.variableIRI;
  }

  @NonNull @Override public SWRLVariableBuiltInArgument asVariable()
  {
    return this;
  }

  @NonNull @Override public SWRLMultiValueVariableBuiltInArgument asMultiValueVariable()
  {
    throw new SWRLAPIException("not a SWRLMultiVariableBuiltInArgument");
  }

  @NonNull @Override public SWRLLiteralBuiltInArgument asSWRLLiteralBuiltInArgument()
  {
    throw new SWRLAPIException("Not a SWRLLiteralBuiltInArgument");
  }

  @NonNull @Override public SWRLNamedBuiltInArgument asSWRLNamedBuiltInArgument()
  {
    throw new SWRLAPIException("Not a SWRLNamedBuiltInArgument");
  }

  @NonNull @Override public String getVariableName()
  {
    return this.variableName;
  }

  @Override public void setBuiltInResult(@NonNull SWRLBuiltInArgument builtInResult) throws SWRLBuiltInException
  {
    if (!isUnbound())
      throw new SWRLBuiltInException("attempt to bind value to bound argument " + this.toString());

    setBound();

    this.builtInResult = builtInResult;
  }

  @NonNull @Override public Optional<@NonNull SWRLBuiltInArgument> getBuiltInResult()
  {
    if (this.builtInResult != null)
      return Optional.of(this.builtInResult);
    else
      return Optional.<@NonNull SWRLBuiltInArgument>empty();
  }

  @Override public boolean hasBuiltInResult()
  {
    return this.builtInResult != null;
  }

  @Override public boolean isUnbound()
  {
    return !this.isBound;
  }

  @Override public boolean isBound()
  {
    return this.isBound;
  }

  @Override public void setUnbound()
  {
    this.isBound = false;
  }

  @Override public void setBound()
  {
    this.isBound = true;
  }

  @NonNull @Override public <@NonNull T> T accept(@NonNull SWRLBuiltInArgumentVisitorEx<@NonNull T> visitor)
  {
    return visitor.visit(this);
  }

  @Override public void accept(@NonNull SWRLBuiltInArgumentVisitor visitor)
  {
    visitor.visit(this);
  }

  @Override public void accept(@NonNull SWRLObjectVisitor visitor)
  {
    visitor.visit(this);
  }

  @NonNull @Override public <@NonNull O> O accept(@NonNull SWRLObjectVisitorEx<@NonNull O> visitor)
  {
    return visitor.visit(this);
  }

  @Override public void accept(@NonNull OWLObjectVisitor visitor)
  {
    visitor.visit(this);
  }

  @NonNull @Override public <@NonNull O> O accept(@NonNull OWLObjectVisitorEx<@NonNull O> visitor)
  {
    return visitor.visit(this);
  }

  private int compareTo(@NonNull SWRLVariableBuiltInArgument o)
  {
    return this.getIRI().compareTo(o.getIRI());
  }

  @SideEffectFree @Deterministic @Override public int compareTo(@NonNull OWLObject o)
  {
    if (!(o instanceof SWRLVariableBuiltInArgument))
      return -1;

    SWRLVariableBuiltInArgument other = (SWRLVariableBuiltInArgument)o;

    return compareTo(other);
  }

  @SideEffectFree @Deterministic @Override public boolean equals(@Nullable Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    DefaultSWRLVariableBuiltInArgument that = (DefaultSWRLVariableBuiltInArgument)o;

    if (isBound != that.isBound)
      return false;
    if (variableIRI != null ? !variableIRI.equals(that.variableIRI) : that.variableIRI != null)
      return false;
    if (variableName != null ? !variableName.equals(that.variableName) : that.variableName != null)
      return false;
    return builtInResult != null ? builtInResult.equals(that.builtInResult) : that.builtInResult == null;

  }

  @SideEffectFree @Deterministic @Override public int hashCode()
  {
    int result = variableIRI != null ? variableIRI.hashCode() : 0;
    result = 31 * result + (variableName != null ? variableName.hashCode() : 0);
    result = 31 * result + (builtInResult != null ? builtInResult.hashCode() : 0);
    result = 31 * result + (isBound ? 1 : 0);
    return result;
  }

  @NonNull @Override public Set<@NonNull OWLAnnotationProperty> getAnnotationPropertiesInSignature()
  {
    return new HashSet<>(); // TODO Implement getAnnotationPropertiesInSignature
  }
}
