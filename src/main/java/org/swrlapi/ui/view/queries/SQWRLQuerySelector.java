package org.swrlapi.ui.view.queries;

import checkers.nullness.quals.NonNull;
import org.swrlapi.ui.view.SWRLRulesTableView;

/**
 * Selector used to communicate the selected SQWRL query between views.
 *
 * @see org.swrlapi.ui.view.SWRLRulesTableView
 * @see org.swrlapi.ui.view.queries.SQWRLQueryExecutionView
 */
class SQWRLQuerySelector
{
  @NonNull private final SWRLRulesTableView swrlRulesTableView;

  public SQWRLQuerySelector(@NonNull SWRLRulesTableView swrlRulesTableView)
  {
    this.swrlRulesTableView = swrlRulesTableView;
  }

  @NonNull public String getSelectedQueryName()
  {
    return this.swrlRulesTableView.getSelectedSWRLRuleName();
  }
}
