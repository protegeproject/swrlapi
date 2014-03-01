package org.protege.swrlapi.core;

import java.util.Set;

import org.protege.swrlapi.exceptions.SWRLRuleException;
import org.protege.swrlapi.ext.SWRLAPIOWLOntology;
import org.protege.swrlapi.ext.SWRLAPIRule;
import org.protege.swrlapi.sqwrl.SQWRLQuery;
import org.protege.swrlapi.sqwrl.SQWRLResult;
import org.protege.swrlapi.sqwrl.SQWRLResultGenerator;
import org.protege.swrlapi.sqwrl.exceptions.SQWRLException;
import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * This interface defines a processor that is used by SWRL rule and SQWRL query engines to analyze an ontology (defined
 * by the interface {@link SWRLAPIOWLOntology}). The main function of a processor is to manage the SWRL rules and SQWRL
 * queries in an ontology. The processor should extract SQWRL queries (which are serialized as SWRL rules in an
 * ontology). SQWRL query management functionality includes managing query results and result generators.
 * <p>
 * Implementations may decide to optimize ontology processing so that, for example, only axioms relevant to the SWRL
 * rules or SQWRL queries in the ontology are extracted.
 * <p>
 * The {@link #processOntology} method should be called before any axioms are retrieved.
 * <p>
 * The {@link DefaultSWRLOntologyProcessor} class provides a default implementation of this interface. Apart from
 * extracting SWRL rules and SQWRL queries, this processor also generates OWL declaration axioms for all OWL entities
 * encountered during axiom processing and records their type, URI and prefixed names using the
 * {@link OWLNamedObjectResolver} class. This class can be used by rule engines to keep track of OWL entities.
 */
public interface SWRLOntologyProcessor
{
	void processOntology() throws SQWRLException;

	/**
	 * Get all OWL axioms in processed ontology (which will include SWRL rules);
	 */
	Set<OWLAxiom> getOWLAxioms();

	int getNumberOfOWLAxioms();

	boolean hasOWLAxiom(OWLAxiom axiom);

	int getNumberOfOWLClassDeclarationAxioms();

	int getNumberOfOWLIndividualDeclarationAxioms();

	int getNumberOfOWLObjectPropertyDeclarationAxioms();

	int getNumberOfOWLDataPropertyDeclarationAxioms();

	OWLNamedObjectResolver getOWLNamedObjectResolver();

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
}
