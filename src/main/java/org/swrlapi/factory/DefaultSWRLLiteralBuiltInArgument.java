package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Deterministic;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.SWRLObjectVisitor;
import org.semanticweb.owlapi.model.SWRLObjectVisitorEx;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentType;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitor;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.literal.OWLLiteralComparator;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

class DefaultSWRLLiteralBuiltInArgument extends DefaultSWRLBuiltInArgument implements SWRLLiteralBuiltInArgument
{
  private static final long serialVersionUID = 1L;

  @NonNull private static final Comparator<OWLLiteral> owlLiteralComparator = OWLLiteralComparator.COMPARATOR;

  @NonNull private final OWLLiteral literal;

  public DefaultSWRLLiteralBuiltInArgument(@NonNull OWLLiteral literal)
  {
    this.literal = literal;
  }

  @NonNull @Override public SWRLBuiltInArgumentType<?> getSWRLBuiltInArgumentType()
  {
    return SWRLBuiltInArgumentType.LITERAL;
  }

  @NonNull @Override public OWLLiteral getLiteral()
  {
    return this.literal;
  }

  @NonNull @Override public SWRLLiteralBuiltInArgument asSWRLLiteralBuiltInArgument() throws SWRLBuiltInException
  {
    return this;
  }

  @SideEffectFree @Deterministic @Override public boolean equals(@Nullable Object o)
  {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    DefaultSWRLLiteralBuiltInArgument that = (DefaultSWRLLiteralBuiltInArgument)o;

    return this.literal.equals(that.literal);
  }

  @SideEffectFree @Deterministic @Override public int hashCode()
  {
    return this.literal.hashCode();
  }

  @SideEffectFree @Deterministic @Override public int compareTo(@NonNull OWLObject o)
  {
    if (o == null)
      throw new NullPointerException();

    if (!(o instanceof SWRLLiteralBuiltInArgument))
      return -1;

    SWRLLiteralBuiltInArgument other = (SWRLLiteralBuiltInArgument)o;

    return owlLiteralComparator.compare(this.getLiteral(), other.getLiteral());
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

  @NonNull @Override public <@NonNull T> T accept(@NonNull SWRLBuiltInArgumentVisitorEx<@NonNull T> visitor)
  {
    return visitor.visit(this);
  }

  @Override public void accept(@NonNull SWRLBuiltInArgumentVisitor visitor)
  {
    visitor.visit(this);
  }

   @NonNull @SideEffectFree @Override public String toString()
  {
    return this.literal.getLiteral();
  }

  @NonNull @Override public Set<@NonNull OWLAnnotationProperty> getAnnotationPropertiesInSignature()
  {
    return Collections.<@NonNull OWLAnnotationProperty>emptySet(); // TODO Implement getAnnotationPropertiesInSignature
  }
}
