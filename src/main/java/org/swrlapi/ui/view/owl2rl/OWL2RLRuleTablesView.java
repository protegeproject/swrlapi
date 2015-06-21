package org.swrlapi.ui.view.owl2rl;

import checkers.nullness.quals.NonNull;
import org.swrlapi.owl2rl.OWL2RLEngine;
import org.swrlapi.owl2rl.OWL2RLNames;
import org.swrlapi.ui.model.OWL2RLModel;
import org.swrlapi.ui.view.SWRLAPIView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class OWL2RLRuleTablesView extends JTabbedPane implements SWRLAPIView
{
  private static final long serialVersionUID = 1L;

  @NonNull private final OWL2RLModel owl2RLModel;
  @NonNull private final OWL2RLControlView owl2RLControlView;
  @NonNull private final List<OWL2RLRuleTableView> owl2RLTableViews;

  public OWL2RLRuleTablesView(@NonNull OWL2RLModel owl2RLModel)
  {
    this.owl2RLModel = owl2RLModel;
    this.owl2RLTableViews = new ArrayList<>();
    this.owl2RLControlView = new OWL2RLControlView(this.owl2RLModel);

    addTab("OWL2RL Control", this.owl2RLControlView);

    for (OWL2RLNames.OWL2RLRuleTable ruleTable : this.owl2RLModel.getOWL2RLEngine().getRuleTables()) {
      OWL2RLRuleTableView owl2RLTableView = new OWL2RLRuleTableView(getOWL2RLModel(), ruleTable);
      this.owl2RLTableViews.add(owl2RLTableView);
      addTab(ruleTable.toString(), owl2RLTableView);
    }
  }

  @Override
  public void update()
  {
    this.owl2RLTableViews.forEach(org.swrlapi.ui.view.owl2rl.OWL2RLRuleTableView::update);

    this.owl2RLControlView.update();
  }

  @NonNull private OWL2RLModel getOWL2RLModel()
  {
    return this.owl2RLModel;
  }
}
