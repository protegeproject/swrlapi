
package org.swrlapi.ui.owl2rl;

import org.swrlapi.owl2rl.OWL2RLEngine;

public class OWL2RLModel
{
	private final OWL2RLTabbedPane view;
	private final OWL2RLEngine owl2RLEngine;

	public OWL2RLModel(OWL2RLTabbedPane view, OWL2RLEngine owl2RLEngine)
	{
		this.view = view;
		this.owl2RLEngine = owl2RLEngine;
	}

	public OWL2RLTabbedPane getView()
	{
		return this.view;
	}

	public OWL2RLEngine getOWL2RLEngine()
	{
		return this.owl2RLEngine;
	}

	public void fireUpdate()
	{
		this.view.update();
	}
}
