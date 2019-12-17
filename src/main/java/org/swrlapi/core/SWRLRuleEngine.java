package org.swrlapi.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.exceptions.SWRLRuleEngineException;
import org.swrlapi.exceptions.SWRLRuleException;
import org.swrlapi.owl2rl.OWL2RLEngine;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.parser.SWRLParser;
import org.swrlapi.ui.model.SWRLAutoCompleter;

import javax.swing.*;
import java.io.File;
import java.util.Optional;
import java.util.Set;

/**
 * This interface defines methods that must be provided by a SWRL rule engine in the SWRLAPI.
 * <p>
 * A native rule engine implementation must also implement the {@link org.swrlapi.bridge.TargetSWRLRuleEngine} interface.
 *
 * @see org.swrlapi.core.SWRLAPIRule
 * @see org.swrlapi.bridge.TargetSWRLRuleEngine
 */
public interface SWRLRuleEngine
{
  /**
   * Load rules and knowledge from OWL, send them to the rule engine, run the rule engine, and write any inferred
   * knowledge back to OWL.
   *
   * @throws SWRLRuleEngineException If an error occurs during inference
   */
  void infer() throws SWRLRuleEngineException;

  /**
   * Load asserted OWL axioms (which include SWRL rules) from a source OWL ontology.
   *
   * @throws SWRLRuleEngineException If an error occurs during inference
   */
  void importAssertedOWLAxioms() throws SWRLRuleEngineException;

  /**
   * Run the rule engine.
   *
   * @throws SWRLRuleEngineException If an error occurs during inference
   */
  void run() throws SWRLRuleEngineException;

  /**
   * Write OWL axioms inferred by rule engine back to the source OWL ontology.
   *
   * @throws SWRLRuleEngineException If an error occurs during the export process
   */
  void exportInferredOWLAxioms() throws SWRLRuleEngineException;

  /**
   * Create a SWRL rule
   *
   * @param ruleName The name of the rule
   * @param rule     The rule text
   * @return A SWRL rule
   * @throws SWRLParseException If an error occurs during parsing
   */
  @NonNull SWRLAPIRule createSWRLRule(@NonNull String ruleName, @NonNull String rule) throws SWRLParseException,
    SWRLBuiltInException;

  /**
   * Create a SWRL rule and associate a comment and active state with it.
   *
   * @param ruleName The name of the rule
   * @param rule     The rule text
   * @param comment  A comment associated with the rule
   * @param isActive Is the rule active
   * @return A SWRL rule
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
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  void replaceSWRLRule(@NonNull String originalRuleName, @NonNull String ruleName, @NonNull String rule,
    @NonNull String comment, boolean isActive) throws SWRLParseException, SWRLBuiltInException;

  /**
   * @return A collection of SWRL rules
   */
  @NonNull Set<@NonNull SWRLAPIRule> getSWRLRules();

  /**
   * @param ruleName The name of the rule
   * @return A SWRL rule
   * @throws SWRLRuleException If an error occurs
   */
  Optional<@NonNull SWRLAPIRule> getSWRLRule(@NonNull String ruleName) throws SWRLRuleException;

  /**
   * @param ruleName The name of a rule
   */
  void deleteSWRLRule(@NonNull String ruleName);

  /**
   * @param iri An IRI
   * @return True if the IRI is a built-in
   */
  boolean isSWRLBuiltInIRI(@NonNull IRI iri);

  /**
   * @param shortName An short name
   * @return True if the short name is a built-in
   */
  boolean isSWRLBuiltIn(@NonNull String shortName);

  /**
   * @return The IRIs of all SWRL built-ins
   */
  @NonNull Set<@NonNull IRI> getSWRLBuiltInIRIs();

  /**
   * @return A SWRL parser
   */
  @NonNull SWRLParser createSWRLParser();

  /**
   * @return A SWRL rule auto-completer
   */
  @NonNull SWRLAutoCompleter createSWRLAutoCompleter();

  /**
   * @return A SWRL rule renderer
   */
  @NonNull SWRLRuleRenderer createSWRLRuleRenderer();

  /**
   * Get the name of this SWRL rule engine.
   *
   * @return A rule engine name
   */
  @NonNull String getRuleEngineName();

  /**
   * Get the version of the native rule engine implementing this SWRL rule engine
   *
   * @return A rule engine version
   */
  @NonNull String getRuleEngineVersion();

  /**
   * @return The underlying OWL ontology
   */
  @NonNull OWLOntology getOWLOntology();

  /**
   * Get the underlying OWL 2 RL reasoner used by the rule and query engine
   *
   * @return An OWL 2 RL engine
   */
  @NonNull OWL2RLEngine getOWL2RLEngine();

  /**
   * A rule engine must also define an {@link org.semanticweb.owlapi.reasoner.OWLReasoner}.
   *
   * @return An OWL reasoner
   */
  @NonNull OWLReasoner getOWLReasoner();

  /**
   * Load user-defined SWRL built-in libraries
   *
   * See <a href="https://github.com/protegeproject/swrlapi/wiki/SWRLBuiltInBridge">here</a> for documentation on
   * defining these libraries.
   *
   * @param swrlBuiltInLibraryDirectory The directory containing the libraries
   */
  void loadExternalSWRLBuiltInLibraries(@NonNull File swrlBuiltInLibraryDirectory);

  /**
   * @return The rule engine's icon
   */
  @NonNull Icon getRuleEngineIcon();

  // The following are convenience methods to display rule engine activity

  /**
   * @return A collection of OWL axioms
   */
  @NonNull Set<@NonNull OWLAxiom> getAssertedOWLAxioms();

  /**
   * @return A collection of OWL axioms
   */
  @NonNull Set<@NonNull OWLAxiom> getInferredOWLAxioms();

  /**
   * @return A collection of OWL axioms
   */
  @NonNull Set<@NonNull OWLAxiom> getInjectedOWLAxioms();

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
   * @return The number of asserted OWL class declaration axioms
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

  /**
   * @return The underlying OWL ontology
   */
  @NonNull SWRLAPIOWLOntology getSWRLAPIOWLOntology();
}
