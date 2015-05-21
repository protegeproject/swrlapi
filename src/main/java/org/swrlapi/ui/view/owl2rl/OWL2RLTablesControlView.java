package org.swrlapi.ui.view.owl2rl;

import checkers.nullness.quals.NonNull;
import org.swrlapi.owl2rl.OWL2RLNames;
import org.swrlapi.ui.model.OWL2RLModel;
import org.swrlapi.ui.view.SWRLAPIView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @see org.swrlapi.ui.model.OWL2RLModel
 */
public class OWL2RLTablesControlView extends JPanel implements SWRLAPIView
{
  private static final long serialVersionUID = 1L;

  @NonNull private final OWL2RLModel owl2RLModel;
  @NonNull private final List<OWL2RLRuleTableActivationButton> ruleTableActivationButtons;

  public OWL2RLTablesControlView(@NonNull OWL2RLModel owl2RLModel)
  {
    this.owl2RLModel = owl2RLModel;
    this.ruleTableActivationButtons = new ArrayList<>();

    setLayout(new GridLayout(1, this.owl2RLModel.getOWL2RLEngine().getNumberOfTables()));

    for (OWL2RLNames.OWL2RLRuleTable ruleTable : this.owl2RLModel.getOWL2RLEngine().getRuleTables()) {
      OWL2RLRuleTableActivationButton button = new OWL2RLRuleTableActivationButton(this.owl2RLModel, ruleTable);
      this.ruleTableActivationButtons.add(button);
      add(button);
    }
  }

  @Override public void update()
  {
    this.ruleTableActivationButtons.forEach(org.swrlapi.ui.view.owl2rl.OWL2RLRuleTableActivationButton::update);
  }
}
