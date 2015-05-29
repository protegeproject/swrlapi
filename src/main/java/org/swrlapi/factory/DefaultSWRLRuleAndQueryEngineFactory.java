package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLOntology;
import org.swrlapi.bridge.SWRLBridge;
import org.swrlapi.bridge.TargetSWRLRuleEngine;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.core.SWRLRuleEngineManager;
import org.swrlapi.bridge.TargetSWRLRuleEngineCreator;
import org.swrlapi.exceptions.InvalidSWRLRuleEngineNameException;
import org.swrlapi.exceptions.NoRegisteredSWRLRuleEnginesException;
import org.swrlapi.exceptions.SWRLRuleEngineException;
import org.swrlapi.owl2rl.OWL2RLPersistenceLayer;
import org.swrlapi.sqwrl.SQWRLQueryEngine;

import java.lang.reflect.Constructor;
import java.util.Optional;

public class DefaultSWRLRuleAndQueryEngineFactory implements SWRLRuleAndQueryEngineFactory
{
	@NonNull private final SWRLRuleEngineManager ruleEngineManager;

	public DefaultSWRLRuleAndQueryEngineFactory()
	{
		this.ruleEngineManager = SWRLAPIFactory.createSWRLRuleEngineManager();

		tryToRegisterDroolsSWRLRuleEngine();
	}

	@Override public void registerRuleEngine(@NonNull TargetSWRLRuleEngineCreator ruleEngineCreator)
	{
		this.ruleEngineManager.registerRuleEngine(ruleEngineCreator);

		// log.info("Rule engine '" + ruleEngineName + "' registered with the SWRLAPI rule engine manager.");
	}

	@NonNull @Override public SWRLRuleEngine createSWRLRuleEngine(@NonNull OWLOntology ontology)
	{
		if (this.ruleEngineManager.hasRegisteredRuleEngines()) {
			Optional<String> ruleEngineName = this.ruleEngineManager.getAnyRegisteredRuleEngineName();
			if (ruleEngineName.isPresent())
				return createSWRLRuleEngine(ruleEngineName.get(), ontology);
			else
				throw new NoRegisteredSWRLRuleEnginesException();
		} else
			throw new NoRegisteredSWRLRuleEnginesException();
	}

	@NonNull @Override public SWRLRuleEngine createSWRLRuleEngine(@NonNull String ruleEngineName,
			@NonNull OWLOntology ontology)
	{
		if (this.ruleEngineManager.isRuleEngineRegistered(ruleEngineName)) {
			try {
				SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology);
				OWL2RLPersistenceLayer owl2RLPersistenceLayer = SWRLAPIFactory.createOWL2RLPersistenceLayer(ontology);
				SWRLBridge bridge = SWRLAPIFactory.createSWRLBridge(swrlapiOWLOntology, owl2RLPersistenceLayer);
				TargetSWRLRuleEngine targetSWRLRuleEngine = this.ruleEngineManager
						.getRegisteredRuleEngineCreator(ruleEngineName).create(bridge);

				bridge.setTargetSWRLRuleEngine(targetSWRLRuleEngine);

				return new DefaultSWRLRuleAndQueryEngine(swrlapiOWLOntology, targetSWRLRuleEngine, bridge, bridge);
			} catch (Throwable e) {
				throw new SWRLRuleEngineException(
						"Error creating rule engine " + ruleEngineName + ". Exception: " + e.getClass().getCanonicalName()
								+ ". Message: " + e.getMessage(), e);
			}
		} else
			throw new InvalidSWRLRuleEngineNameException(ruleEngineName);
	}

	@NonNull @Override public SQWRLQueryEngine createSQWRLQueryEngine(@NonNull OWLOntology owlOntology)
	{
		Optional<String> ruleEngineName = this.ruleEngineManager.getAnyRegisteredRuleEngineName();
		if (ruleEngineName.isPresent())
			return createSQWRLQueryEngine(ruleEngineName.get(), owlOntology);
		else
			throw new NoRegisteredSWRLRuleEnginesException();
	}

	@NonNull @Override public SQWRLQueryEngine createSQWRLQueryEngine(@NonNull String queryEngineName,
			@NonNull OWLOntology ontology)
	{
		if (this.ruleEngineManager.isRuleEngineRegistered(queryEngineName)) {
			try {
				SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology);
				OWL2RLPersistenceLayer owl2RLPersistenceLayer = SWRLAPIFactory.createOWL2RLPersistenceLayer(ontology);
				SWRLBridge bridge = SWRLAPIFactory.createSWRLBridge(swrlapiOWLOntology, owl2RLPersistenceLayer);
				TargetSWRLRuleEngine targetSWRLRuleEngine = this.ruleEngineManager
						.getRegisteredRuleEngineCreator(queryEngineName).create(bridge);

				bridge.setTargetSWRLRuleEngine(targetSWRLRuleEngine);

				return new DefaultSWRLRuleAndQueryEngine(swrlapiOWLOntology, targetSWRLRuleEngine, bridge, bridge);
			} catch (Throwable e) {
				throw new SWRLRuleEngineException(
						"Error creating query engine " + queryEngineName + ". Exception: " + e.getClass().getCanonicalName()
								+ ". Message: " + e.getMessage(), e);
			}
		} else
			throw new InvalidSWRLRuleEngineNameException(queryEngineName);
	}

	private void tryToRegisterDroolsSWRLRuleEngine()
	{
		Optional<TargetSWRLRuleEngineCreator> ruleEngineCreator = getDroolsSWRLRuleEngineCreator();

		if (ruleEngineCreator.isPresent())
			this.ruleEngineManager.registerRuleEngine(ruleEngineCreator.get());
	}

	private Optional<TargetSWRLRuleEngineCreator> getDroolsSWRLRuleEngineCreator()
	{
		return createClass("org.swrlapi.drools.core.DroolsSWRLRuleEngineCreator", TargetSWRLRuleEngineCreator.class);
	}

	private <T> Optional<T> createClass(@NonNull String className, @NonNull Class<T> interfaceClass)
	{
		try {
			Class<? extends T> clazz = Class.forName(className).asSubclass(interfaceClass);
			Optional<T> result = createInstance(clazz);
			if (result.isPresent())
				return Optional.of(result.get());
			else
				return Optional.empty();
		} catch (ClassNotFoundException e) {
			return Optional.empty();
		}
	}

	private <T> Optional<T> createInstance(@NonNull Class<? extends T> classToCreate)
	{
		try {
			Constructor<? extends T> constructor = classToCreate.getDeclaredConstructor();
			return Optional.of(constructor.newInstance());
		} catch (Exception e) {
			return Optional.empty();
		}
	}
}
