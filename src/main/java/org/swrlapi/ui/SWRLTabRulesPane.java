package org.swrlapi.ui;

import javax.swing.Icon;
import javax.swing.JTabbedPane;

import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.ui.owl2rl.SWRLTabOWL2RLPanel;
import org.swrlapi.ui.panels.SWRLTabAssertedAxiomsPanel;
import org.swrlapi.ui.panels.SWRLTabInferredAxiomsPanel;
import org.swrlapi.ui.panels.SWRLTabRuleListPanel;
import org.swrlapi.ui.panels.SWRLTabRulesControlPanel;

public class SWRLTabRulesPane extends JTabbedPane
{
	private static final long serialVersionUID = 1L;

	public SWRLTabRulesPane(SWRLRuleEngine ruleEngine, DefaultPrefixManager prefixManager, Icon ruleEngineIcon,
			Icon reasonerIcon)
	{
		addTab("Control", ruleEngineIcon, new SWRLTabRulesControlPanel(ruleEngine), "Control Tab");

		addTab("Rules", ruleEngineIcon, new SWRLTabRuleListPanel(ruleEngine, prefixManager), "Rules Tab");

		addTab("Asserted Axioms", ruleEngineIcon, new SWRLTabAssertedAxiomsPanel(ruleEngine), "Asserted OWL Axioms Tab");

		addTab("Inferred Axioms", reasonerIcon, new SWRLTabInferredAxiomsPanel(ruleEngine), "Inferred OWL Axioms Tab");

		addTab("OWL 2 RL", reasonerIcon, new SWRLTabOWL2RLPanel(ruleEngine.getOWL2RLEngine()), "OWL 2 RL Tab");
	}
}
