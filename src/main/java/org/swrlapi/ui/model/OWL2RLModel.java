package org.swrlapi.ui.model;

import org.swrlapi.owl2rl.OWL2RLEngine;
import org.swrlapi.ui.view.owl2rl.OWL2RLRuleTablesView;

public class OWL2RLModel implements SWRLAPIModel
{
	private final OWL2RLRuleTablesView owl2RLTablesView;
	private final OWL2RLEngine owl2RLEngine;

	public OWL2RLModel(OWL2RLRuleTablesView owl2RLTablesView, OWL2RLEngine owl2RLEngine)
	{
		this.owl2RLTablesView = owl2RLTablesView;
		this.owl2RLEngine = owl2RLEngine;
	}

	public OWL2RLRuleTablesView getView()
	{
		return this.owl2RLTablesView;
	}

	public OWL2RLEngine getOWL2RLEngine()
	{
		return this.owl2RLEngine;
	}

	public void fireUpdate()
	{
		this.owl2RLTablesView.update();
	}
}
