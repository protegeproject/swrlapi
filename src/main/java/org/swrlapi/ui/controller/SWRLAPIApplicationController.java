package org.swrlapi.ui.controller;

import org.swrlapi.ui.model.SWRLAPIApplicationModel;
import org.swrlapi.ui.view.SWRLAPIApplicationView;

/**
 * Provides an application controller that can be used to build a MVC-based GUI that uses the SWRLAPI. Used in
 * conjunction with a {@link SWRLAPIApplicationModel} and a {@link SWRLAPIApplicationView}.
 * 
 * @see SWRLAPIApplicationModel, SWRLAPIApplicationView
 */
public class SWRLAPIApplicationController
{
	// private final SWRLAPIApplicationView applicationView;
	private final SWRLAPIApplicationModel applicationModel;

	public SWRLAPIApplicationController(SWRLAPIApplicationModel applicationModel)
	{
		this.applicationModel = applicationModel;
	}

	// public SWRLAPIApplicationView getApplicationView()
	// {
	// return applicationView;
	// }

	public SWRLAPIApplicationModel getApplicationModel()
	{
		return applicationModel;
	}
}
