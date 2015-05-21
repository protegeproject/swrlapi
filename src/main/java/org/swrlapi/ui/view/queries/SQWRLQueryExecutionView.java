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

  public SQWRLQueryExecutionView(@NonNull SQWRLQueryEngineModel sqwrlQueryEngineModel, @NonNull Icon queryEngineIcon,
    @NonNull SQWRLQuerySelector querySelector) throws SWRLAPIException
  {
    SQWRLQueryEngine queryEngine = sqwrlQueryEngineModel.getSQWRLQueryEngine();
    Icon owl2RLIcon = SWRLAPIFactory.getOWL2RLReasonerIcon();

    addTab("SWRLAPI Queries", queryEngineIcon, new SQWRLQueryControlView(queryEngine, querySelector, queryEngineIcon),
      "Control Panel");

    addTab("OWL 2 RL", owl2RLIcon, new OWL2RLRuleTablesView(queryEngine.getOWL2RLEngine()), "OWL 2 RL Tab");
  }

  @Override public void update()
  {
    validate();
  }
}
