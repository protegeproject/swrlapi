package org.swrlapi.core.impl;

import org.swrlapi.bridge.TargetSWRLRuleEngine;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.core.SWRLRuleEngineFactory;
import org.swrlapi.core.SWRLRuleEngineManager;
import org.swrlapi.exceptions.InvalidSWRLRuleEngineNameException;
import org.swrlapi.exceptions.NoRegisteredRuleEnginesException;
import org.swrlapi.exceptions.SWRLRuleEngineException;
import org.swrlapi.owl2rl.DefaultOWL2RLPersistenceLayer;
import org.swrlapi.owl2rl.OWL2RLPersistenceLayer;
import org.swrlapi.sqwrl.SQWRLQueryEngine;

/**
 * Factory to create instances of SWRL rule engines in the SWRLAPI
 */
public class DefaultSWRLRuleEngineFactory implements SWRLRuleEngineFactory
{
	private final SWRLRuleEngineManager ruleEngineManager;

	public DefaultSWRLRuleEngineFactory()
	{
		this.ruleEngineManager = new DefaultSWRLRuleEngineManager();
	}

	/**
	 * Register a rule engine. The {@link SWRLRuleEngineManager.TargetSWRLRuleEngineCreator} interface specifies a
	 * {@link SWRLRuleEngineManager.TargetSWRLRuleEngineCreator#create(org.swrlapi.core.SWRLRuleEngineBridge)} method that
	 * returns an implementation of a {@link org.swrlapi.bridge.TargetSWRLRuleEngine}.
	 */
	@Override
	public void registerRuleEngine(SWRLRuleEngineManager.TargetSWRLRuleEngineCreator ruleEngineCreator)
	{
		this.ruleEngineManager.registerRuleEngine(ruleEngineCreator.getRuleEngineName(), ruleEngineCreator);

		// log.info("Rule engine '" + ruleEngineName + "' registered with the SWRLAPI rule engine manager.");
	}

	@Override
	public SQWRLQueryEngine createSQWRLQueryEngine(SWRLAPIOWLOntology swrlapiOWLOntology) throws SWRLRuleEngineException
	{
		return createSWRLRuleEngine(swrlapiOWLOntology);
	}

	@Override
	public SQWRLQueryEngine createSQWRLQueryEngine(String ruleEngineName, SWRLAPIOWLOntology swrlapiOWLOntology)
			throws SWRLRuleEngineException
	{
		return createSWRLRuleEngine(ruleEngineName, swrlapiOWLOntology);
	}

	/**
	 * Create an instance of a rule engine. If no engine is registered, a {@link org.swrlapi.exceptions.NoRegisteredRuleEnginesException} is
	 * generated.
	 */
	@Override
	public SWRLRuleEngine createSWRLRuleEngine(SWRLAPIOWLOntology swrlapiOWLOntology) throws SWRLRuleEngineException
	{
		if (ruleEngineManager.hasRegisteredRuleEngines()) {
			return createSWRLRuleEngine(ruleEngineManager.getAnyRegisteredRuleEngineName(), swrlapiOWLOntology);
		} else
			throw new NoRegisteredRuleEnginesException();
	}

	/**
	 * Create a SWRL rule engine. Throws an {@link org.swrlapi.exceptions.InvalidSWRLRuleEngineNameException}
	 * if an engine with the supplied name is not registered.
	 */
	@Override
	public SWRLRuleEngine createSWRLRuleEngine(String ruleEngineName, SWRLAPIOWLOntology swrlapiOWLOntology)
			throws SWRLRuleEngineException
	{
		if (this.ruleEngineManager.isRuleEngineRegistered(ruleEngineName)) {
			try {
				OWL2RLPersistenceLayer owl2RLPersistenceLayer = new DefaultOWL2RLPersistenceLayer(swrlapiOWLOntology);
				DefaultSWRLBridge bridge = new DefaultSWRLBridge(swrlapiOWLOntology, owl2RLPersistenceLayer);
				TargetSWRLRuleEngine targetSWRLRuleEngine = ruleEngineManager.getRegisteredRuleEngineCreator(ruleEngineName).create(
						bridge);

				bridge.setTargetSWRLRuleEngine(targetSWRLRuleEngine);

				return new DefaultSWRLRuleEngine(swrlapiOWLOntology, targetSWRLRuleEngine, bridge, bridge);
			} catch (Throwable e) {
				throw new SWRLRuleEngineException("Error creating rule engine " + ruleEngineName + ". Exception: "
						+ e.getClass().getCanonicalName() + ". Message: " + e.getMessage(), e);
			}
		} else
			throw new InvalidSWRLRuleEngineNameException(ruleEngineName);
	}
}
