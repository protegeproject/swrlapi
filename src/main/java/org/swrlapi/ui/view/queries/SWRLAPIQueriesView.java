package org.swrlapi.ui.view.queries;

import org.swrlapi.exceptions.SWRLAPIException;
import org.swrlapi.ui.dialog.SWRLAPIApplicationDialogManager;
import org.swrlapi.ui.model.SWRLAPIApplicationModel;
import org.swrlapi.ui.view.SWRLAPIView;
import org.swrlapi.ui.view.SWRLRulesTableView;

import javax.swing.*;

/**
 * Graphical component that presents a SQWRL editor and query execution graphical interface. It can be used to embed
 * SQWRL query editing and execution into an application.
 *
 * @see org.swrlapi.ui.view.rules.SWRLAPIRulesView
 */
public class SWRLAPIQueriesView extends JSplitPane implements SWRLAPIView
{
	private static final long serialVersionUID = 1L;

	private static final double SPLIT_PANE_RESIZE_WEIGHT = 0.6;

	private final SWRLRulesTableView swrlRulesTableView;
	private final SQWRLQueryExecutionView sqwrlQueryExecutionView;

	public SWRLAPIQueriesView(SWRLAPIApplicationModel applicationModel,
			SWRLAPIApplicationDialogManager applicationDialogManager, Icon queryEngineIcon) throws SWRLAPIException
	{
		this.swrlRulesTableView = new SWRLRulesTableView(applicationModel, applicationDialogManager);
		this.sqwrlQueryExecutionView = new SQWRLQueryExecutionView(applicationModel, queryEngineIcon,
				new SQWRLQuerySelector(this.swrlRulesTableView));

		setOrientation(JSplitPane.VERTICAL_SPLIT);
		setResizeWeight(SPLIT_PANE_RESIZE_WEIGHT);
		setTopComponent(this.swrlRulesTableView);
		setBottomComponent(this.sqwrlQueryExecutionView);
	}

	@Override
	public void update()
	{
		this.swrlRulesTableView.update();
		this.sqwrlQueryExecutionView.update();
	}
}
