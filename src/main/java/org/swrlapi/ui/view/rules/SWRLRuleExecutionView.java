package org.swrlapi.ui.view.rules;

import org.checkerframework.checker.nullness.qual.NonNull;
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
  @NonNull private final Icon ruleEngineIcon;
  @NonNull private final Icon owl2RLIcon;

  public SWRLRuleExecutionView(@NonNull SWRLRuleEngineModel swrlRuleEngineModel)
    throws SWRLAPIException
  {
    this.swrlRuleEngineModel = swrlRuleEngineModel;
    this.ruleEngineIcon = swrlRuleEngineModel.getSWRLRuleEngine().getRuleEngineIcon();
    this.owl2RLIcon = SWRLAPIFactory.getOWL2RLReasonerIcon();
  }

  @Override public void initialize()
  {
    OWL2RLRuleTablesView ruleTablesView = new OWL2RLRuleTablesView(this.swrlRuleEngineModel.getOWL2RLModel());
    SWRLRulesControlView swrlRulesControlView = new SWRLRulesControlView(this.swrlRuleEngineModel);
    ImportedSWRLRulesView importedSWRLRulesView = new ImportedSWRLRulesView(this.swrlRuleEngineModel);
    AssertedOWLAxiomsView assertedOWLAxiomsView = new AssertedOWLAxiomsView(this.swrlRuleEngineModel);
    InferredOWLAxiomsView inferredOWLAxiomsView = new InferredOWLAxiomsView(this.swrlRuleEngineModel);

    ruleTablesView.initialize();;
    swrlRulesControlView.initialize();
    importedSWRLRulesView.initialize();
    assertedOWLAxiomsView.initialize();
    inferredOWLAxiomsView.initialize();

    addTab("Control", this.ruleEngineIcon, swrlRulesControlView, "Control Tab");

    addTab("Rules", this.ruleEngineIcon, importedSWRLRulesView, "Rules Tab");

    addTab("Asserted Axioms", this.ruleEngineIcon, assertedOWLAxiomsView, "Asserted OWL Axioms Tab");

    addTab("Inferred Axioms", this.owl2RLIcon, inferredOWLAxiomsView, "Inferred OWL Axioms Tab");

    addTab("OWL 2 RL", this.owl2RLIcon, ruleTablesView, "OWL 2 RL Tab");
  }

  @Override public void update()
  {
    validate();
  }
}
