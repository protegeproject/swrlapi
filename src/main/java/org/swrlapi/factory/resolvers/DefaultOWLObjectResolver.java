package org.swrlapi.factory.resolvers;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.swrlapi.core.OWLObjectResolver;

public class DefaultOWLObjectResolver implements OWLObjectResolver
{
  private final @NonNull OWLClassResolver owlClassResolver;
  private final @NonNull OWLClassExpressionResolver owlClassExpressionResolver;
  private final @NonNull OWLObjectPropertyResolver owlObjectPropertyResolver;
  private final @NonNull OWLNamedIndividualResolver owlNamedIndividualResolver;
  private final @NonNull OWLObjectPropertyExpressionResolver owlObjectPropertyExpressionResolver;
  private final @NonNull OWLDataPropertyResolver owlDataPropertyResolver;
  private final @NonNull OWLDataPropertyExpressionResolver owlDataPropertyExpressionResolver;
  private final @NonNull OWLDataRangeResolver owlDataRangeResolver;

  public DefaultOWLObjectResolver(@NonNull OWLDataFactory owlDataFactory)
  {
    this.owlClassResolver = new DefaultOWLClassResolver(owlDataFactory);
    this.owlClassExpressionResolver = new DefaultOWLClassExpressionResolver(owlDataFactory);
    this.owlNamedIndividualResolver = new DefaultOWLNamedIndividualResolver();
    this.owlObjectPropertyResolver = new DefaultOWLObjectPropertyResolver(owlDataFactory);
    this.owlObjectPropertyExpressionResolver = new DefaultOWLObjectPropertyExpressionResolver();
    this.owlDataPropertyResolver = new DefaultOWLDataPropertyResolver(owlDataFactory);
    this.owlDataPropertyExpressionResolver = new DefaultOWLDataPropertyExpressionResolver();
    this.owlDataRangeResolver = new DefaultOWLDataRangeResolver();
  }

  @Override public void recordOWLClass(@NonNull String classID, @NonNull OWLClass cls)
  {
    this.owlClassExpressionResolver.recordOWLClassExpression(classID, cls);
  }

  @Override public boolean recordsOWLClass(@NonNull OWLClass cls)
  {
    return this.owlClassResolver.recordsOWLClass(cls);
  }

  @Override @NonNull public OWLClass resolveOWLClass(@NonNull String classID)
  {
    return this.owlClassResolver.resolveOWLClass(classID).asOWLClass();
  }

  @Override public void recordOWLClassExpression(@NonNull String classExpressionID,
      @NonNull OWLClassExpression classExpression)
  {
    this.owlClassExpressionResolver.recordOWLClassExpression(classExpressionID, classExpression);
  }

  @Override public boolean recordsOWLClassExpression(@NonNull OWLClassExpression classExpression)
  {
    return this.owlClassExpressionResolver.recordsOWLClassExpression(classExpression);
  }

  @Override @NonNull public String resolveOWLClassExpression2ID(@NonNull OWLClassExpression classExpression)
  {
    return this.owlClassExpressionResolver.resolveOWLClassExpression(classExpression);
  }

  @Override @NonNull public String resolveOWLClass2ID(@NonNull OWLClass cls)
  {
    return this.owlClassResolver.resolveOWLClass(cls);
  }

  @Override @NonNull public OWLClassExpression resolveOWLClassExpression(@NonNull String classExpressionID)
  {
    return this.owlClassExpressionResolver.resolveOWLClassExpression(classExpressionID);
  }

  @Override public boolean recordsOWLNamedIndividual(@NonNull OWLNamedIndividual individual)
  {
    return this.owlNamedIndividualResolver.recordsOWLNamedIndividual(individual);
  }

  @Override @NonNull public OWLNamedIndividual resolveOWLNamedIndividual(@NonNull String individualID)
  {
    return this.owlNamedIndividualResolver.resolveOWLNamedIndividual(individualID);
  }

  @Override public void recordOWLObjectProperty(@NonNull String propertyID, @NonNull OWLObjectProperty property)
  {
    this.owlObjectPropertyResolver.recordOWLObjectProperty(propertyID, property);
  }

  @Override public void recordOWLObjectPropertyExpression(@NonNull String propertyExpressionID,
      @NonNull OWLObjectPropertyExpression propertyExpression)
  {
    this.owlObjectPropertyExpressionResolver
        .recordOWLObjectPropertyExpression(propertyExpressionID, propertyExpression);
  }

  @Override public void recordOWLDataProperty(@NonNull String propertyID, @NonNull OWLDataProperty property)
  {
    this.owlDataPropertyResolver.recordOWLDataProperty(propertyID, property);
  }

  @Override public boolean recordsOWLObjectPropertyExpression(@NonNull OWLObjectPropertyExpression propertyExpression)
  {
    return this.owlObjectPropertyExpressionResolver.recordsOWLObjectPropertyExpression(propertyExpression);
  }

  @Override @NonNull public String resolveOWLObjectPropertyExpression2ID(
      @NonNull OWLObjectPropertyExpression propertyExpression)
  {
    return this.owlObjectPropertyExpressionResolver.resolveOWLObjectPropertyExpression(propertyExpression);
  }

  @Override @NonNull public String resolveOWLNamedIndividual2ID(@NonNull OWLNamedIndividual individual)
  {
    return this.owlNamedIndividualResolver.resolveOWLNamedIndividual(individual);
  }

  @Override @NonNull public String resolveOWLObjectProperty2ID(@NonNull OWLObjectProperty property)
  {
    return this.owlObjectPropertyResolver.resolveOWLObjectProperty(property);
  }

  @Override @NonNull public OWLObjectPropertyExpression resolveOWLObjectPropertyExpression(
      @NonNull String propertyExpressionID)
  {
    return this.owlObjectPropertyExpressionResolver.resolveOWLObjectPropertyExpression(propertyExpressionID);
  }

  @Override @NonNull public OWLObjectProperty resolveOWLObjectProperty(@NonNull String propertyID)
  {
    return this.owlObjectPropertyResolver.resolveOWLObjectProperty(propertyID);
  }

  @Override public void recordOWLDataPropertyExpression(@NonNull String propertyExpressionID,
      @NonNull OWLDataPropertyExpression propertyExpression)
  {
    this.owlDataPropertyExpressionResolver.recordOWLDataPropertyExpression(propertyExpressionID, propertyExpression);
  }

  @Override public boolean recordsOWLDataPropertyExpression(@NonNull OWLDataPropertyExpression propertyExpression)
  {
    return this.owlDataPropertyExpressionResolver.recordsOWLDataPropertyExpression(propertyExpression);
  }

  @Override @NonNull public String resolveOWLDataPropertyExpression2ID(
      @NonNull OWLDataPropertyExpression propertyExpression)
  {
    return this.owlDataPropertyExpressionResolver.resolveOWLDataPropertyExpression(propertyExpression);
  }

  @Override @NonNull public String resolveOWLDataProperty2ID(@NonNull OWLDataProperty property)
  {
    return this.owlDataPropertyResolver.resolveOWLDataProperty(property);
  }

  @Override @NonNull public OWLDataPropertyExpression resolveOWLDataPropertyExpression(
      @NonNull String propertyExpressionID)
  {
    return this.owlDataPropertyExpressionResolver.resolveOWLDataPropertyExpression(propertyExpressionID);
  }

  @Override @NonNull public OWLDataProperty resolveOWLDataProperty(@NonNull String propertyID)
  {
    return this.owlDataPropertyResolver.resolveOWLDataProperty(propertyID);
  }

  @Override public void recordOWLDataRange(@NonNull String dataRangeID, @NonNull OWLDataRange dataRange)
  {
    this.owlDataRangeResolver.recordOWLDataRange(dataRangeID, dataRange);
  }

  @Override @NonNull public OWLDataRange resolveOWLDataRange(@NonNull String dataRangeID)
  {
    return this.owlDataRangeResolver.resolveOWLDataRange(dataRangeID);
  }
}
