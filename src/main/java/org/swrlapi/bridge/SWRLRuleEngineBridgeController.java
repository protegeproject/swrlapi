package org.swrlapi.bridge;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLAxiom;

import java.util.Set;

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
  @NonNull Set<OWLAxiom> getInferredOWLAxioms();
}
