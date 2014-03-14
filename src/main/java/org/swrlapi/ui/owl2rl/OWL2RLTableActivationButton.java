package org.swrlapi.ui.owl2rl;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import org.swrlapi.owl2rl.OWL2RLNames;

public class OWL2RLTableActivationButton extends JCheckBox implements ActionListener
{
	private static final long serialVersionUID = -6791469725092119593L;

	private final OWL2RLModel owl2RLModel;
	private final OWL2RLNames.Table table;

	public OWL2RLTableActivationButton(OWL2RLModel owl2RLModel, OWL2RLNames.Table table)
	{
		super(table.toString());

		this.owl2RLModel = owl2RLModel;
		this.table = table;

		initialize();
	}

	public void update()
	{
		setSelected(getOWL2RLModel().getOWL2RLEngine().hasEnabledRules(this.table));
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (isSelected())
			getOWL2RLModel().getOWL2RLEngine().enableTables(this.table);
		else
			getOWL2RLModel().getOWL2RLEngine().disableTables(this.table);

		getOWL2RLModel().fireUpdate();
	}

	private void initialize()
	{
		setPreferredSize(new Dimension(160, 30));
		setToolTipText("Click to enable or disable OWL 2 RL table " + this.table.toString() + ".");
		setEnabled(getOWL2RLModel().getOWL2RLEngine().hasSwitchableRules(this.table));
		setSelected(getOWL2RLModel().getOWL2RLEngine().hasEnabledRules(this.table));
		addActionListener(this);
	}

	private OWL2RLModel getOWL2RLModel()
	{
		return this.owl2RLModel;
	}
}
