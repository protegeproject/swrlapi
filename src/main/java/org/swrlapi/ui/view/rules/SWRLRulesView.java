package org.swrlapi.ui.view.rules;

import checkers.nullness.quals.NonNull;
import org.swrlapi.exceptions.SWRLAPIException;
import org.swrlapi.ui.dialog.SWRLAPIDialogManager;
import org.swrlapi.ui.model.SWRLRuleEngineModel;
import org.swrlapi.ui.view.SWRLAPIView;
import org.swrlapi.ui.view.SWRLRulesTableView;
import org.swrlapi.ui.view.queries.SQWRLQueriesView;

import javax.swing.*;

/**
 * Component that presents a SWRL editor and rule execution graphical interface. It can be used to embed SWRL rule
 * editing and execution into an application.
 *
 * @see SQWRLQueriesView
 */
public class SWRLRulesView extends JSplitPane implements SWRLAPIView
{
	private static final long serialVersionUID = 1L;

	private static final double SPLIT_PANE_RESIZE_WEIGHT = 0.6;

	@NonNull private final SWRLRulesTableView ruleTablesView;
	@NonNull private final SWRLRuleExecutionView ruleExecutionView;

	public SWRLRulesView(@NonNull SWRLRuleEngineModel ruleEngineModel, @NonNull SWRLAPIDialogManager dialogManager)
			throws SWRLAPIException
	{
		this.ruleTablesView = new SWRLRulesTableView(ruleEngineModel, dialogManager);
		this.ruleExecutionView = new SWRLRuleExecutionView(ruleEngineModel);

		setOrientation(JSplitPane.VERTICAL_SPLIT);
		setResizeWeight(SPLIT_PANE_RESIZE_WEIGHT);
		setTopComponent(this.ruleTablesView);
		setBottomComponent(this.ruleExecutionView);
	}

	@Override public void update()
	{
		this.ruleTablesView.update();
		this.ruleExecutionView.update();
	}
}
