package org.swrlapi.ui.view.queries;

import checkers.nullness.quals.NonNull;
import org.swrlapi.exceptions.SWRLAPIException;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
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
  @NonNull private final SQWRLQueryEngine queryEngine;
  @NonNull private final Icon queryEngineIcon;
  @NonNull private final Icon owl2RLIcon;

  public SQWRLQueryExecutionView(@NonNull SQWRLQueryEngineModel queryEngineModel,
      @NonNull SQWRLQuerySelector querySelector) throws SWRLAPIException
  {
    this.queryEngineModel = queryEngineModel;
    this.querySelector = querySelector;
    this.queryEngine = queryEngineModel.getSQWRLQueryEngine();
    this.queryEngineIcon = queryEngine.getTargetQueryEngineIcon();
    this.owl2RLIcon = SWRLAPIFactory.getOWL2RLReasonerIcon();

  }

  @Override public void initialize()
  {
    SQWRLQueryControlView queryControlView = new SQWRLQueryControlView(queryEngineModel, querySelector);
    OWL2RLRuleTablesView ruleTablesView = new OWL2RLRuleTablesView(queryEngineModel.getOWL2RLModel());

    queryControlView.initialize();
    ruleTablesView.initialize();

    addTab("SQWRL Queries", queryEngineIcon, queryControlView, "Control Panel");
    addTab("OWL 2 RL", owl2RLIcon, ruleTablesView, "OWL 2 RL Tab");
  }

  @Override public void update()
  {
    validate();
  }
}
