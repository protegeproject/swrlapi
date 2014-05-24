package org.swrlapi.ui.model;

import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.ui.view.SWRLAPIApplicationView;

public class SWRLAPIApplicationModel implements SWRLAPIModel
{
	private SWRLAPIApplicationView applicationView;
	private final SWRLRulesTableModel swrlRulesTableModel;
	private final SWRLRuleEngine ruleEngine;
	private final SQWRLQueryEngine queryEngine;
	private final DefaultPrefixManager prefixManager;

	public SWRLAPIApplicationModel(SWRLRuleEngine ruleEngine, DefaultPrefixManager prefixManager)
	{
		this.ruleEngine = ruleEngine;
		this.queryEngine = null;
		this.prefixManager = prefixManager;
		this.swrlRulesTableModel = new SWRLRulesTableModel();
	}

	public SWRLAPIApplicationModel(SQWRLQueryEngine queryEngine, DefaultPrefixManager prefixManager)
	{
		this.ruleEngine = null;
		this.queryEngine = queryEngine;
		this.prefixManager = prefixManager;
		this.swrlRulesTableModel = new SWRLRulesTableModel();
	}

	public SWRLRuleEngine getSWRLRuleEngine()
	{
		return this.ruleEngine;
	}

	public SQWRLQueryEngine getSQWRLQueryEngine()
	{
		return this.queryEngine;
	}

	public DefaultPrefixManager getPrefixManager()
	{
		return this.prefixManager;
	}

	public SWRLRulesTableModel getSWRLRulesTableModel()
	{
		return this.swrlRulesTableModel;
	}

	public void setApplicationView(SWRLAPIApplicationView applicationView)
	{
		this.applicationView = applicationView;
	}

	public SWRLAPIApplicationView getApplicationView()
	{
		return this.applicationView;
	}

	public void saveSWRLRules()
	{
		// TODO
	}

	public boolean areRulesModified()
	{
		return swrlRulesTableModel.hasBeenModified();
	}

	public void clearModifiedStatus()
	{
		swrlRulesTableModel.clearModifiedStatus();
	}
}
