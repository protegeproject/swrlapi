package org.swrlapi.ui.view.queries;

import javax.swing.Icon;
import javax.swing.JSplitPane;

import org.swrlapi.exceptions.SWRLAPIException;
import org.swrlapi.ui.controller.SWRLAPIApplicationController;
import org.swrlapi.ui.dialog.SWRLAPIApplicationDialogManager;
import org.swrlapi.ui.model.SWRLAPIApplicationModel;
import org.swrlapi.ui.model.SWRLRulesTableModel;
import org.swrlapi.ui.view.SWRLAPIView;
import org.swrlapi.ui.view.SWRLRulesTableView;
import org.swrlapi.ui.view.rules.SWRLAPIRulesView;

/**
 * Graphical component that presents a SQWRL editor and query execution graphical interface. It can be used to embed
 * SQWRL query editing and execution into an application.
 * 
 * @see SWRLAPIRulesView
 */
public class SWRLAPIQueriesView extends JSplitPane implements SWRLAPIView
{
	private static final long serialVersionUID = 1L;

	private final SWRLRulesTableView queryTableView;
	private final SWRLQueryExecutionView queryExecutionView;

	private static final double SPLIT_PANE_RESIZE_WEIGHT = 0.6;

	public SWRLAPIQueriesView(SWRLAPIApplicationController applicationController, Icon queryEngineIcon)
			throws SWRLAPIException
	{
		SWRLAPIApplicationDialogManager applicationDialogManager = applicationController.getApplicationDialogManager();
		SWRLAPIApplicationModel applicationModel = applicationController.getApplicationModel();
		SWRLRulesTableModel swrlRulesTableModel = applicationModel.getSWRLRulesTableModel();

		this.queryTableView = new SWRLRulesTableView(applicationDialogManager, swrlRulesTableModel);
		this.queryExecutionView = new SWRLQueryExecutionView(applicationModel, queryEngineIcon, null);

		setOrientation(JSplitPane.VERTICAL_SPLIT);
		setResizeWeight(SPLIT_PANE_RESIZE_WEIGHT);
		setTopComponent(this.queryTableView);
		setBottomComponent(this.queryExecutionView);
	}

	@Override
	public void update()
	{
		this.queryTableView.update();
		this.queryExecutionView.update();
	}
}
