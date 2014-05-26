package org.swrlapi.ui.view.rules;

import javax.swing.Icon;
import javax.swing.JTabbedPane;

import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.core.SWRLAPIFactory;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.ui.model.SWRLAPIApplicationModel;
import org.swrlapi.ui.view.SWRLAPIView;
import org.swrlapi.ui.view.owl2rl.OWL2RLRuleTablesView;

public class SWRLRuleExecutionView extends JTabbedPane implements SWRLAPIView
{
	private static final long serialVersionUID = 1L;

	public SWRLRuleExecutionView(SWRLAPIApplicationModel applicationModel, Icon ruleEngineIcon)
	{
		SWRLRuleEngine swrlRuleEngine = applicationModel.getSWRLRuleEngine();
		DefaultPrefixManager prefixManager = applicationModel.getPrefixManager();
		Icon owl2RLIcon = SWRLAPIFactory.getOWL2RLReasonerIcon();

		addTab("Control", ruleEngineIcon, new SWRLRulesControlView(swrlRuleEngine), "Control Tab");

		addTab("Rules", ruleEngineIcon, new ImportedSWRLRulesView(swrlRuleEngine, prefixManager), "Rules Tab");

		addTab("Asserted Axioms", ruleEngineIcon, new AssertedOWLAxiomsView(swrlRuleEngine), "Asserted OWL Axioms Tab");

		addTab("Inferred Axioms", owl2RLIcon, new InferredOWLAxiomsView(swrlRuleEngine), "Inferred OWL Axioms Tab");

		addTab("OWL 2 RL", owl2RLIcon, new OWL2RLRuleTablesView(swrlRuleEngine.getOWL2RLEngine()), "OWL 2 RL Tab");
	}

	@Override
	public void update()
	{
		validate();
	}
}
