package org.swrlapi.core;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.swrlapi.exceptions.TargetRuleEngineException;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to keep track of data ranges, typically by a rule engine implementation. OWL 2 RL-based
 * reasoners, for example, do not create new data ranges as a result of inference (they do reason with supplied
 * data ranges, though) so rather than translating native rule engine representations of data range back to
 * the OWLAPI representation returning inferred axioms, the original data range supplied to the engine can be tracked
 * via an ID and recorded and retrieved using this class.
 * */
public class OWLDataRangeResolver
{
	private final Map<String, OWLDataRange> dataRangeMap;

	private final OWLDataFactory owlDataFactory;

	public OWLDataRangeResolver(OWLDataFactory owlDataFactory)
	{
		this.dataRangeMap = new HashMap<String, OWLDataRange>();
		this.owlDataFactory = owlDataFactory;
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

	public OWLDataRange resolveOWLDataRange(String dataRangeID) throws TargetRuleEngineException
	{
		if (this.dataRangeMap.containsKey(dataRangeID))
			return this.dataRangeMap.get(dataRangeID);
		else
			throw new TargetRuleEngineException("internal error: no data range found with ID " + dataRangeID);
	}

	private OWLDataFactory getOWLDataFactory()
	{
		return this.owlDataFactory;
	}
}
