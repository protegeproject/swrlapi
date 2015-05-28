package org.swrlapi.resolvers;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLDataRange;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to keep track of data ranges, typically by a rule engine implementation. OWL 2 RL-based reasoners,
 * for example, do not create new data ranges as a result of inference (they do reason with supplied data ranges,
 * though) so rather than translating native rule engine representations of data range back to the OWLAPI representation
 * returning inferred axioms, the original data range supplied to the engine can be tracked via an ID and recorded and
 * retrieved using this class.
 *
 * @see org.semanticweb.owlapi.model.OWLDataRange
 */
public class OWLDataRangeResolver
{
  @NonNull private final Map<String, OWLDataRange> dataRangeMap;

  public OWLDataRangeResolver()
  {
    this.dataRangeMap = new HashMap<>();
  }

  public void reset()
  {
    this.dataRangeMap.clear();
  }

  public void recordOWLDataRange(@NonNull String dataRangeID, @NonNull OWLDataRange dataRange)
  {
    this.dataRangeMap.put(dataRangeID, dataRange);
  }

  @NonNull public OWLDataRange resolveOWLDataRange(@NonNull String dataRangeID)
  {
    if (this.dataRangeMap.containsKey(dataRangeID))
      return this.dataRangeMap.get(dataRangeID);
    else
      throw new IllegalArgumentException("no data range found with ID " + dataRangeID);
  }
}
