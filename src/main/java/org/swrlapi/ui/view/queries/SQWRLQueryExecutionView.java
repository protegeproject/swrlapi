package org.swrlapi.ui.view.queries;

import checkers.nullness.quals.NonNull;
import org.swrlapi.exceptions.SWRLAPIException;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.ui.model.SQWRLQueryEngineModel;
import org.swrlapi.ui.view.SWRLAPIView;
import org.swrlapi.ui.view.owl2rl.OWL2RLRuleTablesView;

import javax.swing.*;

/**
 *
 */
public class SQWRLQueryExecutionView extends JTabbedPane implements SWRLAPIView
{
	private static final long serialVersionUID = 1L;

	public SQWRLQueryExecutionView(@NonNull SQWRLQueryEngineModel queryEngineModel,
			@NonNull SQWRLQuerySelector querySelector) throws SWRLAPIException
	{
		SQWRLQueryEngine queryEngine = queryEngineModel.getSQWRLQueryEngine();
		Icon queryEngineIcon = queryEngine.getTargetQueryEngineIcon();
		Icon owl2RLIcon = SWRLAPIFactory.getOWL2RLReasonerIcon();

		addTab("SQWRL Queries", queryEngineIcon, new SQWRLQueryControlView(queryEngineModel, querySelector),
				"Control Panel");

		addTab("OWL 2 RL", owl2RLIcon, new OWL2RLRuleTablesView(queryEngineModel.getOWL2RLModel()), "OWL 2 RL Tab");
	}

	@Override public void update()
	{
		validate();
	}
}
