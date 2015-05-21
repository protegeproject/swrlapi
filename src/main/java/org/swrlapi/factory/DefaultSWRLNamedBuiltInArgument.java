package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;
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

  DefaultSWRLNamedBuiltInArgument(@NonNull OWLEntity entity)
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

  @Override public int compareTo(@NonNull OWLObject o)
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

  @Override public boolean equals(@Nullable Object obj)
  {
    if (this == obj)
      return true;
    if ((obj == null) || (obj.getClass() != this.getClass()))
      return false;
    DefaultSWRLNamedBuiltInArgument impl = (DefaultSWRLNamedBuiltInArgument)obj;
    return (this.entity.getIRI() == impl.entity.getIRI() || (this.entity.getIRI() != null && this.entity.getIRI()
      .equals(impl.entity.getIRI())));
  }

  @Override public int hashCode()
  {
    int hash = 152;
    hash = hash + (null == this.entity.getIRI() ? 0 : this.entity.getIRI().hashCode());
    return hash;
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

  @NonNull @Override public String toString()
  {
    return this.entity.toString();
  }
}
