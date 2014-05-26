package org.swrlapi.ui.controller;

import org.swrlapi.ui.dialog.SWRLAPIApplicationDialogManager;
import org.swrlapi.ui.model.SWRLAPIApplicationModel;

public class SWRLAPIApplicationController
{
	// private final SWRLAPIApplicationView applicationView;
	private final SWRLAPIApplicationModel applicationModel;
	private final SWRLAPIApplicationDialogManager applicationDialogManager;

	public SWRLAPIApplicationController(SWRLAPIApplicationModel applicationModel)
	{
		this.applicationModel = applicationModel;
		this.applicationDialogManager = new SWRLAPIApplicationDialogManager(this);
	}

	// public SWRLAPIApplicationView getApplicationView()
	// {
	// return applicationView;
	// }

	public SWRLAPIApplicationModel getApplicationModel()
	{
		return applicationModel;
	}

	public SWRLAPIApplicationDialogManager getApplicationDialogManager()
	{
		return this.applicationDialogManager;
	}
}
