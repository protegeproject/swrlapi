package org.swrlapi.resolvers;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/**
 * This class is used to keep track of OWL object property expressions, typically by a rule engine implementation. OWL 2
 * RL-based reasoners, for example, do not create new property expressions as a result of inference (they do reason with
 * supplied property expressions, though) so rather than translating native rule engine representations of property
 * expressions back to the OWLAPI when returning inferred axioms, the original expressions supplied to the engine can be
 * tracked via an ID and recorded and retrieved using this class.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectPropertyExpression
 */
public interface OWLObjectPropertyExpressionResolver
{
	void reset();

	void record(@NonNull String propertyExpressionID, @NonNull OWLObjectPropertyExpression propertyExpression);

	boolean records(@NonNull OWLObjectPropertyExpression propertyExpression);

	@NonNull String resolve(@NonNull OWLObjectPropertyExpression propertyExpression);

	@NonNull OWLObjectPropertyExpression resolve(@NonNull String propertyExpressionID);
}
