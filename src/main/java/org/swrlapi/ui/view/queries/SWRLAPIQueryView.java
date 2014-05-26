package org.swrlapi.ui.view.queries;

import javax.swing.Icon;
import javax.swing.JSplitPane;

import org.swrlapi.ui.controller.SWRLAPIApplicationController;
import org.swrlapi.ui.dialog.SWRLAPIApplicationDialogManager;
import org.swrlapi.ui.model.SWRLAPIApplicationModel;
import org.swrlapi.ui.model.SWRLRulesTableModel;
import org.swrlapi.ui.view.SWRLAPIView;
import org.swrlapi.ui.view.SWRLRulesTableView;

public class SWRLAPIQueryView extends JSplitPane implements SWRLAPIView
{
	private static final long serialVersionUID = 1L;

	private final SWRLRulesTableView ruleTablesView;
	private final SWRLQueryExecutionView queryExecutionView;

	public SWRLAPIQueryView(SWRLAPIApplicationController applicationController, Icon queryEngineIcon)
	{
		SWRLAPIApplicationDialogManager applicationDialogManager = applicationController.getApplicationDialogManager();
		SWRLAPIApplicationModel applicationModel = applicationController.getApplicationModel();
		SWRLRulesTableModel swrlRulesTableModel = applicationModel.getSWRLRulesTableModel();

		this.ruleTablesView = new SWRLRulesTableView(applicationDialogManager, swrlRulesTableModel);
		this.queryExecutionView = new SWRLQueryExecutionView(applicationModel, queryEngineIcon, null);

		setOrientation(JSplitPane.VERTICAL_SPLIT);
		setResizeWeight(0.6);
		setTopComponent(this.ruleTablesView);
		setBottomComponent(this.queryExecutionView);
	}

	@Override
	public void update()
	{
		this.ruleTablesView.update();
		this.queryExecutionView.update();
	}
}
