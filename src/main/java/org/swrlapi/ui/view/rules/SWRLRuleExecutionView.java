package org.swrlapi.ui.view.rules;

import checkers.nullness.quals.NonNull;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.exceptions.SWRLAPIException;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.ui.model.SWRLRuleEngineModel;
import org.swrlapi.ui.view.SWRLAPIView;
import org.swrlapi.ui.view.owl2rl.OWL2RLRuleTablesView;

import javax.swing.*;

public class SWRLRuleExecutionView extends JTabbedPane implements SWRLAPIView
{
  private static final long serialVersionUID = 1L;

  @NonNull private final SWRLRuleEngineModel swrlRuleEngineModel;
  @NonNull private final SWRLRuleEngine swrlRuleEngine;
  @NonNull private final Icon ruleEngineIcon;
  @NonNull private final Icon owl2RLIcon;

  public SWRLRuleExecutionView(@NonNull SWRLRuleEngineModel swrlRuleEngineModel)
    throws SWRLAPIException
  {
    this.swrlRuleEngineModel = swrlRuleEngineModel;
    this.swrlRuleEngine = swrlRuleEngineModel.getSWRLRuleEngine();
    this.ruleEngineIcon = this.swrlRuleEngine.getRuleEngineIcon();
    this.owl2RLIcon = SWRLAPIFactory.getOWL2RLReasonerIcon();
  }

  @Override public void initialize()
  {
    OWL2RLRuleTablesView ruleTablesView = new OWL2RLRuleTablesView(swrlRuleEngineModel.getOWL2RLModel());
    ruleTablesView.initialize();;

    addTab("Control", ruleEngineIcon, new SWRLRulesControlView(swrlRuleEngineModel), "Control Tab");

    addTab("Rules", ruleEngineIcon, new ImportedSWRLRulesView(swrlRuleEngineModel), "Rules Tab");

    addTab("Asserted Axioms", ruleEngineIcon, new AssertedOWLAxiomsView(swrlRuleEngineModel), "Asserted OWL Axioms Tab");

    addTab("Inferred Axioms", owl2RLIcon, new InferredOWLAxiomsView(swrlRuleEngineModel), "Inferred OWL Axioms Tab");

    addTab("OWL 2 RL", owl2RLIcon, ruleTablesView, "OWL 2 RL Tab");
  }

  @Override public void update()
  {
    validate();
  }
}
