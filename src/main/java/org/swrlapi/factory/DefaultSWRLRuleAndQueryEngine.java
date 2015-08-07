package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.swrlapi.bridge.SWRLRuleEngineBridgeController;
import org.swrlapi.bridge.TargetSWRLRuleEngine;
import org.swrlapi.builtins.SWRLBuiltInBridgeController;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.core.SWRLRuleRenderer;
import org.swrlapi.exceptions.SWRLAPIException;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.exceptions.SWRLRuleEngineException;
import org.swrlapi.exceptions.SWRLRuleException;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;
import org.swrlapi.owl2rl.OWL2RLEngine;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.parser.SWRLParser;
import org.swrlapi.sqwrl.SQWRLQuery;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLQueryRenderer;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.ui.model.SWRLAutoCompleter;

import javax.swing.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This class provides an implementation of some of the core functionality required by a SWRL rule engine.
 */
class DefaultSWRLRuleAndQueryEngine implements SWRLRuleEngine, SQWRLQueryEngine
{
  @NonNull private final SWRLAPIOWLOntology swrlapiOWLOntology;
  @NonNull private final TargetSWRLRuleEngine targetSWRLRuleEngine;
  @NonNull private final SWRLBuiltInBridgeController builtInBridgeController;
  @NonNull private final SWRLRuleEngineBridgeController ruleEngineBridgeController;
  @NonNull private final Set<OWLAxiom> exportedOWLAxioms; // Axioms exported to target rule engine

  public DefaultSWRLRuleAndQueryEngine(@NonNull SWRLAPIOWLOntology swrlapiOWLOntology,
    @NonNull TargetSWRLRuleEngine targetSWRLRuleEngine,
    @NonNull SWRLRuleEngineBridgeController ruleEngineBridgeController,
    @NonNull SWRLBuiltInBridgeController builtInBridgeController) throws SWRLRuleEngineException
  {
    this.swrlapiOWLOntology = swrlapiOWLOntology;
    this.targetSWRLRuleEngine = targetSWRLRuleEngine;
    this.builtInBridgeController = builtInBridgeController;
    this.ruleEngineBridgeController = ruleEngineBridgeController;
    this.exportedOWLAxioms = new HashSet<>();

    importAssertedOWLAxioms();
  }

  /**
   * Load rules and knowledge from OWL into bridge. All existing bridge rules and knowledge will first be cleared and
   * the associated rule engine will be reset.
   */
  @Override public void importAssertedOWLAxioms() throws SWRLRuleEngineException
  {
    reset();

    try {
      exportOWLAxioms2TargetRuleEngine(this.swrlapiOWLOntology.getOWLAxioms()); // OWL axioms include SWRL rules
    } catch (SWRLAPIException e) {
      throw new SWRLRuleEngineException("error exporting knowledge to rule engine: " + e.getMessage(), e);
    }
  }

  /**
   * Load named SQWRL query, all enabled SWRL rules, and all relevant knowledge from OWL into bridge. All existing
   * bridge rules and knowledge will first be cleared and the associated rule engine will be reset.
   */
  @Override public void importSQWRLQueryAndOWLAxioms(@NonNull String queryName) throws SWRLRuleEngineException
  {
    reset();

    try {
      exportOWLAxioms2TargetRuleEngine(this.swrlapiOWLOntology.getOWLAxioms()); // OWL axioms include SWRL rules
      exportSQWRLQuery2TargetRuleEngine(queryName);
    } catch (SWRLAPIException e) {
      throw new SWRLRuleEngineException("error exporting SQWRL query rule engine: " + e.getMessage(), e);
    }
  }

  /**
   * Clear all knowledge from rule engine.
   */
  @Override public void reset()
  {
    try {
      this.swrlapiOWLOntology.reset();
      getTargetSWRLRuleEngine().resetRuleEngine(); // Reset the target rule engine
      getBuiltInBridgeController().reset();
      this.exportedOWLAxioms.clear();
      getOWL2RLEngine().resetRuleSelectionChanged();
      getSWRLAPIOWLOntology().resetOntologyChanged();
      this.swrlapiOWLOntology.processOntology();
    } catch (SQWRLException e) {
      throw new SWRLRuleEngineException("error running rule engine: " + e.getMessage(), e);
    }
  }

  /**
   * Run the rule engine.
   */
  @Override public void run() throws SWRLRuleEngineException
  {
    try {
      this.swrlapiOWLOntology.processOntology();
      getTargetSWRLRuleEngine().runRuleEngine();
    } catch (@NonNull SWRLBuiltInException | SWRLAPIException e) {
      throw new SWRLRuleEngineException("error running rule engine: " + e.getMessage(), e);
    }
  }

  @Override public SQWRLQuery createSQWRLQuery(@NonNull String queryName, @NonNull String queryText)
    throws SWRLParseException, SQWRLException
  {
    try {
      return this.swrlapiOWLOntology.createSQWRLQuery(queryName, queryText);
    } catch (RuntimeException e) {
      throw new SQWRLException("error creating SQWRL query: " + e.getMessage(), e);
    }
  }

  /**
   * Create and run a SQWRL query. The query will be created and added to the associated ontology.
   */
  @NonNull @Override public SQWRLResult runSQWRLQuery(@NonNull String queryName, @NonNull String queryText)
    throws SWRLParseException, SQWRLException
  {
    createSQWRLQuery(queryName, queryText);

    return runSQWRLQuery(queryName);
  }

  /**
   * Run a named SQWRL query. SWRL rules will also be executed and any inferences produced by them will be available in
   * the query.
   */
  @NonNull @Override public SQWRLResult runSQWRLQuery(@NonNull String queryName) throws SQWRLException
  {
    try {
      importSQWRLQueryAndOWLAxioms(queryName);

      run();
      return getSQWRLResult(queryName);
    } catch (SWRLAPIException e) {
      throw new SQWRLException("error running SQWRL queries: " + e.getMessage(), e);
    }
  }

  /**
   * Run all SQWRL queries.
   */
  @Override public void runSQWRLQueries() throws SQWRLException
  {
    try {
      importAssertedOWLAxioms();
      exportSQWRLQueries2TargetRuleEngine();
    } catch (SWRLRuleEngineException | TargetSWRLRuleEngineException e) {
      throw new SQWRLException("error processing SQWRL queries: " + e.getMessage(), e);
    }

    try {
      run();
    } catch (SWRLAPIException e) {
      throw new SQWRLException("error running SQWRL queries: " + e.getMessage(), e);
    }
  }

  /**
   * Write knowledge inferred by rule engine back to OWL.
   */
  @Override public void exportInferredOWLAxioms() throws SWRLRuleEngineException
  {
    try {
      getSWRLAPIOWLOntology().startBulkConversion(); // Suspend possible event generation for bulk updates.

      // Write OWL axioms generated by built-ins in rules.
      writeOWLAxioms2OWLOntology(this.builtInBridgeController.getInjectedOWLAxioms());
      // Write OWL axioms inferred by rules.
      writeOWLAxioms2OWLOntology(this.ruleEngineBridgeController.getInferredOWLAxioms());
    } finally {
      getSWRLAPIOWLOntology().completeBulkConversion();
    }
  }

  /**
   * Load rules and knowledge from OWL into bridge, send them to a rule engine, run the rule engine, and write any
   * inferred knowledge back to OWL.
   */
  @Override public void infer() throws SWRLRuleEngineException
  {
    reset();
    importAssertedOWLAxioms();
    run();
    exportInferredOWLAxioms();
  }

  /**
   * Get the results of a previously executed SQWRL query.
   */
  @NonNull @Override public SQWRLResult getSQWRLResult(@NonNull String queryName) throws SQWRLException
  {
    return this.swrlapiOWLOntology.getSQWRLResult(queryName);
  }

  /**
   * Get all the enabled SQWRL queries in the ontology.
   */
  @NonNull @Override public Set<SQWRLQuery> getSQWRLQueries()
  {
    return this.swrlapiOWLOntology.getSQWRLQueries();
  }

  /**
   * Get the names of the enabled SQWRL queries in the ontology.
   */
  @NonNull @Override public Set<String> getSQWRLQueryNames()
  {
    return this.swrlapiOWLOntology.getSQWRLQueryNames();
  }

  @NonNull @Override public SQWRLQueryRenderer createSQWRLQueryRenderer()
  {
    return this.swrlapiOWLOntology.createSQWRLQueryRenderer();
  }

  @NonNull public OWLOntology getOWLOntology()
  {
    return getSWRLAPIOWLOntology().getOWLOntology();
  }

  @NonNull @Override public OWL2RLEngine getOWL2RLEngine()
  {
    return this.targetSWRLRuleEngine.getOWL2RLEngine();
  }

  // Convenience methods to display bridge activity

  @Override public int getNumberOfImportedSWRLRules()
  {
    return this.swrlapiOWLOntology.getNumberOfSWRLRules();
  }

  @Override public int getNumberOfAssertedOWLClassDeclarationAxioms()
  {
    return this.swrlapiOWLOntology.getNumberOfOWLClassDeclarationAxioms();
  }

  @Override public int getNumberOfAssertedOWLIndividualDeclarationsAxioms()
  {
    return this.swrlapiOWLOntology.getNumberOfOWLIndividualDeclarationAxioms();
  }

  @Override public int getNumberOfAssertedOWLObjectPropertyDeclarationAxioms()
  {
    return this.swrlapiOWLOntology.getNumberOfOWLObjectPropertyDeclarationAxioms();
  }

  @Override public int getNumberOfAssertedOWLDataPropertyDeclarationAxioms()
  {
    return this.swrlapiOWLOntology.getNumberOfOWLDataPropertyDeclarationAxioms();
  }

  @Override public int getNumberOfAssertedOWLAxioms()
  {
    return this.swrlapiOWLOntology.getNumberOfOWLAxioms();
  }

  @Override public int getNumberOfInferredOWLAxioms()
  {
    return this.ruleEngineBridgeController.getNumberOfInferredOWLAxioms();
  }

  @Override public int getNumberOfInjectedOWLAxioms()
  {
    return this.builtInBridgeController.getNumberOfInjectedOWLAxioms();
  }

  public boolean isInjectedOWLAxiom(OWLAxiom axiom)
  {
    return this.builtInBridgeController.isInjectedOWLAxiom(axiom);
  }

  // Convenience methods to display the contents of the bridge

  @NonNull @Override public Set<SWRLAPIRule> getSWRLRules()
  {
    return this.swrlapiOWLOntology.getSWRLRules();
  }

  @Override public SWRLAPIRule getSWRLRule(@NonNull String ruleName) throws SWRLRuleException
  {
    return this.swrlapiOWLOntology.getSWRLRule(ruleName);
  }

  @NonNull @Override public SWRLAPIRule createSWRLRule(@NonNull String ruleName, @NonNull String rule)
    throws SWRLParseException
  {
    return this.swrlapiOWLOntology.createSWRLRule(ruleName, rule);
  }

  @NonNull @Override public SWRLAPIRule createSWRLRule(@NonNull String ruleName, @NonNull String rule,
    @NonNull String comment, boolean isActive) throws SWRLParseException
  {
    return this.swrlapiOWLOntology.createSWRLRule(ruleName, rule, comment, isActive);
  }

  @Override public void deleteSWRLRule(@NonNull String ruleName)
  {
    this.swrlapiOWLOntology.deleteSWRLRule(ruleName);
  }

  @Override public boolean isSWRLBuiltIn(@NonNull IRI iri)
  {
    return this.swrlapiOWLOntology.isSWRLBuiltIn(iri);
  }

  @Override public void addSWRLBuiltIn(@NonNull IRI iri)
  {
    this.swrlapiOWLOntology.addSWRLBuiltIn(iri);
  }

  @NonNull @Override public Set<IRI> getSWRLBuiltInIRIs()
  {
    return this.swrlapiOWLOntology.getSWRLBuiltInIRIs();
  }

  @NonNull @Override public SWRLParser createSWRLParser()
  {
    return this.swrlapiOWLOntology.createSWRLParser();
  }

  @NonNull @Override public SWRLAutoCompleter createSWRLAutoCompleter()
  {
    return this.swrlapiOWLOntology.createSWRLAutoCompleter();
  }

  @NonNull @Override public SWRLRuleRenderer createSWRLRuleRenderer()
  {
    return this.swrlapiOWLOntology.createSWRLRuleRenderer();
  }

  @NonNull @Override public Set<OWLAxiom> getAssertedOWLAxioms()
  {
    return this.swrlapiOWLOntology.getOWLAxioms();
  }

  @NonNull @Override public Set<OWLAxiom> getInferredOWLAxioms()
  {
    return this.ruleEngineBridgeController.getInferredOWLAxioms();
  }

  @NonNull @Override public Set<OWLAxiom> getInjectedOWLAxioms()
  {
    return this.builtInBridgeController.getInjectedOWLAxioms();
  }

  @NonNull @Override public String getRuleEngineName()
  {
    return this.targetSWRLRuleEngine.getName();
  }

  @NonNull @Override public String getQueryEngineName()
  {
    return this.targetSWRLRuleEngine.getName();
  }

  @NonNull @Override public String getRuleEngineVersion()
  {
    return this.targetSWRLRuleEngine.getVersion();
  }

  @NonNull @Override public String getQueryEngineVersion()
  {
    return this.targetSWRLRuleEngine.getVersion();
  }

  @NonNull @Override public OWLReasoner getOWLReasoner()
  {
    return this.targetSWRLRuleEngine.getOWLReasoner();
  }

  @NonNull @Override public Icon getRuleEngineIcon()
  {
    return this.targetSWRLRuleEngine.getSWRLRuleEngineIcon();
  }

  @NonNull @Override public Icon getQueryEngineIcon()
  {
    return this.targetSWRLRuleEngine.getSWRLRuleEngineIcon();
  }

  private void exportSQWRLQuery2TargetRuleEngine(@NonNull String activeQueryName)
    throws SWRLRuleEngineException, TargetSWRLRuleEngineException
  {
    for (SQWRLQuery query : this.swrlapiOWLOntology.getSQWRLQueries()) {
      query.setActive(query.getQueryName().equalsIgnoreCase(activeQueryName));
      exportSQWRLQuery2TargetRuleEngine(query);
    }
  }

  private void exportSQWRLQueries2TargetRuleEngine() throws SWRLRuleEngineException, TargetSWRLRuleEngineException
  {
    for (SQWRLQuery query : this.swrlapiOWLOntology.getSQWRLQueries()) {
      query.setActive(true);
      exportSQWRLQuery2TargetRuleEngine(query);
    }
  }

  private void exportSQWRLQuery2TargetRuleEngine(@NonNull SQWRLQuery query)
    throws SWRLRuleEngineException, TargetSWRLRuleEngineException
  {
    getTargetSWRLRuleEngine().defineSQWRLQuery(query);
  }

  @NonNull private TargetSWRLRuleEngine getTargetSWRLRuleEngine() throws SWRLRuleEngineException
  {
    if (this.targetSWRLRuleEngine == null)
      throw new SWRLRuleEngineException("no target rule engine specified");

    return this.targetSWRLRuleEngine;
  }

  private void exportOWLAxioms2TargetRuleEngine(@NonNull Set<OWLAxiom> axioms)
    throws SWRLRuleEngineException, TargetSWRLRuleEngineException
  {
    axioms.stream().filter(axiom -> !this.exportedOWLAxioms.contains(axiom))
      .forEach(axiom -> getTargetSWRLRuleEngine().defineOWLAxiom(axiom));
  }

  private void writeOWLAxioms2OWLOntology(@NonNull Set<OWLAxiom> axioms) throws SWRLRuleEngineException
  {
    try {
      axioms.forEach(this::writeOWLAxiom2OWLOntology);
    } catch (RuntimeException e) {
      throw new SWRLRuleEngineException("Error writing OWL axioms to ontology", e);
    }
  }

  private void writeOWLAxiom2OWLOntology(@NonNull OWLAxiom axiom)
  {
    AddAxiom addAxiomChange = new AddAxiom(getOWLOntology(), axiom);

    getOWLOntologyManager().applyChange(addAxiomChange);
  }

  @NonNull private OWLOntologyManager getOWLOntologyManager()
  {
    return this.getSWRLAPIOWLOntology().getOWLOntologyManager();
  }

  @NonNull private SWRLAPIOWLOntology getSWRLAPIOWLOntology()
  {
    return this.swrlapiOWLOntology;
  }

  @NonNull private SWRLBuiltInBridgeController getBuiltInBridgeController()
  {
    return this.builtInBridgeController;
  }
}
