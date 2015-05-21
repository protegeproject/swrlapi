package org.swrlapi.ui.controller;

import checkers.nullness.quals.NonNull;
import org.swrlapi.ui.model.SWRLRuleEngineModel;

/**
 * Provides an MVC-based rule engine controller. Used in conjunction with a
 * {@link org.swrlapi.ui.model.SWRLRuleEngineModel}.
 *
 * @see SWRLRuleEngineModel
 */
public interface SWRLRuleEngineController
{
  /**
   * @return The rule engine model associated with the controller
   */
  @NonNull SWRLRuleEngineModel getSWRLRuleEngineModel();
}
