package org.swrlapi.core;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
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

  /**
   * @param classExpression A class expression to resolve
   * @return The ID of the resolved class expression
   * @throws IllegalArgumentException If the class expression cannot be resolved
   */
  @NonNull String resolveOWLClassExpression(@NonNull OWLClassExpression classExpression);

  @NonNull OWLClass resolveOWLClass(@NonNull String classID);

  /**
   * @param classExpressionID A class expression ID
   * @return The resolved class expression
   * @throws IllegalArgumentException If the class expression ID cannot be resolved
   */
  @NonNull OWLClassExpression resolveOWLClassExpression(@NonNull String classExpressionID);

  @NonNull OWLNamedIndividual resolveOWLNamedIndividual(@NonNull String individualID);

  @NonNull String resolveOWLObjectPropertyExpression(@NonNull OWLObjectPropertyExpression propertyExpression);


  @NonNull OWLObjectPropertyExpression resolveOWLObjectPropertyExpression(@NonNull String propertyExpressionID);

  @NonNull OWLObjectProperty resolveOWLObjectProperty(@NonNull String propertyID);

  @NonNull String resolveOWLDataPropertyExpression(@NonNull OWLDataPropertyExpression propertyExpression);

  @NonNull OWLDataPropertyExpression resolveOWLDataPropertyExpression(@NonNull String propertyExpressionID);

  @NonNull OWLDataProperty resolveOWLDataProperty(@NonNull String propertyID);

  @NonNull OWLDataRange resolveOWLDataRange(@NonNull String dataRangeID);
}
