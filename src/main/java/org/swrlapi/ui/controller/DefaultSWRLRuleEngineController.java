package org.swrlapi.ui.controller;

import org.swrlapi.ui.model.SWRLRuleEngineModel;

public class DefaultSWRLRuleEngineController implements SWRLRuleEngineController
{
  // private final SWRLAPIApplicationView applicationView;
  private final SWRLRuleEngineModel ruleEngineModel;

  public DefaultSWRLRuleEngineController(SWRLRuleEngineModel ruleEngineModel)
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
  @Override
  public SWRLRuleEngineModel getSWRLRuleEngineModel()
  {
    return this.ruleEngineModel;
  }
}
