package org.protege.swrlapi.core;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * This interface provides access methods to retrieve knowledge inferred by a rule engine bridge.
 */
public interface SWRLRuleEngineBridgeController
{
	int getNumberOfInferredOWLAxioms();

	Set<OWLAxiom> getInferredOWLAxioms();
}
