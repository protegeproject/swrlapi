package org.swrlapi.ui.view.queries;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.exceptions.SWRLAPIException;
import org.swrlapi.ui.model.SQWRLQueryEngineModel;
import org.swrlapi.ui.view.SWRLAPIView;
import org.swrlapi.ui.view.owl2rl.OWL2RLRuleTablesView;

import javax.swing.*;

/**
 *
 */
public class SQWRLQueryExecutionView extends JTabbedPane implements SWRLAPIView
{
  private static final long serialVersionUID = 1L;

  @NonNull private final SQWRLQueryEngineModel queryEngineModel;
  @NonNull private final SQWRLQuerySelector querySelector;

  public SQWRLQueryExecutionView(@NonNull SQWRLQueryEngineModel queryEngineModel,
    @NonNull SQWRLQuerySelector querySelector) throws SWRLAPIException
  {
    this.queryEngineModel = queryEngineModel;
    this.querySelector = querySelector;
  }

  @Override public void initialize()
  {
    SQWRLQueryControlView queryControlView = new SQWRLQueryControlView(queryEngineModel, querySelector);
    OWL2RLRuleTablesView ruleTablesView = new OWL2RLRuleTablesView(queryEngineModel.getOWL2RLModel());

    queryControlView.initialize();
    ruleTablesView.initialize();

    addTab("SQWRL Queries", null, queryControlView, "SQWRL Queries Tab");
    addTab("OWL 2 RL", null, ruleTablesView, "OWL 2 RL Tab");
  }

  @Override public void update()
  {
    validate();
  }
}
