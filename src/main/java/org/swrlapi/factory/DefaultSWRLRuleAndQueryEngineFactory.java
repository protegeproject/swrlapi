package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.bridge.SWRLBridge;
import org.swrlapi.bridge.TargetSWRLRuleEngine;
import org.swrlapi.bridge.TargetSWRLRuleEngineCreator;
import org.swrlapi.core.SWRLAPIOWLOntology;
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
    this.ruleEngineManager = SWRLAPIFactory.createSWRLRuleEngineManager();
  }

  @Override public void registerRuleEngine(@NonNull TargetSWRLRuleEngineCreator ruleEngineCreator)
  {
    this.ruleEngineManager.registerRuleEngine(ruleEngineCreator);

    // log.info("Rule engine '" + ruleEngineName + "' registered with the SWRLAPI rule engine manager.");
  }

  @NonNull @Override public SWRLRuleEngine createSWRLRuleEngine(@NonNull OWLOntology ontology,
    @NonNull DefaultPrefixManager prefixManager)
  {
    if (this.ruleEngineManager.hasRegisteredRuleEngines()) {
      Optional<@NonNull String> ruleEngineName = this.ruleEngineManager.getAnyRegisteredRuleEngineName();
      if (ruleEngineName.isPresent())
        return createSWRLRuleEngine(ruleEngineName.get(), ontology, prefixManager);
      else
        throw new NoRegisteredSWRLRuleEnginesException();
    } else
      throw new NoRegisteredSWRLRuleEnginesException();
  }

  @NonNull @Override public SWRLRuleEngine createSWRLRuleEngine(@NonNull String ruleEngineName,
    @NonNull OWLOntology ontology, @NonNull DefaultPrefixManager prefixManager)
  {
    if (this.ruleEngineManager.isRuleEngineRegistered(ruleEngineName)) {
      try {
        SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);
        OWL2RLPersistenceLayer owl2RLPersistenceLayer = SWRLAPIFactory.createOWL2RLPersistenceLayer(ontology);
        SWRLBridge bridge = SWRLAPIFactory.createSWRLBridge(swrlapiOWLOntology, owl2RLPersistenceLayer);
        Optional<TargetSWRLRuleEngineCreator> targetSWRLRuleEngineCreator = this.ruleEngineManager
          .getRegisteredRuleEngineCreator(ruleEngineName);
        if (targetSWRLRuleEngineCreator.isPresent()) {
          TargetSWRLRuleEngine targetSWRLRuleEngine = targetSWRLRuleEngineCreator.get().create(bridge);
          bridge.setTargetSWRLRuleEngine(targetSWRLRuleEngine);

          SWRLRuleEngine ruleEngine = new DefaultSWRLRuleAndQueryEngine(swrlapiOWLOntology, targetSWRLRuleEngine,
            bridge, bridge);
          ruleEngine.importAssertedOWLAxioms();
          return ruleEngine;
        } else
          throw new SWRLRuleEngineException("Error creating rule engine " + ruleEngineName + ". Creator failed.");
      } catch (Throwable e) {
        throw new SWRLRuleEngineException(
          "Error creating rule engine " + ruleEngineName + ". Exception: " + e.getClass().getCanonicalName()
            + ". Message: " + (e.getMessage() != null ? e.getMessage() : ""), e);
      }
    } else
      throw new InvalidSWRLRuleEngineNameException(ruleEngineName);
  }

  @NonNull @Override public SQWRLQueryEngine createSQWRLQueryEngine(@NonNull OWLOntology ontology,
    @NonNull DefaultPrefixManager prefixManager)
  {
    Optional<@NonNull String> ruleEngineName = this.ruleEngineManager.getAnyRegisteredRuleEngineName();
    if (ruleEngineName.isPresent())
      return createSQWRLQueryEngine(ruleEngineName.get(), ontology, prefixManager);
    else
      throw new NoRegisteredSWRLRuleEnginesException();
  }

  @NonNull @Override public SQWRLQueryEngine createSQWRLQueryEngine(@NonNull String queryEngineName,
    @NonNull OWLOntology ontology, @NonNull DefaultPrefixManager prefixManager)
  {
    if (this.ruleEngineManager.isRuleEngineRegistered(queryEngineName)) {
      try {
        SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);
        OWL2RLPersistenceLayer owl2RLPersistenceLayer = SWRLAPIFactory.createOWL2RLPersistenceLayer(ontology);
        SWRLBridge bridge = SWRLAPIFactory.createSWRLBridge(swrlapiOWLOntology, owl2RLPersistenceLayer);
        Optional<TargetSWRLRuleEngineCreator> targetSWRLRuleEngineCreator = this.ruleEngineManager
          .getRegisteredRuleEngineCreator(queryEngineName);
        if (targetSWRLRuleEngineCreator.isPresent()) {
          TargetSWRLRuleEngine targetSWRLRuleEngine = targetSWRLRuleEngineCreator.get().create(bridge);
          bridge.setTargetSWRLRuleEngine(targetSWRLRuleEngine);

          SQWRLQueryEngine queryEngine = new DefaultSWRLRuleAndQueryEngine(swrlapiOWLOntology, targetSWRLRuleEngine,
            bridge, bridge);
          queryEngine.importAssertedOWLAxioms();
          return queryEngine;

        } else
          throw new SWRLRuleEngineException("Error creating query engine " + queryEngineName + ". Creator failed.");
      } catch (Throwable e) {
        throw new SWRLRuleEngineException(
          "Error creating query engine " + queryEngineName + ". Exception: " + e.getClass().getCanonicalName()
            + ". Message: " + (e.getMessage() != null ? e.getMessage() : ""), e);
      }
    } else
      throw new InvalidSWRLRuleEngineNameException(queryEngineName);
  }

  @Override public void tryToRegisterADefaultSWRLRuleEngine()
  {
    Optional<@NonNull TargetSWRLRuleEngineCreator> ruleEngineCreator = getDroolsSWRLRuleEngineCreator();

    if (ruleEngineCreator.isPresent())
      this.ruleEngineManager.registerRuleEngine(ruleEngineCreator.get());
  }

  private Optional<@NonNull TargetSWRLRuleEngineCreator> getDroolsSWRLRuleEngineCreator()
  {
    return createClass("org.swrlapi.drools.core.DroolsSWRLRuleEngineCreator", TargetSWRLRuleEngineCreator.class);
  }

  private <T> Optional<@NonNull T> createClass(@NonNull String className, @NonNull Class<T> interfaceClass)
  {
    try {
      Class<? extends @NonNull T> clazz = Class.forName(className).asSubclass(interfaceClass);
      Optional<@NonNull T> result = createInstance(clazz);
      if (result.isPresent())
        return Optional.of(result.get());
      else
        return Optional.<@NonNull T>empty();
    } catch (ClassNotFoundException e) {
      return Optional.<@NonNull T>empty();
    }
  }

  private <T> Optional<@NonNull T> createInstance(@NonNull Class<? extends @NonNull T> classToCreate)
  {
    try {
      Constructor<? extends @NonNull T> constructor = classToCreate.getDeclaredConstructor();
      return Optional.of(constructor.newInstance());
    } catch (Exception e) {
      return Optional.<@NonNull T>empty();
    }
  }
}
