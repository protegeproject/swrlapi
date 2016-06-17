package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.ui.model.SQWRLQueryEngineModel;
import org.swrlapi.ui.model.SWRLRulesAndSQWRLQueriesTableModel;

class DefaultSQWRLQueryEngineModel extends DefaultSWRLRuleEngineModel implements SQWRLQueryEngineModel
{
  @NonNull private SQWRLQueryEngine queryEngine;

  public DefaultSQWRLQueryEngineModel(@NonNull SQWRLQueryEngine sqwrlQueryEngine)
  {
    super(sqwrlQueryEngine);
    this.queryEngine = sqwrlQueryEngine;
    this.swrlRulesAndSQWRLQueriesTableModel
      .setContentMode(SWRLRulesAndSQWRLQueriesTableModel.ContentMode.QueryContentOnly);
  }

  @Override public void updateModel(@NonNull SQWRLQueryEngine queryEngine)
  {
    this.queryEngine = queryEngine;
    super.updateModel(queryEngine);
  }

  @NonNull @Override public SQWRLQueryEngine getSQWRLQueryEngine()
  {
    return this.queryEngine;
  }
}
