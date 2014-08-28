package org.swrlapi.ui.view.rules;

import javax.swing.Icon;
import javax.swing.JSplitPane;

import org.swrlapi.exceptions.SWRLAPIException;
import org.swrlapi.ui.dialog.SWRLAPIApplicationDialogManager;
import org.swrlapi.ui.model.SWRLAPIApplicationModel;
import org.swrlapi.ui.model.SWRLRulesTableModel;
import org.swrlapi.ui.view.SWRLAPIView;
import org.swrlapi.ui.view.SWRLRulesTableView;
import org.swrlapi.ui.view.queries.SWRLAPIQueriesView;

/**
 * Component that presents a SWRL editor and rule execution graphical interface. It can be used to embed SWRL rule
 * editing and execution into an application.
 *
 * @see org.swrlapi.ui.view.queries.SWRLAPIQueriesView
 */
public class SWRLAPIRulesView extends JSplitPane implements SWRLAPIView
{
	private static final long serialVersionUID = 1L;

	private static final double SPLIT_PANE_RESIZE_WEIGHT = 0.6;

	private final SWRLRulesTableView ruleTablesView;
	private final SWRLRuleExecutionView ruleExecutionView;

	public SWRLAPIRulesView(SWRLAPIApplicationModel applicationModel,
			SWRLAPIApplicationDialogManager applicationDialogManager, Icon ruleEngineIcon) throws SWRLAPIException
	{
		SWRLRulesTableModel swrlRulesTableModel = applicationModel.getSWRLRulesTableModel();

		this.ruleTablesView = new SWRLRulesTableView(swrlRulesTableModel, applicationDialogManager);
		this.ruleExecutionView = new SWRLRuleExecutionView(applicationModel, ruleEngineIcon);

		setOrientation(JSplitPane.VERTICAL_SPLIT);
		setResizeWeight(SPLIT_PANE_RESIZE_WEIGHT);
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
