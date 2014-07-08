package org.swrlapi.ui.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.TableColumnModel;

import org.swrlapi.ui.dialog.SWRLAPIApplicationDialogManager;
import org.swrlapi.ui.model.SWRLRulesTableModel;

public class SWRLRulesTableView extends JPanel implements SWRLAPIView
{
	private static final long serialVersionUID = 1L;

	private static final int ACTIVE_COLUMN_PREFERRED_WIDTH = 30;
	private static final int ACTIVE_COLUMN_MAX_WIDTH = 50;
	private static final int RULE_NAME_COLUMN_PREFERRED_WIDTH = 150;
	private static final int RULE_NAME_COLUMN_MAX_WIDTH = 200;
	private static final int RULE_TEXT_COLUMN_PREFERRED_WIDTH = 500;
	private static final int RULE_TEXT_COLUMN_MAX_WIDTH = 1400;
	private static final int RULE_COMMENT_COLUMN_PREFERRED_WIDTH = 200;
	private static final int RULE_COMMENT_COLUMN_MAX_WIDTH = 300;

	private final SWRLAPIApplicationDialogManager applicationDialogManager;
	private final SWRLRulesTableModel swrlRulesTableModel;
	private final JTable swrlRulesTable;

	public SWRLRulesTableView(SWRLRulesTableModel swrlRulesTableModel,
			SWRLAPIApplicationDialogManager applicationDialogManager)
	{
		this.applicationDialogManager = applicationDialogManager;
		this.swrlRulesTableModel = swrlRulesTableModel;
		this.swrlRulesTable = new JTable(this.swrlRulesTableModel);

		addTableListeners();
		setPreferredColumnWidths();
		swrlRulesTableModel.setView(this);
		createComponents(applicationDialogManager);
	}

	@Override
	public void update()
	{
		this.swrlRulesTableModel.fireTableDataChanged();
		validate();
	}

	private String getSelectedSWRLRuleName()
	{
		int selectedRow = this.swrlRulesTable.getSelectedRow();

		if (selectedRow != -1)
			return this.swrlRulesTableModel.getSWRLRuleNameByIndex(selectedRow);
		else
			return "";
	}

	private String getSelectedSWRLRuleText()
	{
		int selectedRow = this.swrlRulesTable.getSelectedRow();

		if (selectedRow != -1)
			return this.swrlRulesTableModel.getSWRLRuleTextByIndex(selectedRow);
		else
			return "";
	}

	private String getSelectedSWRLRuleComment()
	{
		int selectedRow = this.swrlRulesTable.getSelectedRow();

		if (selectedRow != -1)
			return this.swrlRulesTableModel.getSWRLRuleCommentByIndex(selectedRow);
		else
			return "";
	}

	private void setPreferredColumnWidths()
	{
		TableColumnModel columnModel = this.swrlRulesTable.getColumnModel();

		columnModel.getColumn(SWRLRulesTableModel.ACTIVE_COLUMN).setPreferredWidth(ACTIVE_COLUMN_PREFERRED_WIDTH);
		columnModel.getColumn(SWRLRulesTableModel.ACTIVE_COLUMN).setMaxWidth(ACTIVE_COLUMN_MAX_WIDTH);
		columnModel.getColumn(SWRLRulesTableModel.RULE_NAME_COLUMN).setPreferredWidth(RULE_NAME_COLUMN_PREFERRED_WIDTH);
		columnModel.getColumn(SWRLRulesTableModel.RULE_NAME_COLUMN).setMaxWidth(RULE_NAME_COLUMN_MAX_WIDTH);
		columnModel.getColumn(SWRLRulesTableModel.RULE_TEXT_COLUMN).setPreferredWidth(RULE_TEXT_COLUMN_PREFERRED_WIDTH);
		columnModel.getColumn(SWRLRulesTableModel.RULE_TEXT_COLUMN).setMaxWidth(RULE_TEXT_COLUMN_MAX_WIDTH);
		columnModel.getColumn(SWRLRulesTableModel.RULE_COMMENT_COLUMN).setPreferredWidth(
				RULE_COMMENT_COLUMN_PREFERRED_WIDTH);
		columnModel.getColumn(SWRLRulesTableModel.RULE_COMMENT_COLUMN).setMaxWidth(RULE_COMMENT_COLUMN_MAX_WIDTH);
	}

	private void addTableListeners()
	{
		this.swrlRulesTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() == 2) {
					if (e.getSource() == swrlRulesTable) {
						editSelectedSWRLRule();
					}
				}
			}
		});
	}

	private void editSelectedSWRLRule()
	{
		String ruleName = getSelectedSWRLRuleName();
		String ruleText = getSelectedSWRLRuleText();
		String ruleComment = getSelectedSWRLRuleComment();

		if (ruleName.length() != 0)
			this.applicationDialogManager.getSWRLRuleEditorDialog(ruleName, ruleText, ruleComment).setVisible(true);
	}

	private void createComponents(SWRLAPIApplicationDialogManager applicationDialogManager)
	{
		JScrollPane scrollPane = new JScrollPane(swrlRulesTable);
		JViewport viewport = scrollPane.getViewport();

		setLayout(new BorderLayout());

		JPanel headingPanel = new JPanel(new BorderLayout());
		add(headingPanel, BorderLayout.SOUTH);

		viewport.setBackground(swrlRulesTable.getBackground());

		JPanel buttonPanel = new JPanel(new BorderLayout());
		headingPanel.add(buttonPanel, BorderLayout.EAST);

		JButton addButton = new JButton("Add");
		addButton.addActionListener(new AddSWRLRuleActionListener(applicationDialogManager));
		buttonPanel.add(addButton, BorderLayout.WEST);

		JButton editButton = new JButton("Edit");
		editButton.addActionListener(new EditSWRLRuleActionListener(applicationDialogManager));
		buttonPanel.add(editButton, BorderLayout.CENTER);

		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new DeleteSWRLRuleActionListener(applicationDialogManager));
		buttonPanel.add(deleteButton, BorderLayout.EAST);

		add(scrollPane, BorderLayout.CENTER);

		validate();
	}

	private abstract class ActionListenerBase implements ActionListener
	{
		protected final SWRLAPIApplicationDialogManager applicationDialogManager;

		protected ActionListenerBase(SWRLAPIApplicationDialogManager applicationDialogManager)
		{
			this.applicationDialogManager = applicationDialogManager;
		}
	}

	private class AddSWRLRuleActionListener extends ActionListenerBase
	{
		public AddSWRLRuleActionListener(SWRLAPIApplicationDialogManager applicationDialogManager)
		{
			super(applicationDialogManager);
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			this.applicationDialogManager.getCreateSWRLRuleDialog().setVisible(true);
		}
	}

	private class EditSWRLRuleActionListener extends ActionListenerBase
	{
		public EditSWRLRuleActionListener(SWRLAPIApplicationDialogManager applicationDialogManager)
		{
			super(applicationDialogManager);
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			editSelectedSWRLRule();
		}
	}

	private class DeleteSWRLRuleActionListener extends ActionListenerBase
	{
		public DeleteSWRLRuleActionListener(SWRLAPIApplicationDialogManager applicationDialogManager)
		{
			super(applicationDialogManager);
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			deleteSelectedSWRLRule();
		}

		private void deleteSelectedSWRLRule()
		{
			String selectedRuleName = getSelectedSWRLRuleName();

			if (SWRLRulesTableView.this.swrlRulesTableModel.hasSWRLRule(selectedRuleName)
					&& this.applicationDialogManager.showConfirmDialog("Do you really want to delete the rule?", "Delete Rule")) {
				SWRLRulesTableView.this.swrlRulesTableModel.removeSWRLRule(selectedRuleName);
			}
		}
	}
}
