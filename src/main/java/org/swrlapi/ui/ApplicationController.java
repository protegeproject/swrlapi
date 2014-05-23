package org.swrlapi.ui;

import org.swrlapi.ui.core.ApplicationModel;
import org.swrlapi.ui.core.ApplicationView;
import org.swrlapi.ui.core.SWRLRulesPersistenceLayer;

public class ApplicationController
{
	private final ApplicationView applicationViewController;
	private final ApplicationModel applicationModel;

	public ApplicationController(ApplicationView applicationViewController, ApplicationModel applicationModel)
	{
		this.applicationViewController = applicationViewController;
		this.applicationModel = applicationModel;
	}

	public ApplicationView getApplicationViewController()
	{
		return applicationViewController;
	}

	public ApplicationModel getApplicationModel()
	{
		return applicationModel;
	}

	public SWRLRulesPersistenceLayer getMappingExpressionsPersistenceLayer()
	{
		return getApplicationModel().getSWRLRulesPersistenceLayer();
	}
}
