package org.swrlapi.ui.owl2rl;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.swrlapi.owl2rl.OWL2RLNames;

public class OWL2RLTablesControlPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	private final OWL2RLModel owl2RLModel;
	private final List<OWL2RLTableActivationButton> tableActivationButtons;

	public OWL2RLTablesControlPanel(OWL2RLModel owl2RLModel)
	{
		this.owl2RLModel = owl2RLModel;
		this.tableActivationButtons = new ArrayList<OWL2RLTableActivationButton>();

		initialize();
	}

	public void update()
	{
		for (OWL2RLTableActivationButton button : this.tableActivationButtons)
			button.update();
	}

	private void initialize()
	{
		setLayout(new GridLayout(1, getOWL2RLModel().getOWL2RLEngine().getNumberOfTables()));

		for (OWL2RLNames.Table table : getOWL2RLModel().getOWL2RLEngine().getTables()) {
			OWL2RLTableActivationButton button = new OWL2RLTableActivationButton(getOWL2RLModel(), table);
			this.tableActivationButtons.add(button);
			add(button);
		}
	}

	private OWL2RLModel getOWL2RLModel()
	{
		return this.owl2RLModel;
	}
}
