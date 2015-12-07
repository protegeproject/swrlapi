package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;
import dataflow.quals.Deterministic;
import dataflow.quals.SideEffectFree;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.SWRLObjectVisitor;
import org.semanticweb.owlapi.model.SWRLObjectVisitorEx;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedBuiltInArgument;
import org.swrlapi.exceptions.SWRLAPIException;

import java.util.Collections;
import java.util.Set;

abstract class DefaultSWRLNamedBuiltInArgument extends DefaultSWRLBuiltInArgument implements SWRLNamedBuiltInArgument
{
  private static final long serialVersionUID = 1L;

  @NonNull private final OWLEntity entity;

  protected DefaultSWRLNamedBuiltInArgument(@NonNull OWLEntity entity)
  {
    this.entity = entity;
  }

  @NonNull OWLEntity getOWLEntity()
  {
    return this.entity;
  }

  @NonNull @Override public IRI getIRI()
  {
    return this.entity.getIRI();
  }

  public @Override boolean isLiteral()
  {
    return false;
  }

  public @Override boolean isNamed()
  {
    return true;
  }

  @NonNull @Override public SWRLLiteralBuiltInArgument asSWRLLiteralBuiltInArgument()
  {
    throw new SWRLAPIException("Not a SWRLLiteralBuiltInArgument");
  }

  @NonNull @Override public SWRLNamedBuiltInArgument asSWRLNamedBuiltInArgument()
  {
    return this;
  }

  @SideEffectFree @Deterministic @Override public int compareTo(@NonNull OWLObject o)
  {
    if (!(o instanceof DefaultSWRLNamedBuiltInArgument))
      return -1;

    DefaultSWRLNamedBuiltInArgument other = (DefaultSWRLNamedBuiltInArgument)o;

    return this.entity.getIRI().compareTo(other.entity.getIRI());
  }

  @NonNull @Override public Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature()
  {
    return Collections.emptySet(); // TODO Implement getAnnotationPropertiesInSignature
  }

  @SideEffectFree @Deterministic @Override public boolean equals(@Nullable Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    DefaultSWRLNamedBuiltInArgument that = (DefaultSWRLNamedBuiltInArgument)o;

    return !(entity != null ? !entity.equals(that.entity) : that.entity != null);

  }

  @SideEffectFree @Deterministic @Override public int hashCode()
  {
    return entity != null ? entity.hashCode() : 0;
  }

  @Override public void accept(@NonNull SWRLObjectVisitor visitor)
  {
    visitor.visit(this);
  }

  @NonNull @Override public <O> O accept(@NonNull SWRLObjectVisitorEx<O> visitor)
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

  @SideEffectFree @NonNull @Override public String toString()
  {
    return this.entity.toString();
  }
}
