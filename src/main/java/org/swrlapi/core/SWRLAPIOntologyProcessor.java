package org.swrlapi.core;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.swrlapi.exceptions.SWRLRuleException;
import org.swrlapi.factory.DefaultIRIResolver;
import org.swrlapi.sqwrl.SQWRLQuery;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.SQWRLResultGenerator;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

import java.util.Set;

/**
 * This interface defines a processor that processes a SWRLAPI-based OWL ontology (represented by the interface
 * {@link org.swrlapi.core.SWRLAPIOWLOntology}) and provides methods to manage the SWRL rules and SQWRL queries in that
 * ontology. The processor can also extract SQWRL queries (which are serialized as SWRL rules) from the ontology. SQWRL
 * query management functionality includes managing query results and result generators.
 * <p>
 * Implementations may decide to optimize ontology processing so that, for example, only axioms relevant to the SWRL
 * rules or SQWRL queries in the ontology are extracted.
 * <p>
 * The {@link #processOntology()} method should be called before any axioms are retrieved. It will throw a
 * {@link org.swrlapi.sqwrl.exceptions.SQWRLException} if it finds invalid SQWRL queries in the ontology.
 * <p>
 * In addition to extracting SWRL rules and SQWRL queries, this processor also generates OWL declaration axioms for all
 * OWL properties encountered during axiom processing and records their type, IRI and prefixed names using the
 * {@link DefaultIRIResolver} class. This class can be used by rule engines to resolve OWL named
 * objects using their short name.
 *
 * @see org.swrlapi.core.SWRLAPIRule
 * @see org.swrlapi.sqwrl.SQWRLQuery
 */
public interface SWRLAPIOntologyProcessor
{
  void processOntology() throws SQWRLException;

  void reset();

  // SWRL rule-related methods

  int getNumberOfSWRLRules();

  void addSWRLRule(@NonNull SWRLAPIRule rule, @NonNull SWRLRule owlapiRule);

  @NonNull SWRLAPIRule getSWRLRule(@NonNull String ruleName) throws SWRLRuleException;

  @NonNull String getComment(@NonNull SWRLRule rule);

  @NonNull String getRuleName(@NonNull SWRLRule rule);

  boolean getIsActive(@NonNull SWRLRule rule);

  void deleteSWRLRule(@NonNull String ruleName);

  // SQWRL query-related methods

  @NonNull SQWRLQuery createSWRLQueryFromSWRLRule(@NonNull SWRLAPIRule rule) throws SQWRLException;

  int getNumberOfSQWRLQueries();

  @NonNull Set<String> getSQWRLQueryNames();

  @NonNull SQWRLQuery getSQWRLQuery(@NonNull String queryName) throws SQWRLException;

  @NonNull Set<SQWRLQuery> getSQWRLQueries();

  @NonNull SQWRLResult getSQWRLResult(@NonNull String queryName) throws SQWRLException;

  @NonNull SQWRLResultGenerator getSQWRLResultGenerator(@NonNull String queryName) throws SQWRLException;

  // OWL axiom-releated methods

  @NonNull Set<OWLAxiom> getOWLAxioms();

  int getNumberOfOWLAxioms();

  boolean hasAssertedOWLAxiom(OWLAxiom axiom);

  int getNumberOfOWLClassDeclarationAxioms();

  int getNumberOfOWLIndividualDeclarationAxioms();

  int getNumberOfOWLObjectPropertyDeclarationAxioms();

  int getNumberOfOWLDataPropertyDeclarationAxioms();
}
