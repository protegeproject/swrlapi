package org.swrlapi.ui.view.rules;

import javax.swing.Icon;
import javax.swing.JSplitPane;

import org.swrlapi.ui.controller.SWRLAPIApplicationController;
import org.swrlapi.ui.dialog.SWRLAPIApplicationDialogManager;
import org.swrlapi.ui.model.SWRLAPIApplicationModel;
import org.swrlapi.ui.model.SWRLRulesTableModel;
import org.swrlapi.ui.view.SWRLAPIView;
import org.swrlapi.ui.view.SWRLRulesTableView;

public class SWRLAPIRuleView extends JSplitPane implements SWRLAPIView
{
	private static final long serialVersionUID = 1L;

	private final SWRLRulesTableView ruleTablesView;
	private final SWRLRuleExecutionView ruleExecutionView;

	public SWRLAPIRuleView(SWRLAPIApplicationController applicationController, Icon ruleEngineIcon)
	{
		SWRLAPIApplicationDialogManager applicationDialogManager = applicationController.getApplicationDialogManager();
		SWRLAPIApplicationModel applicationModel = applicationController.getApplicationModel();
		SWRLRulesTableModel swrlRulesTableModel = applicationModel.getSWRLRulesTableModel();

		this.ruleTablesView = new SWRLRulesTableView(applicationDialogManager, swrlRulesTableModel);
		this.ruleExecutionView = new SWRLRuleExecutionView(applicationModel, ruleEngineIcon);

		setOrientation(JSplitPane.VERTICAL_SPLIT);
		setResizeWeight(0.6);
		setTopComponent(this.ruleTablesView);
		setBottomComponent(this.ruleExecutionView);
	}

	@Override
	public void update()
	{
		this.ruleTablesView.update();
		this.ruleExecutionView.update();
	}
}
