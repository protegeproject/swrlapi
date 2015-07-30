package org.swrlapi.factory.resolvers;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLDataRange;

import java.util.HashMap;
import java.util.Map;

class DefaultOWLDataRangeResolver implements OWLDataRangeResolver
{
  @NonNull private final Map<String, OWLDataRange> dataRangeMap;

  public DefaultOWLDataRangeResolver()
  {
    this.dataRangeMap = new HashMap<>();
  }

  @Override public void reset()
  {
    this.dataRangeMap.clear();
  }

  @Override public void recordOWLDataRange(@NonNull String dataRangeID, @NonNull OWLDataRange dataRange)
  {
    this.dataRangeMap.put(dataRangeID, dataRange);
  }

  @Override @NonNull public OWLDataRange resolveOWLDataRange(@NonNull String dataRangeID)
  {
    if (this.dataRangeMap.containsKey(dataRangeID))
      return this.dataRangeMap.get(dataRangeID);
    else
      throw new IllegalArgumentException("no data range found with ID " + dataRangeID);
  }
}
