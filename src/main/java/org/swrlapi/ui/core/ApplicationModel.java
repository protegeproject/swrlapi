package org.swrlapi.ui.core;

import org.swrlapi.ui.action.SaveSWRLRulesAction;
import org.swrlapi.ui.model.SWRLRulesDataSourceModel;
import org.swrlapi.ui.model.SWRLRulesModel;
import org.swrlapi.ui.view.MappingsControlView;

public class ApplicationModel implements Model
{
	private ApplicationView applicationView;
	private final SWRLRulesDataSourceModel dataSourceModel;
	private final SWRLRulesModel swrlRulesModel;
	private final SWRLRuleExecutor swrlRuleExecutor;
	private final ConfigurationOptionsManager configurationOptionsManager;
	private final SWRLRulesPersistenceLayer swrlRulesPersistenceLayer;
	private SaveSWRLRulesAction saveRulesAction;
	private String mappingFileName = null;

	public ApplicationModel(SWRLRulesDataSource swrlRulesDataSource, SWRLRuleExecutor renderer,
			SWRLRulesPersistenceLayer swrlRulesPersistenceLayer)
	{
		this.dataSourceModel = new SWRLRulesDataSourceModel(swrlRulesDataSource);
		this.swrlRulesModel = new SWRLRulesModel();
		this.swrlRuleExecutor = renderer;
		this.configurationOptionsManager = new ConfigurationOptionsManager(renderer);
		this.swrlRulesPersistenceLayer = swrlRulesPersistenceLayer;
	}

	public SWRLRulesDataSourceModel getDataSourceModel()
	{
		return dataSourceModel;
	}

	public SWRLRulesModel getSWRLRulesModel()
	{
		return swrlRulesModel;
	}

	public SWRLRuleExecutor getSWRLRuleExecutor()
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

	public void setApplicationView(ApplicationView applicationView)
	{
		this.applicationView = applicationView;
	}

	public ApplicationView getApplicationView()
	{
		return applicationView;
	}

	public void setSaveSWRLRuleAction(SaveSWRLRulesAction saveSWRLRulesAction)
	{
		this.saveRulesAction = saveSWRLRulesAction;
	}

	public void saveSWRLRules()
	{
		if (saveRulesAction != null)
			saveRulesAction.saveSWRLRules();
	}

	public void dataSourceUpdated()
	{
		swrlRuleExecutor.setDataSource(getDataSourceModel().getSWRLRulesDataSource());
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

	private MappingsControlView getMappingsControlView()
	{
		return applicationView.getMappingsControlView();
	}
}
