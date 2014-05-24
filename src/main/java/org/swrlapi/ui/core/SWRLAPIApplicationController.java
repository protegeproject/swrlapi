package org.swrlapi.ui.core;

public class SWRLAPIApplicationController
{
	private final SQWRLApplicationView applicationViewController;
	private final SWRLAPIApplicationModel applicationModel;

	public SWRLAPIApplicationController(SQWRLApplicationView applicationViewController,
			SWRLAPIApplicationModel applicationModel)
	{
		this.applicationViewController = applicationViewController;
		this.applicationModel = applicationModel;
	}

	public SQWRLApplicationView getApplicationView()
	{
		return applicationViewController;
	}

	public SWRLAPIApplicationModel getApplicationModel()
	{
		return applicationModel;
	}
}
