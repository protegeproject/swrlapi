package org.swrlapi.ui.core;

import org.swrlapi.ui.action.SaveSWRLRuleAction;
import org.swrlapi.ui.model.SWRLRulesTableModel;
import org.swrlapi.ui.view.SQWRLQueryControllerView;

public class SWRLAPIApplicationModel implements SWRLAPIModel
{
	private SQWRLApplicationView applicationView;
	private final SWRLAPIRulesDataSource swrlRulesDataSource;
	private final SWRLRulesTableModel swrlRulesModel;
	private final SWRLAPIRuleExecutor swrlRuleExecutor;
	private final ConfigurationOptionsManager configurationOptionsManager;
	private final SWRLRulesPersistenceLayer swrlRulesPersistenceLayer;
	private SaveSWRLRuleAction saveRulesAction;
	private String mappingFileName = null;

	public SWRLAPIApplicationModel(SWRLAPIRulesDataSource swrlRulesDataSource, SWRLAPIRuleExecutor swrlRuleExecutor,
			SWRLRulesPersistenceLayer swrlRulesPersistenceLayer)
	{
		this.swrlRulesDataSource = swrlRulesDataSource;
		this.swrlRulesModel = new SWRLRulesTableModel();
		this.swrlRuleExecutor = swrlRuleExecutor;
		this.configurationOptionsManager = new ConfigurationOptionsManager(swrlRuleExecutor);
		this.swrlRulesPersistenceLayer = swrlRulesPersistenceLayer;
	}

	public SWRLAPIRulesDataSource getSWRLRulesDataSource()
	{
		return this.swrlRulesDataSource;
	}

	public SWRLRulesTableModel getSWRLRulesTableModel()
	{
		return this.swrlRulesModel;
	}

	public SWRLAPIRuleExecutor getSWRLRuleExecutor()
	{
		return swrlRuleExecutor;
	}

	public ConfigurationOptionsManager getConfigurationOptionsManager()
	{
		return configurationOptionsManager;
	}

	public SWRLRulesPersistenceLayer getSWRLRulesPersistenceLayer()
	{
		return swrlRulesPersistenceLayer;
	}

	public void setApplicationView(SQWRLApplicationView applicationView)
	{
		this.applicationView = applicationView;
	}

	public SQWRLApplicationView getApplicationView()
	{
		return applicationView;
	}

	public void saveSWRLRules()
	{
		if (saveRulesAction != null)
			saveRulesAction.saveSWRLRule();
	}

	public void dataSourceUpdated()
	{
		swrlRuleExecutor.setSWRLRuleDataSource(getSWRLRulesDataSource());
	}

	public void setMappingFileName(String mappingFileName)
	{
		this.mappingFileName = mappingFileName;
		updateView();
	}

	public void clearMappingFileName()
	{
		mappingFileName = null;
		getMappingsControlView().update();
	}

	public boolean hasMappingFile()
	{
		return mappingFileName != null;
	}

	public String getMappingFileName()
	{
		return mappingFileName;
	}

	public boolean areMappingsModified()
	{
		return swrlRulesModel.hasBeenModified();
	}

	public void clearModifiedStatus()
	{
		swrlRulesModel.clearModifiedStatus();
	}

	private void updateView()
	{
		if (applicationView != null)
			applicationView.update();
	}

	private SQWRLQueryControllerView getMappingsControlView()
	{
		return applicationView.getSQWRLQueryControllerView();
	}
}
