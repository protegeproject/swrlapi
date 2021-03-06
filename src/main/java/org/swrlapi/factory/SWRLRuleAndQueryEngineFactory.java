package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLOntology;
import org.swrlapi.bridge.TargetSWRLRuleEngineCreator;
import org.swrlapi.core.IRIResolver;
import org.swrlapi.core.SWRLRuleEngine;
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
   * @param ontology    A OWL ontology
   * @param iriResolver An IRI resolver
   * @return A SWRL rule engine
   * @throws SWRLRuleEngineException If an error occurs during creation
   */
  @NonNull SWRLRuleEngine createSWRLRuleEngine(@NonNull OWLOntology ontology, @NonNull IRIResolver iriResolver)
    throws SWRLRuleEngineException;

  /**
   * @param ruleEngineName The name of a rule engine
   * @param ontology       An OWL ontology
   * @param iriResolver    An IRI resolver
   * @return A SWRL rule engine
   * @throws SWRLRuleEngineException If an error occurs during creation
   */
  @NonNull SWRLRuleEngine createSWRLRuleEngine(@NonNull String ruleEngineName, @NonNull OWLOntology ontology,
    @NonNull IRIResolver iriResolver) throws SWRLRuleEngineException;

  /**
   * @param ontology    An OWL ontology
   * @param iriResolver An IRI resolver
   * @return A SQWRL query engine
   * @throws SWRLRuleEngineException If an error occurs during creation
   */
  @NonNull SQWRLQueryEngine createSQWRLQueryEngine(@NonNull OWLOntology ontology, @NonNull IRIResolver iriResolver)
    throws SWRLRuleEngineException;

  /**
   * @param ruleEngineName The name of a rule engine
   * @param ontology       An OWL ontology
   * @param iriResolver    An IRI resolver
   * @return A SQWRL query engine
   * @throws SWRLRuleEngineException If an error occurs during creation
   */
  @NonNull SQWRLQueryEngine createSQWRLQueryEngine(@NonNull String ruleEngineName, @NonNull OWLOntology ontology,
    @NonNull IRIResolver iriResolver) throws SWRLRuleEngineException;

  void tryToRegisterADefaultSWRLRuleEngine();
}
