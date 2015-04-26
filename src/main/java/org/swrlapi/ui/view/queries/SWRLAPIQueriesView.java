package org.swrlapi.ui.view.queries;

import javax.swing.Icon;
import javax.swing.JSplitPane;

import org.swrlapi.exceptions.SWRLAPIException;
import org.swrlapi.ui.dialog.SWRLRuleEngineDialogManager;
import org.swrlapi.ui.model.SQWRLQueryEngineModel;
import org.swrlapi.ui.view.SWRLAPIView;
import org.swrlapi.ui.view.SWRLRulesTableView;

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

	public SWRLAPIQueriesView(SQWRLQueryEngineModel sqwrlQueryEngineModel,
			SWRLRuleEngineDialogManager applicationDialogManager, Icon queryEngineIcon) throws SWRLAPIException
	{
		this.swrlRulesTableView = new SWRLRulesTableView(sqwrlQueryEngineModel, applicationDialogManager);
		this.sqwrlQueryExecutionView = new SQWRLQueryExecutionView(sqwrlQueryEngineModel, queryEngineIcon,
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
