package org.swrlapi.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.swrlapi.builtins.SWRLBuiltInLibraryManager;
import org.swrlapi.exceptions.SWRLBuiltInException;
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
import org.swrlapi.ui.model.SWRLRuleEngineModel;

import java.util.Optional;
import java.util.Set;

/**
 * Wraps the OWLAPI's {@link org.semanticweb.owlapi.model.OWLOntology} class with additional functionality used by the
 * SWRLAPI. Primarily it provides methods for dealing with SWRL rules and SQWRL queries.
 * <p>
 * The {@link org.swrlapi.core.SWRLAPIRule} class provides an equivalent wrapping of the OWLAPI's
 * {@link org.semanticweb.owlapi.model.SWRLRule}. The SWRLAPI also provides a range of types extending the OWLAPI's
 * {@link org.semanticweb.owlapi.model.SWRLDArgument} interface to define arguments to built-in atoms. This extension
 * point is defined by the {@link org.swrlapi.builtins.arguments.SWRLBuiltInArgument} interface, which extends the
 * OWLAPI's {@link org.semanticweb.owlapi.model.SWRLDArgument} interface. A {@link org.swrlapi.core.SWRLAPIOWLOntology}
 * will construct SWRLAPI rules from the SWRL rules in an OWLAPI-based ontology to contain these additional built-in
 * argument types.
 * <p>
 * The {@link #startEventFreezeMode()}, {@link #finishEventFreezeMode()}, {@link #hasOntologyChanged()}, and
 * {@link #resetOntologyChanged()} methods can be used for optimization purposes. For example, in the Protege-OWL API
 * the {@link #startEventFreezeMode()} method turns off listener notification so that bulk transfer of OWL axioms can be
 * performed more efficiently. The {@link #hasOntologyChanged()} method can be used by rule engines to avoid unnecessary
 * regeneration of knowledge.
 *
 * @see org.swrlapi.core.SWRLAPIRule
 * @see org.swrlapi.sqwrl.SQWRLQuery
 * @see org.semanticweb.owlapi.model.OWLOntology
 */
public interface SWRLAPIOWLOntology
{
  /**
   *
   * @param swrlRuleEngineModel A SWRL rule engine model
   */
  void registerRuleEngineModel(SWRLRuleEngineModel swrlRuleEngineModel);

  /**
   *
   * @param swrlRuleEngineModel A SWRL rule engine model
   */
  void unregisterRuleEngineModel(SWRLRuleEngineModel swrlRuleEngineModel);


  // Methods for handling SWRL Rules

  @NonNull Set<@NonNull SWRLAPIRule> getSWRLRules();

  @NonNull Optional<@NonNull SWRLAPIRule> getSWRLRule(@NonNull String ruleName) throws SWRLRuleException;

  /**
   * @param ruleName The name of the rule
   * @param rule     Rule text
   * @return The rule representation
   * @throws SWRLParseException If an error occurs during parsing
   */
  @NonNull SWRLAPIRule createSWRLRule(@NonNull String ruleName, @NonNull String rule) throws SWRLParseException,
    SWRLBuiltInException;

  /**
   * @param ruleName The name of the rule
   * @param rule     Rule text
   * @param comment  A comment associated with the rule
   * @param isActive Is the rule active
   * @return The rule representation
   * @throws SWRLParseException If an error occurs during parsing
   */
  @NonNull SWRLAPIRule createSWRLRule(@NonNull String ruleName, @NonNull String rule, @NonNull String comment,
      boolean isActive) throws SWRLParseException, SWRLBuiltInException;

  /**
   *
   * @param originalRuleName The original name of the rule
   * @param ruleName The new name of the rule
   * @param rule     The rule text
   * @param comment  A comment associated with the rule
   * @param isActive Is the rule active
   * @throws SWRLParseException If a parse error occurs
   */
  void replaceSWRLRule(@NonNull String originalRuleName, @NonNull String ruleName, @NonNull String rule,
    @NonNull String comment, boolean isActive) throws SWRLParseException, SWRLBuiltInException;

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

  boolean isSWRLBuiltInIRI(@NonNull IRI iri);

  boolean isSWRLBuiltIn(@NonNull String prefixedName);

  @NonNull Set<@NonNull IRI> getSWRLBuiltInIRIs();

  Optional<@NonNull IRI> swrlBuiltInPrefixedName2IRI(@NonNull String prefixedName);

  @NonNull SWRLParser createSWRLParser();

  @NonNull SWRLAutoCompleter createSWRLAutoCompleter();

  @NonNull SWRLRuleRenderer createSWRLRuleRenderer();

  @NonNull SWRLBuiltInLibraryManager getSWRLBuiltInLibraryManager();

  /**
   * @return A rule name
   */
  @NonNull Optional<String> getNextRuleName();

  // Methods for handling SQWRL Queries

  @NonNull SQWRLQuery createSQWRLQuery(@NonNull String queryName, @NonNull String query)
      throws SWRLParseException, SQWRLException, SWRLBuiltInException;

  @NonNull SQWRLQuery createSQWRLQuery(@NonNull String queryName, @NonNull String query, @NonNull String comment,
      boolean isActive) throws SWRLParseException, SQWRLException, SWRLBuiltInException;

  @NonNull SQWRLResult getSQWRLResult(@NonNull String queryName) throws SQWRLException;

  @NonNull Set<@NonNull String> getSQWRLQueryNames();

  @NonNull Set<@NonNull SQWRLQuery> getSQWRLQueries();

  @NonNull SQWRLResultGenerator getSQWRLResultGenerator(@NonNull String queryName) throws SQWRLException;

  @NonNull SQWRLResultGenerator createSQWRLResultGenerator();

  @NonNull SQWRLQueryRenderer createSQWRLQueryRenderer();

  // Process methods

  void reset();

  void processOntology() throws SQWRLException, SWRLBuiltInException;

  // Optimization methods

  void startEventFreezeMode(); // Can be used, for example, to switch off notification during bulk conversion.

  void finishEventFreezeMode();

  boolean hasOntologyChanged();

  void resetOntologyChanged();

  // Axiom counting methods

  boolean hasAssertedOWLAxiom(OWLAxiom axiom);

  @NonNull Set<@NonNull OWLAxiom> getOWLAxioms();

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

  @NonNull OWLOntology getOWLOntology();

  @NonNull OWLDataFactory getOWLDataFactory();
}
