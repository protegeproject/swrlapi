package org.swrlapi.core;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.swrlapi.exceptions.SWRLRuleEngineException;
import org.swrlapi.owl2rl.OWL2RLEngine;
import org.swrlapi.sqwrl.SQWRLQueryEngine;

/**
 * This interface defines methods that must be provided by a SWRL rule engine in the SWRLAPI.
 * <P>
 * A native rule engine implementation must implement the {@link org.swrlapi.bridge.TargetSWRLRuleEngine}
 * interface.
 *
 * @see org.swrlapi.core.SWRLAPIRule
 * @see org.swrlapi.bridge.TargetSWRLRuleEngine
 * @see org.swrlapi.core.impl.DefaultSWRLRuleEngine
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
	void reset();

	/**
	 * Get the underlying OWL 2 RL reasoner used by the rule and query engine.
	 */
	@Override
	OWL2RLEngine getOWL2RLEngine();

	/**
	 * Get the name of the native rule engine implementing this SWRL rule engine.
	 */
	@Override
	String getTargetRuleEngineName();

	/**
	 * Get the version of the native rule engine implementing this SWRL rule engine.
	 */
	@Override
	String getTargetRuleEngineVersion();

	/**
	 * A rule engine must also define an {@link org.semanticweb.owlapi.reasoner.OWLReasoner}.
	 */
	OWLReasoner getOWLReasoner();

	// The following are convenience methods to display rule engine activity

	Set<SWRLAPIRule> getSWRLRules();

	Set<OWLAxiom> getAssertedOWLAxioms();

	Set<OWLAxiom> getInferredOWLAxioms();

	Set<OWLAxiom> getInjectedOWLAxioms();

	int getNumberOfImportedSWRLRules();

	int getNumberOfAssertedOWLAxioms();

	int getNumberOfInferredOWLAxioms();

	int getNumberOfInjectedOWLAxioms();

	int getNumberOfAssertedOWLClassDeclarationAxioms();

	int getNumberOfAssertedOWLIndividualDeclarationsAxioms();

	int getNumberOfAssertedOWLObjectPropertyDeclarationAxioms();

	int getNumberOfAssertedOWLDataPropertyDeclarationAxioms();
}
