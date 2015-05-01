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
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.SQWRLResultGenerator;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.ui.model.SWRLAutoCompleter;

/**
 * Wraps the OWLAPI's {@link org.semanticweb.owlapi.model.OWLOntology} class with additional functionality used by the
 * SWRLAPI. Primarily it provides methods for dealing with SWRL rules and SQWRL queries.
 * 
 * The {@link org.swrlapi.core.SWRLAPIRule} class provides an equivalent wrapping of the OWLAPI's
 * {@link org.semanticweb.owlapi.model.SWRLRule}. The SWRLAPI also provides a range of types extending the OWLAPI's
 * {@link org.semanticweb.owlapi.model.SWRLDArgument} interface to define arguments to built-in atoms. This extension
 * point is defined by the {@link org.swrlapi.builtins.arguments.SWRLBuiltInArgument} interface, which extends the
 * OWLAPI's {@link org.semanticweb.owlapi.model.SWRLDArgument} interface. A {@link org.swrlapi.core.SWRLAPIOWLOntology}
 * will construct SWRLAPI rules from the SWRL rules in an OWLAPI-based ontology to contain these additional built-in
 * argument types.
 * <p>
 * The {@link #startBulkConversion()}, {@link #completeBulkConversion()}, {@link #hasOntologyChanged()}, and
 * {@link #resetOntologyChanged()} methods can be used for optimization purposes. For example, in the Protege-OWL API
 * the {@link #startBulkConversion()} method turns off listener notification so that bulk transfer of OWL axioms can be
 * performed more efficiently. The {@link #hasOntologyChanged()} method can be used by rule engines to avoid unnecessary
 * regeneration of knowledge.
 *
 * @see org.swrlapi.core.SWRLAPIRule
 * @see org.swrlapi.sqwrl.SQWRLQuery
 */
public interface SWRLAPIOWLOntology
{
	// Methods for handling SWRL Rules

	Set<SWRLAPIRule> getSWRLRules();

	SWRLAPIRule getSWRLRule(String ruleName) throws SWRLRuleException;

	SWRLAPIRule createSWRLRule(String ruleName, String rule) throws SWRLParseException;

	SWRLAPIRule createSWRLRule(String ruleName, String rule, String comment, boolean isActive) throws SWRLParseException;

	void deleteSWRLRule(String ruleName);

	boolean isSWRLBuiltIn(IRI iri); // The SWRLAPI provides built-ins beyond the core set defined in the SWRL submission.

	void addSWRLBuiltIn(IRI iri);

	Set<IRI> getSWRLBuiltInIRIs();

	SWRLParser createSWRLParser();

	SWRLAutoCompleter createSWRLAutoCompleter();

	SWRLRuleRenderer createSWRLRuleRenderer();

	// Methods for handling SQWRL Queries

	SQWRLQuery createSQWRLQuery(String queryName, String query) throws SWRLParseException, SQWRLException;

	SQWRLQuery createSQWRLQuery(String queryName, String query, String comment, boolean isActive)
			throws SWRLParseException, SQWRLException;

	SQWRLQuery getSQWRLQuery(String queryName) throws SQWRLException;

	SQWRLResult getSQWRLResult(String queryName) throws SQWRLException;

	int getNumberOfSQWRLQueries();

	Set<String> getSQWRLQueryNames();

	Set<SQWRLQuery> getSQWRLQueries();

	SQWRLResultGenerator getSQWRLResultGenerator(String queryName) throws SQWRLException;

	SQWRLResultGenerator createSQWRLResultGenerator();

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
