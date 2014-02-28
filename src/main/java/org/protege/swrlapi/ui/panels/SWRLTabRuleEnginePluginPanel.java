package org.protege.swrlapi.ui.panels;

import javax.swing.Icon;
import javax.swing.JTabbedPane;

import org.protege.swrlapi.core.SWRLRuleEngine;
import org.protege.swrlapi.ui.owl2rl.OWL2RLTabbedPane;

/**
 * GUI for a each SWRLTab rule engine plugin.
 */
public class SWRLTabRuleEnginePluginPanel extends JTabbedPane
{
	private static final long serialVersionUID = -5623046112227817953L;

	public SWRLTabRuleEnginePluginPanel(String pluginName, SWRLRuleEngine ruleEngine, String ruleEngineName,
			Icon ruleEngineIcon, Icon reasonerIcon)
	{
		addTab(pluginName, ruleEngineIcon, new SWRLControlPanel(ruleEngine, ruleEngineName), "Control Tab");

		addTab("Rules", ruleEngineIcon, new SWRLRulesPanel(ruleEngine), "Rules Tab");

		addTab("Asserted Axioms", ruleEngineIcon, new SWRLImportedAxiomsPanel(ruleEngine), "Asserted OWL Axioms Tab");

		addTab("Inferred Axioms", reasonerIcon, new SWRLInferredAxiomsPanel(ruleEngine), "Inferred OWL Axioms Tab");

		addTab("OWL 2 RL", reasonerIcon, new OWL2RLTabbedPane(ruleEngine.getOWL2RLEngine()), "OWL 2 RL Tab");
	}
}
