package org.swrlapi.core;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.exceptions.SWRLRuleException;
import org.swrlapi.factory.SWRLAPIOWLDataFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.parser.SWRLParser;
import org.swrlapi.sqwrl.SQWRLQuery;
import org.swrlapi.sqwrl.SQWRLQueryRenderer;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.SQWRLResultGenerator;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.ui.model.SWRLAutoCompleter;

import java.util.Optional;
import java.util.Set;

/**
 * Wraps the OWLAPI's {@link org.semanticweb.owlapi.model.OWLOntology} class with additional functionality used by the
 * SWRLAPI. Primarily it provides methods for dealing with SWRL rules and SQWRL queries.
 * <p/>
 * The {@link org.swrlapi.core.SWRLAPIRule} class provides an equivalent wrapping of the OWLAPI's
 * {@link org.semanticweb.owlapi.model.SWRLRule}. The SWRLAPI also provides a range of types extending the OWLAPI's
 * {@link org.semanticweb.owlapi.model.SWRLDArgument} interface to define arguments to built-in atoms. This extension
 * point is defined by the {@link org.swrlapi.builtins.arguments.SWRLBuiltInArgument} interface, which extends the
 * OWLAPI's {@link org.semanticweb.owlapi.model.SWRLDArgument} interface. A {@link org.swrlapi.core.SWRLAPIOWLOntology}
 * will construct SWRLAPI rules from the SWRL rules in an OWLAPI-based ontology to contain these additional built-in
 * argument types.
 * <p/>
 * The {@link #startBulkConversion()}, {@link #completeBulkConversion()}, {@link #hasOntologyChanged()}, and
 * {@link #resetOntologyChanged()} methods can be used for optimization purposes. For example, in the Protege-OWL API
 * the {@link #startBulkConversion()} method turns off listener notification so that bulk transfer of OWL axioms can be
 * performed more efficiently. The {@link #hasOntologyChanged()} method can be used by rule engines to avoid unnecessary
 * regeneration of knowledge.
 *
 * @see org.swrlapi.core.SWRLAPIRule
 * @see org.swrlapi.sqwrl.SQWRLQuery
 * @see org.semanticweb.owlapi.model.OWLOntology
 */
public interface SWRLAPIOWLOntology
{
  // Methods for handling SWRL Rules

  @NonNull Set<SWRLAPIRule> getSWRLRules();

  Optional<SWRLAPIRule> getSWRLRule(@NonNull String ruleName) throws SWRLRuleException;

  /**
   * @param ruleName The name of the rule
   * @param rule     Rule text
   * @return The rule representation
   * @throws SWRLParseException If an error occurs during parsing
   */
  @NonNull SWRLAPIRule createSWRLRule(@NonNull String ruleName, @NonNull String rule) throws SWRLParseException;

  /**
   * @param ruleName The name of the rule
   * @param rule     Rule text
   * @param comment  A comment associated with the rule
   * @param isActive Is the rule active
   * @return The rule representation
   * @throws SWRLParseException If an error occurs during parsing
   */
  @NonNull SWRLAPIRule createSWRLRule(@NonNull String ruleName, @NonNull String rule, @NonNull String comment,
      boolean isActive) throws SWRLParseException;

  void deleteSWRLRule(@NonNull String ruleName);

  /**
   * @param ruleName      The name of the rule
   * @param comment       A comment for the rule
   * @param isRuleEnabled Is the rule enabled
   * @return A set of rule annotations
   */
  @NonNull Set<@NonNull OWLAnnotation> generateRuleAnnotations(@NonNull String ruleName, @NonNull String comment,
      boolean isRuleEnabled);

  // The SWRLAPI provides built-ins beyond the core set defined in the SWRL submission.

  boolean isSWRLBuiltIn(@NonNull IRI iri);

  void addSWRLBuiltIn(@NonNull IRI iri);

  @NonNull Set<IRI> getSWRLBuiltInIRIs();

  @NonNull SWRLParser createSWRLParser();

  @NonNull SWRLAutoCompleter createSWRLAutoCompleter();

  @NonNull SWRLRuleRenderer createSWRLRuleRenderer();

  // Methods for handling SQWRL Queries

  @NonNull SQWRLQuery createSQWRLQuery(@NonNull String queryName, @NonNull String query)
      throws SWRLParseException, SQWRLException;

  @NonNull SQWRLQuery createSQWRLQuery(@NonNull String queryName, @NonNull String query, @NonNull String comment,
      boolean isActive) throws SWRLParseException, SQWRLException;

  @NonNull SQWRLResult getSQWRLResult(@NonNull String queryName) throws SQWRLException;

  @NonNull Set<@NonNull String> getSQWRLQueryNames();

  @NonNull Set<SQWRLQuery> getSQWRLQueries();

  @NonNull SQWRLResultGenerator getSQWRLResultGenerator(@NonNull String queryName) throws SQWRLException;

  @NonNull SQWRLResultGenerator createSQWRLResultGenerator();

  @NonNull SQWRLQueryRenderer createSQWRLQueryRenderer();

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

  @NonNull Set<OWLAxiom> getOWLAxioms();

  int getNumberOfSWRLRules();

  int getNumberOfOWLAxioms();

  int getNumberOfOWLClassDeclarationAxioms();

  int getNumberOfOWLIndividualDeclarationAxioms();

  int getNumberOfOWLObjectPropertyDeclarationAxioms();

  int getNumberOfOWLDataPropertyDeclarationAxioms();

  // Utility methods

  @NonNull SWRLAPIOWLDataFactory getSWRLAPIOWLDataFactory();

  @NonNull IRIResolver getIRIResolver();

  @NonNull OWLOntologyManager getOWLOntologyManager();

  @NonNull DefaultPrefixManager getPrefixManager();

  @NonNull OWLOntology getOWLOntology();

  @NonNull OWLDataFactory getOWLDataFactory();
}
