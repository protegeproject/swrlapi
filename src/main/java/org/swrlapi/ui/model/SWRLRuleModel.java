package org.swrlapi.ui.model;

public class SWRLRuleModel
{
	private boolean active;
	private String ruleName, ruleText, comment;

	public SWRLRuleModel(String ruleName, String ruleText, String comment)
	{
		this.active = true;
		this.ruleText = ruleText;
		this.ruleName = ruleName;
		this.comment = comment;
	}

	public void update(String ruleName, String ruleText, String comment)
	{
		this.ruleName = ruleName;
		this.ruleText = ruleText;
		this.comment = comment;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	public boolean isActive()
	{
		return active;
	}

	public String getRuleText()
	{
		return ruleText;
	}

	public String getRuleName()
	{
		return ruleName;
	}

	public String getComment()
	{
		return comment;
	}

	@Override
	public String toString()
	{
		return "(ruleName: " + ruleName + ", ruleText: " + ruleText + ", comment: " + comment + ", active: " + active + ")";
	}
}