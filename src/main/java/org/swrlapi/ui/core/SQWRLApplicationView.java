package org.swrlapi.ui.core;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import org.swrlapi.ui.dialog.SWRLAPIApplicationDialogManager;
import org.swrlapi.ui.view.SWRLRulesTableView;

public class SQWRLApplicationView extends JSplitPane implements SWRLAPIApplicationView
{
	private static final long serialVersionUID = 1L;

	private final SWRLAPIApplicationController applicationController;
	private final SWRLAPIApplicationDialogManager applicationDialogManager;
	private final JTabbedPane sqwrlQueriesPane;
	private final JPanel sqwrlQueryControllerView;
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

		this.sqwrlQueryControllerView = new JPanel();
		sqwrlQueriesPane.addTab("Mappings Control", null, sqwrlQueryControllerView, "Mappings Control Tab");
	}

	@Override
	public void update()
	{
		sqwrlQueriesTableView.update();
		// TODO sqwrlQueryControllerView.update();
	}

	public JPanel getSQWRLQueryControllerView()
	{
		return this.sqwrlQueryControllerView;
	}

	public SWRLAPIApplicationDialogManager getApplicationDialogManager()
	{
		return this.applicationDialogManager;
	}
}
