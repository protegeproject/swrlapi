package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.swrlapi.ui.controller.SWRLRuleEngineController;
import org.swrlapi.ui.model.SWRLRuleEngineModel;

public class DefaultSWRLRuleEngineController implements SWRLRuleEngineController
{
  // private final SWRLAPIApplicationView applicationView;
  @NonNull private final SWRLRuleEngineModel ruleEngineModel;

  public DefaultSWRLRuleEngineController(@NonNull SWRLRuleEngineModel ruleEngineModel)
  {
    this.ruleEngineModel = ruleEngineModel;
  }

  // public SWRLAPIApplicationView getApplicationView()
  // {
  // return applicationView;
  // }

  /**
   * @return The application model associated with the controller
   */
  @NonNull @Override
  public SWRLRuleEngineModel getSWRLRuleEngineModel()
  {
    return this.ruleEngineModel;
  }
}
