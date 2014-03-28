package org.swrlapi.core;

import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.swrlapi.exceptions.InvalidSWRLRuleEngineNameException;
import org.swrlapi.exceptions.NoRegisteredRuleEnginesException;
import org.swrlapi.exceptions.SWRLRuleEngineException;
import org.swrlapi.ext.SWRLAPIOWLOntology;
import org.swrlapi.owl2rl.DefaultOWL2RLPersistenceLayer;
import org.swrlapi.owl2rl.OWL2RLPersistenceLayer;

/**
 * Factory to create instances of SWRL rule engines in the SWRLAPI
 */
public class SWRLRuleEngineFactory
{
	private static SWRLRuleEngineManager ruleEngineManager;

	static {
		ruleEngineManager = new DefaultSWRLRuleEngineManager();
	}

	/**
	 * Register a rule engine. The {@link SWRLRuleEngineManager.TargetSWRLRuleEngineCreator} interface specifies a
	 * {@link SWRLRuleEngineManager.TargetSWRLRuleEngineCreator#create(org.protege.swrlapi.core.SWRLRuleEngineBridge)}
	 * method that returns an implementation of a {@link TargetRuleEngine}.
	 */
	public static void registerRuleEngine(String ruleEngineName,
			SWRLRuleEngineManager.TargetSWRLRuleEngineCreator ruleEngineCreator)
	{
		ruleEngineManager.registerRuleEngine(ruleEngineName, ruleEngineCreator);

		// log.info("Rule engine '" + ruleEngineName + "' registered with the SWRLTab rule engine manager.");
	}

	/**
	 * Create an instance of a rule engine. If no engine is registered, a {@link NoRegisteredRuleEnginesException} is
	 * generated. If no default engine is specified in the {@link SWRLNames#DEFAULT_RULE_ENGINE} property in the
	 * protege.properties file then the Drools rule engine is returned (if it is registered).
	 */
	public static SWRLRuleEngine create(SWRLAPIOWLOntology swrlapiOWLOntology, OWLOntologyManager owlOntologyManager)
			throws SWRLRuleEngineException
	{
		if (ruleEngineManager.hasRegisteredRuleEngines()) {
			String defaultRuleEngineName = "Drools"; // TODO
			if (ruleEngineManager.isRuleEngineRegistered(defaultRuleEngineName))
				return create(ruleEngineManager.getAnyRegisteredRuleEngineName(), owlOntologyManager, swrlapiOWLOntology);
			else
				return create(defaultRuleEngineName, owlOntologyManager, swrlapiOWLOntology);
		} else
			throw new NoRegisteredRuleEnginesException();
	}

	/**
	 * Create a SWRL rule engine. Throws an {@link InvalidSWRLRuleEngineNameException} if an engine of this name is not
	 * registered.
	 */
	public static SWRLRuleEngine create(String ruleEngineName, OWLOntologyManager owlOntologyManager,
			SWRLAPIOWLOntology swrlapiOWLOntology) throws SWRLRuleEngineException
	{
		if (ruleEngineManager.isRuleEngineRegistered(ruleEngineName)) {
			try {
				OWL2RLPersistenceLayer owl2RLPersistenceLayer = new DefaultOWL2RLPersistenceLayer(swrlapiOWLOntology);
				DefaultSWRLBridge bridge = new DefaultSWRLBridge(swrlapiOWLOntology, owl2RLPersistenceLayer);
				TargetRuleEngine targetRuleEngine = ruleEngineManager.getRegisteredRuleEngineCreator(ruleEngineName).create(
						bridge);

				bridge.setTargetRuleEngine(targetRuleEngine);

				return new DefaultSWRLRuleEngine(owlOntologyManager, swrlapiOWLOntology, targetRuleEngine, bridge, bridge);
			} catch (Throwable e) {
				throw new SWRLRuleEngineException("Error creating rule engine " + ruleEngineName + ". Exception: "
						+ e.getClass().getCanonicalName() + ". Message: " + e.getMessage(), e);
			}
		} else
			throw new InvalidSWRLRuleEngineNameException(ruleEngineName);
	}
}

// public DefaultSWRLRuleEngine(OWLOntologyManager ontologyManager, SWRLAPIOWLOntology swrlapiOWLOntology,
// TargetRuleEngine targetRuleEngine, SWRLRuleEngineBridgeController ruleEngineBridgeController,
// SWRLBuiltInBridgeController builtInBridgeController) throws SWRLRuleEngineException
