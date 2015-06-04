package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.swrlapi.factory.DefaultOWLClassExpressionResolver;
import org.swrlapi.factory.DefaultOWLDataPropertyExpressionResolver;
import org.swrlapi.factory.DefaultOWLDataRangeResolver;
import org.swrlapi.factory.DefaultOWLIndividualResolver;
import org.swrlapi.factory.DefaultOWLObjectPropertyExpressionResolver;
import org.swrlapi.resolvers.OWLClassExpressionResolver;
import org.swrlapi.resolvers.OWLDataPropertyExpressionResolver;
import org.swrlapi.resolvers.OWLDataRangeResolver;
import org.swrlapi.resolvers.OWLIndividualResolver;
import org.swrlapi.resolvers.OWLObjectPropertyExpressionResolver;
import org.swrlapi.resolvers.OWLResolver;

public class DefaultOWLResolver implements OWLResolver
{
  private final @NonNull OWLClassExpressionResolver owlClassExpressionResolver;
  private final @NonNull OWLIndividualResolver owlIndividualExpressionResolver;
  private final @NonNull OWLObjectPropertyExpressionResolver owlObjectPropertyExpressionResolver;
  private final @NonNull OWLDataPropertyExpressionResolver owlDataPropertyExpressionResolver;
  private final @NonNull OWLDataRangeResolver owlDataRangeResolver;

  public DefaultOWLResolver(@NonNull OWLDataFactory owlDataFactory)
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
