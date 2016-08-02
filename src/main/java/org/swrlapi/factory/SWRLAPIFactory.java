package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLOntology;
import org.swrlapi.core.IRIResolver;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.exceptions.SWRLRuleEngineException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.ui.dialog.SWRLRuleEngineDialogManager;
import org.swrlapi.ui.model.FileBackedSQWRLQueryEngineModel;
import org.swrlapi.ui.model.FileBackedSWRLRuleEngineModel;
import org.swrlapi.ui.model.SQWRLQueryEngineModel;
import org.swrlapi.ui.model.SWRLRuleEngineModel;

import java.io.File;
import java.util.Optional;

/**
 * Factory for generating some of the core entities defined by the SWRLAPI.
 */
public class SWRLAPIFactory
{
  @NonNull private static final SWRLRuleAndQueryEngineFactory swrlRuleAndQueryEngineFactory;

  static {
    swrlRuleAndQueryEngineFactory = new DefaultSWRLRuleAndQueryEngineFactory();
    swrlRuleAndQueryEngineFactory.tryToRegisterADefaultSWRLRuleEngine();
  }

  /**
   * @param ontology An OWL ontology
   * @return A SWRL rule engine
   * @throws SWRLRuleEngineException If an error occurs during rule engine creation
   */
  @NonNull public static SWRLRuleEngine createSWRLRuleEngine(@NonNull OWLOntology ontology)
    throws SWRLRuleEngineException
  {
    IRIResolver iriResolver = createIRIResolver();
    return swrlRuleAndQueryEngineFactory.createSWRLRuleEngine(ontology, iriResolver);
  }

  /**
   * @param ontology An OWL ontology
   * @return A SWRL rule engine
   * @throws SWRLRuleEngineException If an error occurs during rule engine creation
   */
  @NonNull public static SWRLRuleEngine createSWRLRuleEngine(@NonNull OWLOntology ontology,
    @NonNull IRIResolver iriResolver) throws SWRLRuleEngineException
  {
    return swrlRuleAndQueryEngineFactory.createSWRLRuleEngine(ontology, iriResolver);
  }

  /**
   * @param ontology An OWL ontology
   * @return A SQWRL query engine
   * @throws SWRLRuleEngineException If an error occurs during query engine creation
   */
  @NonNull public static SQWRLQueryEngine createSQWRLQueryEngine(@NonNull OWLOntology ontology)
    throws SWRLRuleEngineException
  {
    IRIResolver iriResolver = createIRIResolver();
    return swrlRuleAndQueryEngineFactory.createSQWRLQueryEngine(ontology, iriResolver);
  }

  /**
   * @param ontology    An OWL ontology
   * @param iriResolver An IRI resolver
   * @return A SQWRL query engine
   * @throws SWRLRuleEngineException If an error occurs during query engine creation
   */
  @NonNull public static SQWRLQueryEngine createSQWRLQueryEngine(@NonNull OWLOntology ontology,
    @NonNull IRIResolver iriResolver) throws SWRLRuleEngineException
  {
    return swrlRuleAndQueryEngineFactory.createSQWRLQueryEngine(ontology, iriResolver);
  }

  /**
   * @return An IRI resolver
   */
  @NonNull public static IRIResolver createIRIResolver()
  {
    return new DefaultIRIResolver();
  }

  /**
   * @param defaultPrefix Default prefix
   * @return An IRI resolver
   */
  @NonNull public static IRIResolver createIRIResolver(String defaultPrefix)
  {
    return new DefaultIRIResolver(defaultPrefix);
  }

  /**
   * @param ruleEngine A SWRL rule engine name
   * @return A SWRL rule engine model
   */
  public static @NonNull SWRLRuleEngineModel createSWRLRuleEngineModel(@NonNull SWRLRuleEngine ruleEngine)
  {
    return new DefaultSWRLRuleEngineModel(ruleEngine);
  }

  /**
   * @param ruleEngine A SWRL rule engine
   * @param file       An optional file
   * @return A file-backed SWRL rule engine model
   */
  public static @NonNull FileBackedSWRLRuleEngineModel createFileBackedSWRLRuleEngineModel(
    @NonNull SWRLRuleEngine ruleEngine, Optional<File> file)
  {
    return new DefaultFileBackedSWRLRuleEngineModel(ruleEngine, file);
  }

  /**
   * @param queryEngine A SQWRL query engine
   * @return A SQWRL query engine model
   */
  public static @NonNull SQWRLQueryEngineModel createSQWRLQueryEngineModel(@NonNull SQWRLQueryEngine queryEngine)
  {
    return new DefaultSQWRLQueryEngineModel(queryEngine);
  }

  /**
   * @param queryEngine A SQWRL query engine
   * @param file        An optional file
   * @return A file-backed SQWRL query engine model
   */
  public static @NonNull FileBackedSQWRLQueryEngineModel createFileBackedSQWRLQueryEngineModel(
    @NonNull SQWRLQueryEngine queryEngine, Optional<File> file)
  {
    return new DefaultFileBackedSQWRLQueryEngineModel(queryEngine, file);
  }

  /**
   * @param ontology An OWL ontology
   * @return A SQWRL query engine model
   */
  @NonNull public static SQWRLQueryEngineModel createSQWRLQueryEngineModel(@NonNull OWLOntology ontology)
  {
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    return new DefaultSQWRLQueryEngineModel(queryEngine);
  }

  /**
   * @param swrlRuleEngineModel An SWRL rule engine model
   * @return A SWRL rule engine dialog manager
   */
  public static @NonNull SWRLRuleEngineDialogManager createSWRLRuleEngineDialogManager(
    @NonNull SWRLRuleEngineModel swrlRuleEngineModel)
  {
    return new DefaultSWRLRuleEngineDialogManager(swrlRuleEngineModel);
  }
}
