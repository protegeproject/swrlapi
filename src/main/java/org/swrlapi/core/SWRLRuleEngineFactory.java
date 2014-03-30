package org.swrlapi.core;

import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.swrlapi.exceptions.SWRLRuleEngineException;
import org.swrlapi.ext.SWRLAPIOWLOntology;
import org.swrlapi.sqwrl.SQWRLQueryEngine;

public interface SWRLRuleEngineFactory
{
	void registerRuleEngine(SWRLRuleEngineManager.TargetSWRLRuleEngineCreator ruleEngineCreator);

	SQWRLQueryEngine createSQWRLQueryEngine(OWLOntologyManager owlOntologyManager, SWRLAPIOWLOntology swrlapiOWLOntology)
			throws SWRLRuleEngineException;

	SQWRLQueryEngine createSQWRLQueryEngine(String ruleEngineName, OWLOntologyManager owlOntologyManager,
			SWRLAPIOWLOntology swrlapiOWLOntology) throws SWRLRuleEngineException;

	SWRLRuleEngine createSWRLRuleEngine(OWLOntologyManager owlOntologyManager, SWRLAPIOWLOntology swrlapiOWLOntology)
			throws SWRLRuleEngineException;

	SWRLRuleEngine createSWRLRuleEngine(String ruleEngineName, OWLOntologyManager owlOntologyManager,
			SWRLAPIOWLOntology swrlapiOWLOntology) throws SWRLRuleEngineException;
}
