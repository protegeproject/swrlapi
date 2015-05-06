package org.swrlapi.ui.view.rules;

import org.swrlapi.exceptions.SWRLAPIException;
import org.swrlapi.ui.dialog.SWRLAPIDialogManager;
import org.swrlapi.ui.model.SWRLRuleEngineModel;
import org.swrlapi.ui.view.SWRLAPIView;
import org.swrlapi.ui.view.SWRLRulesTableView;

import javax.swing.*;

/**
 * Component that presents a SWRL editor and rule execution graphical interface. It can be used to embed SWRL rule
 * editing and execution into an application.
 *
 * @see org.swrlapi.ui.view.queries.SWRLAPIQueriesView
 */
public class SWRLRulesView extends JSplitPane implements SWRLAPIView
{
  private static final long serialVersionUID = 1L;

  private static final double SPLIT_PANE_RESIZE_WEIGHT = 0.6;

  private final SWRLRulesTableView ruleTablesView;
  private final SWRLRuleExecutionView ruleExecutionView;

  public SWRLRulesView(SWRLRuleEngineModel swrlRuleEngineModel, SWRLAPIDialogManager applicationDialogManager,
    Icon ruleEngineIcon) throws SWRLAPIException
  {
    this.ruleTablesView = new SWRLRulesTableView(swrlRuleEngineModel, applicationDialogManager);
    this.ruleExecutionView = new SWRLRuleExecutionView(swrlRuleEngineModel, ruleEngineIcon);

    setOrientation(JSplitPane.VERTICAL_SPLIT);
    setResizeWeight(SPLIT_PANE_RESIZE_WEIGHT);
    setTopComponent(this.ruleTablesView);
    setBottomComponent(this.ruleExecutionView);
  }

  @Override public void update()
  {
    this.ruleTablesView.update();
    this.ruleExecutionView.update();
  }
}
