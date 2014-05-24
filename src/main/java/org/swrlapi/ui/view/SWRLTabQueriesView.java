package org.swrlapi.ui.view;

import javax.swing.Icon;
import javax.swing.JTabbedPane;

import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.ui.core.SWRLAPIApplicationModel;
import org.swrlapi.ui.core.SWRLAPIView;
import org.swrlapi.ui.owl2rl.SWRLTabOWL2RLPanel;

public class SWRLTabQueriesView extends JTabbedPane implements SWRLAPIView
{
	private static final long serialVersionUID = 1L;

	public SWRLTabQueriesView(SWRLAPIApplicationModel applicationModel, Icon queryEngineIcon, Icon reasonerIcon,
			SWRLRuleSelector ruleSelector)
	{
		SQWRLQueryEngine queryEngine = applicationModel.getSQWRLQueryEngine();

		addTab("SWRLTab Qeries", queryEngineIcon, new SQWRLQueryControlView(queryEngine, ruleSelector, queryEngineIcon),
				"Control Panel");

		addTab("OWL 2 RL", reasonerIcon, new SWRLTabOWL2RLPanel(queryEngine.getOWL2RLEngine()), "OWL 2 RL Tab");
	}

	@Override
	public void update()
	{
		validate();
	}
}
