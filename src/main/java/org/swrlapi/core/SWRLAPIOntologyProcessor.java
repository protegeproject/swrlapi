package org.swrlapi.core;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.swrlapi.exceptions.SWRLRuleException;
import org.swrlapi.ext.SWRLAPIOWLDataFactory;
import org.swrlapi.ext.SWRLAPIOWLOntology;
import org.swrlapi.ext.SWRLAPIRule;
import org.swrlapi.sqwrl.SQWRLQuery;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.SQWRLResultGenerator;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

/**
 * This interface defines a processor processes an SWRLAPI-based OWL ontology (represented by the interface
 * {@link SWRLAPIOWLOntology} and provides methods manage the SWRL rules and SQWRL queries in that ontology. The
 * processor should also process SQWRL queries (which are serialized as SWRL rules in an ontology). SQWRL query
 * management functionality includes managing query results and result generators.
 * <p>
 * Implementations may decide to optimize ontology processing so that, for example, only axioms relevant to the SWRL
 * rules or SQWRL queries in the ontology are extracted.
 * <p>
 * The {@link #processOntology} method should be called before any axioms are retrieved.
 * <p>
 * The {@link DefaultSWRLAPIOntologyProcessor} class provides a default implementation of this interface. Apart from
 * extracting SWRL rules and SQWRL queries, this processor also generates OWL declaration axioms for all OWL entities
 * encountered during axiom processing and records their type, IRI and prefixed names using the
 * {@link OWLNamedObjectResolver} class. This class can be used by rule engines to resolve OWL named objects using their
 * prefixed name.
 */
public interface SWRLAPIOntologyProcessor
{
	void processOntology() throws SQWRLException;

	OWLNamedObjectResolver getOWLNamedObjectResolver();

	SWRLAPIOWLDataFactory getSWRLAPIOWLDataFactory();

	Set<OWLAxiom> getOWLAxioms();

	int getNumberOfOWLAxioms();

	boolean hasAssertedOWLAxiom(OWLAxiom axiom);

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

	int getNumberOfOWLClassDeclarationAxioms();

	int getNumberOfOWLIndividualDeclarationAxioms();

	int getNumberOfOWLObjectPropertyDeclarationAxioms();

	int getNumberOfOWLDataPropertyDeclarationAxioms();
}
