package org.swrlapi.core.resolvers;

import org.semanticweb.owlapi.model.OWLIndividual;
import org.swrlapi.exceptions.TargetRuleEngineException;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to keep track of OWL individuals, typically by a rule engine implementation. OWL 2 RL-based
 * reasoners, for example, do not create new individual as a result of inference (they do reason with supplied
 * individuals, though) so rather than translating native rule engine representations of individuals
 * back to the OWLAPI when returning inferred axioms, the original individuals supplied to the engine can be tracked via
 * an ID and recorded and retrieved using this class.
 *
 * @see org.semanticweb.owlapi.model.OWLIndividual
 * @see org.semanticweb.owlapi.model.OWLNamedIndividual
 * @see org.semanticweb.owlapi.model.OWLAnonymousIndividual
 */
public class OWLIndividualResolver
{
	private final Map<String, OWLIndividual> individualID2OWLIndividual;

	public OWLIndividualResolver()
	{
		this.individualID2OWLIndividual = new HashMap<String, OWLIndividual>();
	}

	public void reset()
	{
		this.individualID2OWLIndividual.clear();
	}

	public void record(String individualID, OWLIndividual individual)
	{
		this.individualID2OWLIndividual.put(individualID, individual);
	}

	public OWLIndividual resolve(String individualID) throws TargetRuleEngineException
	{
		if (this.individualID2OWLIndividual.containsKey(individualID))
			return this.individualID2OWLIndividual.get(individualID);
		else
			throw new TargetRuleEngineException("internal error: no individual found with ID " + individualID);
	}
}
