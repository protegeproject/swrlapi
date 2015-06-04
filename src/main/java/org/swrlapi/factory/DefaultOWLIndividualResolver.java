package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;
import org.swrlapi.bridge.resolvers.OWLIndividualResolver;

import java.util.HashMap;
import java.util.Map;

class DefaultOWLIndividualResolver implements OWLIndividualResolver
{
  @NonNull private final Map<String, OWLIndividual> individualID2OWLIndividual;

  public DefaultOWLIndividualResolver()
  {
    this.individualID2OWLIndividual = new HashMap<>();
  }

  @Override public void reset()
  {
    this.individualID2OWLIndividual.clear();
  }

  @Override public void recordOWLIndividual(@NonNull String individualID, @NonNull OWLIndividual individual)
  {
    this.individualID2OWLIndividual.put(individualID, individual);
  }

  @Override @NonNull public OWLIndividual resolveOWLIndividual(@NonNull String individualID) throws TargetSWRLRuleEngineException
  {
    if (this.individualID2OWLIndividual.containsKey(individualID))
      return this.individualID2OWLIndividual.get(individualID);
    else
      throw new TargetSWRLRuleEngineException("internal error: no individual found with ID " + individualID);
  }
}
