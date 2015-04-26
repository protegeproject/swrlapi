package org.swrlapi.core;

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
	 * @param swrlapiOWLOntology A SWQRLAPI-based OWL ontology
	 * @return A SWRL rule engine
	 * @throws SWRLRuleEngineException If an error occurs during creation
	 */
	SWRLRuleEngine createSWRLRuleEngine(SWRLAPIOWLOntology swrlapiOWLOntology) throws SWRLRuleEngineException;

	/**
	 * @param ruleEngineName The name of a rule engine
	 * @param swrlapiOWLOntology A SWQRLAPI-based OWL ontology
	 * @return A SWRL rule engine
	 * @throws SWRLRuleEngineException If an error occurs during creation
	 */
	SWRLRuleEngine createSWRLRuleEngine(String ruleEngineName, SWRLAPIOWLOntology swrlapiOWLOntology)
			throws SWRLRuleEngineException;

	/**
	 * @param swrlapiOWLOntology A SWQRLAPI-based OWL ontology
	 * @return A SQWRL query engine
	 * @throws SWRLRuleEngineException If an error occurs during creation
	 */
	SQWRLQueryEngine createSQWRLQueryEngine(SWRLAPIOWLOntology swrlapiOWLOntology) throws SWRLRuleEngineException;

	/**
	 * @param ruleEngineName The name of a rule engine
	 * @param swrlapiOWLOntology A SWQRLAPI-based OWL ontology
	 * @return A SQWRL query engine
	 * @throws SWRLRuleEngineException If an error occurs during creation
	 */
	SQWRLQueryEngine createSQWRLQueryEngine(String ruleEngineName, SWRLAPIOWLOntology swrlapiOWLOntology)
			throws SWRLRuleEngineException;

}
