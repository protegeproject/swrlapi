package org.swrlapi.core;

import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.swrlapi.exceptions.SWRLRuleEngineException;
import org.swrlapi.exceptions.SWRLRuleException;
import org.swrlapi.owl2rl.OWL2RLEngine;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.parser.SWRLParser;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.ui.model.SWRLAutoCompleter;

/**
 * This interface defines methods that must be provided by a SWRL rule engine in the SWRLAPI.
 * <p>
 * A native rule engine implementation must implement the {@link org.swrlapi.bridge.TargetSWRLRuleEngine} interface.
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
	 *
	 * @throws SWRLRuleEngineException If an error occurs during inference
	 */
	void infer() throws SWRLRuleEngineException;

	/**
	 * Load rules and relevant knowledge from OWL. All existing bridge rules and knowledge will first be cleared and the
	 * associated rule engine will be reset.
	 *
	 * @throws SWRLRuleEngineException If an error occurs during inference
	 */
	void importSWRLRulesAndOWLKnowledge() throws SWRLRuleEngineException;

	/**
	 * Load specific query, all enabled rules, and relevant knowledge from OWL. All existing bridge rules and knowledge
	 * will first be cleared and the rule engine will be reset.
	 *
	 * @param queryName The name of the query
	 * @throws SWRLRuleEngineException If an error occurs during inference
	 */
	void importSQWRLQueryAndOWLKnowledge(String queryName) throws SWRLRuleEngineException;

	/**
	 * Run the rule engine.
	 *
	 * @throws SWRLRuleEngineException If an error occurs during inference
	 */
	void run() throws SWRLRuleEngineException;

	/**
	 * Write knowledge inferred by rule engine back to OWL ontology.
	 *
	 * @throws SWRLRuleEngineException If an error occurs during inference
	 */
	void writeInferredKnowledge() throws SWRLRuleEngineException;

	/**
	 * Reset the rule engine.
	 */
	@Override
	void reset();

	/**
	 * @return A collection of SWRL rules
	 */
	Set<SWRLAPIRule> getSWRLRules();

	/**
	 * @param ruleName The name of the rule
	 * @return A SWRL rule
	 * @throws SWRLRuleException If the rule of the specified name does not exist
	 */
	SWRLAPIRule getSWRLRule(String ruleName) throws SWRLRuleException;

	/**
	 * @param ruleName The name of the rule
	 * @param rule The rule text
	 * @return A SWRL rule
	 * @throws SWRLParseException If an error occurs during parsing
	 */
	SWRLAPIRule createSWRLRule(String ruleName, String rule) throws SWRLParseException;

	/**
	 * @param ruleName The name of the rule
	 * @param rule The rule text
	 * @param comment A comment associated with the rule
	 * @param isActive Is the rule active
	 * @return A SWRL rule
	 * @throws SWRLParseException If an error occurs during parsing
	 */
	SWRLAPIRule createSWRLRule(String ruleName, String rule, String comment, boolean isActive) throws SWRLParseException;

	/**
	 * @param ruleName The name of a rule
	 */
	void deleteSWRLRule(String ruleName);

	/**
	 * @param iri An IRI
	 * @return True if the IRI is a built-in
	 */
	boolean isSWRLBuiltIn(IRI iri);

	/**
	 * @param iri The IRI of a built-in
	 */
	void addSWRLBuiltIn(IRI iri);

	/**
	 * @return The IRIs of all SWRL built-ins
	 */
	Set<IRI> getSWRLBuiltInIRIs();

	/**
	 * @return A SWRL parser
	 */
	SWRLParser createSWRLParser();

	/**
	 * @return A SWRL rule auto-completer
	 */
	SWRLAutoCompleter createSWRLAutoCompleter();

	/**
	 * @return A SWRL rule renderer
	 */
	SWRLRuleRenderer createSWRLRuleRenderer();

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
	 *
	 * @return An OWL reasoner
	 */
	OWLReasoner getOWLReasoner();

	// The following are convenience methods to display rule engine activity

	/**
	 * @return A collection of OWL axioms
	 */
	Set<OWLAxiom> getAssertedOWLAxioms();

	/**
	 * @return A collection of OWL axioms
	 */
	Set<OWLAxiom> getInferredOWLAxioms();

	/**
	 * @return A collection of OWL axioms
	 */
	Set<OWLAxiom> getInjectedOWLAxioms();

	/**
	 * @return The number of imported SWRL rules
	 */
	int getNumberOfImportedSWRLRules();

	/**
	 * @return The number of asserted OWL axioms
	 */
	int getNumberOfAssertedOWLAxioms();

	/**
	 * @return The number of inferred OWL axioms
	 */
	int getNumberOfInferredOWLAxioms();

	/**
	 * @return The number of injected OWL axioms
	 */
	int getNumberOfInjectedOWLAxioms();

	/**
	 * @return The number of asserted OWL classdeclaration axioms
	 */
	int getNumberOfAssertedOWLClassDeclarationAxioms();

	/**
	 * @return The number of asserted OWL individual declaration axioms
	 */
	int getNumberOfAssertedOWLIndividualDeclarationsAxioms();

	/**
	 * @return The number of asserted OWL object property declaration axioms
	 */
	int getNumberOfAssertedOWLObjectPropertyDeclarationAxioms();

	/**
	 * @return The number of asserted OWL data property declaration axioms
	 */
	int getNumberOfAssertedOWLDataPropertyDeclarationAxioms();
}
