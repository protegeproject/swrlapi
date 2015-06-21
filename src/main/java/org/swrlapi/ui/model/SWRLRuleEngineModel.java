package org.swrlapi.ui.model;

import checkers.nullness.quals.NonNull;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.core.SWRLRuleRenderer;
import org.swrlapi.parser.SWRLParser;

/**
 * Provides a model that can be used to build a MVC-based GUI that uses a SWRL rule engine. Used in conjunction with a
 * {@link org.swrlapi.ui.controller.SWRLRuleEngineController}.
 *
 * @see org.swrlapi.ui.controller.SWRLRuleEngineController
 */
public interface SWRLRuleEngineModel extends SWRLAPIModel
{
  /**
   * @return A SWRL rule engine
   */
  @NonNull SWRLRuleEngine getSWRLRuleEngine();

  /**
   * @return A SWRL parser
   */
  @NonNull SWRLParser getSWRLParser();

  /**
   * @return A SWRL auto-completer
   */
  @NonNull SWRLAutoCompleter getSWRLAutoCompleter();

  /**
   *
   * @return A SWRL rule renderer
   */
  @NonNull SWRLRuleRenderer getSWRLRuleRenderer();

  /**
   * @return A SWRL rules table model
   */
  @NonNull SWRLRulesTableModel getSWRLRulesTableModel();

  /**
   *
   * @return An OWL 2 RL model
   */
  @NonNull OWL2RLModel getOWL2RLModel();

  /**
   * @return True if the rules in the underlying ontology have been modified since the last call to
   *         {@link SWRLRuleEngineModel#clearSWRLRulesModified()}.
   */
  boolean areSWRLRulesModified();

  /**
   * Clear the modified status of SWRL rules. Used in conjunction with {@link #areSWRLRulesModified()}.
   */
  void clearSWRLRulesModified();
}
