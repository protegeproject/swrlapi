package org.swrlapi.ui.view.queries;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.ui.view.SWRLRulesTableView;

import java.util.Optional;

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

  public Optional<@NonNull String> getSelectedQueryName()
  {
    return this.swrlRulesTableView.getSelectedSWRLRuleName();
  }
}
