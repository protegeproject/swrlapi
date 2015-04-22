package org.swrlapi.bridge;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * This interface provides access methods to retrieve knowledge inferred by a target rule engine implementation after it
 * executes.
 *
 * @see org.swrlapi.bridge.TargetSWRLRuleEngine
 * @see org.swrlapi.bridge.SWRLRuleEngineBridge
 */
public interface SWRLRuleEngineBridgeController
{
	/**
	 * @return The number of inferred OWL axioms
	 */
	int getNumberOfInferredOWLAxioms();

	/**
	 * @return A set of inferred OWL axioms
	 */
	Set<OWLAxiom> getInferredOWLAxioms();
}
