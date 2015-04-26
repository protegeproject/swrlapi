package org.swrlapi.ui.dialog;

import java.awt.Component;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

/**
 * Provides a set of standard dialogs. Used in conjunction with a
 * {@link org.swrlapi.ui.controller.SWRLRuleEngineController}.
 *
 * @see org.swrlapi.ui.controller.SWRLRuleEngineController
 * @see org.swrlapi.ui.model.SWRLRuleEngineModel
 * @see org.swrlapi.ui.view.SWRLAPIApplicationView
 */
public interface SWRLRuleEngineDialogManager
{
	JDialog getSWRLRuleEditorDialog(Component parent);

	JDialog getSWRLRuleEditorDialog(Component parent, String ruleName, String ruleText, String comment);

	int showConfirmCancelDialog(Component parent, String message, String title);

	boolean showConfirmDialog(Component parent, String message, String title);

	void showErrorMessageDialog(Component parent, String message, String title);

	String showInputDialog(Component parent, String message, String initialValue);

	void showMessageDialog(Component parent, String message, String title);

	JFileChooser createFileChooser(String title, String fileDescription, String fileExtension);

	JFileChooser createSaveFileChooser(String title, String fileDescription, String fileExtension, final boolean overwrite);
}
