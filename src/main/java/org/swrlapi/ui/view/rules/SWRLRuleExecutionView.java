package org.swrlapi.ui.view.rules;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.exceptions.SWRLAPIException;
import org.swrlapi.ui.model.SWRLRuleEngineModel;
import org.swrlapi.ui.view.SWRLAPIView;
import org.swrlapi.ui.view.owl2rl.OWL2RLRuleTablesView;

import javax.swing.*;

public class SWRLRuleExecutionView extends JTabbedPane implements SWRLAPIView
{
  private static final long serialVersionUID = 1L;

  @NonNull private final SWRLRuleEngineModel swrlRuleEngineModel;

  public SWRLRuleExecutionView(@NonNull SWRLRuleEngineModel swrlRuleEngineModel) throws SWRLAPIException
  {
    this.swrlRuleEngineModel = swrlRuleEngineModel;
  }

  @Override public void initialize()
  {
    OWL2RLRuleTablesView ruleTablesView = new OWL2RLRuleTablesView(this.swrlRuleEngineModel.getOWL2RLModel());
    SWRLRulesControlView swrlRulesControlView = new SWRLRulesControlView(this.swrlRuleEngineModel);
    ImportedSWRLRulesView importedSWRLRulesView = new ImportedSWRLRulesView(this.swrlRuleEngineModel);
    AssertedOWLAxiomsView assertedOWLAxiomsView = new AssertedOWLAxiomsView(this.swrlRuleEngineModel);
    InferredOWLAxiomsView inferredOWLAxiomsView = new InferredOWLAxiomsView(this.swrlRuleEngineModel);

    ruleTablesView.initialize();

    swrlRulesControlView.initialize();
    importedSWRLRulesView.initialize();
    assertedOWLAxiomsView.initialize();
    inferredOWLAxiomsView.initialize();

    addTab("Control", null, swrlRulesControlView, "Control Tab");

    addTab("Rules", null, importedSWRLRulesView, "Rules Tab");

    addTab("Asserted Axioms", null, assertedOWLAxiomsView, "Asserted OWL Axioms Tab");

    addTab("Inferred Axioms", null, inferredOWLAxiomsView, "Inferred OWL Axioms Tab");

    addTab("OWL 2 RL", null, ruleTablesView, "OWL 2 RL Tab");
  }

  @Override public void update()
  {
    validate();
  }
}
