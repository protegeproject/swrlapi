package org.swrlapi.ui.model;

import org.swrlapi.core.SWRLAPIFactory;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.core.SWRLRuleRenderer;
import org.swrlapi.parser.SWRLParser;
import org.swrlapi.sqwrl.SQWRLQueryEngine;

/**
 * Provides an application model that can be used to build a MVC-based GUI that uses the SWRLAPI. Used in conjunction
 * with a {@link org.swrlapi.ui.model.SWRLAPIApplicationModel} and a
 * {@link org.swrlapi.ui.controller.SWRLAPIApplicationController}.
 *
 * @see org.swrlapi.ui.view.SWRLAPIApplicationView
 * @see org.swrlapi.ui.controller.SWRLAPIApplicationController
 */
public class SWRLAPIApplicationModel implements SWRLAPIModel
{
	private final SWRLAPIOWLOntology swrlapiOWLOntology;
	private final SWRLRulesTableModel swrlRulesTableModel;
	private final SQWRLQueryEngine queryEngine;
	private final SWRLRuleEngine ruleEngine;
	private final SWRLParser swrlParser;
	private final SWRLRuleRenderer swrlRuleRenderer;

	public SWRLAPIApplicationModel(SWRLAPIOWLOntology swrlapiOWLOntology, SWRLRuleEngine ruleEngine)
	{
		this.swrlapiOWLOntology = swrlapiOWLOntology;
		this.ruleEngine = ruleEngine;
		this.queryEngine = ruleEngine;
		this.swrlRuleRenderer = this.swrlapiOWLOntology.createSWRLRuleRenderer();
		this.swrlRulesTableModel = SWRLAPIFactory.createSWRLRulesTableModel(ruleEngine, swrlRuleRenderer);
		this.swrlParser = this.swrlapiOWLOntology.createSWRLParser();
	}

	public SWRLAPIOWLOntology getSWRLAPIOWLOntology()
	{
		return this.swrlapiOWLOntology;
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

	public SWRLRulesTableModel getSWRLRulesTableModel()
	{
		return this.swrlRulesTableModel;
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
