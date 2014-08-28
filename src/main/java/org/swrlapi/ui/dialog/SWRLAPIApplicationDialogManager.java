package org.swrlapi.ui.dialog;

import java.awt.Component;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.swrlapi.ui.model.SWRLAPIApplicationModel;

/**
 * Provides a set of dialogs that can be used to build a MVC-based GUI that uses the SWRLAPI. Used in
 * conjunction with a {@link org.swrlapi.ui.controller.SWRLAPIApplicationController}.
 * 
 * @see org.swrlapi.ui.controller.SWRLAPIApplicationController
 * @see org.swrlapi.ui.model.SWRLAPIApplicationModel
 * @see org.swrlapi.ui.view.SWRLAPIApplicationView
 */
public class SWRLAPIApplicationDialogManager
{
	private final SWRLRuleEditorDialog swrlRuleEditorDialog;
	private File lastDirectory = null;

	public SWRLAPIApplicationDialogManager(SWRLAPIApplicationModel applicationModel)
	{
		this.swrlRuleEditorDialog = new SWRLRuleEditorDialog(applicationModel, this);
	}

	public JDialog getSWRLRuleEditorDialog(Component parent)
	{
		this.swrlRuleEditorDialog.setLocationRelativeTo(parent);
		return this.swrlRuleEditorDialog;
	}

	public JDialog getSWRLRuleEditorDialog(Component parent, String ruleName, String ruleText, String comment)
	{
		this.swrlRuleEditorDialog.setLocationRelativeTo(parent);
		this.swrlRuleEditorDialog.setEditData(ruleName, ruleText, comment);

		return this.swrlRuleEditorDialog;
	}

	public int showConfirmCancelDialog(Component parent, String message, String title)
	{
		return JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.YES_NO_CANCEL_OPTION);
	}

	public boolean showConfirmDialog(Component parent, String message, String title)
	{
		return JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
	}

	public void showErrorMessageDialog(Component parent, String message, String title)
	{
		JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
	}

	public String showInputDialog(Component parent, String message, String initialValue)
	{
		if (initialValue == null) {
			initialValue = "";
		}
		return JOptionPane.showInputDialog(parent, message, initialValue);
	}

	public void showMessageDialog(Component parent, String message, String title)
	{
		JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
	}

	public JFileChooser createFileChooser(String title, String fileDescription, String fileExtension)
	{
		JFileChooser chooser = new JFileChooser(this.lastDirectory) {
			private static final long serialVersionUID = 1L;

			@Override
			public int showDialog(Component c, String s)
			{
				int rval = super.showDialog(c, s);
				if (rval == APPROVE_OPTION) {
					SWRLAPIApplicationDialogManager.this.lastDirectory = getCurrentDirectory();
				}
				return rval;
			}
		};
		chooser.setDialogTitle(title);
		if (fileExtension == null) {
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		} else if (fileExtension.length() > 0) {
			chooser.setFileFilter(new ExtensionFilter(fileExtension, fileDescription));
		}
		return chooser;
	}

	public JFileChooser createSaveFileChooser(String title, String fileDescription, String fileExtension,
			final boolean overwrite)
	{
		JFileChooser chooser = new JFileChooser(this.lastDirectory) {
			private static final long serialVersionUID = 1L;

			@Override
			public int showDialog(Component c, String s)
			{
				int rval = super.showDialog(c, s);
				if (rval == APPROVE_OPTION) {
					SWRLAPIApplicationDialogManager.this.lastDirectory = getCurrentDirectory();
				}
				return rval;
			}

			@Override
			public void approveSelection()
			{
				if (!overwrite) {
					return;
				}

				File f = getSelectedFile();

				if (f.exists()) {
					String msg = "The file '" + f.getName() + "' already exists!\nDo you want to replace it?";
					String title = getDialogTitle();
					int option = JOptionPane.showConfirmDialog(this, msg, title, JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE);
					if (option == JOptionPane.NO_OPTION) {
						return;
					}
				}
				super.approveSelection();
			}
		};

		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setDialogTitle(title);
		if (fileExtension != null) {
			String text = fileDescription;
			chooser.setFileFilter(new ExtensionFilter(fileExtension, text));
		}
		return chooser;
	}
}
