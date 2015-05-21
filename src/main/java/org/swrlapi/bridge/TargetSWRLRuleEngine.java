package org.swrlapi.bridge;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;
import org.swrlapi.owl2rl.OWL2RLEngine;
import org.swrlapi.sqwrl.SQWRLQuery;

import javax.swing.*;

/**
 * This interface defines the methods that must be provided by an implementation of a SWRLAPI-based SWRL rule engine. A
 * {@link org.swrlapi.core.SWRLRuleEngine} uses an implementation of this interface to interact with an underlying rule
 * engine.
 * <P>
 * A SWRL rule engine must also implement an OWL 2 RL reasoner.
 * <P>
 * A target rule engine can communicate with the bridge using the {@link SWRLRuleEngineBridge} interface.
 *
 * @see org.swrlapi.core.SWRLRuleEngine
 * @see org.swrlapi.owl2rl.OWL2RLEngine
 * @see org.swrlapi.bridge.SWRLRuleEngineBridge
 * @see org.semanticweb.owlapi.model.OWLAxiom
 * @see org.swrlapi.sqwrl.SQWRLQuery
 */
public interface TargetSWRLRuleEngine
{
  /**
   * Define a target rule engine representation of an OWL axiom. Note that SWRL rules are a type of OWL axiom.
   * 
   * @param axiom The OWL axiom to define
   * @throws TargetSWRLRuleEngineException If an error occurs during definition
   */
  void defineOWLAxiom(OWLAxiom axiom) throws TargetSWRLRuleEngineException;

  /**
   * Define a target rule engine representation of a SQWRL query.
   * 
   * @param query A SQWRL query to define
   * @throws TargetSWRLRuleEngineException If an error occurs in the target rule engine
   */
  void defineSQWRLQuery(SQWRLQuery query) throws TargetSWRLRuleEngineException;

  /**
   * Run the rule engine.
   * 
   * @throws TargetSWRLRuleEngineException If an error occurs in the target rule engine
   */
  void runRuleEngine() throws TargetSWRLRuleEngineException;

  /**
   * Reset the rule engine.
   * 
   * @throws TargetSWRLRuleEngineException If an error occurs in the target rule engine
   */
  void resetRuleEngine() throws TargetSWRLRuleEngineException;

  /**
   * Return the name of the target rule engine.
   *
   * @return The name of the target rule engine
   */
  @NonNull String getName();

  /**
   * Return version information of the target rule engine.
   *
   * @return The version of the target rule engine
   */
  @NonNull String getVersion();

  /**
   *
   * @return An icon for the rule engine
   */
  Icon getSWRLRuleEngineIcon();
  /**
   * A target rule engine must also define an OWL reasoner implementation.
   *
   * @return An OWL reasoner
   */
  @NonNull OWLReasoner getOWLReasoner();

  /**
   * Get the underlying OWL 2 RL reasoner provided by the rule engine.
   *
   * @return The underlying OWL 2 RL-based rule engine
   */
  @NonNull OWL2RLEngine getOWL2RLEngine();
}
