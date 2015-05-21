package org.swrlapi.core.resolvers;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLDataFactory;

public class OWLResolver
{
  @NonNull private final OWLClassExpressionResolver owlClassExpressionResolver;
  @NonNull private final OWLIndividualResolver owlIndividualExpressionResolver;
  @NonNull private final OWLObjectPropertyExpressionResolver owlObjectPropertyExpressionResolver;
  @NonNull private final OWLDataPropertyExpressionResolver owlDataPropertyExpressionResolver;
  @NonNull private final OWLDataRangeResolver owlDataRangeResolver;

  public OWLResolver(@NonNull OWLDataFactory owlDataFactory)
  {
    this.owlClassExpressionResolver = new OWLClassExpressionResolver(owlDataFactory);
    this.owlIndividualExpressionResolver = new OWLIndividualResolver();
    this.owlObjectPropertyExpressionResolver = new OWLObjectPropertyExpressionResolver();
    this.owlDataPropertyExpressionResolver = new OWLDataPropertyExpressionResolver();
    this.owlDataRangeResolver = new OWLDataRangeResolver();
  }

  @NonNull public OWLClassExpressionResolver getOWLClassExpressionResolver()
  {
    return this.owlClassExpressionResolver;
  }

  @NonNull public OWLIndividualResolver getOWLIndividualExpressionResolver()
  {
    return this.owlIndividualExpressionResolver;
  }

  @NonNull public OWLObjectPropertyExpressionResolver getOWLObjectPropertyExpressionResolver()
  {
    return this.owlObjectPropertyExpressionResolver;
  }

  @NonNull public OWLDataPropertyExpressionResolver getOWLDataPropertyExpressionResolver()
  {
    return this.owlDataPropertyExpressionResolver;
  }

  @NonNull public OWLDataRangeResolver getOWLDataRangeResolver()
  {
    return this.owlDataRangeResolver;
  }
}
