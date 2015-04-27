package org.swrlapi.ui.dialog;

import java.awt.Component;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

/**
 * Provides a set of standard dialogs.
 */
public interface SWRLAPIDialogManager
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
