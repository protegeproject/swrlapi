package org.swrlapi.ui.core;

public class SWRLAPIRuleExecutor
{
	private SWRLAPIRulesDataSource swrlRuleDataSource;

	public SWRLAPIRuleExecutor(SWRLAPIRulesDataSource swrlRuleDataSource)
	{
		this.swrlRuleDataSource = swrlRuleDataSource;
	}

	public void setSWRLRuleDataSource(SWRLAPIRulesDataSource swrRuleDataSource)
	{
		this.swrlRuleDataSource = swrRuleDataSource;
	}

	public void reset()
	{
	}

	public SWRLAPIRulesDataSource getSWRLRuleDataSource()
	{
		return swrlRuleDataSource;
	}
}
