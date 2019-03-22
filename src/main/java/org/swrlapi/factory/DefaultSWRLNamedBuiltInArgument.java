package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Deterministic;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.SWRLObjectVisitor;
import org.semanticweb.owlapi.model.SWRLObjectVisitorEx;
import org.swrlapi.builtins.arguments.SWRLNamedBuiltInArgument;

import java.util.Collections;
import java.util.Objects;
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

  @NonNull @Override public Set<@NonNull OWLAnnotationProperty> getAnnotationPropertiesInSignature()
  {
    return Collections.<@NonNull OWLAnnotationProperty>emptySet(); // TODO Implement getAnnotationPropertiesInSignature
  }

  @SideEffectFree @Deterministic @Override public boolean equals(@Nullable Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    DefaultSWRLNamedBuiltInArgument that = (DefaultSWRLNamedBuiltInArgument)o;

    return Objects.equals(entity, that.entity);

  }

  @SideEffectFree @Deterministic @Override public int hashCode()
  {
    return entity != null ? entity.hashCode() : 0;
  }

  @Override public void accept(@NonNull SWRLObjectVisitor visitor)
  {
    visitor.visit(this);
  }

  @NonNull @Override public <O> O accept(@NonNull SWRLObjectVisitorEx<@NonNull O> visitor)
  {
    return visitor.visit(this);
  }

  @Override public void accept(@NonNull OWLObjectVisitor visitor)
  {
    visitor.visit(this);
  }

  @NonNull @Override public <O> O accept(@NonNull OWLObjectVisitorEx<@NonNull O> visitor)
  {
    return visitor.visit(this);
  }

   @NonNull @SideEffectFree @Override public String toString()
  {
    return this.entity.toString();
  }
}
