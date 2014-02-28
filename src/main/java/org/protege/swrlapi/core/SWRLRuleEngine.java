package org.protege.swrlapi.core;

import java.util.Set;

import org.protege.owl.portability.axioms.OWLAxiomAdapter;
import org.protege.swrlapi.exceptions.SWRLRuleEngineException;
import org.protege.swrlapi.ext.SWRLAPIRule;
import org.protege.swrlapi.owl2rl.OWL2RLEngine;
import org.protege.swrlapi.sqwrl.SQWRLQueryEngine;

/**
 * This interface defines methods that must be provided by a SWRL rule engine.
 * <p>
 * Detailed documentation for this mechanism can be found <a
 * href="http://protege.cim3.net/cgi-bin/wiki.pl?SWRLRuleEngineBridgeFAQ">here</a>.
 */
public interface SWRLRuleEngine extends SQWRLQueryEngine
{
	/**
	 * Load rules and knowledge from OWL, send them to the rule engine, run the rule engine, and write any inferred
	 * knowledge back to OWL.
	 */
	void infer() throws SWRLRuleEngineException;

	/**
	 * Load rules and relevant knowledge from OWL. All existing bridge rules and knowledge will first be cleared and the
	 * associated rule engine will be reset.
	 */
	void importSWRLRulesAndOWLKnowledge() throws SWRLRuleEngineException;

	/**
	 * Load specific query, all enabled rules, and relevant knowledge from OWL. All existing bridge rules and knowledge
	 * will first be cleared and the rule engine will be reset.
	 */
	void importSQWRLQueryAndOWLKnowledge(String queryName) throws SWRLRuleEngineException;

	/**
	 * Run the rule engine.
	 */
	void run() throws SWRLRuleEngineException;

	/**
	 * Write knowledge inferred by rule engine back to OWL ontology.
	 */
	void writeInferredKnowledge() throws SWRLRuleEngineException;

	/**
	 * Reset the rule engine.
	 */
	@Override
	void reset() throws SWRLRuleEngineException;

	/**
	 * Get the underlying controller for the OWL 2 RL reasoner used by the rule and query engine.
	 */
	@Override
	OWL2RLEngine getOWL2RLEngine();

	@Override
	String getTargetRuleEngineName();

	@Override
	String getTargetRuleEngineVersion();

	// Convenience methods to display rule engine activity

	Set<SWRLAPIRule> getSWRLRules();

	Set<OWLAxiomAdapter> getAssertedOWLAxioms();

	Set<OWLAxiomAdapter> getInferredOWLAxioms();

	Set<OWLAxiomAdapter> getInjectedOWLAxioms();

	int getNumberOfImportedSWRLRules();

	int getNumberOfAssertedOWLAxioms();

	int getNumberOfInferredOWLAxioms();

	int getNumberOfInjectedOWLAxioms();

	int getNumberOfAssertedOWLClassDeclarationAxioms();

	int getNumberOfAssertedOWLIndividualDeclarationsAxioms();

	int getNumberOfAssertedOWLObjectPropertyDeclarationAxioms();

	int getNumberOfAssertedOWLDataPropertyDeclarationAxioms();
}
