package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.swrlapi.bridge.resolvers.OWLClassExpressionResolver;
import org.swrlapi.bridge.resolvers.OWLDataPropertyExpressionResolver;
import org.swrlapi.bridge.resolvers.OWLDataRangeResolver;
import org.swrlapi.bridge.resolvers.OWLIndividualResolver;
import org.swrlapi.bridge.resolvers.OWLObjectPropertyExpressionResolver;
import org.swrlapi.bridge.resolvers.OWLObjectResolver;

class DefaultOWLObjectResolver implements OWLObjectResolver
{
  private final @NonNull OWLClassExpressionResolver owlClassExpressionResolver;
  private final @NonNull OWLIndividualResolver owlIndividualExpressionResolver;
  private final @NonNull OWLObjectPropertyExpressionResolver owlObjectPropertyExpressionResolver;
  private final @NonNull OWLDataPropertyExpressionResolver owlDataPropertyExpressionResolver;
  private final @NonNull OWLDataRangeResolver owlDataRangeResolver;

  public DefaultOWLObjectResolver(@NonNull OWLDataFactory owlDataFactory)
  {
    this.owlClassExpressionResolver = new DefaultOWLClassExpressionResolver(owlDataFactory);
    this.owlIndividualExpressionResolver = new DefaultOWLIndividualResolver();
    this.owlObjectPropertyExpressionResolver = new DefaultOWLObjectPropertyExpressionResolver();
    this.owlDataPropertyExpressionResolver = new DefaultOWLDataPropertyExpressionResolver();
    this.owlDataRangeResolver = new DefaultOWLDataRangeResolver();
  }

  @Override @NonNull public OWLClassExpressionResolver getOWLClassExpressionResolver()
  {
    return this.owlClassExpressionResolver;
  }

  @Override @NonNull public OWLIndividualResolver getOWLIndividualExpressionResolver()
  {
    return this.owlIndividualExpressionResolver;
  }

  @Override @NonNull public OWLObjectPropertyExpressionResolver getOWLObjectPropertyExpressionResolver()
  {
    return this.owlObjectPropertyExpressionResolver;
  }

  @Override @NonNull public OWLDataPropertyExpressionResolver getOWLDataPropertyExpressionResolver()
  {
    return this.owlDataPropertyExpressionResolver;
  }

  @Override @NonNull public OWLDataRangeResolver getOWLDataRangeResolver()
  {
    return this.owlDataRangeResolver;
  }
}
