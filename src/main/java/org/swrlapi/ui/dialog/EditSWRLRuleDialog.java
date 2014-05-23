package org.swrlapi.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.swrlapi.ui.ApplicationController;
import org.swrlapi.ui.core.ApplicationView;
import org.swrlapi.ui.core.SWRLRuleModel;
import org.swrlapi.ui.model.SWRLRulesModel;

public class EditSWRLRuleDialog extends JDialog
{
	private static final long serialVersionUID = 1L;

	private final ApplicationController application;

	private JLabel ruleNameLabel, ruleTextLabel, commentLabel;
	private JTextField ruleNameTextField, commentTextField;
	private JTextArea ruleTextTextArea;

	private boolean editMode = false;
	private SWRLRuleModel swrlRuleModel;

	public EditSWRLRuleDialog(ApplicationController application)
	{
		setTitle("Edit SWRL Rule");
		setModal(true);

		this.application = application;

		createComponents();

		setLocationRelativeTo(application.getApplicationViewController());

		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we)
			{
			}
		}); // Thwart user close
	}

	public void reset()
	{
		clearEntryFields();
	}

	public void setEditMappingExpression(SWRLRuleModel swrlRuleModel)
	{
		String ruleName = swrlRuleModel.getRuleName();
		String ruleText = swrlRuleModel.getRuleText();
		String comment = swrlRuleModel.getComment();

		this.swrlRuleModel = swrlRuleModel;

		clearEntryFields();

		ruleNameTextField.setText(ruleName);
		ruleTextTextArea.setText(ruleText);
		commentTextField.setText(comment);

		editMode = true;
	}

	private void clearEntryFields()
	{
		ruleNameTextField.setText("");
		ruleNameTextField.setEnabled(true);
		ruleTextTextArea.setText("");
		ruleTextTextArea.setEnabled(true);
		ruleTextTextArea.setText("");
		commentTextField.setText("");

		editMode = false;
		this.swrlRuleModel = null;
	}

	private void createComponents()
	{
		Container contentPane = getContentPane();
		JPanel surroundPanel, buttonPanel, textFieldPanel;
		JButton cancelButton, okButton;

		ruleNameLabel = new JLabel("Comment");
		ruleNameTextField = new JTextField("");

		ruleTextTextArea = new JTextArea("", 20, 80);
		ruleTextTextArea.setBorder(BorderFactory.createLoweredBevelBorder());

		ruleTextLabel = new JLabel("Sheet name");

		commentLabel = new JLabel("Start column");

		commentTextField = new JTextField("");

		cancelButton = new JButton("Cancel");
		cancelButton.setPreferredSize(new Dimension(100, 30));
		cancelButton.addActionListener(new CancelButtonActionListener());

		okButton = new JButton("OK");
		okButton.setPreferredSize(new Dimension(100, 30));
		okButton.addActionListener(new OkButtonActionListener());

		contentPane.setLayout(new BorderLayout());

		surroundPanel = new JPanel(new BorderLayout());
		surroundPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		contentPane.add(surroundPanel, BorderLayout.CENTER);

		textFieldPanel = new JPanel(new GridLayout(6, 2));

		surroundPanel.add(textFieldPanel, BorderLayout.NORTH);
		surroundPanel.add(ruleTextTextArea, BorderLayout.CENTER);

		textFieldPanel.add(ruleNameLabel);
		textFieldPanel.add(ruleNameTextField);
		textFieldPanel.add(ruleTextLabel);
		textFieldPanel.add(commentLabel);
		textFieldPanel.add(commentTextField);

		buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(cancelButton);
		buttonPanel.add(okButton);

		surroundPanel.add(buttonPanel, BorderLayout.SOUTH);

		pack();
	}

	private class CancelButtonActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			clearEntryFields();
			setVisible(false);
		}
	}

	private class OkButtonActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			String ruleName = "", ruleText = "", comment = "";
			boolean errorOccurred = false;

			comment = ruleNameTextField.getText();

			try {
				ruleName = ruleNameTextField.getText().trim().toUpperCase();
				ruleText = ruleTextTextArea.getText().trim().toUpperCase();
				comment = commentTextField.getText().trim().toUpperCase();

			} catch (Exception ex) {
				getApplicationDialogManager().showErrorMessageDialog(ex.getMessage());
				errorOccurred = true;
			}

			if (!errorOccurred) {
				if (editMode) {
					swrlRuleModel.update(ruleName, ruleText, comment);
					getSWRLRulesModel().removeSWRLRuleModel(swrlRuleModel); // Remove original
					getSWRLRulesModel().addSWRLRuleModel(swrlRuleModel);
				} else {
					SWRLRuleModel swrlRuleModel = new SWRLRuleModel(ruleName, ruleText, comment);
					getSWRLRulesModel().addSWRLRuleModel(swrlRuleModel);
				}

				setVisible(false);
				clearEntryFields();
			}
		}
	}

	private SWRLRulesModel getSWRLRulesModel()
	{
		return application.getApplicationModel().getSWRLRulesModel();
	}

	private ApplicationView getApplicationView()
	{
		return application.getApplicationViewController();
	}

	private ApplicationDialogManager getApplicationDialogManager()
	{
		return getApplicationView().getApplicationDialogManager();
	}
}
