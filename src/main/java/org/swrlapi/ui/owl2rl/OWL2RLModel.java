
package org.swrlapi.ui.owl2rl;

import org.swrlapi.owl2rl.OWL2RLEngine;

public class OWL2RLModel
{
	private final SWRLTabOWL2RLPanel view;
	private final OWL2RLEngine owl2RLEngine;

	public OWL2RLModel(SWRLTabOWL2RLPanel view, OWL2RLEngine owl2RLEngine)
	{
		this.view = view;
		this.owl2RLEngine = owl2RLEngine;
	}

	public SWRLTabOWL2RLPanel getView()
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
