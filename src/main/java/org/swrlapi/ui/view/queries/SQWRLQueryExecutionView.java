package org.swrlapi.ui.view.queries;

import javax.swing.Icon;
import javax.swing.JTabbedPane;

import org.swrlapi.core.SWRLAPIFactory;
import org.swrlapi.exceptions.SWRLAPIException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.ui.model.SWRLAPIApplicationModel;
import org.swrlapi.ui.view.SWRLAPIView;
import org.swrlapi.ui.view.owl2rl.OWL2RLRuleTablesView;

/**
 *
 */
public class SQWRLQueryExecutionView extends JTabbedPane implements SWRLAPIView
{
	private static final long serialVersionUID = 1L;

	public SQWRLQueryExecutionView(SWRLAPIApplicationModel applicationModel, Icon queryEngineIcon,
			SQWRLQuerySelector querySelector) throws SWRLAPIException
	{
		SQWRLQueryEngine queryEngine = applicationModel.getSQWRLQueryEngine();
		Icon owl2RLIcon = SWRLAPIFactory.getOWL2RLReasonerIcon();

		addTab("SWRLAPI Queries", queryEngineIcon, new SQWRLQueryControlView(queryEngine, querySelector, queryEngineIcon),
				"Control Panel");

		addTab("OWL 2 RL", owl2RLIcon, new OWL2RLRuleTablesView(queryEngine.getOWL2RLEngine()), "OWL 2 RL Tab");
	}

	@Override
	public void update()
	{
		validate();
	}
}
