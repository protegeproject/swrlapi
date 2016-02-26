package org.swrlapi.ui.model;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.core.SWRLRuleRenderer;
import org.swrlapi.parser.SWRLParser;

/**
 * Provides a model that can be used to build an MVC-based GUI that uses a SWRL rule engine.
 */
public interface SWRLRuleEngineModel extends OntologyModel
{
  /**
   * Update the model's rule engine
   *
   * @param ruleEngine A new SWRL rule engine
   */
 void updateModel(@NonNull SWRLRuleEngine ruleEngine);

  /**
   * @return A SWRL rule engine
   */
  @NonNull SWRLRuleEngine getSWRLRuleEngine();

  /**
   * @return A SWRL parser
   */
  @NonNull SWRLParser createSWRLParser();

  /**
   * @return A SWRL auto-completer
   */
  @NonNull SWRLAutoCompleter createSWRLAutoCompleter();

  /**
   * @return A SWRL rule renderer
   */
  @NonNull SWRLRuleRenderer createSWRLRuleRenderer();

  /**
   * @return A SWRL rules table model
   */
  @NonNull SWRLRulesAndSQWRLQueriesTableModel getSWRLRulesTableModel();

  /**
   * @return An OWL 2 RL model
   */
  @NonNull OWL2RLModel getOWL2RLModel();

  /**
   * @return True if the rules in the underlying ontology have been modified since the last call to
   * {@link SWRLRuleEngineModel#clearSWRLRulesModified()}.
   */
  boolean areSWRLRulesModified();

  /**
   * Clear the modified status of SWRL rules. Used in conjunction with {@link #areSWRLRulesModified()}.
   */
  void clearSWRLRulesModified();
}
