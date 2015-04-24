package org.swrlapi.ui.dialog;

import java.awt.Component;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.swrlapi.ui.model.SWRLAPIApplicationModel;

public class DefaultSWRLAPIApplicationDialogManager implements SWRLAPIApplicationDialogManager
{
	private final SWRLRuleEditorDialog swrlRuleEditorDialog;
	private File lastDirectory = null;

	public DefaultSWRLAPIApplicationDialogManager(SWRLAPIApplicationModel applicationModel)
	{
		this.swrlRuleEditorDialog = new SWRLRuleEditorDialog(applicationModel, this);
	}

	@Override
	public JDialog getSWRLRuleEditorDialog(Component parent)
	{
		this.swrlRuleEditorDialog.setLocationRelativeTo(parent);
		return this.swrlRuleEditorDialog;
	}

	@Override
	public JDialog getSWRLRuleEditorDialog(Component parent, String ruleName, String ruleText, String comment)
	{
		this.swrlRuleEditorDialog.setLocationRelativeTo(parent);
		this.swrlRuleEditorDialog.enableEditMode(ruleName, ruleText, comment);

		return this.swrlRuleEditorDialog;
	}

	@Override
	public int showConfirmCancelDialog(Component parent, String message, String title)
	{
		return JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.YES_NO_CANCEL_OPTION);
	}

	@Override
	public boolean showConfirmDialog(Component parent, String message, String title)
	{
		return JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
	}

	@Override
	public void showErrorMessageDialog(Component parent, String message, String title)
	{
		JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public String showInputDialog(Component parent, String message, String initialValue)
	{
		if (initialValue == null) {
			initialValue = "";
		}
		return JOptionPane.showInputDialog(parent, message, initialValue);
	}

	@Override
	public void showMessageDialog(Component parent, String message, String title)
	{
		JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public JFileChooser createFileChooser(String title, String fileDescription, String fileExtension)
	{
		JFileChooser chooser = new JFileChooser(this.lastDirectory) {
			private static final long serialVersionUID = 1L;

			@Override
			public int showDialog(Component c, String s)
			{
				int rval = super.showDialog(c, s);
				if (rval == APPROVE_OPTION) {
					DefaultSWRLAPIApplicationDialogManager.this.lastDirectory = getCurrentDirectory();
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

	@Override
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
					DefaultSWRLAPIApplicationDialogManager.this.lastDirectory = getCurrentDirectory();
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
