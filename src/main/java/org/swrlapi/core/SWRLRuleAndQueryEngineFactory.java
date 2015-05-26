package org.swrlapi.core;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLOntology;
import org.swrlapi.exceptions.SWRLRuleEngineException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;

/**
 * Factory to create SWRL rule engines and SQWRL query engines.
 *
 * @see org.swrlapi.core.SWRLRuleEngine
 * @see org.swrlapi.sqwrl.SQWRLQueryEngine
 */
public interface SWRLRuleAndQueryEngineFactory
{
  /**
   * Register a rule engine. The {@link TargetSWRLRuleEngineCreator} interface specifies a
   * {@link TargetSWRLRuleEngineCreator#create(org.swrlapi.bridge.SWRLRuleEngineBridge)} method
   * that returns an implementation of a {@link org.swrlapi.bridge.TargetSWRLRuleEngine}. *
   *
   * @param ruleEngineCreator A rule engine creator
   */
  void registerRuleEngine(@NonNull TargetSWRLRuleEngineCreator ruleEngineCreator);

  /**
   * Create an instance of a rule engine.
   *
   * @param ontology A OWL ontology
   * @return A SWRL rule engine
   * @throws SWRLRuleEngineException If an error occurs during creation
   */
  @NonNull SWRLRuleEngine createSWRLRuleEngine(@NonNull OWLOntology ontology) throws SWRLRuleEngineException;

  /**
   * @param ruleEngineName The name of a rule engine
   * @param ontology       An OWL ontology
   * @return A SWRL rule engine
   * @throws SWRLRuleEngineException If an error occurs during creation
   */
  @NonNull SWRLRuleEngine createSWRLRuleEngine(@NonNull String ruleEngineName, @NonNull OWLOntology ontology)
    throws SWRLRuleEngineException;

  /**
   * @param ontology An OWL ontology
   * @return A SQWRL query engine
   * @throws SWRLRuleEngineException If an error occurs during creation
   */
  @NonNull SQWRLQueryEngine createSQWRLQueryEngine(@NonNull OWLOntology ontology) throws SWRLRuleEngineException;

  /**
   * @param ruleEngineName The name of a rule engine
   * @param ontology       An OWL ontology
   * @return A SQWRL query engine
   * @throws SWRLRuleEngineException If an error occurs during creation
   */
  @NonNull SQWRLQueryEngine createSQWRLQueryEngine(@NonNull String ruleEngineName, @NonNull OWLOntology ontology)
    throws SWRLRuleEngineException;
}
