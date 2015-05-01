package org.swrlapi.core.resolvers;

import org.semanticweb.owlapi.model.OWLDataFactory;

public class OWLResolver
{
  private final OWLClassExpressionResolver owlClassExpressionResolver;
  private final OWLIndividualResolver owlIndividualExpressionResolver;
  private final OWLObjectPropertyExpressionResolver owlObjectPropertyExpressionResolver;
  private final OWLDataPropertyExpressionResolver owlDataPropertyExpressionResolver;
  private final OWLDataRangeResolver owlDataRangeResolver;

  public OWLResolver(OWLDataFactory owlDataFactory)
  {
    this.owlClassExpressionResolver = new OWLClassExpressionResolver(owlDataFactory);
    this.owlIndividualExpressionResolver = new OWLIndividualResolver();
    this.owlObjectPropertyExpressionResolver = new OWLObjectPropertyExpressionResolver();
    this.owlDataPropertyExpressionResolver = new OWLDataPropertyExpressionResolver();
    this.owlDataRangeResolver = new OWLDataRangeResolver();
  }

  public OWLClassExpressionResolver getOWLClassExpressionResolver()
  {
    return this.owlClassExpressionResolver;
  }

  public OWLIndividualResolver getOWLIndividualExpressionResolver()
  {
    return this.owlIndividualExpressionResolver;
  }

  public OWLObjectPropertyExpressionResolver getOWLObjectPropertyExpressionResolver()
  {
    return this.owlObjectPropertyExpressionResolver;
  }

  public OWLDataPropertyExpressionResolver getOWLDataPropertyExpressionResolver()
  {
    return this.owlDataPropertyExpressionResolver;
  }

  public OWLDataRangeResolver getOWLDataRangeResolver()
  {
    return this.owlDataRangeResolver;
  }
}
