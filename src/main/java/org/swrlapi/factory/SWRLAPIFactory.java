package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.util.SimpleRenderer;
import org.swrlapi.bridge.SWRLBridge;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.core.IRIResolver;
import org.swrlapi.core.OWLObjectResolver;
import org.swrlapi.core.SWRLAPIBuiltInAtom;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.core.SWRLRuleEngineManager;
import org.swrlapi.core.SWRLRuleRenderer;
import org.swrlapi.exceptions.SWRLAPIException;
import org.swrlapi.exceptions.SWRLBuiltInBridgeException;
import org.swrlapi.exceptions.SWRLRuleEngineException;
import org.swrlapi.factory.resolvers.DefaultOWLObjectResolver;
import org.swrlapi.literal.Literal;
import org.swrlapi.owl2rl.OWL2RLEngine;
import org.swrlapi.owl2rl.OWL2RLPersistenceLayer;
import org.swrlapi.sqwrl.SQWRLQuery;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLQueryRenderer;
import org.swrlapi.sqwrl.SQWRLResultGenerator;
import org.swrlapi.sqwrl.SQWRLResultManager;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.ui.controller.SWRLRuleEngineController;
import org.swrlapi.ui.dialog.SWRLRuleEngineDialogManager;
import org.swrlapi.ui.model.FileBackedSQWRLQueryEngineModel;
import org.swrlapi.ui.model.FileBackedSWRLRuleEngineModel;
import org.swrlapi.ui.model.OWL2RLModel;
import org.swrlapi.ui.model.SQWRLQueryEngineModel;
import org.swrlapi.ui.model.SWRLAutoCompleter;
import org.swrlapi.ui.model.SWRLRuleEngineModel;
import org.swrlapi.ui.model.SWRLRulesAndSQWRLQueriesTableModel;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Optional;

/**
 * Factory for generating some of the core entities defined by the SWRLAPI.
 *
 * @see SWRLRuleAndQueryEngineFactory
 * @see SQWRLResultValueFactory
 * @see SWRLAPIOWLDataFactory
 * @see OWLDatatypeFactory
 */
public class SWRLAPIFactory
{
  @NonNull private static final String SQWRL_ICON_NAME = "SQWRL.gif";
  @NonNull private static final String OWL2RL_ICON_NAME = "OWL2RL.gif";

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
   * @param ontology    An OWL ontology
   * @param iriResolver An IRI resolver
   * @return A SWRL rule renderer
   */
  @NonNull public static SWRLRuleRenderer createSWRLRuleRenderer(@NonNull OWLOntology ontology,
    @NonNull IRIResolver iriResolver, @NonNull OWLObjectRenderer owlObjectRenderer)
  {
    return new DefaultSWRLRuleAndQueryRenderer(ontology, iriResolver, owlObjectRenderer);
  }

  /**
   * @param ontology    An OWL ontology
   * @param iriResolver An IRI resolver
   * @return A SQWRL query renderer
   */
  @NonNull public static SQWRLQueryRenderer createSQWRLQueryRenderer(@NonNull OWLOntology ontology,
    @NonNull IRIResolver iriResolver, @NonNull OWLObjectRenderer owlObjectRenderer)
  {
    return new DefaultSWRLRuleAndQueryRenderer(ontology, iriResolver, owlObjectRenderer);
  }

  /**
   * @return An IRI resolver
   */
  @NonNull public static IRIResolver createIRIResolver()
  {
    return new DefaultIRIResolver();
  }

  /**
   * @return An OWL object renderer
   */
  @NonNull public static OWLObjectRenderer createOWLObjectRenderer()
  {
    return new SimpleRenderer();
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
   * @return A SWRL rule engine manager
   */
  @NonNull public static SWRLRuleEngineManager createSWRLRuleEngineManager()
  {
    return new DefaultSWRLRuleEngineManager();
  }

  /**
   * @param iriResolver An IRI resolver
   * @return A SQWRL result
   */
  public static @NonNull SQWRLResultManager createSQWRLResultManager(@NonNull IRIResolver iriResolver)
  {
    return new DefaultSQWRLResultManager(iriResolver);
  }

  /**
   * @param dataFactory An OWL data factory
   * @return An OWL object resolver
   */
  public static @NonNull OWLObjectResolver createOWLObjectResolver(@NonNull OWLDataFactory dataFactory)
  {
    return new DefaultOWLObjectResolver(dataFactory);
  }

  /**
   * @param ontology An OWL ontology
   * @return An OWL 2 RL persistence layer
   */
  @NonNull public static OWL2RLPersistenceLayer createOWL2RLPersistenceLayer(@NonNull OWLOntology ontology)
  {
    return new DefaultOWL2RLPersistenceLayer(ontology);
  }

  /**
   * @param iriResolver An IRI resolver
   * @return A SWRL built-in argument factory
   */
  @NonNull public static SWRLBuiltInArgumentFactory createSWRLBuiltInArgumentFactory(@NonNull IRIResolver iriResolver)
  {
    return new DefaultSWRLBuiltInArgumentFactory(iriResolver);
  }

  /**
   * @param iriResolver An IRI resolver
   * @return A SQWRL result value factory
   */
  @NonNull public static SQWRLResultValueFactory createSQWRLResultValueFactory(@NonNull IRIResolver iriResolver)
  {
    return new DefaultSQWRLResultValueFactory(iriResolver);
  }

  @NonNull public static SQWRLQuery createSQWRLQuery(@NonNull String queryName,
    @NonNull List<@NonNull SWRLAtom> bodyAtoms, @NonNull List<@NonNull SWRLAtom> headAtoms, boolean active,
    @NonNull String comment, @NonNull LiteralFactory literalFactory, @NonNull IRIResolver iriResolver)
    throws SQWRLException
  {
    return new DefaultSQWRLQuery(queryName, bodyAtoms, headAtoms, active, comment, literalFactory, iriResolver);
  }

  /**
   * @param iriResolver An IRI resolver
   * @return A SWRLAPI-based OWL data factory
   */
  @NonNull public static SWRLAPIOWLDataFactory createSWRLAPIOWLDataFactory(@NonNull IRIResolver iriResolver)
  {
    return new DefaultSWRLAPIOWLDataFactory(iriResolver);
  }

  /**
   * @param swrlRuleEngine A SWRL rule engine
   * @return A SWRL rule and SQWRL query table model
   */
  public static @NonNull SWRLRulesAndSQWRLQueriesTableModel createSWRLRulesAndSQWRLQueriesTableModel(
    @NonNull SWRLRuleEngine swrlRuleEngine)
  {
    return new SWRLRulesAndSQWRLQueriesTableModel(swrlRuleEngine);
  }

  @NonNull public static OWL2RLModel createOWL2RLModel(@NonNull OWL2RLEngine owl2RLEngine)
  {
    return new OWL2RLModel(owl2RLEngine);
  }

  /**
   * @return A SWRLAPI-based literal factory
   */
  @NonNull public static LiteralFactory createLiteralFactory()
  {
    return new DefaultLiteralFactory();
  }

  /**
   * @return A SWRLAPI-based OWL datatype factory
   */
  @NonNull public static OWLDatatypeFactory createOWLDatatypeFactory()
  {
    return new DefaultOWLDatatypeFactory();
  }

  /**
   * @return An OWL literal factory
   */
  @NonNull public static OWLLiteralFactory createOWLLiteralFactory()
  {
    return new DefaultOWLLiteralFactory();
  }

  /**
   * @param iriResolver An IRI resolver
   * @return A SQWRL result generator
   */
  @NonNull public static SQWRLResultGenerator createSQWRLResultGenerator(IRIResolver iriResolver)
  {
    return new DefaultSQWRLResultManager(iriResolver);
  }

  /**
   * @param ruleEngine A SWRL rule engine name
   * @return A SWRL rule engine model
   */
  @NonNull public static SWRLRuleEngineModel createSWRLRuleEngineModel(@NonNull SWRLRuleEngine ruleEngine)
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
  @NonNull public static SQWRLQueryEngineModel createSQWRLQueryEngineModel(@NonNull SQWRLQueryEngine queryEngine)
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
    SQWRLQueryEngine queryEngine = createSQWRLQueryEngine(ontology);

    return new DefaultSQWRLQueryEngineModel(queryEngine);
  }

  /**
   * @param swrlRuleEngineModel A SWRL rule engine model
   * @return A SWRL rule engine controller
   */
  @NonNull public static SWRLRuleEngineController createSWRLRuleEngineController(
    @NonNull SWRLRuleEngineModel swrlRuleEngineModel)
  {
    return new DefaultSWRLRuleEngineController(swrlRuleEngineModel);
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

  /**
   * @return A SQWRL icon
   * @throws SWRLAPIException If an icon cannot be found
   */
  @NonNull public static Icon getSQWRLIcon() throws SWRLAPIException
  {
    URL url = SWRLAPIFactory.class.getResource(SQWRL_ICON_NAME);

    if (url != null)
      return new ImageIcon(url);
    else
      throw new SWRLAPIException("No SQWRL icon found!");
  }

  /**
   * @return An icon for an OWL 2 RL reasoner
   * @throws SWRLAPIException If an icon cannot be found
   */
  @NonNull public static Icon getOWL2RLReasonerIcon() throws SWRLAPIException
  {
    URL url = SWRLAPIFactory.class.getResource(OWL2RL_ICON_NAME);

    if (url != null)
      return new ImageIcon(url);
    else
      throw new SWRLAPIException("No OWL 2 RL icon found!");
  }

  @NonNull public static Literal createLiteral(OWLLiteral owlLiteral) { return new DefaultLiteral(owlLiteral); }

  @NonNull public static SWRLAPIRule createSWRLAPIRule(@NonNull String ruleName,
    @NonNull List<? extends SWRLAtom> bodyAtoms, @NonNull List<? extends SWRLAtom> headAtoms, @NonNull String comment,
    boolean isActive)
  {
    return new DefaultSWRLAPIRule(ruleName, bodyAtoms, headAtoms, comment, isActive);
  }

  @NonNull public static SWRLAPIBuiltInAtom createSWRLAPIBuiltInAtom(@NonNull String ruleName, @NonNull IRI builtInIRI,
    @NonNull String builtInPrefixedName, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
  {
    return new DefaultSWRLAPIBuiltInAtom(ruleName, builtInIRI, builtInPrefixedName, arguments);
  }

  /**
   * Create a {@link org.swrlapi.core.SWRLAPIOWLOntology} from an OWLAPI-based
   * {@link org.semanticweb.owlapi.model.OWLOntology}.
   *
   * @param ontology    An OWLAPI-based ontology
   * @param iriResolver An IRI resolver
   * @return A SWRLAPI-based wrapper of an OWL ontology
   * @throws SQWRLException If a SQWRL error occurs during ontology processing
   */
  @NonNull public static SWRLAPIOWLOntology createSWRLAPIOntology(@NonNull OWLOntology ontology,
    @NonNull IRIResolver iriResolver) throws SQWRLException
  {
    SWRLAPIOWLOntology swrlapiowlOntology = new DefaultSWRLAPIOWLOntology(ontology, iriResolver,
      createOWLObjectRenderer());
    swrlapiowlOntology.processOntology();

    return swrlapiowlOntology;
  }

  /**
   * Create a {@link org.swrlapi.core.SWRLAPIOWLOntology} from an OWLAPI-based
   * {@link org.semanticweb.owlapi.model.OWLOntology}.
   *
   * @param ontology An OWLAPI-based ontology
   * @return A SWRLAPI-based wrapper of an OWL ontology
   * @throws SQWRLException If a SQWRL error occurs during ontology processing
   */
  @NonNull public static SWRLAPIOWLOntology createSWRLAPIOntology(@NonNull OWLOntology ontology) throws SQWRLException
  {
    IRIResolver iriResolver = createIRIResolver();
    OWLObjectRenderer owlObjectRenderer = createOWLObjectRenderer();
    SWRLAPIOWLOntology swrlapiowlOntology = new DefaultSWRLAPIOWLOntology(ontology, iriResolver, owlObjectRenderer);
    swrlapiowlOntology.processOntology();

    return swrlapiowlOntology;
  }

  /**
   * @param swrlapiOWLOntology     A SWRLAPI-based OWL ontology
   * @param owl2RLPersistenceLayer An OWL 2 RL persistence layer
   * @return A SWRL rule engine bridge
   * @throws SWRLBuiltInBridgeException If an error occurs during rule engine bridge creation
   */
  public static @NonNull SWRLBridge createSWRLBridge(@NonNull SWRLAPIOWLOntology swrlapiOWLOntology,
    @NonNull OWL2RLPersistenceLayer owl2RLPersistenceLayer) throws SWRLBuiltInBridgeException
  {
    return new DefaultSWRLBridge(swrlapiOWLOntology, owl2RLPersistenceLayer);
  }

  /**
   * @param swrlapiowlOntology A SWRLAPI-based OWL ontology
   * @return A SWRL auto-completer
   */
  public static @NonNull SWRLAutoCompleter createSWRLAutoCompleter(@NonNull SWRLAPIOWLOntology swrlapiowlOntology)
  {
    return new DefaultSWRLAutoCompleter(swrlapiowlOntology);
  }

  @NonNull private static OWLOntology createOWLOntology(@NonNull OWLOntologyManager ontologyManager, @NonNull File file)
    throws SWRLAPIException
  {
    try {
      return ontologyManager.loadOntologyFromOntologyDocument(file);
    } catch (OWLOntologyCreationException e) {
      throw new SWRLAPIException(
        "Error create OWL ontology from file " + file.getAbsolutePath() + ": " + (e.getMessage() != null ?
          e.getMessage() :
          ""));
    }
  }
}
