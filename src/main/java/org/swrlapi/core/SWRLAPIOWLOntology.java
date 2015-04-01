package org.swrlapi.core;

import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.core.resolvers.IRIResolver;
import org.swrlapi.exceptions.SWRLRuleException;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.parser.SWRLParser;
import org.swrlapi.sqwrl.SQWRLQuery;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.SQWRLResultGenerator;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

/**
 * Wraps the OWLAPI's {@link org.semanticweb.owlapi.model.OWLOntology} class with additional functionality used by the
 * SWRLAPI. Primarily the {@link #getSWRLAPIRules()} method extracts {@link org.swrlapi.core.SWRLAPIRule} objects from
 * an OWL ontology. This class, which extends the standard OWLAPI {@link org.semanticweb.owlapi.model.SWRLRule} class,
 * provide the richer representation of a SWRL rule required by the SWRLAPI. In particular, the SWRLAPI has a range of
 * types extending the OWLAPI's {@link org.semanticweb.owlapi.model.SWRLDArgument} interface to define arguments to
 * built-in atoms.
 * <p/>
 * This extension point is defined by the {@link org.swrlapi.builtins.arguments.SWRLBuiltInArgument} interface, which
 * extends the OWLAPI's {@link org.semanticweb.owlapi.model.SWRLDArgument} interface. A
 * {@link org.swrlapi.core.SWRLAPIOWLOntology} will construct SWRLAPI rules from the SWRL rules in an OWLAPI-based
 * ontology to contain these additional built-in argument types.
 * <p/>
 * The {@link #startBulkConversion()}, {@link #completeBulkConversion()}, {@link #hasOntologyChanged()}, and
 * {@link #resetOntologyChanged()} methods can be used for optimization purposes. For example, in the Protege-OWL API
 * the {@link #startBulkConversion()} method turns off listener notification so that bulk transfer of OWL axioms can be
 * performed more efficiently. The {@link #hasOntologyChanged()} method can be used by rule engines to avoid unnecessary
 * regeneration of knowledge.
 * <p/>
 *
 * @see org.swrlapi.core.SWRLAPIRule
 * @see org.swrlapi.core.SWRLAPIOntologyProcessor
 * @see org.swrlapi.core.SWRLAPIOWLDataFactory
 * @see org.swrlapi.builtins.arguments.SWRLBuiltInArgument
 */
public interface SWRLAPIOWLOntology
{
	// SWRL Rules

	SWRLRuleEngine createSWRLRuleEngine(SWRLRuleEngineManager.TargetSWRLRuleEngineCreator ruleEngineCreator);

	Set<SWRLAPIRule> getSWRLAPIRules();

	SWRLAPIRule getSWRLRule(String ruleName) throws SWRLRuleException;

	SWRLAPIRule createSWRLRule(String ruleName, String rule) throws SWRLParseException;

	SWRLAPIRule createSWRLRule(String ruleName, String rule, String comment, boolean isActive) throws SWRLParseException;

	void deleteSWRLRule(String ruleName);

	boolean isSWRLBuiltIn(IRI iri); // The SWRLAPI provides built-ins beyond the core set defined in the SWRL submission.

	void addSWRLBuiltIn(IRI iri);

	Set<IRI> getSWRLBuiltInIRIs();

	SWRLParser createSWRLParser();

	SWRLRuleRenderer createSWRLRuleRenderer();

	// SQWRL Queries

	SQWRLQueryEngine createSQWRLQueryEngine(SWRLRuleEngineManager.TargetSWRLRuleEngineCreator ruleEngineCreator);

	SQWRLQuery createSQWRLQuery(String queryName, String query) throws SWRLParseException, SQWRLException;

	SQWRLQuery createSQWRLQuery(String queryName, String query, String comment, boolean isActive)
			throws SWRLParseException, SQWRLException;

	int getNumberOfSQWRLQueries();

	Set<String> getSQWRLQueryNames();

	SQWRLQuery getSQWRLQuery(String queryName) throws SQWRLException;

	Set<SQWRLQuery> getSQWRLQueries();

	SQWRLResult getSQWRLResult(String queryName) throws SQWRLException;

	SQWRLResultGenerator getSQWRLResultGenerator(String queryName) throws SQWRLException;

	SQWRLQueryRenderer createSQWRLQueryRenderer();

	// Process methods

	void reset();

	void processOntology() throws SQWRLException;

	// Optimization methods

	void startBulkConversion(); // Can be used, for example, to switch off notification during bulk conversion.

	void completeBulkConversion();

	boolean hasOntologyChanged();

	void resetOntologyChanged();

	// Axiom counting methods

	boolean hasAssertedOWLAxiom(OWLAxiom axiom);

	Set<OWLAxiom> getOWLAxioms();

	int getNumberOfSWRLRules();

	int getNumberOfOWLAxioms();

	int getNumberOfOWLClassDeclarationAxioms();

	int getNumberOfOWLIndividualDeclarationAxioms();

	int getNumberOfOWLObjectPropertyDeclarationAxioms();

	int getNumberOfOWLDataPropertyDeclarationAxioms();

	// Utility methods

	SWRLAPIOWLDataFactory getSWRLAPIOWLDataFactory();

	IRIResolver getIRIResolver();

	OWLOntologyManager getOWLOntologyManager();

	DefaultPrefixManager getPrefixManager();

	OWLOntology getOWLOntology();

	OWLDataFactory getOWLDataFactory();
}
