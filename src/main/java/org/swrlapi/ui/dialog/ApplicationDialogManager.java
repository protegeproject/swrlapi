package org.swrlapi.ui.dialog;

import java.awt.Component;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

import org.swrlapi.ui.ApplicationController;
import org.swrlapi.ui.core.SWRLRuleModel;

public interface ApplicationDialogManager
{
	void initialize(ApplicationController application);

	JDialog getCreateMappingExpressionDialog();

	JDialog getCreateMappingExpressionDialog(SWRLRuleModel mappingExpression);

	boolean showConfirmDialog(Component component, String title, String message);

	void showMessageDialog(Component component, String message);

	void showErrorMessageDialog(Component component, String message);

	void showErrorMessageDialog(String message);

	JFileChooser createFileChooser(String message, String extension);

	JFileChooser createSaveFileChooser(String message, String extension, boolean overwrite);
}
