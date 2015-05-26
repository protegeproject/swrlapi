package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.PrefixDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.SimpleIRIMapper;
import org.swrlapi.bridge.SWRLBridge;
import org.swrlapi.bridge.SWRLRuleEngineBridgeController;
import org.swrlapi.bridge.TargetSWRLRuleEngine;
import org.swrlapi.builtins.SWRLBuiltInBridgeController;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentFactory;
import org.swrlapi.core.Literal;
import org.swrlapi.core.LiteralFactory;
import org.swrlapi.core.OWLDatatypeFactory;
import org.swrlapi.core.OWLLiteralFactory;
import org.swrlapi.core.SQWRLQueryRenderer;
import org.swrlapi.core.SWRLAPIBuiltInAtom;
import org.swrlapi.core.SWRLAPIOWLDataFactory;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLAPIOntologyProcessor;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.core.SWRLRuleAndQueryEngineFactory;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.core.SWRLRuleEngineManager;
import org.swrlapi.core.SWRLRuleRenderer;
import org.swrlapi.core.resolvers.IRIResolver;
import org.swrlapi.exceptions.SWRLAPIException;
import org.swrlapi.exceptions.SWRLBuiltInBridgeException;
import org.swrlapi.exceptions.SWRLRuleEngineException;
import org.swrlapi.owl2rl.OWL2RLPersistenceLayer;
import org.swrlapi.sqwrl.SQWRLQuery;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResultGenerator;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLResultValueFactory;
import org.swrlapi.ui.controller.SWRLRuleEngineController;
import org.swrlapi.ui.dialog.SWRLAPIDialogManager;
import org.swrlapi.ui.model.FileBackedOWLOntologyModel;
import org.swrlapi.ui.model.SQWRLQueryEngineModel;
import org.swrlapi.ui.model.SWRLAutoCompleter;
import org.swrlapi.ui.model.SWRLRuleEngineModel;
import org.swrlapi.ui.model.SWRLRulesTableModel;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Factory for generating some of the core entities defined by the SWRLAPI.
 *
 * @see org.swrlapi.core.SWRLAPIOWLOntology
 * @see org.swrlapi.core.SWRLAPIOntologyProcessor
 * @see org.swrlapi.core.SWRLRuleAndQueryEngineFactory
 * @see org.swrlapi.sqwrl.values.SQWRLResultValueFactory
 * @see org.swrlapi.core.SWRLAPIOWLDataFactory
 * @see org.swrlapi.core.OWLDatatypeFactory
 */
public class SWRLAPIFactory
{
  @NonNull private static final String SQWRL_ICON_NAME = "SQWRL.gif";
  @NonNull private static final String OWL2RL_ICON_NAME = "OWL2RL.gif";

  @NonNull private static final SWRLRuleAndQueryEngineFactory swrlRuleAndQueryEngineFactory = new DefaultSWRLRuleAndQueryEngineFactory();

  /**
   * @param ontology An OWL ontology
   * @return A SWRL rule engine
   * @throws SWRLRuleEngineException If an error occurs during rule engine creation
   */
  @NonNull public static SWRLRuleEngine createSWRLRuleEngine(@NonNull OWLOntology ontology)
    throws SWRLRuleEngineException
  {
    return swrlRuleAndQueryEngineFactory.createSWRLRuleEngine(ontology);
  }

  /**
   * @param ontology An OWL ontology
   * @return A SQWRL query engine
   * @throws SWRLRuleEngineException If an error occurs during query engine creation
   */
  @NonNull public static SQWRLQueryEngine createSQWRLQueryEngine(@NonNull OWLOntology ontology)
    throws SWRLRuleEngineException
  {
    return swrlRuleAndQueryEngineFactory.createSQWRLQueryEngine(ontology);
  }

  /**
   * Create an empty {@link org.swrlapi.core.SWRLAPIOWLOntology}.
   *
   * @return A SWRLAPI-based wrapper of an OWL ontology
   */
  @NonNull public static SWRLAPIOWLOntology createSWRLAPIOntology()
  {
    try {
      OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
      OWLOntology ontology = ontologyManager.createOntology();
      DefaultPrefixManager prefixManager = new DefaultPrefixManager();

      addDefaultPrefixes(ontology, prefixManager);
      addSWRLAPIBuiltInOntologies(ontologyManager);

      return createSWRLAPIOntology(ontology, prefixManager);
    } catch (OWLOntologyCreationException e) {
      throw new SWRLAPIException("Error creating OWL ontology", e);
    }
  }

  /**
   * @param ontology An OWLAPI-based ontology
   * @return A SWRLAPI-based wrapper of an OWL ontology
   */
  @NonNull public static SWRLAPIOWLOntology createSWRLAPIOntology(@NonNull OWLOntology ontology)
  {
    DefaultPrefixManager prefixManager = new DefaultPrefixManager();

    addDefaultPrefixes(ontology, prefixManager);
    addSWRLAPIBuiltInOntologies(ontology.getOWLOntologyManager());

    return new DefaultSWRLAPIOWLOntology(ontology, prefixManager);
  }

  /**
   * Create a {@link org.swrlapi.core.SWRLAPIOWLOntology} from an OWLAPI-based
   * {@link org.semanticweb.owlapi.model.OWLOntology}.
   *
   * @param ontology      An OWLAPI-based ontology
   * @param prefixManager A prefix manager
   * @return A SWRLAPI-based wrapper of an OWL ontology
   */
  @NonNull public static SWRLAPIOWLOntology createSWRLAPIOntology(@NonNull OWLOntology ontology,
    @NonNull DefaultPrefixManager prefixManager)
  {
    if (ontology == null)
      throw new SWRLAPIException("supplied OWL ontology is null");

    if (prefixManager == null)
      throw new SWRLAPIException("supplied prefix manager is null");

    addDefaultPrefixes(ontology, prefixManager);
    addSWRLAPIBuiltInOntologies(ontology.getOWLOntologyManager());

    return new DefaultSWRLAPIOWLOntology(ontology, prefixManager);
  }

  /**
   * Create a {@link org.swrlapi.core.SWRLAPIOWLOntology} from a file.
   *
   * @param owlFile A file containing an OWL ontology
   * @return A SWRLAPI-based wrapper of an OWL ontology
   */
  @NonNull public static SWRLAPIOWLOntology createSWRLAPIOntology(@NonNull File owlFile)
  {
    if (owlFile == null)
      throw new SWRLAPIException("supplied OWL file is null");

    OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
    OWLOntology ontology = createOWLOntology(ontologyManager, owlFile);

    DefaultPrefixManager prefixManager = new DefaultPrefixManager();

    addDefaultPrefixes(ontology, prefixManager);
    addSWRLAPIBuiltInOntologies(ontologyManager);

    return createSWRLAPIOntology(ontology, prefixManager);
  }

  /**
   * @param swrlapiOWLOntology         A SWRLAPI-based OWL ontology
   * @param targetSWRLRuleEngine       A target SWRL rule engine implementation
   * @param ruleEngineBridgeController A rule engine bridge controller
   * @param builtInBridgeController    A built-in bridge controller
   * @return A SQWRL query engine
   * @throws SWRLRuleEngineException If an error occurs during query engine creation
   */
  @NonNull public static SQWRLQueryEngine getSQWRLQueryEngine(@NonNull SWRLAPIOWLOntology swrlapiOWLOntology,
    @NonNull TargetSWRLRuleEngine targetSWRLRuleEngine,
    @NonNull SWRLRuleEngineBridgeController ruleEngineBridgeController,
    @NonNull SWRLBuiltInBridgeController builtInBridgeController) throws SWRLRuleEngineException
  {
    return new DefaultSWRLRuleAndQueryEngine(swrlapiOWLOntology, targetSWRLRuleEngine, ruleEngineBridgeController,
      builtInBridgeController);
  }

  /**
   * @param swrlapiOWLOntology         A SWRLAPI-based OWL ontology
   * @param targetSWRLRuleEngine       A target SWRL rule engine implementation
   * @param ruleEngineBridgeController A rule engine bridge controller
   * @param builtInBridgeController    A built-in bridge controller
   * @return A SWRL rule engine
   * @throws SWRLRuleEngineException If an error occurs during query engine creation
   */
  @NonNull public static SWRLRuleEngine getSWRLRuleEngine(@NonNull SWRLAPIOWLOntology swrlapiOWLOntology,
    @NonNull TargetSWRLRuleEngine targetSWRLRuleEngine,
    @NonNull SWRLRuleEngineBridgeController ruleEngineBridgeController,
    @NonNull SWRLBuiltInBridgeController builtInBridgeController) throws SWRLRuleEngineException
  {
    return new DefaultSWRLRuleAndQueryEngine(swrlapiOWLOntology, targetSWRLRuleEngine, ruleEngineBridgeController,
      builtInBridgeController);
  }

  /**
   * @param swrlapiOWLOntology     A SWRLAPI-based OWL ontology
   * @param owl2RLPersistenceLayer An OWL 2 RL persistence layer
   * @return A SWRL rule engine bridge
   * @throws SWRLBuiltInBridgeException If an error occurs during rule engine bridge creation
   */
  @NonNull public static SWRLBridge getSWRLBridge(@NonNull SWRLAPIOWLOntology swrlapiOWLOntology,
    @NonNull OWL2RLPersistenceLayer owl2RLPersistenceLayer) throws SWRLBuiltInBridgeException
  {
    return new DefaultSWRLBridge(swrlapiOWLOntology, owl2RLPersistenceLayer);
  }

  /**
   * @param swrlapiowlOntology A SWRLAPI-based OWL ontology
   * @return A SWRL rule renderer
   */
  @NonNull public static SWRLRuleRenderer getSWRLRuleRenderer(@NonNull SWRLAPIOWLOntology swrlapiowlOntology)
  {
    return new DefaultSWRLRuleAndQueryRenderer(swrlapiowlOntology);
  }

  /**
   * @return A SWRL rule engine manager
   */
  @NonNull public static SWRLRuleEngineManager getSWRLRuleEngineManager()
  {
    return new DefaultSWRLRuleEngineManager();
  }

  /**
   * @param swrlapiowlOntology A SWRLAPI-based OWL ontology
   * @return A SQWRL query renderer
   */
  @NonNull public static SQWRLQueryRenderer getSQWRLQueryRenderer(@NonNull SWRLAPIOWLOntology swrlapiowlOntology)
  {
    return new DefaultSWRLRuleAndQueryRenderer(swrlapiowlOntology);
  }

  /**
   * @param swrlapiowlOntology A SWRLAPI-based OWL ontology
   * @return A SWRL auto-completer
   */
  @NonNull public static SWRLAutoCompleter getSWRLAutoCompleter(@NonNull SWRLAPIOWLOntology swrlapiowlOntology)
  {
    return new DefaultSWRLAutoCompleter(swrlapiowlOntology);
  }

  /**
   * @param sqwrlResultValueFactory A SQWRL result value factory
   * @return A SQWRL result
   */
  @NonNull public static SQWRLResultManager getSQWRLResult(@NonNull SQWRLResultValueFactory sqwrlResultValueFactory)
  {
    return new SQWRLResultManager(sqwrlResultValueFactory);
  }

  /**
   * @param swrlapiOWLOntology A SWRLAPI-based OWL ontology
   * @return An OWL 2 RL persistence layer
   */
  @NonNull public static OWL2RLPersistenceLayer getOWL2RLPersistenceLayer(
    @NonNull SWRLAPIOWLOntology swrlapiOWLOntology)
  {
    return new DefaultOWL2RLPersistenceLayer(swrlapiOWLOntology);
  }

  /**
   * @param swrlapiOWLOntology A SWRLAPI-based OWL ontology
   * @return A SWRLAPI ontology processor
   */
  @NonNull public static SWRLAPIOntologyProcessor createSWRLAPIOntologyProcessor(
    @NonNull SWRLAPIOWLOntology swrlapiOWLOntology)
  {
    return new DefaultSWRLAPIOntologyProcessor(swrlapiOWLOntology);
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

  @NonNull public static SQWRLQuery getSQWRLQuery(@NonNull String queryName, @NonNull List<SWRLAtom> bodyAtoms,
    @NonNull List<SWRLAtom> headAtoms, boolean active, @NonNull String comment, @NonNull LiteralFactory literalFactory,
    @NonNull SQWRLResultValueFactory sqwrlResultValueFactory) throws SQWRLException
  {
    return new DefaultSQWRLQuery(queryName, bodyAtoms, headAtoms, active, comment, literalFactory,
      sqwrlResultValueFactory);
  }

  /**
   * @return and OWL datatype factory
   */
  @NonNull public static OWLDatatypeFactory getOWLDatatypeFactory()
  {
    return new DefaultOWLDatatypeFactory();
  }

  /**
   * @param swrlapiOWLOntology A SWRLAPI-based OWL ontology
   * @return A SWRLAPI-based OWL data factory
   */
  @NonNull public static SWRLAPIOWLDataFactory createSWRLAPIOWLDataFactory(
    @NonNull SWRLAPIOWLOntology swrlapiOWLOntology)
  {
    return new DefaultSWRLAPIOWLDataFactory(swrlapiOWLOntology);
  }

  /**
   * @param swrlRuleEngine   A SWRL rule engine
   * @param swrlRuleRenderer A SWRL renderer
   * @return A SWRL rule table model
   */
  @NonNull public static SWRLRulesTableModel createSWRLRulesTableModel(@NonNull SWRLRuleEngine swrlRuleEngine,
    @NonNull SWRLRuleRenderer swrlRuleRenderer)
  {
    return new SWRLRulesTableModel(swrlRuleEngine, swrlRuleRenderer);
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
   * @return A SWRL rule engine factory
   */
  @NonNull public static SWRLRuleAndQueryEngineFactory createSWRLRuleAndQueryEngineFactory()
  {
    return new DefaultSWRLRuleAndQueryEngineFactory();
  }

  @NonNull public static SQWRLResultGenerator createSQWRLResultGenerator(
    SQWRLResultValueFactory sqwrlResultValueFactory)
  {
    return new SQWRLResultManager(sqwrlResultValueFactory);
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
   * @param queryEngine A SQWRL query engine
   * @return A SQWRL query engine model
   */
  @NonNull public static SQWRLQueryEngineModel createSQWRLQueryEngineModel(@NonNull SQWRLQueryEngine queryEngine)
  {
    return new DefaultSQWRLQueryEngineModel(queryEngine);
  }

  /**
   * @param swrlRuleEngineModel A SWRL rule engine model
   * @return A SWRL rule engine controller
   */
  @NonNull public static SWRLRuleEngineController createSWRLRuleEngineController(
    SWRLRuleEngineModel swrlRuleEngineModel)
  {
    return new DefaultSWRLRuleEngineController(swrlRuleEngineModel);
  }

  /**
   * @param swrlRuleEngineModel An SWRL rule engine model
   * @return A SWRL rule engine dialog manager
   */
  @NonNull public static SWRLAPIDialogManager createDialogManager(@NonNull SWRLRuleEngineModel swrlRuleEngineModel)
  {
    return new DefaultSWRLAPIDialogManager(swrlRuleEngineModel);
  }

  /**
   * @param ontology            An OWL ontology
   * @param swrlRuleEngineModel A SWRL rule engine model
   * @param file                The file containing it
   * @return An ontology model
   */
  @NonNull public static FileBackedOWLOntologyModel createFileBackedOWLOntologyModel(@NonNull OWLOntology ontology,
    @NonNull SWRLRuleEngineModel swrlRuleEngineModel, @NonNull File file)
  {
    return new DefaultFileBackedOWLOntologyModel(ontology, swrlRuleEngineModel, file);
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

  @NonNull public static OWLLiteralFactory getOWLLiteralFactory()
  {
    return new DefaultOWLLiteralFactory();
  }

  @NonNull public static Literal getLiteral(OWLLiteral owlLiteral) { return new DefaultLiteral(owlLiteral); }

  @NonNull public static SWRLAPIRule getSWRLAPIRule(@NonNull String ruleName,
    @NonNull List<? extends SWRLAtom> bodyAtoms, @NonNull List<? extends SWRLAtom> headAtoms, @NonNull String comment,
    boolean isActive)
  {
    return new DefaultSWRLAPIRule(ruleName, bodyAtoms, headAtoms, comment, isActive);
  }

  @NonNull public static SWRLAPIBuiltInAtom getSWRLAPIBuiltInAtom(@NonNull String ruleName, @NonNull IRI builtInIRI,
    @NonNull String builtInPrefixedName, @NonNull List<SWRLBuiltInArgument> arguments)
  {
    return new DefaultSWRLAPIBuiltInAtom(ruleName, builtInIRI, builtInPrefixedName, arguments);
  }

  private static void addDefaultPrefixes(@NonNull OWLOntology ontology, @NonNull DefaultPrefixManager prefixManager)
  {
    OWLOntologyManager owlOntologyManager = ontology.getOWLOntologyManager();
    OWLDocumentFormat ontologyFormat = owlOntologyManager.getOntologyFormat(ontology);

    if (ontologyFormat != null && ontologyFormat.isPrefixOWLOntologyFormat()) {
      PrefixDocumentFormat prefixOntologyFormat = ontologyFormat.asPrefixOWLOntologyFormat();
      String defaultPrefix = prefixOntologyFormat.getDefaultPrefix();

      Map<String, String> map = prefixOntologyFormat.getPrefixName2PrefixMap();
      for (String prefix : map.keySet())
        prefixManager.setPrefix(prefix, map.get(prefix));

      // TODO Look at this. Seems dodgy.
      if (defaultPrefix != null)
        prefixManager.setDefaultPrefix(defaultPrefix);
      else
        prefixManager.setDefaultPrefix(":");
    }
    addSWRLAPIPrefixes(prefixManager);
  }

  private static void addSWRLAPIPrefixes(@NonNull DefaultPrefixManager prefixManager)
  {
    prefixManager.setPrefix("owl:", "http://www.w3.org/2002/07/owl#");
    prefixManager.setPrefix("swrl:", "http://www.w3.org/2003/11/swrl#");
    prefixManager.setPrefix("swrlb:", "http://www.w3.org/2003/11/swrlb#");
    prefixManager.setPrefix("sqwrl:", "http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl#");
    prefixManager.setPrefix("swrlm:", "http://swrl.stanford.edu/ontologies/built-ins/3.4/swrlm.owl#");
    prefixManager.setPrefix("temporal:", "http://swrl.stanford.edu/ontologies/built-ins/3.3/temporal.owl#");
    prefixManager.setPrefix("swrlx:", "http://swrl.stanford.edu/ontologies/built-ins/3.3/swrlx.owl#");
    prefixManager.setPrefix("swrla:", "http://swrl.stanford.edu/ontologies/3.3/swrla.owl#");
  }

  private static void addSWRLAPIBuiltInOntologies(@NonNull OWLOntologyManager ontologyManager)
  {
    Map<String, String> map = new HashMap<>();

    map.put("http://www.w3.org/2003/11/swrl#", resourceName2File("owl/swrl.owl"));
    map.put("http://www.w3.org/2003/11/swrlb#", resourceName2File("owl/swrlb.owl"));
    map.put("http://swrl.stanford.edu/ontologies/3.3/swrla.owl", resourceName2File("owl/swrla.owl"));
    map.put("http://swrl.stanford.edu/ontologies/built-ins/3.4/swrlm.owl", resourceName2File("owl/swrlm.owl"));
    map.put("http://swrl.stanford.edu/ontologies/built-ins/3.3/swrlx.owl", resourceName2File("owl/swrlx.owl"));
    map.put("http://swrl.stanford.edu/ontologies/built-ins/3.3/temporal.owl", resourceName2File("owl/temporal.owl"));
    map.put("http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl", resourceName2File("owl/sqwrl.owl"));

    for (String key : map.keySet())
      ontologyManager.getIRIMappers().add(new SimpleIRIMapper(IRI.create(key), IRI.create(map.get(key))));
  }

  @NonNull private static OWLOntology createOWLOntology(@NonNull OWLOntologyManager ontologyManager, @NonNull File file)
    throws SWRLAPIException
  {
    try {
      return ontologyManager.loadOntologyFromOntologyDocument(file);
    } catch (OWLOntologyCreationException e) {
      throw new SWRLAPIException(
        "Error create OWL ontology from file " + file.getAbsolutePath() + ": " + e.getMessage());
    }
  }

  // TODO This looks dodgy
  @NonNull private static String resourceName2File(@NonNull String resourceName)
  {
    URL url = SWRLAPIFactory.class.getClassLoader().getResource(resourceName);
    if (url == null)
      throw new SWRLAPIException("Could not find resource " + resourceName);

    return "file:///" + url.getFile();
  }
}
