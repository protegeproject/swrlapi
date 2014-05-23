package org.swrlapi.ui.core;

import org.swrlapi.ui.model.SWRLRulesTableModel;

public class SWRLAPIApplicationModel implements SWRLAPIModel
{
	private SWRLAPIApplicationView applicationView;
	private final SWRLRulesTableModel swrlRulesTableModel;
	private final SWRLAPIRuleExecutor swrlRuleExecutor;
	private final ConfigurationOptionsManager configurationOptionsManager;

	public SWRLAPIApplicationModel(SWRLAPIRuleExecutor swrlRuleExecutor)
	{
		this.swrlRulesTableModel = new SWRLRulesTableModel();
		this.swrlRuleExecutor = swrlRuleExecutor;
		this.configurationOptionsManager = new ConfigurationOptionsManager(swrlRuleExecutor);
	}

	public SWRLRulesTableModel getSWRLRulesTableModel()
	{
		return this.swrlRulesTableModel;
	}

	public SWRLAPIRuleExecutor getSWRLRuleExecutor()
	{
		return this.swrlRuleExecutor;
	}

	public ConfigurationOptionsManager getConfigurationOptionsManager()
	{
		return this.configurationOptionsManager;
	}

	public void setApplicationView(SQWRLApplicationView applicationView)
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
