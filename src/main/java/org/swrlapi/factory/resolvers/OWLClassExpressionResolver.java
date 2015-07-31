package org.swrlapi.factory.resolvers;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLClassExpression;

/**
 * This class is used to keep track of class expressions, typically by a rule engine implementation. OWL 2 RL-based
 * reasoners, for example, do not create new class expressions as a result of inference (they do reason with supplied
 * class expressions, though) so rather than translating native rule engine representations of class expressions back to
 * the OWLAPI representation when returning inferred axioms, the original expressions supplied to the engine can be
 * tracked via an ID and recorded and retrieved using this class.
 *
 * @see org.semanticweb.owlapi.model.OWLClassExpression
 */
interface OWLClassExpressionResolver
{
  void reset();

  void recordOWLClassExpression(@NonNull String classExpressionID, @NonNull OWLClassExpression classExpression);

  boolean recordsOWLClassExpression(@NonNull OWLClassExpression owlClassExpression);

  /**
   *
   * @param owlClassExpression A class expression to resolve
   * @return The ID of the resolved class expression
   * @throws IllegalArgumentException If the class expression cannot be resolved
   */
  @NonNull String resolveOWLClassExpression(@NonNull OWLClassExpression owlClassExpression);

  /**
   *
   * @param classExpressionID A class expression ID
   * @return The resolved class expression
   * @throws IllegalArgumentException If the class expression ID cannot be resolved
   */
  @NonNull OWLClassExpression resolveOWLClassExpression(@NonNull String classExpressionID);
}
