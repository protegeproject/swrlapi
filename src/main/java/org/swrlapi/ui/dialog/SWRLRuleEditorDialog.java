package org.swrlapi.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

import org.swrlapi.parser.SWRLIncompleteRuleException;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.parser.SWRLParser;
import org.swrlapi.ui.controller.SWRLAPIApplicationController;
import org.swrlapi.ui.model.SWRLRulesTableModel;

public class SWRLRuleEditorDialog extends JDialog
{
	private static final long serialVersionUID = 1L;

	private static final String TITLE = "Edit SWRL Rule";
	private static final String INVALID_RULE_TITLE = "Invalid Rule";
	private static final int BUTTON_PREFERRED_WIDTH = 100;
	private static final int BUTTON_PREFERRED_HEIGHT = 30;

	private final SWRLAPIApplicationController applicationController;

	private JTextField ruleNameTextField, commentTextField;
	private JTextArea ruleTextTextArea;

	private boolean editMode = false;

	public SWRLRuleEditorDialog(SWRLAPIApplicationController applicationController)
	{
		this.applicationController = applicationController;

		setTitle(TITLE);
		setModal(true);

		createComponents();
		this.ruleTextTextArea.addKeyListener(new SWRLRuleEditorKeyAdapter());

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

	public void setEditData(String ruleName, String ruleText, String comment)
	{
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
	}

	private void createComponents()
	{
		Container contentPane = getContentPane();

		JLabel ruleNameLabel = new JLabel("Name");
		ruleNameTextField = new JTextField("");

		JLabel ruleTextLabel = new JLabel("Body");
		ruleTextTextArea = new JTextArea("", 20, 60);
		ruleTextTextArea.setBorder(BorderFactory.createLoweredBevelBorder());

		JLabel commentLabel = new JLabel("Comment");
		commentTextField = new JTextField("");

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setPreferredSize(new Dimension(BUTTON_PREFERRED_WIDTH, BUTTON_PREFERRED_HEIGHT));
		cancelButton.addActionListener(new CancelSWRLRuleEditActionListener());

		JButton okButton = new JButton("OK");
		okButton.setPreferredSize(new Dimension(BUTTON_PREFERRED_WIDTH, BUTTON_PREFERRED_HEIGHT));
		okButton.addActionListener(new OkSWRLRuleEditActionListener());

		contentPane.setLayout(new BorderLayout());

		JPanel surroundPanel = new JPanel(new BorderLayout());
		surroundPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		contentPane.add(surroundPanel, BorderLayout.CENTER);

		JPanel innerPanel = new JPanel(new GridLayout(6, 2));

		surroundPanel.add(innerPanel, BorderLayout.NORTH);
		surroundPanel.add(ruleTextTextArea, BorderLayout.CENTER);

		innerPanel.add(ruleNameLabel);
		innerPanel.add(ruleNameTextField);
		innerPanel.add(commentLabel);
		innerPanel.add(commentTextField);
		innerPanel.add(ruleTextLabel);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(cancelButton);
		buttonPanel.add(okButton);

		surroundPanel.add(buttonPanel, BorderLayout.SOUTH);

		pack();
	}

	private class SWRLRuleEditorKeyAdapter extends KeyAdapter
	{
		@Override
		public void keyTyped(KeyEvent event)
		{
			char ch = event.getKeyChar();
			JTextComponent component = (JTextComponent)event.getSource();
			try {
				String ruleText = component.getDocument().getText(0, component.getCaretPosition()).trim();
				ruleText += ch;
				// System.out.println("ch: " + ch);
				System.out.println("ruleText: " + ruleText);

				if (ruleText.length() != 0)
					getSWRLParser().parseSWRLRule(ruleText, true);
			} catch (SWRLIncompleteRuleException e) {
				System.out.println("Incomplete " + e.getMessage());
			} catch (SWRLParseException e) {
				System.err.println("Error " + e.getMessage());
			} catch (BadLocationException e) {
			}
		}
	}

	private class CancelSWRLRuleEditActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			clearEntryFields();
			setVisible(false);
		}
	}

	private class OkSWRLRuleEditActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			String ruleName = "", ruleText = "", comment = "";
			boolean errorOccurred = false;

			comment = ruleNameTextField.getText();

			try {
				ruleName = ruleNameTextField.getText().trim();
				ruleText = ruleTextTextArea.getText().trim();
				comment = commentTextField.getText().trim();

				// TODO Check the rule and name
			} catch (Exception ex) {
				getApplicationDialogManager().showErrorMessageDialog(ex.getMessage(), INVALID_RULE_TITLE);
				errorOccurred = true;
			}

			if (!errorOccurred) {
				if (editMode) {
					getSWRLRulesModel().removeSWRLRule(ruleName); // Remove original
					getSWRLRulesModel().addSWRLRule(ruleName, ruleText, comment);
				} else {
					getSWRLRulesModel().addSWRLRule(ruleName, ruleText, comment);
				}
				setVisible(false);
				clearEntryFields();
			}
		}
	}

	private SWRLParser getSWRLParser()
	{
		return applicationController.getApplicationModel().getSWRLParser();
	}

	private SWRLRulesTableModel getSWRLRulesModel()
	{
		return applicationController.getApplicationModel().getSWRLRulesTableModel();
	}

	private SWRLAPIApplicationDialogManager getApplicationDialogManager()
	{
		return applicationController.getApplicationDialogManager();
	}
}
