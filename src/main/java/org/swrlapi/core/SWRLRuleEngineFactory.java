package org.swrlapi.core;

import org.swrlapi.exceptions.SWRLRuleEngineException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;

public interface SWRLRuleEngineFactory
{
	void registerRuleEngine(SWRLRuleEngineManager.TargetSWRLRuleEngineCreator ruleEngineCreator);

	SQWRLQueryEngine createSQWRLQueryEngine(SWRLAPIOWLOntology swrlapiOWLOntology) throws SWRLRuleEngineException;

	SQWRLQueryEngine createSQWRLQueryEngine(String ruleEngineName, SWRLAPIOWLOntology swrlapiOWLOntology)
			throws SWRLRuleEngineException;

	SWRLRuleEngine createSWRLRuleEngine(SWRLAPIOWLOntology swrlapiOWLOntology) throws SWRLRuleEngineException;

	SWRLRuleEngine createSWRLRuleEngine(String ruleEngineName, SWRLAPIOWLOntology swrlapiOWLOntology)
			throws SWRLRuleEngineException;
}
