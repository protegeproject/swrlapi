package org.swrlapi.core.resolvers;

import java.util.HashMap;
import java.util.Map;

import org.semanticweb.owlapi.model.OWLDataRange;
import org.swrlapi.exceptions.SWRLAPIInternalException;

/**
 * This class is used to keep track of data ranges, typically by a rule engine implementation. OWL 2 RL-based
 * reasoners, for example, do not create new data ranges as a result of inference (they do reason with supplied
 * data ranges, though) so rather than translating native rule engine representations of data range back to
 * the OWLAPI representation returning inferred axioms, the original data range supplied to the engine can be tracked
 * via an ID and recorded and retrieved using this class.
 *
 * @see org.semanticweb.owlapi.model.OWLDataRange
 */
public class OWLDataRangeResolver
{
  private final Map<String, OWLDataRange> dataRangeMap;

  public OWLDataRangeResolver()
  {
    this.dataRangeMap = new HashMap<>();
    reset();
  }

  public void reset()
  {
    this.dataRangeMap.clear();
  }

  public void recordOWLDataRange(String dataRangeID, OWLDataRange dataRange)
  {
    this.dataRangeMap.put(dataRangeID, dataRange);
  }

  public OWLDataRange resolveOWLDataRange(String dataRangeID)
  {
    if (this.dataRangeMap.containsKey(dataRangeID))
      return this.dataRangeMap.get(dataRangeID);
    else
      throw new SWRLAPIInternalException("no data range found with ID " + dataRangeID);
  }
}
