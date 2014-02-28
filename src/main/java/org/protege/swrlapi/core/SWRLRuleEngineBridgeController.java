
package org.protege.swrlapi.core;

import java.util.Set;

import org.protege.owl.portability.axioms.OWLAxiomAdapter;

/**
 * This interface provides access methods to retrieve knowledge inferred by a rule engine bridge.
 */
public interface SWRLRuleEngineBridgeController
{
	int getNumberOfInferredOWLAxioms();

	Set<OWLAxiomAdapter> getInferredOWLAxioms();	
}
