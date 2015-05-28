package org.swrlapi.resolvers;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

import java.util.HashMap;
import java.util.Map;

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
public class OWLIndividualResolver
{
  @NonNull private final Map<String, OWLIndividual> individualID2OWLIndividual;

  public OWLIndividualResolver()
  {
    this.individualID2OWLIndividual = new HashMap<>();
  }

  public void reset()
  {
    this.individualID2OWLIndividual.clear();
  }

  public void record(@NonNull String individualID, @NonNull OWLIndividual individual)
  {
    this.individualID2OWLIndividual.put(individualID, individual);
  }

  @NonNull public OWLIndividual resolve(@NonNull String individualID) throws TargetSWRLRuleEngineException
  {
    if (this.individualID2OWLIndividual.containsKey(individualID))
      return this.individualID2OWLIndividual.get(individualID);
    else
      throw new TargetSWRLRuleEngineException("internal error: no individual found with ID " + individualID);
  }
}
