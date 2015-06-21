package org.swrlapi.ui.view.queries;

import checkers.nullness.quals.NonNull;
import org.swrlapi.exceptions.SWRLAPIException;
import org.swrlapi.ui.dialog.SWRLAPIDialogManager;
import org.swrlapi.ui.model.OWLOntologyModel;
import org.swrlapi.ui.model.SQWRLQueryEngineModel;
import org.swrlapi.ui.view.SWRLAPIView;
import org.swrlapi.ui.view.SWRLRulesTableView;

import javax.swing.*;

/**
 * Graphical component that presents a SQWRL editor and query execution graphical interface. It can be used to embed
 * SQWRL query editing and execution into an application.
 *
 * @see org.swrlapi.ui.view.rules.SWRLRulesView
 */
public class SQWRLQueriesView extends JSplitPane implements SWRLAPIView
{
	private static final long serialVersionUID = 1L;

	private static final double SPLIT_PANE_RESIZE_WEIGHT = 0.6;

	@NonNull private final SWRLRulesTableView swrlRulesTableView;
	@NonNull private final SQWRLQueryExecutionView sqwrlQueryExecutionView;

	public SQWRLQueriesView(@NonNull SQWRLQueryEngineModel queryEngineModel, @NonNull SWRLAPIDialogManager dialogManager)
			throws SWRLAPIException
	{
		this.swrlRulesTableView = new SWRLRulesTableView(queryEngineModel, dialogManager);
		this.sqwrlQueryExecutionView = new SQWRLQueryExecutionView(queryEngineModel,
				new SQWRLQuerySelector(this.swrlRulesTableView));

		setOrientation(JSplitPane.VERTICAL_SPLIT);
		setResizeWeight(SPLIT_PANE_RESIZE_WEIGHT);
		setTopComponent(this.swrlRulesTableView);
		setBottomComponent(this.sqwrlQueryExecutionView);
	}

	@Override public void update()
	{
		this.swrlRulesTableView.update();
		this.sqwrlQueryExecutionView.update();
	}
}
