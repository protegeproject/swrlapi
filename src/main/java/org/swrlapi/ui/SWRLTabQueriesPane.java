package org.swrlapi.ui;

import javax.swing.Icon;
import javax.swing.JTabbedPane;

import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.ui.owl2rl.SWRLTabOWL2RLPanel;
import org.swrlapi.ui.panels.SWRLRuleSelector;
import org.swrlapi.ui.panels.SWRLTabSQWRLControlPanel;

public class SWRLTabQueriesPane extends JTabbedPane
{
	private static final long serialVersionUID = 1L;

	public SWRLTabQueriesPane(SQWRLQueryEngine queryEngine, Icon queryEngineIcon, Icon reasonerIcon,
			SWRLRuleSelector ruleSelector)
	{
		addTab("SWRLTab Queries", queryEngineIcon,
				new SWRLTabSQWRLControlPanel(queryEngine, queryEngineIcon, ruleSelector), "Control Panel");

		addTab("OWL 2 RL", reasonerIcon, new SWRLTabOWL2RLPanel(queryEngine.getOWL2RLEngine()), "OWL 2 RL Tab");
	}
}
