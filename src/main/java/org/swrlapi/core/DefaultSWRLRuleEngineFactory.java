package org.swrlapi.core;

import org.swrlapi.exceptions.InvalidSWRLRuleEngineNameException;
import org.swrlapi.exceptions.NoRegisteredRuleEnginesException;
import org.swrlapi.exceptions.SWRLRuleEngineException;
import org.swrlapi.ext.SWRLAPIOWLOntology;
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
	 * returns an implementation of a {@link TargetRuleEngine}.
	 */
	@Override
	public void registerRuleEngine(SWRLRuleEngineManager.TargetSWRLRuleEngineCreator ruleEngineCreator)
	{
		this.ruleEngineManager.registerRuleEngine(ruleEngineCreator.getRuleEngineName(), ruleEngineCreator);

		// log.info("Rule engine '" + ruleEngineName + "' registered with the SWRLTab rule engine manager.");
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
	 * Create an instance of a rule engine. If no engine is registered, a {@link NoRegisteredRuleEnginesException} is
	 * generated. If no default engine is specified in the {@link SWRLNames#DEFAULT_RULE_ENGINE} property in the
	 * protege.properties file then the Drools rule engine is returned (if it is registered).
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
	 * Create a SWRL rule engine. Throws an {@link InvalidSWRLRuleEngineNameException} if an engine of this name is not
	 * registered.
	 */
	@Override
	public SWRLRuleEngine createSWRLRuleEngine(String ruleEngineName, SWRLAPIOWLOntology swrlapiOWLOntology)
			throws SWRLRuleEngineException
	{
		if (this.ruleEngineManager.isRuleEngineRegistered(ruleEngineName)) {
			try {
				OWL2RLPersistenceLayer owl2RLPersistenceLayer = new DefaultOWL2RLPersistenceLayer(swrlapiOWLOntology);
				DefaultSWRLBridge bridge = new DefaultSWRLBridge(swrlapiOWLOntology, owl2RLPersistenceLayer);
				TargetRuleEngine targetRuleEngine = ruleEngineManager.getRegisteredRuleEngineCreator(ruleEngineName).create(
						bridge);

				bridge.setTargetRuleEngine(targetRuleEngine);

				return new DefaultSWRLRuleEngine(swrlapiOWLOntology, targetRuleEngine, bridge, bridge);
			} catch (Throwable e) {
				throw new SWRLRuleEngineException("Error creating rule engine " + ruleEngineName + ". Exception: "
						+ e.getClass().getCanonicalName() + ". Message: " + e.getMessage(), e);
			}
		} else
			throw new InvalidSWRLRuleEngineNameException(ruleEngineName);
	}
}
