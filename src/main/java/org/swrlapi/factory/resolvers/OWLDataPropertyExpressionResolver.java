package org.swrlapi.factory.resolvers;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;

/**
 * This class is used to keep track of OWL data property expressions, typically by a rule engine implementation. OWL 2
 * RL-based reasoners, for example, do not create new property expressions as a result of inference (they do reason with
 * supplied property expressions, though) so rather than translating native rule engine representations of property
 * expressions back to the OWLAPI when returning inferred axioms, the original expressions supplied to the engine can be
 * tracked via an ID and recorded and retrieved using this class.
 *
 * @see org.semanticweb.owlapi.model.OWLDataPropertyExpression
 */
interface OWLDataPropertyExpressionResolver
{
  void reset();

  void recordOWLDataPropertyExpression(@NonNull String propertyExpressionID,
    @NonNull OWLDataPropertyExpression propertyExpression);

  boolean recordsOWLDataPropertyExpression(@NonNull OWLDataPropertyExpression propertyExpression);

  @NonNull String resolveOWLDataPropertyExpression(@NonNull OWLDataPropertyExpression propertyExpression);

  @NonNull OWLDataPropertyExpression resolveOWLDataPropertyExpression(@NonNull String propertyExpressionID);
}
