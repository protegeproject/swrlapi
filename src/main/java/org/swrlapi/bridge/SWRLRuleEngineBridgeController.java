package org.swrlapi.bridge;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * This interface provides access methods to retrieve knowledge inferred by a target rule engine implementation after it
 * executes.
 */
public interface SWRLRuleEngineBridgeController
{
	int getNumberOfInferredOWLAxioms();

	Set<OWLAxiom> getInferredOWLAxioms();
}
