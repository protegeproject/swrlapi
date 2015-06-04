package org.swrlapi.bridge.resolvers;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLDataRange;


/**
 * This class is used to keep track of data ranges, typically by a rule engine implementation. OWL 2 RL-based reasoners,
 * for example, do not create new data ranges as a result of inference (they do reason with supplied data ranges,
 * though) so rather than translating native rule engine representations of data range back to the OWLAPI representation
 * returning inferred axioms, the original data range supplied to the engine can be tracked via an ID and recorded and
 * retrieved using this class.
 *
 * @see org.semanticweb.owlapi.model.OWLDataRange
 */
public interface OWLDataRangeResolver
{
	void reset();

	void recordOWLDataRange(@NonNull String dataRangeID, @NonNull OWLDataRange dataRange);

	@NonNull OWLDataRange resolveOWLDataRange(@NonNull String dataRangeID);
}
