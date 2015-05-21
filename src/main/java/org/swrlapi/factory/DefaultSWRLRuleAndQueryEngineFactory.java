package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLOntology;
import org.swrlapi.bridge.SWRLBridge;
import org.swrlapi.bridge.TargetSWRLRuleEngine;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLRuleAndQueryEngineFactory;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.core.SWRLRuleEngineManager;
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
    this.ruleEngineManager = SWRLAPIFactory.getSWRLRuleEngineManager();

    tryToRegisterDroolsSWRLRuleEngine();
  }

  @Override
  public void registerRuleEngine(SWRLRuleEngineManager.TargetSWRLRuleEngineCreator ruleEngineCreator)
  {
    this.ruleEngineManager.registerRuleEngine(ruleEngineCreator);

    // log.info("Rule engine '" + ruleEngineName + "' registered with the SWRLAPI rule engine manager.");
  }

  @NonNull @Override
  public SWRLRuleEngine createSWRLRuleEngine(@NonNull OWLOntology owlOntology)
  {
    if (this.ruleEngineManager.hasRegisteredRuleEngines()) {
      Optional<String> ruleEngineName = this.ruleEngineManager.getAnyRegisteredRuleEngineName();
      if (ruleEngineName.isPresent())
        return createSWRLRuleEngine(ruleEngineName.get(), owlOntology);
      else
        throw new NoRegisteredSWRLRuleEnginesException();
    } else
      throw new NoRegisteredSWRLRuleEnginesException();
  }

  @NonNull @Override
  public SWRLRuleEngine createSWRLRuleEngine(@NonNull String ruleEngineName, @NonNull OWLOntology owlOntology)
  {
    if (this.ruleEngineManager.isRuleEngineRegistered(ruleEngineName)) {
      try {
        SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(owlOntology);
        OWL2RLPersistenceLayer owl2RLPersistenceLayer = SWRLAPIFactory.getOWL2RLPersistenceLayer(swrlapiOWLOntology);
        SWRLBridge bridge = SWRLAPIFactory.getSWRLBridge(swrlapiOWLOntology, owl2RLPersistenceLayer);
        TargetSWRLRuleEngine targetSWRLRuleEngine = this.ruleEngineManager.getRegisteredRuleEngineCreator(
            ruleEngineName).create(bridge);

        bridge.setTargetSWRLRuleEngine(targetSWRLRuleEngine);

        return SWRLAPIFactory.getSWRLRuleEngine(swrlapiOWLOntology, targetSWRLRuleEngine, bridge, bridge);
      } catch (Throwable e) {
        throw new SWRLRuleEngineException("Error creating rule engine " + ruleEngineName + ". Exception: "
            + e.getClass().getCanonicalName() + ". Message: " + e.getMessage(), e);
      }
    } else
      throw new InvalidSWRLRuleEngineNameException(ruleEngineName);
  }

  @NonNull @Override
  public SQWRLQueryEngine createSQWRLQueryEngine(@NonNull OWLOntology owlOntology)
  {
    Optional<String> ruleEngineName = this.ruleEngineManager.getAnyRegisteredRuleEngineName();
    if (ruleEngineName.isPresent())
      return createSQWRLQueryEngine(ruleEngineName.get(), owlOntology);
    else
      throw new NoRegisteredSWRLRuleEnginesException();
  }

  @NonNull @Override
  public SQWRLQueryEngine createSQWRLQueryEngine(@NonNull String queryEngineName, @NonNull OWLOntology owlOntology)
  {
    if (this.ruleEngineManager.isRuleEngineRegistered(queryEngineName)) {
      try {
        SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(owlOntology);
        OWL2RLPersistenceLayer owl2RLPersistenceLayer = SWRLAPIFactory.getOWL2RLPersistenceLayer(swrlapiOWLOntology);
        SWRLBridge bridge = SWRLAPIFactory.getSWRLBridge(swrlapiOWLOntology, owl2RLPersistenceLayer);
        TargetSWRLRuleEngine targetSWRLRuleEngine = this.ruleEngineManager.getRegisteredRuleEngineCreator(
            queryEngineName).create(bridge);

        bridge.setTargetSWRLRuleEngine(targetSWRLRuleEngine);

        return SWRLAPIFactory.getSQWRLQueryEngine(swrlapiOWLOntology, targetSWRLRuleEngine, bridge, bridge);
      } catch (Throwable e) {
        throw new SWRLRuleEngineException("Error creating query engine " + queryEngineName + ". Exception: "
            + e.getClass().getCanonicalName() + ". Message: " + e.getMessage(), e);
      }
    } else
      throw new InvalidSWRLRuleEngineNameException(queryEngineName);
  }

  private void tryToRegisterDroolsSWRLRuleEngine()
  {
    Optional<SWRLRuleEngineManager.TargetSWRLRuleEngineCreator> ruleEngineCreator = getDroolsSWRLRuleEngineCreator();

    if (ruleEngineCreator.isPresent())
      this.ruleEngineManager.registerRuleEngine(ruleEngineCreator.get());
  }

  private Optional<SWRLRuleEngineManager.TargetSWRLRuleEngineCreator> getDroolsSWRLRuleEngineCreator()
  {
    return createClass("org.swrlapi.drools.core.DroolsSWRLRuleEngineCreator",
        SWRLRuleEngineManager.TargetSWRLRuleEngineCreator.class);
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
