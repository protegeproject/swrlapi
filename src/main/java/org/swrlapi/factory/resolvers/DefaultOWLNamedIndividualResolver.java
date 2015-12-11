package org.swrlapi.factory.resolvers;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.HashMap;
import java.util.Map;

class DefaultOWLNamedIndividualResolver implements OWLNamedIndividualResolver
{
  @NonNull private final Map<@NonNull String, OWLNamedIndividual> id2OWLNamedIndividual;
  @NonNull private final Map<OWLNamedIndividual, String> owlNamedIndividual2ID;
  
  public DefaultOWLNamedIndividualResolver()
  {
    this.id2OWLNamedIndividual = new HashMap<>();
    this.owlNamedIndividual2ID = new HashMap<>();
  }

  @Override public void reset()
  {
    this.id2OWLNamedIndividual.clear();
    this.owlNamedIndividual2ID.clear();
  }

  @Override public void recordOWLNamedIndividual(@NonNull String individualID, @NonNull OWLNamedIndividual individual)
  {
    this.id2OWLNamedIndividual.put(individualID, individual);
    this.owlNamedIndividual2ID.put(individual, individualID);
  }

  @Override public boolean recordsOWLNamedIndividual(@NonNull OWLNamedIndividual Individual)
  {
    return this.owlNamedIndividual2ID.containsKey(Individual);
  }

  @Override @NonNull public String resolveOWLNamedIndividual(@NonNull OWLNamedIndividual individual)
  {
    if (this.owlNamedIndividual2ID.containsKey(individual))
      return this.owlNamedIndividual2ID.get(individual);
    else
      throw new IllegalArgumentException("no ID found for named individual " + individual);
  }

  @Override @NonNull public OWLNamedIndividual resolveOWLNamedIndividual(@NonNull String individualID)
  {
    if (this.id2OWLNamedIndividual.containsKey(individualID))
      return this.id2OWLNamedIndividual.get(individualID);
    else
      throw new IllegalArgumentException("no named individual found with ID " + individualID);
  }
}
