package org.swrlapi.ui.owl2rl;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import org.swrlapi.owl2rl.OWL2RLNames;

public class OWL2RLRuleActivationButton extends JCheckBox implements ActionListener
{
	private static final long serialVersionUID = 2147290781863250401L;

	private final OWL2RLModel owl2RLModel;
	private final OWL2RLNames.Rule rule;

	public OWL2RLRuleActivationButton(OWL2RLModel owl2RLModel, OWL2RLNames.Rule rule)
	{
		super(rule.toString());

		this.owl2RLModel = owl2RLModel;
		this.rule = rule;

		initialize();
	}

	public void update()
	{
		switch (getOWL2RLModel().getOWL2RLEngine().getRuleStatus(this.rule)) {
		case Unsupported:
			setSelected(false);
			setEnabled(false);
			break;
		case PermanentlyOn:
			setSelected(true);
			setEnabled(false);
			break;
		case Switchable:
			setSelected(getOWL2RLModel().getOWL2RLEngine().isRuleEnabled(this.rule));
			setEnabled(true);
			break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (isSelected())
			getOWL2RLModel().getOWL2RLEngine().enableRules(this.rule);
		else
			getOWL2RLModel().getOWL2RLEngine().disableRules(this.rule);

		getOWL2RLModel().fireUpdate();
	}

	private void initialize()
	{
		setPreferredSize(new Dimension(160, 30));
		setToolTipText("Click to enable or disable OWL 2 RL rule " + this.rule.toString() + ".");
		addActionListener(this);
		update();
	}

	private OWL2RLModel getOWL2RLModel()
	{
		return this.owl2RLModel;
	}
}
