package org.protege.swrlapi.ui.panels;

import javax.swing.Icon;
import javax.swing.JTabbedPane;

import org.protege.swrlapi.sqwrl.SQWRLQueryEngine;
import org.protege.swrlapi.ui.owl2rl.OWL2RLTabbedPane;

/**
 * GUI for a each SWRLTab SQWRL query engine plugin.
 */
public class SWRLTabSQWRLPluginPanel extends JTabbedPane
{
	private static final long serialVersionUID = -2959798859917609752L;

	public SWRLTabSQWRLPluginPanel(String pluginName, SQWRLQueryEngine queryEngine, Icon queryEngineIcon,
			Icon reasonerIcon, SWRLRuleSelector ruleSelector)
	{
		addTab(pluginName, queryEngineIcon, new SQWRLQueryControlPanel(queryEngine, queryEngineIcon, ruleSelector),
				"Control Panel");

		addTab("OWL 2 RL", reasonerIcon, new OWL2RLTabbedPane(queryEngine.getOWL2RLEngine()), "OWL 2 RL Tab");
	}
}
