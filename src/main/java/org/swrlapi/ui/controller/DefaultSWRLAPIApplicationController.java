package org.swrlapi.ui.controller;

import org.swrlapi.ui.model.SWRLAPIApplicationModel;

public class DefaultSWRLAPIApplicationController implements SWRLAPIApplicationController
{
	// private final SWRLAPIApplicationView applicationView;
	private final SWRLAPIApplicationModel applicationModel;

	public DefaultSWRLAPIApplicationController(SWRLAPIApplicationModel applicationModel)
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
	public SWRLAPIApplicationModel getApplicationModel()
	{
		return this.applicationModel;
	}
}
