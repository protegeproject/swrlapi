package org.swrlapi.ui.owl2rl;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTabbedPane;

import org.swrlapi.owl2rl.OWL2RLEngine;
import org.swrlapi.owl2rl.OWL2RLNames;

public class OWL2RLTabbedPane extends JTabbedPane
{
	private static final long serialVersionUID = -4421681774525818086L;

	private final OWL2RLModel owl2RLModel;
	private final OWL2RLControlPanel controlPanel;
	private final List<OWL2RLTablePanel> tablePanels;

	public OWL2RLTabbedPane(OWL2RLEngine owl2RLEngine)
	{
		this.owl2RLModel = new OWL2RLModel(this, owl2RLEngine);
		this.tablePanels = new ArrayList<OWL2RLTablePanel>();
		this.controlPanel = new OWL2RLControlPanel(this.owl2RLModel);

		initialize();
	}

	public void update()
	{
		for (OWL2RLTablePanel tablePanel : this.tablePanels)
			tablePanel.update();

		this.controlPanel.update();
	}

	private void initialize()
	{
		addTab("Control", this.controlPanel);

		for (OWL2RLNames.Table table : getOWL2RLModel().getOWL2RLEngine().getTables()) {
			OWL2RLTablePanel tablePanel = new OWL2RLTablePanel(getOWL2RLModel(), table);
			this.tablePanels.add(tablePanel);
			addTab(table.toString(), tablePanel);
		}
	}

	private OWL2RLModel getOWL2RLModel()
	{
		return this.owl2RLModel;
	}
}
