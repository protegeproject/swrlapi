package org.swrlapi.ui.dialog;

import javax.swing.JDialog;

public interface SWRLAPIApplicationDialogManager
{
	JDialog getCreateSWRLRuleDialog();

	JDialog getEditSWRLRuleDialog(String ruleName, String ruleText, String comment);

	boolean showConfirmDialog(String title, String message);

	void showMessageDialog(String message);

	void showErrorMessageDialog(String message);
}
