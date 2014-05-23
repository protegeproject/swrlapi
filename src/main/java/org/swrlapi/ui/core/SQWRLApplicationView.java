package org.swrlapi.ui.core;

import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import org.swrlapi.ui.SWRLAPIApplicationController;
import org.swrlapi.ui.dialog.SWRLAPIApplicationDialogManager;
import org.swrlapi.ui.view.SQWRLQueryControllerView;
import org.swrlapi.ui.view.SWRLRulesTableView;

public class SQWRLApplicationView extends JSplitPane implements SWRLAPIApplicationView
{
	private static final long serialVersionUID = 1L;

	private final SWRLAPIApplicationController applicationController;
	private final SWRLAPIApplicationDialogManager applicationDialogManager;
	private final JTabbedPane sqwrlQueriesPane;
	private final SQWRLQueryControllerView sqwrlQueryControllerView;
	private final SWRLRulesTableView sqwrlQueriesTableView;

	public SQWRLApplicationView(SWRLAPIApplicationModel applicationModel,
			SWRLAPIApplicationDialogManager applicationDialogManager)
	{
		applicationModel.setApplicationView(this);

		this.applicationDialogManager = applicationDialogManager;
		this.applicationController = new SWRLAPIApplicationController(this, applicationModel);
		this.applicationDialogManager.initialize(applicationController);

		setOrientation(JSplitPane.VERTICAL_SPLIT);
		setResizeWeight(0.5);

		this.sqwrlQueriesPane = new JTabbedPane();
		setBottomComponent(sqwrlQueriesPane);

		this.sqwrlQueriesTableView = new SWRLRulesTableView(applicationController);
		sqwrlQueriesPane.addTab("SWRL Rules", null, sqwrlQueriesTableView, "SWRL Rules Tab");

		this.sqwrlQueryControllerView = new SQWRLQueryControllerView(applicationController);
		sqwrlQueriesPane.addTab("Mappings Control", null, sqwrlQueryControllerView, "Mappings Control Tab");
	}

	@Override
	public void update()
	{
		sqwrlQueriesTableView.update();
		sqwrlQueryControllerView.update();
	}

	public SWRLAPIApplicationController getApplicationController()
	{
		return this.applicationController;
	}

	public SQWRLQueryControllerView getSQWRLQueryControllerView()
	{
		return this.sqwrlQueryControllerView;
	}

	public SWRLAPIApplicationDialogManager getApplicationDialogManager()
	{
		return this.applicationDialogManager;
	}
}
