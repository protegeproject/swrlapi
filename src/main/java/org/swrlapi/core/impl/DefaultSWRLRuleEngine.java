package org.swrlapi.core.impl;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.swrlapi.bridge.SWRLRuleEngineBridgeController;
import org.swrlapi.bridge.TargetSWRLRuleEngine;
import org.swrlapi.builtins.SWRLBuiltInBridge;
import org.swrlapi.builtins.SWRLBuiltInBridgeController;
import org.swrlapi.core.SWRLAPIOWLDataFactory;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLAPIOntologyProcessor;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.exceptions.SWRLBuiltInBridgeException;
import org.swrlapi.exceptions.SWRLRuleEngineException;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;
import org.swrlapi.owl2rl.OWL2RLEngine;
import org.swrlapi.sqwrl.SQWRLQuery;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

/**
 * This class provides an implementation of some of the core functionality required by a SWRL rule engine.
 */
public class DefaultSWRLRuleEngine implements SWRLRuleEngine
{
	private final SWRLAPIOWLOntology swrlapiOWLOntology;
	private final TargetSWRLRuleEngine targetSWRLRuleEngine;
	private final SWRLBuiltInBridgeController builtInBridgeController;
	private final SWRLRuleEngineBridgeController ruleEngineBridgeController;
	private final Set<OWLAxiom> exportedOWLAxioms; // Axioms exported to target rule engine

	public DefaultSWRLRuleEngine(SWRLAPIOWLOntology swrlapiOWLOntology, TargetSWRLRuleEngine targetSWRLRuleEngine,
			SWRLRuleEngineBridgeController ruleEngineBridgeController, SWRLBuiltInBridgeController builtInBridgeController)
			throws SWRLRuleEngineException
	{
		this.swrlapiOWLOntology = swrlapiOWLOntology;
		this.targetSWRLRuleEngine = targetSWRLRuleEngine;
		this.builtInBridgeController = builtInBridgeController;
		this.ruleEngineBridgeController = ruleEngineBridgeController;
		this.exportedOWLAxioms = new HashSet<OWLAxiom>();

		importSWRLRulesAndOWLKnowledge();
	}

	/**
	 * Load rules and knowledge from OWL into bridge. All existing bridge rules and knowledge will first be cleared and
	 * the associated rule engine will be reset.
	 */
	@Override
	public void importSWRLRulesAndOWLKnowledge() throws SWRLRuleEngineException
	{
		reset();

		try {
			exportOWLAxioms2TargetRuleEngine(this.swrlapiOWLOntology.getOWLAxioms()); // OWL axioms include SWRL rules
		} catch (TargetSWRLRuleEngineException e) {
			throw new SWRLRuleEngineException("error exporting knowledge to rule engine: " + e.getMessage(), e);
		}
	}

	/**
	 * Load named SQWRL query, all enabled SWRL rules, and all relevant knowledge from OWL into bridge. All existing
	 * bridge rules and knowledge will first be cleared and the associated rule engine will be reset.
	 */
	@Override
	public void importSQWRLQueryAndOWLKnowledge(String queryName) throws SWRLRuleEngineException
	{
		reset();

		try {
			exportOWLAxioms2TargetRuleEngine(this.swrlapiOWLOntology.getOWLAxioms()); // OWL axioms include SWRL rules
			exportSQWRLQueries2TargetRuleEngine(queryName);
		} catch (SWRLBuiltInException e) {
			throw new SWRLRuleEngineException("error exporting SQWRL query to rule engine: " + e.getMessage(), e);
		} catch (TargetSWRLRuleEngineException e) {
			throw new SWRLRuleEngineException("error exporting SQWRL query rule engine: " + e.getMessage(), e);
		}
	}

	/**
	 * Clear all knowledge from rule engine.
	 */
	@Override
	public void reset()
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
		} catch (SWRLBuiltInException e) {
			throw new SWRLRuleEngineException("error running rule engine: " + e.getMessage(), e);
		}

	}

	/**
	 * Run the rule engine.
	 */
	@Override
	public void run() throws SWRLRuleEngineException
	{
		try {
			this.swrlapiOWLOntology.processOntology();
			getTargetSWRLRuleEngine().runRuleEngine();
		} catch (SQWRLException e) {
			throw new SWRLRuleEngineException("error running rule engine: " + e.getMessage(), e);
		} catch (SWRLBuiltInException e) {
			throw new SWRLRuleEngineException("error running rule engine: " + e.getMessage(), e);
		} catch (TargetSWRLRuleEngineException e) {
			throw new SWRLRuleEngineException("error running rule engine: " + e.getMessage(), e);
		}
	}

	@Override
	public void createSQWRLQuery(String queryName, String queryText) throws SQWRLException
	{
		try {
			SWRLAPIRule query = getSWRLAPIOWLDataFactory().getSWRLRule(queryName, queryText);
			writeOWLAxiom2OWLOntology(query);
		} catch (RuntimeException e) {
			throw new SQWRLException("error creating SQWRL query: " + e.getMessage(), e);
		}
	}

	/**
	 * Create and run a SQWRL query. The query will be created and added to the associated ontology.
	 */
	@Override
	public SQWRLResult runSQWRLQuery(String queryName, String queryText) throws SQWRLException
	{
		createSQWRLQuery(queryName, queryText);

		return runSQWRLQuery(queryName);
	}

	/**
	 * Run a named SQWRL query. SWRL rules will also be executed and any inferences produced by them will be available in
	 * the query.
	 */
	@Override
	public SQWRLResult runSQWRLQuery(String queryName) throws SQWRLException
	{
		try {
			importSQWRLQueryAndOWLKnowledge(queryName);

			run();
			return getSQWRLResult(queryName);
		} catch (SWRLRuleEngineException e) {
			throw new SQWRLException("error running SQWRL queries: " + e.getMessage(), e);
		}
	}

	/**
	 * Run all SQWRL queries.
	 */
	@Override
	public void runSQWRLQueries() throws SQWRLException
	{
		try {
			importSWRLRulesAndOWLKnowledge();
			exportSQWRLQueries2TargetRuleEngine();
		} catch (SWRLRuleEngineException e) {
			throw new SQWRLException("error processing SQWRL queries: " + e.getMessage(), e);
		} catch (TargetSWRLRuleEngineException e) {
			throw new SQWRLException("error processing SQWRL queries: " + e.getMessage(), e);
		} catch (SWRLBuiltInException e) {
			throw new SQWRLException("error processing SQWRL queries: " + e.getMessage(), e);
		}

		try {
			run();
		} catch (SWRLRuleEngineException e) {
			throw new SQWRLException("error running SQWRL queries: " + e.getMessage(), e);
		}
	}

	/**
	 * Write knowledge inferred by rule engine back to OWL.
	 */
	@Override
	public void writeInferredKnowledge() throws SWRLRuleEngineException
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
	@Override
	public void infer() throws SWRLRuleEngineException
	{
		reset();
		importSWRLRulesAndOWLKnowledge();
		run();
		writeInferredKnowledge();
	}

	/**
	 * Get the results of a previously executed SQWRL query.
	 */
	@Override
	public SQWRLResult getSQWRLResult(String queryName) throws SQWRLException
	{
		return this.swrlapiOWLOntology.getSQWRLResult(queryName);
	}

	/**
	 * Get all the enabled SQWRL queries in the ontology.
	 */
	@Override
	public Set<SQWRLQuery> getSQWRLQueries() throws SQWRLException
	{
		return this.swrlapiOWLOntology.getSQWRLQueries();
	}

	/**
	 * Get the names of the enabled SQWRL queries in the ontology.
	 */
	@Override
	public Set<String> getSQWRLQueryNames()
	{
		return this.swrlapiOWLOntology.getSQWRLQueryNames();
	}

	@Override
	public OWL2RLEngine getOWL2RLEngine()
	{
		return this.targetSWRLRuleEngine.getOWL2RLEngine();
	}

	// Convenience methods to display bridge activity

	@Override
	public int getNumberOfImportedSWRLRules()
	{
		return this.swrlapiOWLOntology.getNumberOfSWRLRules();
	}

	@Override
	public int getNumberOfAssertedOWLClassDeclarationAxioms()
	{
		return this.swrlapiOWLOntology.getNumberOfOWLClassDeclarationAxioms();
	}

	@Override
	public int getNumberOfAssertedOWLIndividualDeclarationsAxioms()
	{
		return this.swrlapiOWLOntology.getNumberOfOWLIndividualDeclarationAxioms();
	}

	@Override
	public int getNumberOfAssertedOWLObjectPropertyDeclarationAxioms()
	{
		return this.swrlapiOWLOntology.getNumberOfOWLObjectPropertyDeclarationAxioms();
	}

	@Override
	public int getNumberOfAssertedOWLDataPropertyDeclarationAxioms()
	{
		return this.swrlapiOWLOntology.getNumberOfOWLDataPropertyDeclarationAxioms();
	}

	@Override
	public int getNumberOfAssertedOWLAxioms()
	{
		return this.swrlapiOWLOntology.getNumberOfOWLAxioms();
	}

	@Override
	public int getNumberOfInferredOWLAxioms()
	{
		return this.ruleEngineBridgeController.getNumberOfInferredOWLAxioms();
	}

	@Override
	public int getNumberOfInjectedOWLAxioms()
	{
		return this.builtInBridgeController.getNumberOfInjectedOWLAxioms();
	}

	public boolean isInjectedOWLAxiom(OWLAxiom axiom)
	{
		return this.builtInBridgeController.isInjectedOWLAxiom(axiom);
	}

	// Convenience methods to display the contents of the bridge

	@Override
	public Set<SWRLAPIRule> getSWRLRules()
	{
		return this.swrlapiOWLOntology.getSWRLRules();
	}

	@Override
	public Set<OWLAxiom> getAssertedOWLAxioms()
	{
		return this.swrlapiOWLOntology.getOWLAxioms();
	}

	@Override
	public Set<OWLAxiom> getInferredOWLAxioms()
	{
		return this.ruleEngineBridgeController.getInferredOWLAxioms();
	}

	@Override
	public Set<OWLAxiom> getInjectedOWLAxioms()
	{
		return this.builtInBridgeController.getInjectedOWLAxioms();
	}

	@Override
	public String getTargetRuleEngineName()
	{
		return this.targetSWRLRuleEngine.getName();
	}

	@Override
	public String getTargetRuleEngineVersion()
	{
		return this.targetSWRLRuleEngine.getVersion();
	}

	public OWLReasoner getOWLReasoner()
	{
		return this.targetSWRLRuleEngine.getOWLReasoner();
	}

	private void exportSQWRLQueries2TargetRuleEngine(String activeQueryName) throws SWRLRuleEngineException,
			TargetSWRLRuleEngineException, SWRLBuiltInException
	{
		for (SQWRLQuery query : this.swrlapiOWLOntology.getSQWRLQueries()) {
			query.setActive(query.getQueryName().equalsIgnoreCase(activeQueryName));
			exportSQWRLQuery2TargetRuleEngine(query);
		}
	}

	private void exportSQWRLQueries2TargetRuleEngine() throws SWRLRuleEngineException, TargetSWRLRuleEngineException,
			SWRLBuiltInException
	{
		for (SQWRLQuery query : this.swrlapiOWLOntology.getSQWRLQueries()) {
			query.setActive(true);
			exportSQWRLQuery2TargetRuleEngine(query);
		}
	}

	private void exportSQWRLQuery2TargetRuleEngine(SQWRLQuery query) throws SWRLRuleEngineException,
			TargetSWRLRuleEngineException, SWRLBuiltInException
	{
		getTargetSWRLRuleEngine().defineSQWRLQuery(query);
	}

	private TargetSWRLRuleEngine getTargetSWRLRuleEngine() throws SWRLRuleEngineException
	{
		if (this.targetSWRLRuleEngine == null)
			throw new SWRLRuleEngineException("no target rule engine specified");

		return this.targetSWRLRuleEngine;
	}

	private void exportOWLAxioms2TargetRuleEngine(Set<OWLAxiom> axioms) throws SWRLRuleEngineException,
			TargetSWRLRuleEngineException
	{
		for (OWLAxiom axiom : axioms) {
			if (!this.exportedOWLAxioms.contains(axiom))
				getTargetSWRLRuleEngine().defineOWLAxiom(axiom);
		}
	}

	private void writeOWLAxioms2OWLOntology(Set<OWLAxiom> axioms) throws SWRLRuleEngineException
	{
		try {
			for (OWLAxiom axiom : axioms) {
				writeOWLAxiom2OWLOntology(axiom);
			}
		} catch (RuntimeException e) {
			throw new SWRLRuleEngineException("Error writing OWL axioms to ontology", e);
		}
	}

	private void writeOWLAxiom2OWLOntology(OWLAxiom axiom)
	{
		AddAxiom addAxiomChange = new AddAxiom(getOWLOntology(), axiom);

		getOWLOntologyManager().applyChange(addAxiomChange);
	}

	private OWLOntologyManager getOWLOntologyManager()
	{
		return this.getSWRLAPIOWLOntology().getOWLOntologyManager();
	}

	private SWRLAPIOWLOntology getSWRLAPIOWLOntology()
	{
		return this.swrlapiOWLOntology;
	}

	private OWLOntology getOWLOntology()
	{
		return getSWRLAPIOWLOntology().getOWLOntology();
	}

	private SWRLAPIOWLDataFactory getSWRLAPIOWLDataFactory()
	{
		return getSWRLAPIOWLOntology().getSWRLAPIOWLDataFactory();
	}

	private SWRLBuiltInBridgeController getBuiltInBridgeController()
	{
		return this.builtInBridgeController;
	}
}
