package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;
import dataflow.quals.Deterministic;
import dataflow.quals.SideEffectFree;
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

  @Nullable @Override public SWRLBuiltInArgument getBuiltInResult()
  {
    return this.builtInResult;
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

  @Override public <@NonNull T> T accept(@NonNull SWRLBuiltInArgumentVisitorEx<T> visitor)
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

  @NonNull @Override public <@NonNull O> O accept(@NonNull SWRLObjectVisitorEx<O> visitor)
  {
    return visitor.visit(this);
  }

  @Override public void accept(@NonNull OWLObjectVisitor visitor)
  {
    visitor.visit(this);
  }

  @NonNull @Override public <@NonNull O> O accept(@NonNull OWLObjectVisitorEx<O> visitor)
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

  @SideEffectFree @Deterministic @Override public boolean equals(@Nullable Object obj)
  {
    if (this == obj)
      return true;
    if ((obj == null) || (obj.getClass() != this.getClass()))
      return false;
    DefaultSWRLVariableBuiltInArgument impl = (DefaultSWRLVariableBuiltInArgument)obj;
    return super.equals(impl) && ((this.builtInResult == impl.builtInResult)
        || (this.builtInResult != null && this.builtInResult.equals(impl.builtInResult))
        && this.isBound == impl.isBound);
  }

  @SideEffectFree @Deterministic @Override public int hashCode()
  {
    int hash = 78;
    hash = hash + (null == this.builtInResult ? 0 : this.builtInResult.hashCode());
    hash = hash + (this.isBound ? 1 : 0);
    return hash;
  }

  @NonNull @Override public Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature()
  {
    return new HashSet<>(); // TODO Implement getAnnotationPropertiesInSignature
  }
}
