package org.swrlapi.core;

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
	 * Register a rule engine. The {@link SWRLRuleEngineManager.TargetSWRLRuleEngineCreator} interface specifies a
	 * {@link SWRLRuleEngineManager.TargetSWRLRuleEngineCreator#create(org.swrlapi.bridge.SWRLRuleEngineBridge)} method
	 * that returns an implementation of a {@link org.swrlapi.bridge.TargetSWRLRuleEngine}. *
	 * 
	 * @param ruleEngineCreator A rule engine creator
	 */
	void registerRuleEngine(SWRLRuleEngineManager.TargetSWRLRuleEngineCreator ruleEngineCreator);

	/**
	 * Create an instance of a rule engine.
	 * 
	 * @param ontology A OWL ontology
	 * @return A SWRL rule engine
	 * @throws SWRLRuleEngineException If an error occurs during creation
	 */
	SWRLRuleEngine createSWRLRuleEngine(OWLOntology ontology) throws SWRLRuleEngineException;

	/**
	 * @param ruleEngineName The name of a rule engine
	 * @param ontology An OWL ontology
	 * @return A SWRL rule engine
	 * @throws SWRLRuleEngineException If an error occurs during creation
	 */
	SWRLRuleEngine createSWRLRuleEngine(String ruleEngineName, OWLOntology ontology) throws SWRLRuleEngineException;

	/**
	 * @param ontology An OWL ontology
	 * @return A SQWRL query engine
	 * @throws SWRLRuleEngineException If an error occurs during creation
	 */
	SQWRLQueryEngine createSQWRLQueryEngine(OWLOntology ontology) throws SWRLRuleEngineException;

	/**
	 * @param ruleEngineName The name of a rule engine
	 * @param ontology An OWL ontology
	 * @return A SQWRL query engine
	 * @throws SWRLRuleEngineException If an error occurs during creation
	 */
	SQWRLQueryEngine createSQWRLQueryEngine(String ruleEngineName, OWLOntology ontology) throws SWRLRuleEngineException;
}
