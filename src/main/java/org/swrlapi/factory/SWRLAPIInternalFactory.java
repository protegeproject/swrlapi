package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.util.SimpleRenderer;
import org.swrlapi.bridge.SWRLBridge;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.core.IRIResolver;
import org.swrlapi.core.SWRLAPIBuiltInAtom;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.core.SWRLRuleEngineManager;
import org.swrlapi.core.SWRLRuleRenderer;
import org.swrlapi.exceptions.SWRLAPIException;
import org.swrlapi.exceptions.SWRLBuiltInBridgeException;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.literal.Literal;
import org.swrlapi.owl2rl.OWL2RLEngine;
import org.swrlapi.owl2rl.OWL2RLPersistenceLayer;
import org.swrlapi.sqwrl.SQWRLQuery;
import org.swrlapi.sqwrl.SQWRLQueryRenderer;
import org.swrlapi.sqwrl.SQWRLResultGenerator;
import org.swrlapi.sqwrl.SQWRLResultManager;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.ui.controller.SWRLRuleEngineController;
import org.swrlapi.ui.model.OWL2RLModel;
import org.swrlapi.ui.model.SWRLAutoCompleter;
import org.swrlapi.ui.model.SWRLRuleEngineModel;
import org.swrlapi.ui.model.SWRLRulesAndSQWRLQueriesTableModel;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * @see SWRLRuleAndQueryEngineFactory
 * @see SQWRLResultValueFactory
 * @see SWRLAPIOWLDataFactory
 * @see OWLDatatypeFactory
 */
public class SWRLAPIInternalFactory
{
  @NonNull private static final String SQWRL_ICON_NAME = "SQWRL.gif";
  @NonNull private static final String OWL2RL_ICON_NAME = "OWL2RL.gif";

  /**
   * @param ontology    An OWL ontology
   * @param iriResolver An IRI resolver
   * @return A SWRL rule renderer
   */
  public static @NonNull SWRLRuleRenderer createSWRLRuleRenderer(@NonNull OWLOntology ontology,
    @NonNull IRIResolver iriResolver, @NonNull OWLObjectRenderer owlObjectRenderer)
  {
    return new DefaultSWRLRuleAndQueryRenderer(ontology, iriResolver, owlObjectRenderer);
  }

  /**
   * @param ontology    An OWL ontology
   * @param iriResolver An IRI resolver
   * @return A SQWRL query renderer
   */
  public static @NonNull SQWRLQueryRenderer createSQWRLQueryRenderer(@NonNull OWLOntology ontology,
    @NonNull IRIResolver iriResolver, @NonNull OWLObjectRenderer owlObjectRenderer)
  {
    return new DefaultSWRLRuleAndQueryRenderer(ontology, iriResolver, owlObjectRenderer);
  }

  /**
   * @return An OWL object renderer
   */
  @NonNull public static OWLObjectRenderer createOWLObjectRenderer()
  {
    return new SimpleRenderer();
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
  @NonNull public static SQWRLResultManager createSQWRLResultManager(@NonNull IRIResolver iriResolver,
    @NonNull OWLObjectRenderer owlObjectRenderer)
  {
    return new DefaultSQWRLResultManager(iriResolver, owlObjectRenderer);
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
   * @param iriResolver       An IRI resolver
   * @param owlObjectRenderer An OWL object renderer
   * @return A SQWRL result value factory
   */
  @NonNull public static SQWRLResultValueFactory createSQWRLResultValueFactory(@NonNull IRIResolver iriResolver,
    @NonNull OWLObjectRenderer owlObjectRenderer)
  {
    return new DefaultSQWRLResultValueFactory(iriResolver, owlObjectRenderer);
  }

  public static @NonNull SQWRLQuery createSQWRLQuery(@NonNull String queryName,
    @NonNull List<@NonNull SWRLAtom> bodyAtoms, @NonNull List<@NonNull SWRLAtom> headAtoms, boolean active,
    @NonNull String comment, @NonNull LiteralFactory literalFactory, @NonNull IRIResolver iriResolver,
    @NonNull OWLObjectRenderer owlObjectRenderer) throws SWRLBuiltInException
  {
    return new DefaultSQWRLQuery(queryName, bodyAtoms, headAtoms, active, comment, literalFactory, iriResolver,
      owlObjectRenderer);
  }

  /**
   * @param iriResolver       An IRI resolver
   * @param owlObjectRenderer An OWL object renderer
   * @return A SWRLAPI-based OWL data factory
   */
  @NonNull public static SWRLAPIOWLDataFactory createSWRLAPIOWLDataFactory(@NonNull IRIResolver iriResolver,
    @NonNull OWLObjectRenderer owlObjectRenderer)
  {
    return new DefaultSWRLAPIOWLDataFactory(iriResolver, owlObjectRenderer);
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

  public static @NonNull OWL2RLModel createOWL2RLModel(@NonNull OWL2RLEngine owl2RLEngine)
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
  public static @NonNull SQWRLResultGenerator createSQWRLResultGenerator(@NonNull IRIResolver iriResolver,
    @NonNull OWLObjectRenderer owlObjectRenderer)
  {
    return new DefaultSQWRLResultManager(iriResolver, owlObjectRenderer);
  }

  /**
   * @param swrlRuleEngineModel A SWRL rule engine model
   * @return A SWRL rule engine controller
   */
  public static @NonNull SWRLRuleEngineController createSWRLRuleEngineController(
    @NonNull SWRLRuleEngineModel swrlRuleEngineModel)
  {
    return new DefaultSWRLRuleEngineController(swrlRuleEngineModel);
  }

  /**
   * @return A SQWRL icon
   * @throws SWRLAPIException If an icon cannot be found
   */
  public static @NonNull Icon getSQWRLIcon() throws SWRLAPIException
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

  public static @NonNull Literal createLiteral(OWLLiteral owlLiteral) { return new DefaultLiteral(owlLiteral); }

  public static @NonNull SWRLAPIRule createSWRLAPIRule(@NonNull String ruleName,
    @NonNull List<? extends SWRLAtom> bodyAtoms, @NonNull List<? extends SWRLAtom> headAtoms, @NonNull String comment,
    boolean isActive) throws SWRLBuiltInException
  {
    return new DefaultSWRLAPIRule(ruleName, bodyAtoms, headAtoms, comment, isActive);
  }

  public static @NonNull SWRLAPIBuiltInAtom createSWRLAPIBuiltInAtom(@NonNull String ruleName, @NonNull IRI builtInIRI,
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
  public static @NonNull SWRLAPIOWLOntology createSWRLAPIOntology(@NonNull OWLOntology ontology,
    @NonNull IRIResolver iriResolver) throws SWRLBuiltInException
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
  @NonNull public static SWRLAPIOWLOntology createSWRLAPIOntology(@NonNull OWLOntology ontology)
    throws SWRLBuiltInException
  {
    IRIResolver iriResolver = SWRLAPIFactory.createIRIResolver();
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
