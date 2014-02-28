package org.protege.swrlapi.core;

import java.util.HashSet;
import java.util.Set;

import org.protege.owl.portability.axioms.OWLAxiomAdapter;
import org.protege.swrlapi.exceptions.BuiltInException;
import org.protege.swrlapi.exceptions.SWRLBuiltInBridgeException;
import org.protege.swrlapi.exceptions.SWRLRuleEngineException;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.protege.swrlapi.ext.SWRLAPIOWLOntology;
import org.protege.swrlapi.ext.SWRLAPIRule;
import org.protege.swrlapi.owl2rl.OWL2RLEngine;
import org.protege.swrlapi.sqwrl.SQWRLQuery;
import org.protege.swrlapi.sqwrl.SQWRLResult;
import org.protege.swrlapi.sqwrl.exceptions.SQWRLException;

/**
 * This class provides an implementation of some of the core functionality required by a SWRL rule engine. Detailed
 * documentation for this mechanism can be found <a
 * href="http://protege.cim3.net/cgi-bin/wiki.pl?SWRLRuleEngineBridgeFAQ">here</a>.
 */
public class AbstractSWRLRuleEngine implements SWRLRuleEngine
{
	private final TargetRuleEngine targetRuleEngine;
	private final SWRLAPIOWLOntology owlOntology;
	private final SWRLOntologyProcessor swrlOntologyProcessor;
	private final SWRLBuiltInBridgeController builtInBridgeController;
	private final SWRLRuleEngineBridgeController ruleEngineBridgeController;
	private final Set<OWLAxiomAdapter> exportedOWLAxioms; // Axioms exported to target rule engine

	public AbstractSWRLRuleEngine(SWRLAPIOWLOntology owlOntology, SWRLOntologyProcessor swrlOntologyProcessor,
			TargetRuleEngine targetRuleEngine, SWRLRuleEngineBridgeController ruleEngineBridgeController,
			SWRLBuiltInBridgeController builtInBridgeController) throws SWRLRuleEngineException
	{
		this.owlOntology = owlOntology;
		this.swrlOntologyProcessor = swrlOntologyProcessor;
		this.targetRuleEngine = targetRuleEngine;
		this.builtInBridgeController = builtInBridgeController;
		this.ruleEngineBridgeController = ruleEngineBridgeController;
		this.exportedOWLAxioms = new HashSet<OWLAxiomAdapter>();
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
			exportOWLAxioms2TargetRuleEngine(getSWRLAPIOntologyProcessor().getOWLAxioms()); // OWL axioms include SWRL rules
		} catch (TargetRuleEngineException e) {
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
			exportOWLAxioms2TargetRuleEngine(getSWRLAPIOntologyProcessor().getOWLAxioms()); // OWL axioms include SWRL rules
			exportSQWRLQueries2TargetRuleEngine(queryName);
		} catch (BuiltInException e) {
			throw new SWRLRuleEngineException("error exporting SQWRL query to rule engine: " + e.getMessage(), e);
		} catch (TargetRuleEngineException e) {
			throw new SWRLRuleEngineException("error exporting SQWRL query rule engine: " + e.getMessage(), e);
		}
	}

	/**
	 * Clear all knowledge from rule engine.
	 */
	@Override
	public void reset() throws SWRLRuleEngineException
	{
		try {
			getSWRLAPIOntologyProcessor().processOntology();
			getTargetRuleEngine().resetRuleEngine(); // Reset the target rule engine
			getBuiltInBridgeController().resetController();
			this.exportedOWLAxioms.clear();
			getOWL2RLEngine().resetRuleSelectionChanged();
			getOWLOntology().resetOntologyChanged();
		} catch (SQWRLException e) {
			throw new SWRLRuleEngineException("error resetting target rule engine: " + e.getMessage(), e);
		} catch (TargetRuleEngineException e) {
			throw new SWRLRuleEngineException("error resetting target rule engine: " + e.getMessage(), e);
		} catch (SWRLBuiltInBridgeException e) {
			throw new SWRLRuleEngineException("error resetting built-in libraries: " + e.getMessage(), e);
		}
	}

	/**
	 * Run the rule engine.
	 */
	@Override
	public void run() throws SWRLRuleEngineException
	{
		try {
			getTargetRuleEngine().runRuleEngine();
		} catch (TargetRuleEngineException e) {
			throw new SWRLRuleEngineException("error running rule engine: " + e.getMessage(), e);
		}
	}

	@Override
	public void createSQWRLQuery(String queryName, String queryText) throws SQWRLException
	{
		try {
			getOWLOntology().createSWRLRule(queryName, queryText);
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
		} catch (TargetRuleEngineException e) {
			throw new SQWRLException("error processing SQWRL queries: " + e.getMessage(), e);
		} catch (BuiltInException e) {
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
			getOWLOntology().startBulkConversion(); // Suspend possible event generation for bulk updates.

			// Write OWL axioms generated by built-ins in rules.
			writeOWLAxioms2OWLOntology(this.builtInBridgeController.getInjectedOWLAxioms());
			// Write OWL axioms inferred by rules.
			writeOWLAxioms2OWLOntology(this.ruleEngineBridgeController.getInferredOWLAxioms());
		} finally {
			getOWLOntology().completeBulkConversion();
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
		return getSWRLAPIOntologyProcessor().getSQWRLResult(queryName);
	}

	/**
	 * Get all the enabled SQWRL queries in the ontology.
	 */
	@Override
	public Set<SQWRLQuery> getSQWRLQueries() throws SQWRLException
	{
		return getSWRLAPIOntologyProcessor().getSQWRLQueries();
	}

	/**
	 * Get the names of the enabled SQWRL queries in the ontology.
	 */
	@Override
	public Set<String> getSQWRLQueryNames() throws SQWRLException
	{
		return getSWRLAPIOntologyProcessor().getSQWRLQueryNames();
	}

	@Override
	public OWL2RLEngine getOWL2RLEngine()
	{
		return this.targetRuleEngine.getOWL2RLEngine();
	}

	// Convenience methods to display bridge activity

	@Override
	public int getNumberOfImportedSWRLRules()
	{
		return getSWRLAPIOntologyProcessor().getNumberOfSWRLRules();
	}

	@Override
	public int getNumberOfAssertedOWLClassDeclarationAxioms()
	{
		return getSWRLAPIOntologyProcessor().getNumberOfOWLClassDeclarationAxioms();
	}

	@Override
	public int getNumberOfAssertedOWLIndividualDeclarationsAxioms()
	{
		return getSWRLAPIOntologyProcessor().getNumberOfOWLIndividualDeclarationAxioms();
	}

	@Override
	public int getNumberOfAssertedOWLObjectPropertyDeclarationAxioms()
	{
		return getSWRLAPIOntologyProcessor().getNumberOfOWLObjectPropertyDeclarationAxioms();
	}

	@Override
	public int getNumberOfAssertedOWLDataPropertyDeclarationAxioms()
	{
		return getSWRLAPIOntologyProcessor().getNumberOfOWLDataPropertyDeclarationAxioms();
	}

	@Override
	public int getNumberOfAssertedOWLAxioms()
	{
		return getSWRLAPIOntologyProcessor().getNumberOfOWLAxioms();
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

	public boolean isInjectedOWLAxiom(OWLAxiomAdapter axiom)
	{
		return this.builtInBridgeController.isInjectedOWLAxiom(axiom);
	}

	// Convenience methods to display the contents of the bridge

	@Override
	public Set<SWRLAPIRule> getSWRLRules()
	{
		return getSWRLAPIOntologyProcessor().getSWRLRules();
	}

	@Override
	public Set<OWLAxiomAdapter> getAssertedOWLAxioms()
	{
		return getSWRLAPIOntologyProcessor().getOWLAxioms();
	}

	@Override
	public Set<OWLAxiomAdapter> getInferredOWLAxioms()
	{
		return this.ruleEngineBridgeController.getInferredOWLAxioms();
	}

	@Override
	public Set<OWLAxiomAdapter> getInjectedOWLAxioms()
	{
		return this.builtInBridgeController.getInjectedOWLAxioms();
	}

	@Override
	public String getTargetRuleEngineName()
	{
		return this.targetRuleEngine.getName();
	}

	@Override
	public String getTargetRuleEngineVersion()
	{
		return this.targetRuleEngine.getVersion();
	}

	private void exportSQWRLQueries2TargetRuleEngine(String activeQueryName) throws SWRLRuleEngineException,
			TargetRuleEngineException, BuiltInException
	{
		for (SQWRLQuery query : getSWRLAPIOntologyProcessor().getSQWRLQueries()) {
			query.setActive(query.getName().equalsIgnoreCase(activeQueryName));
			exportSQWRLQuery2TargetRuleEngine(query);
		}
	}

	private void exportSQWRLQueries2TargetRuleEngine() throws SWRLRuleEngineException, TargetRuleEngineException,
			BuiltInException
	{
		for (SQWRLQuery query : getSWRLAPIOntologyProcessor().getSQWRLQueries()) {
			query.setActive(true);
			exportSQWRLQuery2TargetRuleEngine(query);
		}
	}

	private void exportSQWRLQuery2TargetRuleEngine(SQWRLQuery query) throws SWRLRuleEngineException,
			TargetRuleEngineException, BuiltInException
	{
		getTargetRuleEngine().defineSQWRLQuery(query);
	}

	private TargetRuleEngine getTargetRuleEngine() throws SWRLRuleEngineException
	{
		if (this.targetRuleEngine == null)
			throw new SWRLRuleEngineException("no target rule engine specified");

		return this.targetRuleEngine;
	}

	private void exportOWLAxioms2TargetRuleEngine(Set<OWLAxiomAdapter> axioms) throws SWRLRuleEngineException,
			TargetRuleEngineException
	{
		for (OWLAxiomAdapter axiom : axioms) {
			if (!this.exportedOWLAxioms.contains(axiom))
				getTargetRuleEngine().defineOWLAxiom(axiom);
		}
	}

	private void writeOWLAxioms2OWLOntology(Set<OWLAxiomAdapter> axioms) throws SWRLRuleEngineException
	{
		try {
			for (OWLAxiomAdapter axiom : axioms)
				getOWLOntology().putOWLAxiom(axiom);
		} catch (RuntimeException e) {
			throw new SWRLRuleEngineException("Error writing OWL axioms", e);
		}
	}

	private SWRLAPIOWLOntology getOWLOntology()
	{
		return this.owlOntology;
	}

	private SWRLOntologyProcessor getSWRLAPIOntologyProcessor()
	{
		return this.swrlOntologyProcessor;
	}

	private SWRLBuiltInBridgeController getBuiltInBridgeController()
	{
		return this.builtInBridgeController;
	}
}
