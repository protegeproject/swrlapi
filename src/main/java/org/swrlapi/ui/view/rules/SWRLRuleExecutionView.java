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

  public SWRLRuleExecutionView(@NonNull SWRLRuleEngineModel swrlRuleEngineModel)
    throws SWRLAPIException
  {
    SWRLRuleEngine swrlRuleEngine = swrlRuleEngineModel.getSWRLRuleEngine();
    Icon ruleEngineIcon = swrlRuleEngine.getRuleEngineIcon();
    Icon owl2RLIcon = SWRLAPIFactory.getOWL2RLReasonerIcon();

    addTab("Control", ruleEngineIcon, new SWRLRulesControlView(swrlRuleEngineModel), "Control Tab");

    addTab("Rules", ruleEngineIcon, new ImportedSWRLRulesView(swrlRuleEngineModel), "Rules Tab");

    addTab("Asserted Axioms", ruleEngineIcon, new AssertedOWLAxiomsView(swrlRuleEngineModel), "Asserted OWL Axioms Tab");

    addTab("Inferred Axioms", owl2RLIcon, new InferredOWLAxiomsView(swrlRuleEngineModel), "Inferred OWL Axioms Tab");

    addTab("OWL 2 RL", owl2RLIcon, new OWL2RLRuleTablesView(swrlRuleEngineModel.getOWL2RLModel()), "OWL 2 RL Tab");
  }

  @Override public void update()
  {
    validate();
  }
}
