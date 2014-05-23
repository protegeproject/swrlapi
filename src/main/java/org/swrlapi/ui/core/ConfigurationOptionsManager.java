package org.swrlapi.ui.core;

public class ConfigurationOptionsManager
{
	private final SWRLAPIRuleExecutor swrlRuleExecutor;

	public ConfigurationOptionsManager(SWRLAPIRuleExecutor swrlRuleExecutor)
	{
		this.swrlRuleExecutor = swrlRuleExecutor;
	}

	public SWRLAPIRuleExecutor getSWRLRuleExecutor()
	{
		return this.swrlRuleExecutor;
	}
}
