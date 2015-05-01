package org.swrlapi.ui.view.queries;

import org.swrlapi.ui.view.SWRLRulesTableView;

/**
 * Selector used to communicate the selected SQWRL query between views.
 *
 * @see org.swrlapi.ui.view.SWRLRulesTableView
 * @see org.swrlapi.ui.view.queries.SQWRLQueryExecutionView
 */
class SQWRLQuerySelector
{
  private final SWRLRulesTableView swrlRulesTableView;

  public SQWRLQuerySelector(SWRLRulesTableView swrlRulesTableView)
  {
    this.swrlRulesTableView = swrlRulesTableView;
  }

  public String getSelectedQueryName()
  {
    return this.swrlRulesTableView.getSelectedSWRLRuleName();
  }
}
