package org.swrlapi.ui.model;

import org.swrlapi.core.SWRLAPIFactory;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.core.SWRLRuleRenderer;
import org.swrlapi.parser.SWRLParser;
import org.swrlapi.sqwrl.SQWRLQueryEngine;

public class DefaultSWRLAPIApplicationModel implements SWRLAPIApplicationModel
{
	private final SWRLRulesTableModel swrlRulesTableModel;
	private final SQWRLQueryEngine queryEngine;
	private final SWRLRuleEngine ruleEngine;
	private final SWRLParser swrlParser;
	private final SWRLRuleRenderer swrlRuleRenderer;
	private final SWRLAutoCompleter swrlAutoCompleter;

	public DefaultSWRLAPIApplicationModel(SWRLRuleEngine ruleEngine)
	{
		this.ruleEngine = ruleEngine;
		this.queryEngine = ruleEngine;
		this.swrlRuleRenderer = this.ruleEngine.createSWRLRuleRenderer();
		this.swrlRulesTableModel = SWRLAPIFactory.createSWRLRulesTableModel(ruleEngine, swrlRuleRenderer);
		this.swrlParser = this.ruleEngine.createSWRLParser();
		this.swrlAutoCompleter = this.ruleEngine.createSWRLAutoCompleter();
	}

	@Override
	public SWRLRuleEngine getSWRLRuleEngine()
	{
		return this.ruleEngine;
	}

	@Override
	public SQWRLQueryEngine getSQWRLQueryEngine()
	{
		return this.queryEngine;
	}

	@Override
	public SWRLParser getSWRLParser()
	{
		return this.swrlParser;
	}

	@Override
	public SWRLAutoCompleter getSWRLAutoCompleter()
	{
		return this.swrlAutoCompleter;
	}

	@Override
	public SWRLRulesTableModel getSWRLRulesTableModel()
	{
		return this.swrlRulesTableModel;
	}

	@Override
	public boolean areSWRLRulesModified()
	{
		return swrlRulesTableModel.hasBeenModified();
	}

	@Override
	public void clearSWRLRulesModified()
	{
		swrlRulesTableModel.clearModifiedStatus();
	}
}
