package org.swrlapi.ui.view.owl2rl;

import org.swrlapi.owl2rl.OWL2RLNames;
import org.swrlapi.ui.model.OWL2RLModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @see org.swrlapi.ui.model.OWL2RLModel
 * @see org.swrlapi.owl2rl.OWL2RLNames
 */
public class OWL2RLRuleTableActivationButton extends JCheckBox implements ActionListener
{
  private static final long serialVersionUID = 1L;

  private static final int TOOLTIP_PREFERRED_WIDTH = 160;
  private static final int TOOLTIP_PREFERRED_HEIGHT = 30;

  private final OWL2RLModel owl2RLModel;
  private final OWL2RLNames.OWL2RLRuleTable ruleTable;

  public OWL2RLRuleTableActivationButton(OWL2RLModel owl2RLModel, OWL2RLNames.OWL2RLRuleTable ruleTable)
  {
    super(ruleTable.toString());

    this.owl2RLModel = owl2RLModel;
    this.ruleTable = ruleTable;

    initialize();
  }

  public void update()
  {
    setSelected(getOWL2RLModel().getOWL2RLEngine().hasEnabledRules(this.ruleTable));
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    if (isSelected())
      getOWL2RLModel().getOWL2RLEngine().enableTables(this.ruleTable);
    else
      getOWL2RLModel().getOWL2RLEngine().disableTables(this.ruleTable);

    getOWL2RLModel().updateView();
  }

  private void initialize()
  {
    setPreferredSize(new Dimension(TOOLTIP_PREFERRED_WIDTH, TOOLTIP_PREFERRED_HEIGHT));
    setToolTipText("Click to enable or disable OWL 2 RL ruleTable " + this.ruleTable.toString() + ".");
    setEnabled(getOWL2RLModel().getOWL2RLEngine().hasSwitchableRules(this.ruleTable));
    setSelected(getOWL2RLModel().getOWL2RLEngine().hasEnabledRules(this.ruleTable));
    addActionListener(this);
  }

  private OWL2RLModel getOWL2RLModel()
  {
    return this.owl2RLModel;
  }
}
