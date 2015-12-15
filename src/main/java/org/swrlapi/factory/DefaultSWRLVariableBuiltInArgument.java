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

  @NonNull private final IRI iri;
  @NonNull private final String variablePrefixedName;

  @Nullable private SWRLBuiltInArgument builtInResult; // Used to store result of binding for unbound arguments
  private boolean isBound;

  public DefaultSWRLVariableBuiltInArgument(@NonNull IRI iri, @NonNull String variablePrefixedName)
  {
    this.iri = iri;
    this.variablePrefixedName = variablePrefixedName;
    this.builtInResult = null;
    this.isBound = true;
  }

  @NonNull @Override public IRI getIRI()
  {
    return this.iri;
  }

  @Override public boolean isVariable()
  {
    return true;
  }

  @Override public boolean isMultiValueVariable()
  {
    return false;
  }

  @Override public boolean isLiteral()
  {
    return false;
  }

  @Override public boolean isNamed()
  {
    return false;
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

  @Override public String getVariablePrefixedName()
  {
    return this.variablePrefixedName;
  }

  @NonNull @Override public String getVariableName()
  {
    return this.variablePrefixedName.startsWith(":") ?
      this.variablePrefixedName.substring(1) :
      this.variablePrefixedName;
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

  @Override public <@NonNull T> T accept(@NonNull SWRLBuiltInArgumentVisitorEx<@NonNull T> visitor)
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

  @Override public <O> @NonNull O accept(@NonNull SWRLObjectVisitorEx<O> visitor)
  {
    return visitor.visit(this);
  }

  @Override public void accept(@NonNull OWLObjectVisitor visitor)
  {
    visitor.visit(this);
  }

  @NonNull @Override public <O> O accept(@NonNull OWLObjectVisitorEx<O> visitor)
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
    if (iri != null ? !iri.equals(that.iri) : that.iri != null)
      return false;
    if (variablePrefixedName != null ?
      !variablePrefixedName.equals(that.variablePrefixedName) :
      that.variablePrefixedName != null)
      return false;
    return builtInResult != null ? builtInResult.equals(that.builtInResult) : that.builtInResult == null;

  }

  @SideEffectFree @Deterministic @Override public int hashCode()
  {
    int result = iri != null ? iri.hashCode() : 0;
    result = 31 * result + (variablePrefixedName != null ? variablePrefixedName.hashCode() : 0);
    result = 31 * result + (builtInResult != null ? builtInResult.hashCode() : 0);
    result = 31 * result + (isBound ? 1 : 0);
    return result;
  }

  @NonNull @Override public Set<@NonNull OWLAnnotationProperty> getAnnotationPropertiesInSignature()
  {
    return new HashSet<>(); // TODO Implement getAnnotationPropertiesInSignature
  }
}
