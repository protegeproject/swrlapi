package org.swrlapi.factory.resolvers;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.swrlapi.core.OWLObjectResolver;

public class DefaultOWLObjectResolver implements OWLObjectResolver
{
  private final @NonNull OWLClassExpressionResolver owlClassResolver;
  private final @NonNull OWLClassExpressionResolver owlClassExpressionResolver;
  private final @NonNull OWLObjectPropertyExpressionResolver owlObjectPropertyExpressionResolver;
  private final @NonNull OWLDataPropertyExpressionResolver owlDataPropertyResolver;
  private final @NonNull OWLDataPropertyExpressionResolver owlDataPropertyExpressionResolver;
  private final @NonNull OWLDataRangeResolver owlDataRangeResolver;

  public DefaultOWLObjectResolver(@NonNull OWLDataFactory owlDataFactory)
  {
    this.owlClassResolver = new DefaultOWLClassExpressionResolver(owlDataFactory);
    this.owlClassExpressionResolver = new DefaultOWLClassExpressionResolver(owlDataFactory);
    this.owlObjectPropertyExpressionResolver = new DefaultOWLObjectPropertyExpressionResolver();
    this.owlDataPropertyResolver = new DefaultOWLDataPropertyExpressionResolver();
    this.owlDataPropertyExpressionResolver = new DefaultOWLDataPropertyExpressionResolver();
    this.owlDataRangeResolver = new DefaultOWLDataRangeResolver();
  }

  @Override public void recordOWLClass(@NonNull String classID, @NonNull OWLClass cls)
  {
    this.owlClassExpressionResolver.recordOWLClassExpression(classID, cls);
  }

  @Override public boolean recordsOWLClass(@NonNull OWLClass cls)
  {
    return this.owlClassResolver.recordsOWLClassExpression(cls);
  }

  @Override @NonNull public OWLClass resolveOWLClass(@NonNull String classID)
  {
    return this.owlClassResolver.resolveOWLClassExpression(classID).asOWLClass();
  }

  @Override public void recordOWLClassExpression(@NonNull String classExpressionID,
    @NonNull OWLClassExpression classExpression)
  {
    this.owlClassExpressionResolver.recordOWLClassExpression(classExpressionID, classExpression);
  }

  @Override public boolean recordsOWLClassExpression(@NonNull OWLClassExpression owlClassExpression)
  {
    return this.owlClassExpressionResolver.recordsOWLClassExpression(owlClassExpression);
  }

  @Override public boolean recordsOWLNamedIndividual(@NonNull OWLNamedIndividual individual)
  {
    return false; // TODO
  }

  @Override @NonNull public String resolveOWLClassExpression(@NonNull OWLClassExpression owlClassExpression)
  {
    return this.owlClassExpressionResolver.resolveOWLClassExpression(owlClassExpression);
  }

  @Override @NonNull public OWLClassExpression resolveOWLClassExpression(@NonNull String classExpressionID)
  {
    return this.owlClassExpressionResolver.resolveOWLClassExpression(classExpressionID);
  }

  @Override public @NonNull OWLNamedIndividual resolveOWLNamedIndividual(@NonNull String individualID)
  {
    return null; // TODO
  }

  @Override public void recordOWLObjectPropertyExpression(@NonNull String propertyExpressionID,
    @NonNull OWLObjectPropertyExpression propertyExpression)
  {
    this.owlObjectPropertyExpressionResolver
      .recordOWLObjectPropertyExpression(propertyExpressionID, propertyExpression);
  }

  @Override public void recordOWLDataProperty(@NonNull String propertyID, @NonNull OWLDataProperty property)
  {
    this.owlDataPropertyResolver.recordOWLDataPropertyExpression(propertyID, property);
  }

  @Override public boolean recordsOWLObjectPropertyExpression(@NonNull OWLObjectPropertyExpression propertyExpression)
  {
    return this.owlObjectPropertyExpressionResolver.recordsOWLObjectPropertyExpression(propertyExpression);
  }

  @Override @NonNull public String resolveOWLObjectPropertyExpression(
    @NonNull OWLObjectPropertyExpression propertyExpression)
  {
    return this.owlObjectPropertyExpressionResolver.resolveOWLObjectPropertyExpression(propertyExpression);
  }

  @Override @NonNull public OWLObjectPropertyExpression resolveOWLObjectPropertyExpression(
    @NonNull String propertyExpressionID)
  {
    return this.owlObjectPropertyExpressionResolver.resolveOWLObjectPropertyExpression(propertyExpressionID);
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

  @Override @NonNull public String resolveOWLDataPropertyExpression(
    @NonNull OWLDataPropertyExpression propertyExpression)
  {
    return this.owlDataPropertyExpressionResolver.resolveOWLDataPropertyExpression(propertyExpression);
  }

  @Override @NonNull public OWLDataPropertyExpression resolveOWLDataPropertyExpression(
    @NonNull String propertyExpressionID)
  {
    return this.owlDataPropertyExpressionResolver.resolveOWLDataPropertyExpression(propertyExpressionID);
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
