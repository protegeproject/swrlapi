package org.swrlapi.ui.view.queries;

import javax.swing.Icon;
import javax.swing.JTabbedPane;

import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.ui.model.SWRLAPIApplicationModel;
import org.swrlapi.ui.view.SWRLAPIView;
import org.swrlapi.ui.view.SWRLRuleSelector;
import org.swrlapi.ui.view.owl2rl.OWL2RLRuleTablesView;

public class SWRLAPIQueriesView extends JTabbedPane implements SWRLAPIView
{
	private static final long serialVersionUID = 1L;

	public SWRLAPIQueriesView(SWRLAPIApplicationModel applicationModel, Icon queryEngineIcon, Icon reasonerIcon,
			SWRLRuleSelector ruleSelector)
	{
		SQWRLQueryEngine queryEngine = applicationModel.getSQWRLQueryEngine();

		addTab("SWRLTab Queries", queryEngineIcon, new SQWRLQueryControlView(queryEngine, ruleSelector, queryEngineIcon),
				"Control Panel");

		addTab("OWL 2 RL", reasonerIcon, new OWL2RLRuleTablesView(queryEngine.getOWL2RLEngine()), "OWL 2 RL Tab");
	}

	@Override
	public void update()
	{
		validate();
	}
}
