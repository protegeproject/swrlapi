package org.swrlapi.ui.dialog;

import java.awt.Component;

import javax.swing.JDialog;

import org.swrlapi.ui.SWRLAPIApplicationController;
import org.swrlapi.ui.model.SWRLRuleModel;

public interface SWRLAPIApplicationDialogManager
{
	void initialize(SWRLAPIApplicationController applicationContoroller);

	JDialog getCreateSWRLRuleDialog();

	JDialog getCreateSWRLRuleDialog(SWRLRuleModel swrlRulesModel);

	boolean showConfirmDialog(Component component, String title, String message);

	void showMessageDialog(Component component, String message);

	void showErrorMessageDialog(Component component, String message);

	void showErrorMessageDialog(String message);
}
