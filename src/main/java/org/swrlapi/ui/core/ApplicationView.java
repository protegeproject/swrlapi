package org.swrlapi.ui.core;

import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import org.swrlapi.ui.ApplicationController;
import org.swrlapi.ui.action.SaveSWRLRulesAction;
import org.swrlapi.ui.dialog.ApplicationDialogManager;
import org.swrlapi.ui.view.DataSourceView;
import org.swrlapi.ui.view.SWRLRulesView;
import org.swrlapi.ui.view.MappingsControlView;

public class ApplicationView extends JSplitPane implements View
{
	private static final long serialVersionUID = 1L;

	private final ApplicationController applicationController;
	private DataSourceView dataSourceView;
	private MappingsControlView mappingsControlView;
	private SWRLRulesView mappingsExpressionsView;
	private final ApplicationDialogManager applicationDialogManager;
	private JTabbedPane mappingsPane;

	public ApplicationView(ApplicationModel applicationModel, ApplicationDialogManager applicationDialogManager)
	{
		applicationModel.setApplicationView(this);

		this.applicationDialogManager = applicationDialogManager;

		applicationController = new ApplicationController(this, applicationModel);

		applicationModel.setSaveSWRLRuleAction(new SaveSWRLRulesAction(applicationController));

		this.applicationDialogManager.initialize(applicationController);

		createComponents();
	}

	@Override
	public void update()
	{
		dataSourceView.update();
		mappingsControlView.update();
		mappingsExpressionsView.update();
	}

	public ApplicationController getApplication()
	{
		return applicationController;
	}

	public DataSourceView getDataSourceView()
	{
		return dataSourceView;
	}

	public MappingsControlView getMappingsControlView()
	{
		return mappingsControlView;
	}

	public ApplicationDialogManager getApplicationDialogManager()
	{
		return applicationDialogManager;
	}

	private void createComponents()
	{
		setOrientation(JSplitPane.VERTICAL_SPLIT);

		setResizeWeight(0.5);

		dataSourceView = new DataSourceView(applicationController);
		setTopComponent(dataSourceView);

		mappingsPane = new JTabbedPane();
		setBottomComponent(mappingsPane);

		mappingsControlView = new MappingsControlView(applicationController);
		mappingsPane.addTab("Mappings Control", null, mappingsControlView, "Mappings Control Tab");

		mappingsExpressionsView = new SWRLRulesView(applicationController);
		mappingsPane.addTab("Expressions", null, mappingsExpressionsView, "Expressions Tab");
	}
}
