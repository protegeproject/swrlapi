package org.swrlapi.ui.panels;

import javax.swing.Icon;
import javax.swing.JTabbedPane;

import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.ui.owl2rl.OWL2RLTabbedPane;

/**
 * GUI for a each SWRLAPI SQWRL query engine plugin.
 */
public class SWRLAPISQWRLPluginPanel extends JTabbedPane
{
	private static final long serialVersionUID = 1L;

	public SWRLAPISQWRLPluginPanel(String pluginName, SQWRLQueryEngine queryEngine, Icon queryEngineIcon,
			Icon reasonerIcon, SWRLRuleSelector ruleSelector)
	{
		addTab(pluginName, queryEngineIcon, new SQWRLQueryControlPanel(queryEngine, queryEngineIcon, ruleSelector),
				"Control Panel");

		addTab("OWL 2 RL", reasonerIcon, new OWL2RLTabbedPane(queryEngine.getOWL2RLEngine()), "OWL 2 RL Tab");
	}
}
