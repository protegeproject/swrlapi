package org.swrlapi.factory.resolvers;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This class is used to keep track of OWL individuals, typically by a rule engine implementation. OWL 2 RL-based
 * reasoners, for example, do not create new individual as a result of inference (they do reason with supplied
 * individuals, though) so rather than translating native rule engine representations of individuals back to the OWLAPI
 * when returning inferred axioms, the original individuals supplied to the engine can be tracked via an ID and recorded
 * and retrieved using this class.
 *
 * @see org.semanticweb.owlapi.model.OWLIndividual
 * @see org.semanticweb.owlapi.model.OWLNamedIndividual
 * @see org.semanticweb.owlapi.model.OWLAnonymousIndividual
 */
interface OWLIndividualResolver
{
	void reset();

	void recordOWLIndividual(@NonNull String individualID, @NonNull OWLIndividual individual);

	@NonNull OWLIndividual resolveOWLIndividual(@NonNull String individualID) throws TargetSWRLRuleEngineException;
}
