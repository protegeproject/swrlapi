package org.swrlapi.core;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/**
 * OWL 2 RL-based rules engine do not necessarily need to create class or property expressions or data ranges but
 * they do need to generate OWL axioms that use them. This class allows implementations to record and resolve class and
 * property expressions and data ranges using a unique identifier, obviating the need to recreate expressions
 * or data ranges.
 */
public interface OWLObjectResolver
{
  void recordOWLClass(@NonNull String classID, @NonNull OWLClass cls);

  void recordOWLClassExpression(@NonNull String classExpressionID, @NonNull OWLClassExpression classExpression);

  void recordOWLObjectProperty(@NonNull String propertyID, @NonNull OWLObjectProperty property);

  void recordOWLObjectPropertyExpression(@NonNull String propertyExpressionID,
    @NonNull OWLObjectPropertyExpression propertyExpression);

  void recordOWLDataProperty(@NonNull String propertyID, @NonNull OWLDataProperty property);

  void recordOWLDataPropertyExpression(@NonNull String propertyExpressionID,
    @NonNull OWLDataPropertyExpression propertyExpression);

  void recordOWLDataRange(@NonNull String dataRangeID, @NonNull OWLDataRange dataRange);

  boolean recordsOWLClass(@NonNull OWLClass cls);

  boolean recordsOWLClassExpression(@NonNull OWLClassExpression classExpression);

  boolean recordsOWLNamedIndividual(@NonNull OWLNamedIndividual individual);

  boolean recordsOWLObjectPropertyExpression(@NonNull OWLObjectPropertyExpression propertyExpression);

  boolean recordsOWLDataPropertyExpression(@NonNull OWLDataPropertyExpression propertyExpression);

  @NonNull String resolveOWLClass2ID(@NonNull OWLClass cls);

  @NonNull String resolveOWLClassExpression2ID(@NonNull OWLClassExpression classExpression);

  @NonNull String resolveOWLNamedIndividual2ID(@NonNull OWLNamedIndividual individual);

  @NonNull String resolveOWLObjectProperty2ID(@NonNull OWLObjectProperty property);

  @NonNull String resolveOWLObjectPropertyExpression2ID(@NonNull OWLObjectPropertyExpression propertyExpression);

  @NonNull String resolveOWLDataProperty2ID(@NonNull OWLDataProperty property);

  @NonNull String resolveOWLDatatype2ID(@NonNull OWLDatatype datatype);

  @NonNull String resolveOWLDataPropertyExpression2ID(@NonNull OWLDataPropertyExpression propertyExpression);

  @NonNull OWLClass resolveOWLClass(@NonNull String classID);

  @NonNull OWLClassExpression resolveOWLClassExpression(@NonNull String classExpressionID);

  @NonNull OWLNamedIndividual resolveOWLNamedIndividual(@NonNull String individualID);

  @NonNull OWLObjectPropertyExpression resolveOWLObjectPropertyExpression(@NonNull String propertyExpressionID);

  @NonNull OWLObjectProperty resolveOWLObjectProperty(@NonNull String propertyID);

  @NonNull OWLDataPropertyExpression resolveOWLDataPropertyExpression(@NonNull String propertyExpressionID);

  @NonNull OWLDataProperty resolveOWLDataProperty(@NonNull String propertyID);

  @NonNull OWLDatatype resolveOWLDatatype(@NonNull String datatypeID);

  @NonNull OWLDataRange resolveOWLDataRange(@NonNull String dataRangeID);
}
