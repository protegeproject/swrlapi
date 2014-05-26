package org.swrlapi.ui.controller;

import org.swrlapi.ui.dialog.SWRLAPIApplicationDialogManager;
import org.swrlapi.ui.model.SWRLAPIApplicationModel;
import org.swrlapi.ui.view.SWRLAPIApplicationView;

public class SWRLAPIApplicationController
{
	private final SWRLAPIApplicationView applicationView;
	private final SWRLAPIApplicationModel applicationModel;
	private final SWRLAPIApplicationDialogManager applicationDialogManager;

	public SWRLAPIApplicationController(SWRLAPIApplicationView applicationView, SWRLAPIApplicationModel applicationModel,
			SWRLAPIApplicationDialogManager applicationDialogManager)
	{
		this.applicationView = applicationView;
		this.applicationModel = applicationModel;
		this.applicationDialogManager = applicationDialogManager;
	}

	public SWRLAPIApplicationView getApplicationView()
	{
		return applicationView;
	}

	public SWRLAPIApplicationModel getApplicationModel()
	{
		return applicationModel;
	}

	public SWRLAPIApplicationDialogManager getApplicationDialogManager()
	{
		return this.applicationDialogManager;
	}
}
