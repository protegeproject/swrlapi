package org.swrlapi.ui.model;

import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.core.SWRLAPIFactory;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.core.impl.DefaultSWRLAPIRulePrinter;
import org.swrlapi.parser.SWRLParser;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.ui.controller.SWRLAPIApplicationController;

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

	public SWRLAPIApplicationModel(SWRLAPIOWLOntology swrlapiOWLOntology, SWRLRuleEngine ruleEngine)
	{
		this.ruleEngine = ruleEngine;
		this.queryEngine = ruleEngine;
		this.prefixManager = swrlapiOWLOntology.getPrefixManager();
		this.swrlRulePrinter = SWRLAPIFactory.createRulePrinter(prefixManager);
		this.swrlRulesTableModel = SWRLAPIFactory.createSWRLRulesTableModel(ruleEngine, swrlRulePrinter);
		this.swrlParser = SWRLAPIFactory.createSWRLParser(swrlapiOWLOntology);
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
