package org.swrlapi.ui.dialog;

import java.awt.Component;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.swrlapi.ui.controller.SWRLAPIApplicationController;

public class SWRLAPIApplicationDialogManager
{
	private final EditSWRLRuleDialog editSWRLRuleDialog;
	private Component parent;
	private File lastDirectory = null;

	// TODO setLocationRelativeTo(Component parent);

	public SWRLAPIApplicationDialogManager(SWRLAPIApplicationController applicationController)
	{
		this.editSWRLRuleDialog = new EditSWRLRuleDialog(applicationController);
	}

	public JDialog getCreateSWRLRuleDialog()
	{
		return this.editSWRLRuleDialog;
	}

	public JDialog getEditSWRLRuleDialog(String ruleName, String ruleText, String comment)
	{
		this.editSWRLRuleDialog.setEditData(ruleName, ruleText, comment);

		return this.editSWRLRuleDialog;
	}

	public int showConfirmCancelDialog(String message, String title)
	{
		return JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.YES_NO_CANCEL_OPTION);
	}

	public boolean showConfirmDialog(String message, String title)
	{
		return JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
	}

	public void showErrorMessageDialog(String message, String title)
	{
		JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
	}

	public String showInputDialog(String message, String initialValue)
	{
		if (initialValue == null) {
			initialValue = "";
		}
		return JOptionPane.showInputDialog(parent, message, initialValue);
	}

	public void showMessageDialog(String message, String title)
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
			String text = fileDescription;
			chooser.setFileFilter(new ExtensionFilter(fileExtension, text));
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
