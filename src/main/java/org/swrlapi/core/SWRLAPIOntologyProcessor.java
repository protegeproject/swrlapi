package org.swrlapi.core;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.swrlapi.exceptions.SWRLRuleException;
import org.swrlapi.sqwrl.SQWRLQuery;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.SQWRLResultGenerator;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

/**
 * This interface defines a processor processes an SWRLAPI-based OWL ontology (represented by the interface
 * {@link org.swrlapi.core.SWRLAPIOWLOntology}) and provides methods manage the SWRL rules and SQWRL queries in
 * that ontology. The processor can also extract SQWRL queries (which are serialized as SWRL rules) from on ontology.
 * SQWRL query management functionality includes managing query results and result generators.
 * <p>
 * Implementations may decide to optimize ontology processing so that, for example, only axioms relevant to the SWRL
 * rules or SQWRL queries in the ontology are extracted.
 * <p>
 * The {@link #processOntology()} method should be called before any axioms are retrieved. It will throw a
 * {@link org.swrlapi.sqwrl.exceptions.SQWRLException} if it finds invalid SQWRL queries in the ontology.
 * <p>
 * In addition to extracting SWRL rules and SQWRL queries, this processor also generates OWL declaration axioms for all
 * OWL properties encountered during axiom processing and records their type, IRI and prefixed names using the
 * {@link org.swrlapi.core.resolvers.IRIResolver} class. This class can be used by rule engines to resolve OWL
 * named objects using their short name.
 * 
 * @see org.swrlapi.core.SWRLAPIRule
 * @see org.swrlapi.sqwrl.SQWRLQuery
 */
public interface SWRLAPIOntologyProcessor
{
	void reset();

	void processOntology() throws SQWRLException;

	int getNumberOfSWRLRules();

	Set<String> getSWRLRuleNames();

	Set<SWRLAPIRule> getSWRLRules();

	SWRLAPIRule getSWRLRule(String ruleName) throws SWRLRuleException;

	int getNumberOfSQWRLQueries();

	Set<String> getSQWRLQueryNames();

	SQWRLQuery getSQWRLQuery(String queryName) throws SQWRLException;

	Set<SQWRLQuery> getSQWRLQueries();

	SQWRLResult getSQWRLResult(String queryName) throws SQWRLException;

	SQWRLResultGenerator getSQWRLResultGenerator(String queryName) throws SQWRLException;

	Set<OWLAxiom> getOWLAxioms();

	int getNumberOfOWLAxioms();

	boolean hasAssertedOWLAxiom(OWLAxiom axiom);

	int getNumberOfOWLClassDeclarationAxioms();

	int getNumberOfOWLIndividualDeclarationAxioms();

	int getNumberOfOWLObjectPropertyDeclarationAxioms();

	int getNumberOfOWLDataPropertyDeclarationAxioms();
}
