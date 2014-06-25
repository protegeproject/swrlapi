package org.swrlapi.ui.model;

import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.core.SWRLAPIFactory;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.core.impl.DefaultSWRLAPIRulePrinter;
import org.swrlapi.parser.SWRLParser;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.ui.controller.SWRLAPIApplicationController;
import org.swrlapi.ui.view.SWRLAPIApplicationView;

/**
 * Provides an application model that can be used to build a MVC-based GUI that uses the SWRLAPI. Used in conjunction
 * with a {@link SWRLAPIApplicationModel} and a {@link SWRLAPIApplicationController}.
 * 
 * @see SWRLAPIApplicationView, SWRLAPIApplicationController
 */
public class SWRLAPIApplicationModel implements SWRLAPIModel
{
	private final SWRLRuleEngine ruleEngine;
	private final SWRLParser swrlParser;
	private final SWRLRulesTableModel swrlRulesTableModel;
	private final SQWRLQueryEngine queryEngine;
	private final DefaultPrefixManager prefixManager;
	private final DefaultSWRLAPIRulePrinter swrlRulePrinter;

	private SWRLAPIApplicationView applicationView;

	public SWRLAPIApplicationModel(SWRLAPIOWLOntology swrlapiOWLOntology, SWRLRuleEngine ruleEngine,
			DefaultPrefixManager prefixManager)
	{
		this.ruleEngine = ruleEngine;
		this.queryEngine = ruleEngine;
		this.prefixManager = prefixManager;
		this.swrlParser = SWRLAPIFactory.createSWRLParser(swrlapiOWLOntology, prefixManager);
		this.swrlRulePrinter = SWRLAPIFactory.createSWRLAPIRulePrinter(prefixManager);
		this.swrlRulesTableModel = SWRLAPIFactory.createSWRLRulesTableModel(ruleEngine, swrlRulePrinter);
	}

	public SWRLAPIApplicationView getApplicationView()
	{
		return this.applicationView;
	}

	public SWRLRuleEngine getSWRLRuleEngine()
	{
		return this.ruleEngine;
	}

	public SQWRLQueryEngine getSQWRLQueryEngine()
	{
		return this.queryEngine;
	}

	public SWRLParser getSWRLParser()
	{
		return this.swrlParser;
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

	public void saveSWRLRules()
	{
		// TODO
	}

	public boolean areSWRLRulesModified()
	{
		return swrlRulesTableModel.hasBeenModified();
	}

	public void clearSWRLRulesModified()
	{
		swrlRulesTableModel.clearModifiedStatus();
	}
}
