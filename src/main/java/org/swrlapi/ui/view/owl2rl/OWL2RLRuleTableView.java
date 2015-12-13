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
 * @see org.swrlapi.owl2rl.OWL2RLNames
 */
public class OWL2RLRuleTableView extends JPanel implements SWRLAPIView
{
  private static final long serialVersionUID = 1L;

  @NonNull private final OWL2RLModel owl2RLModel;
  @NonNull private final List<@NonNull OWL2RLRuleActivationButton> ruleActivationButtons;
  private final OWL2RLNames.OWL2RLRuleTable owl2RLRuleTable;

  public OWL2RLRuleTableView(@NonNull OWL2RLModel owl2RLModel, OWL2RLNames.OWL2RLRuleTable owl2RLRuleTable)
  {
    this.owl2RLModel = owl2RLModel;
    this.owl2RLRuleTable = owl2RLRuleTable;
    this.ruleActivationButtons = new ArrayList<>();
  }

  @Override public void initialize()
  {
    int numberOfButtons = getOWL2RLModel().getOWL2RLEngine().getRules(this.owl2RLRuleTable).size();
    int n = 1 + (int)java.lang.Math.sqrt(numberOfButtons);

    setLayout(new GridLayout(n, n));

    for (OWL2RLNames.OWL2RLRule rule : this.owl2RLModel.getOWL2RLEngine().getRules(this.owl2RLRuleTable)) {
      OWL2RLRuleActivationButton button = new OWL2RLRuleActivationButton(getOWL2RLModel(), rule);
      this.ruleActivationButtons.add(button);
      button.initialize();
      add(button);
    }
  }

  @Override
  public void update()
  {
    this.ruleActivationButtons.forEach(org.swrlapi.ui.view.owl2rl.OWL2RLRuleActivationButton::update);
  }

  @NonNull private OWL2RLModel getOWL2RLModel()
  {
    return this.owl2RLModel;
  }
}
