package org.swrlapi.core.impl;

import org.swrlapi.bridge.TargetSWRLRuleEngine;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLRuleAndQueryEngineFactory;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.core.SWRLRuleEngineManager;
import org.swrlapi.exceptions.InvalidSWRLRuleEngineNameException;
import org.swrlapi.exceptions.NoRegisteredSWRLRuleEnginesException;
import org.swrlapi.exceptions.SWRLRuleEngineException;
import org.swrlapi.owl2rl.DefaultOWL2RLPersistenceLayer;
import org.swrlapi.owl2rl.OWL2RLPersistenceLayer;
import org.swrlapi.sqwrl.SQWRLQueryEngine;

public class DefaultSWRLRuleAndQueryEngineFactory implements SWRLRuleAndQueryEngineFactory
{
	private final SWRLRuleEngineManager ruleEngineManager;

	public DefaultSWRLRuleAndQueryEngineFactory()
	{
		this.ruleEngineManager = new DefaultSWRLRuleEngineManager();
	}

	@Override
	public void registerRuleEngine(SWRLRuleEngineManager.TargetSWRLRuleEngineCreator ruleEngineCreator)
	{
		this.ruleEngineManager.registerRuleEngine(ruleEngineCreator.getRuleEngineName(), ruleEngineCreator);

		// log.info("Rule engine '" + ruleEngineName + "' registered with the SWRLAPI rule engine manager.");
	}

	@Override
	public SWRLRuleEngine createSWRLRuleEngine(SWRLAPIOWLOntology swrlapiOWLOntology)
	{
		if (ruleEngineManager.hasRegisteredRuleEngines()) {
			return createSWRLRuleEngine(ruleEngineManager.getAnyRegisteredRuleEngineName(), swrlapiOWLOntology);
		} else
			throw new NoRegisteredSWRLRuleEnginesException();
	}

	@Override
	public SWRLRuleEngine createSWRLRuleEngine(String ruleEngineName, SWRLAPIOWLOntology swrlapiOWLOntology)
	{
		if (this.ruleEngineManager.isRuleEngineRegistered(ruleEngineName)) {
			try {
				OWL2RLPersistenceLayer owl2RLPersistenceLayer = new DefaultOWL2RLPersistenceLayer(swrlapiOWLOntology);
				DefaultSWRLBridge bridge = new DefaultSWRLBridge(swrlapiOWLOntology, owl2RLPersistenceLayer);
				TargetSWRLRuleEngine targetSWRLRuleEngine = ruleEngineManager.getRegisteredRuleEngineCreator(ruleEngineName)
						.create(bridge);

				bridge.setTargetSWRLRuleEngine(targetSWRLRuleEngine);

				return new DefaultSWRLRuleAndQueryEngine(swrlapiOWLOntology, targetSWRLRuleEngine, bridge, bridge);
			} catch (Throwable e) {
				throw new SWRLRuleEngineException("Error creating rule engine " + ruleEngineName + ". Exception: "
						+ e.getClass().getCanonicalName() + ". Message: " + e.getMessage(), e);
			}
		} else
			throw new InvalidSWRLRuleEngineNameException(ruleEngineName);
	}

	@Override
	public SQWRLQueryEngine createSQWRLQueryEngine(SWRLAPIOWLOntology swrlapiOWLOntology)
	{
		if (ruleEngineManager.hasRegisteredRuleEngines()) {
			return createSQWRLQueryEngine(ruleEngineManager.getAnyRegisteredRuleEngineName(), swrlapiOWLOntology);
		} else
			throw new NoRegisteredSWRLRuleEnginesException();
	}

	@Override
	public SQWRLQueryEngine createSQWRLQueryEngine(String queryEngineName, SWRLAPIOWLOntology swrlapiOWLOntology)
	{
		if (this.ruleEngineManager.isRuleEngineRegistered(queryEngineName)) {
			try {
				OWL2RLPersistenceLayer owl2RLPersistenceLayer = new DefaultOWL2RLPersistenceLayer(swrlapiOWLOntology);
				DefaultSWRLBridge bridge = new DefaultSWRLBridge(swrlapiOWLOntology, owl2RLPersistenceLayer);
				TargetSWRLRuleEngine targetSWRLRuleEngine = ruleEngineManager.getRegisteredRuleEngineCreator(queryEngineName)
						.create(bridge);

				bridge.setTargetSWRLRuleEngine(targetSWRLRuleEngine);

				return new DefaultSWRLRuleAndQueryEngine(swrlapiOWLOntology, targetSWRLRuleEngine, bridge, bridge);
			} catch (Throwable e) {
				throw new SWRLRuleEngineException("Error creating query engine " + queryEngineName + ". Exception: "
						+ e.getClass().getCanonicalName() + ". Message: " + e.getMessage(), e);
			}
		} else
			throw new InvalidSWRLRuleEngineNameException(queryEngineName);
	}
}
