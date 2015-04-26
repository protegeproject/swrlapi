package org.swrlapi.ui.controller;

import org.swrlapi.ui.model.SWRLRuleEngineModel;

public class DefaultSWRLAPIApplicationController implements SWRLAPIApplicationController
{
	// private final SWRLAPIApplicationView applicationView;
	private final SWRLRuleEngineModel applicationModel;

	public DefaultSWRLAPIApplicationController(SWRLRuleEngineModel applicationModel)
	{
		this.applicationModel = applicationModel;
	}

	// public SWRLAPIApplicationView getApplicationView()
	// {
	// return applicationView;
	// }

	/**
	 * @return The application model associated with the controller
	 */
	public SWRLRuleEngineModel getSWRLRuleEngineModel()
	{
		return this.applicationModel;
	}
}
