package org.swrlapi.ui.owl2rl;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.swrlapi.owl2rl.OWL2RLNames;

public class OWL2RLTablePanel extends JPanel
{
	private static final long serialVersionUID = 1972703711110563090L;

	private final OWL2RLModel owl2RLModel;
	private final OWL2RLNames.Table table;
	private final List<OWL2RLRuleActivationButton> radioButtons;

	public OWL2RLTablePanel(OWL2RLModel owl2RLModel, OWL2RLNames.Table table)
	{
		this.owl2RLModel = owl2RLModel;
		this.table = table;
		this.radioButtons = new ArrayList<OWL2RLRuleActivationButton>();

		initialize();
	}

	public void update()
	{
		for (OWL2RLRuleActivationButton button : this.radioButtons)
			button.update();
	}

	private void initialize()
	{
		int numberOfButtons = getOWL2RLModel().getOWL2RLEngine().getRules(this.table).size();
		int n = 1 + (int)java.lang.Math.sqrt(numberOfButtons);

		setLayout(new GridLayout(n, n));

		for (OWL2RLNames.Rule rule : getOWL2RLModel().getOWL2RLEngine().getRules(this.table)) {
			OWL2RLRuleActivationButton button = new OWL2RLRuleActivationButton(getOWL2RLModel(), rule);
			this.radioButtons.add(button);
			add(button);
		}
	}

	private OWL2RLModel getOWL2RLModel()
	{
		return this.owl2RLModel;
	}
}
