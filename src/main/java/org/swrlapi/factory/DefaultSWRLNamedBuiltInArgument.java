package org.swrlapi.factory;

import java.util.Collections;
import java.util.Set;

import javax.annotation.Nonnull;

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

abstract class DefaultSWRLNamedBuiltInArgument extends DefaultSWRLBuiltInArgument implements SWRLNamedBuiltInArgument
{
  private static final long serialVersionUID = 1L;

  private final OWLEntity entity;

  public DefaultSWRLNamedBuiltInArgument(OWLEntity entity)
  {
    this.entity = entity;
  }

  @Override
  public IRI getIRI()
  {
    return this.entity.getIRI();
  }

  protected OWLEntity getOWLEntity()
  {
    return this.entity;
  }

  public @Override boolean isLiteral()
  {
    return false;
  }

  public @Override boolean isNamed()
  {
    return true;
  }

  @Override
  public SWRLLiteralBuiltInArgument asSWRLLiteralBuiltInArgument()
  {
    throw new SWRLAPIException("Not a SWRLLiteralBuiltInArgument");
  }

  @Override
  public SWRLNamedBuiltInArgument asSWRLNamedBuiltInArgument()
  {
    return this;
  }

  @Override
  public int compareTo(OWLObject o)
  {
    if (!(o instanceof DefaultSWRLNamedBuiltInArgument))
      return -1;

    DefaultSWRLNamedBuiltInArgument other = (DefaultSWRLNamedBuiltInArgument)o;

    return this.entity.getIRI().compareTo(other.entity.getIRI());
  }

  @Nonnull
  @Override
  public Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature()
  {
    return Collections.emptySet(); // TODO Implement getAnnotationPropertiesInSignature
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if ((obj == null) || (obj.getClass() != this.getClass()))
      return false;
    DefaultSWRLNamedBuiltInArgument impl = (DefaultSWRLNamedBuiltInArgument)obj;
    return (this.entity.getIRI() == impl.entity.getIRI() || (this.entity.getIRI() != null && this.entity.getIRI()
        .equals(impl.entity.getIRI())));
  }

  @Override
  public int hashCode()
  {
    int hash = 152;
    hash = hash + (null == this.entity.getIRI() ? 0 : this.entity.getIRI().hashCode());
    return hash;
  }

  @Override
  public void accept(SWRLObjectVisitor visitor)
  {
    visitor.visit(this);
  }

  @Override
  public <O> O accept(SWRLObjectVisitorEx<O> visitor)
  {
    return visitor.visit(this);
  }

  @Override
  public void accept(OWLObjectVisitor visitor)
  {
    visitor.visit(this);
  }

  @Override
  public <O> O accept(OWLObjectVisitorEx<O> visitor)
  {
    return visitor.visit(this);
  }

  @Override
  public String toString()
  {
    return this.entity.toString();
  }
}
