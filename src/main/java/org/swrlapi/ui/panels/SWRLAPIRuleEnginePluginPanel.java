package org.swrlapi.ui.panels;

import javax.swing.Icon;
import javax.swing.JTabbedPane;

import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.ui.owl2rl.OWL2RLTabbedPane;

/**
 * GUI for a each SWRLAPI rule engine plugin.
 */
public class SWRLAPIRuleEnginePluginPanel extends JTabbedPane
{
	private static final long serialVersionUID = 1L;

	public SWRLAPIRuleEnginePluginPanel(String pluginName, SWRLRuleEngine ruleEngine, String ruleEngineName,
			Icon ruleEngineIcon, Icon reasonerIcon)
	{
		addTab(pluginName, ruleEngineIcon, new SWRLAPIControlPanel(ruleEngine, ruleEngineName), "Control Tab");

		addTab("Rules", ruleEngineIcon, new SWRLRulesPanel(ruleEngine), "Rules Tab");

		addTab("Asserted Axioms", ruleEngineIcon, new SWRLAPIAssertedAxiomsPanel(ruleEngine), "Asserted OWL Axioms Tab");

		addTab("Inferred Axioms", reasonerIcon, new SWRLAPIInferredAxiomsPanel(ruleEngine), "Inferred OWL Axioms Tab");

		addTab("OWL 2 RL", reasonerIcon, new OWL2RLTabbedPane(ruleEngine.getOWL2RLEngine()), "OWL 2 RL Tab");
	}
}
