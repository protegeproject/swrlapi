package org.swrlapi.ui.core;

public class SWRLRuleExecutor
{
	private SWRLRulesDataSource swrlRuleDataSource;

	public SWRLRuleExecutor(SWRLRulesDataSource swrlRuleDataSource)
	{
		this.swrlRuleDataSource = swrlRuleDataSource;
	}

	public void setDataSource(SWRLRulesDataSource swrRuleDataSource)
	{
		this.swrlRuleDataSource = swrRuleDataSource;
	}

	public void reset()
	{
	}

	public SWRLRulesDataSource getSWRLRuleDataSource()
	{
		return swrlRuleDataSource;
	}
}
