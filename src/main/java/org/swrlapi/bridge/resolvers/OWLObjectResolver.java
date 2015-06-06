package org.swrlapi.bridge.resolvers;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

public interface OWLObjectResolver
{
	void recordOWLClassExpression(@NonNull String classExpressionID, @NonNull OWLClassExpression classExpression);

	boolean recordsOWLClassExpression(@NonNull OWLClassExpression owlClassExpression);

	/**
	 * @param owlClassExpression A class expression to resolveOWLClassExpression
	 * @return The ID of the resolved class expression
	 * @throws IllegalArgumentException If the class expression cannot be resolved
	 */
	@NonNull String resolveOWLClassExpression(@NonNull OWLClassExpression owlClassExpression);

	/**
	 * @param classExpressionID A class expression ID
	 * @return The resolved class expression
	 * @throws IllegalArgumentException If the class expression ID cannot be resolved
	 */
	@NonNull OWLClassExpression resolveOWLClassExpression(@NonNull String classExpressionID);

	void recordOWLIndividual(@NonNull String individualID, @NonNull OWLIndividual individual);

	@NonNull public OWLIndividual resolveOWLIndividual(@NonNull String individualID) throws TargetSWRLRuleEngineException;

	void recordOWLObjectPropertyExpression(@NonNull String propertyExpressionID,
			@NonNull OWLObjectPropertyExpression propertyExpression);

	boolean recordsOWLObjectPropertyExpression(@NonNull OWLObjectPropertyExpression propertyExpression);

	@NonNull String resolveOWLObjectPropertyExpression(@NonNull OWLObjectPropertyExpression propertyExpression);

	@NonNull OWLObjectPropertyExpression resolveOWLObjectPropertyExpression(@NonNull String propertyExpressionID);

	void recordOWLDataPropertyExpression(@NonNull String propertyExpressionID,
			@NonNull OWLDataPropertyExpression propertyExpression);

	boolean recordsOWLDataPropertyExpression(@NonNull OWLDataPropertyExpression propertyExpression);

	@NonNull String resolveOWLDataPropertyExpression(@NonNull OWLDataPropertyExpression propertyExpression);

	@NonNull OWLDataPropertyExpression resolveOWLDataPropertyExpression(@NonNull String propertyExpressionID);

	void recordOWLDataRange(@NonNull String dataRangeID, @NonNull OWLDataRange dataRange);

	@NonNull OWLDataRange resolveOWLDataRange(@NonNull String dataRangeID);
}
