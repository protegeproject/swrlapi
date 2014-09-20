package org.swrlapi.ui.dialog;

class SWRLRuleEditorInitialDialogState
{
	private String ruleName = "", comment = "", ruleText = "";

	public void setState(String ruleName, String comment, String ruleText)
	{
		this.ruleName = ruleName.trim();
		this.comment = comment.trim();
		this.ruleText = ruleText.trim();
	}

	public String getRuleName() { return this.ruleName; }

	public boolean hasStateChanged(String currentRuleName, String currentComment, String currentRuleText)
	{
		return !this.ruleName.equals(currentRuleName.trim()) ||
				!this.comment.equals(currentComment.trim()) ||
				!this.ruleText.equals(currentRuleText.trim());
	}
}