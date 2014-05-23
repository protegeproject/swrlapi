package org.swrlapi.ui.core;

public class ConfigurationOptionsManager
{
	private final SWRLRuleExecutor swrlRuleExecutor;

	public ConfigurationOptionsManager(SWRLRuleExecutor swrlRuleExecutor)
	{
		this.swrlRuleExecutor = swrlRuleExecutor;
	}

	public SWRLRuleExecutor getSWRLRuleExecutor()
	{
		return this.swrlRuleExecutor;
	}
}
