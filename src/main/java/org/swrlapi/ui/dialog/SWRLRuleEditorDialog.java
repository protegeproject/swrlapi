package org.swrlapi.ui.dialog;

import java.awt.*;
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
import org.swrlapi.ui.model.SWRLAPIApplicationModel;
import org.swrlapi.ui.model.SWRLRulesTableModel;

public class SWRLRuleEditorDialog extends JDialog
{
	private static final long serialVersionUID = 1L;

	private static final String TITLE = "Edit SWRL Rule";
	private static final String RULE_NAME_TITLE = "Rule";
	private static final String COMMENT_LABEL_TITLE = "Comment";
	private static final String STATUS_LABEL_TITLE = "Status";
	private static final String OK_BUTTON_TITLE = "Ok";
	private static final String CANCEL_BUTTON_TITLE = "Cancel";

	private static final String INVALID_RULE_TITLE = "Invalid Rule";
	private static final int BUTTON_PREFERRED_WIDTH = 100;
	private static final int BUTTON_PREFERRED_HEIGHT = 30;
	private static final int RULE_EDIT_AREA_COLUMNS = 20;
	private static final int RULE_EDIT_AREA_ROWS = 60;

	private final SWRLAPIApplicationModel applicationModel;
	private final SWRLAPIApplicationDialogManager applicationDialogManager;

	private JTextField ruleNameTextField, commentTextField, statusTextField;
	private JTextArea ruleTextTextArea;

	private boolean editMode = false;

	public SWRLRuleEditorDialog(SWRLAPIApplicationModel applicationModel,
			SWRLAPIApplicationDialogManager applicationDialogManager)
	{
		this.applicationModel = applicationModel;
		this.applicationDialogManager = applicationDialogManager;

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
		statusTextField.setText("");

		editMode = false;
	}

	private void createComponents()
	{
		Container contentPane = getContentPane();

		JLabel ruleNameLabel = new JLabel(RULE_NAME_TITLE);
		ruleNameTextField = new JTextField("");

		ruleTextTextArea = new JTextArea("", RULE_EDIT_AREA_COLUMNS, RULE_EDIT_AREA_ROWS);
		ruleTextTextArea.setBorder(BorderFactory.createLoweredBevelBorder());

		JLabel commentLabel = new JLabel(COMMENT_LABEL_TITLE);
		commentTextField = new JTextField("");

		JLabel statusLabel = new JLabel(STATUS_LABEL_TITLE);
		statusTextField = new JTextField("Please enter or edit rule below");
		statusTextField.setEnabled(false);

		JButton cancelButton = new JButton(CANCEL_BUTTON_TITLE);
		cancelButton.setPreferredSize(new Dimension(BUTTON_PREFERRED_WIDTH, BUTTON_PREFERRED_HEIGHT));
		cancelButton.addActionListener(new CancelSWRLRuleEditActionListener());

		JButton okButton = new JButton(OK_BUTTON_TITLE);
		okButton.setPreferredSize(new Dimension(BUTTON_PREFERRED_WIDTH, BUTTON_PREFERRED_HEIGHT));
		okButton.addActionListener(new OkSWRLRuleEditActionListener(contentPane));

		contentPane.setLayout(new BorderLayout());

		JPanel upperPanel = new JPanel(new GridLayout(6, 2));
		upperPanel.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 3));
		upperPanel.add(ruleNameLabel);
		upperPanel.add(ruleNameTextField);
		upperPanel.add(commentLabel);
		upperPanel.add(commentTextField);
		upperPanel.add(statusLabel);
		upperPanel.add(statusTextField);

		JPanel rulePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		rulePanel.add(ruleTextTextArea);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(cancelButton);
		buttonPanel.add(okButton);

		JPanel surroundPanel = new JPanel(new BorderLayout());
		surroundPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		contentPane.add(surroundPanel, BorderLayout.CENTER);
		surroundPanel.add(upperPanel, BorderLayout.NORTH);
		surroundPanel.add(rulePanel, BorderLayout.CENTER);
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
				//System.out.println("ruleText: " + ruleText);

				if (ruleText.length() != 0)
					getSWRLParser().parseSWRLRule(ruleText, true);
				statusTextField.setText("Sooper");
			} catch (SWRLIncompleteRuleException e) {
				statusTextField.setText(e.getMessage());
			} catch (SWRLParseException e) {
				statusTextField.setText("Error: " + e.getMessage());
			} catch (BadLocationException e) {
				// Not much we can do here!
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
		private final Component parent;

		public OkSWRLRuleEditActionListener(Component parent) { this.parent = parent; }

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
				getApplicationDialogManager().showErrorMessageDialog(parent, ex.getMessage(), INVALID_RULE_TITLE);
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
		return this.applicationModel.getSWRLParser();
	}

	private SWRLRulesTableModel getSWRLRulesModel()
	{
		return this.applicationModel.getSWRLRulesTableModel();
	}

	private SWRLAPIApplicationDialogManager getApplicationDialogManager()
	{
		return this.applicationDialogManager;
	}
}
