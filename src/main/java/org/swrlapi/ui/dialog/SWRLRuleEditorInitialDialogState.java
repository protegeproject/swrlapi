package org.swrlapi.ui.dialog;

import checkers.nullness.quals.NonNull;

class SWRLRuleEditorInitialDialogState
{
  @NonNull private String ruleName = "";
  @NonNull private String comment = "";
  @NonNull private String ruleText = "";

  public void setState(@NonNull String ruleName, @NonNull String comment, @NonNull String ruleText)
  {
    this.ruleName = ruleName.trim();
    this.comment = comment.trim();
    this.ruleText = ruleText.trim();
  }

  @NonNull public String getRuleName()
  {
    return this.ruleName;
  }

  public boolean hasStateChanged(@NonNull String currentRuleName, @NonNull String currentComment,
    @NonNull String currentRuleText)
  {
    return !this.ruleName.equals(currentRuleName.trim()) || !this.comment.equals(currentComment.trim())
      || !this.ruleText.equals(currentRuleText.trim());
  }
}