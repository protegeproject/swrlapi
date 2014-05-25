package org.swrlapi.ui.view.rules;

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

import org.swrlapi.ui.controller.SWRLAPIApplicationController;
import org.swrlapi.ui.dialog.SWRLAPIApplicationDialogManager;
import org.swrlapi.ui.model.SWRLRulesTableModel;
import org.swrlapi.ui.view.SWRLAPIView;

public class ImportedSWRLRulesView extends JPanel implements SWRLAPIView
{
	private static final long serialVersionUID = 1L;

	private final SWRLAPIApplicationController applicationController;
	private final SWRLRulesTableModel swrlRulesTableModel;
	private final JTable swrlRulesTable;

	public ImportedSWRLRulesView(SWRLAPIApplicationController applicationController,
			SWRLRulesTableModel swrlRulesTableModel)
	{
		this.applicationController = applicationController;
		this.swrlRulesTableModel = swrlRulesTableModel;
		this.swrlRulesTable = new JTable(this.swrlRulesTableModel);

		addTableListeners();
		setPreferredColumnWidths();
		swrlRulesTableModel.setView(this);
		createComponents();
	}

	@Override
	public void update()
	{
		this.swrlRulesTableModel.fireTableDataChanged();
		validate();
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

	private void setPreferredColumnWidths()
	{
		TableColumnModel columnModel = this.swrlRulesTable.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(30);
		columnModel.getColumn(0).setMaxWidth(50);
		columnModel.getColumn(1).setPreferredWidth(150);
		columnModel.getColumn(2).setPreferredWidth(300);
		columnModel.getColumn(3).setPreferredWidth(100);
		columnModel.getColumn(3).setMaxWidth(200);
		columnModel.getColumn(4).setPreferredWidth(100);
		columnModel.getColumn(4).setMaxWidth(150);
		columnModel.getColumn(5).setPreferredWidth(100);
		columnModel.getColumn(5).setMaxWidth(150);
		columnModel.getColumn(6).setMaxWidth(100);
		columnModel.getColumn(7).setMaxWidth(100);
	}

	public String getSelectedSWRLRuleName()
	{
		int selectedRow = this.swrlRulesTable.getSelectedRow();

		if (selectedRow != -1)
			return this.swrlRulesTableModel.getSWRLRuleNameByIndex(selectedRow);
		else
			return "";
	}

	private void createComponents()
	{
		JScrollPane scrollPane = new JScrollPane(swrlRulesTable);
		JViewport viewport = scrollPane.getViewport();

		setLayout(new BorderLayout());

		JPanel headingPanel = new JPanel(new BorderLayout());
		add(headingPanel, BorderLayout.NORTH);

		viewport.setBackground(swrlRulesTable.getBackground());

		JPanel buttonPanel = new JPanel(new BorderLayout());
		headingPanel.add(buttonPanel, BorderLayout.EAST);

		JButton addButton = new JButton("Add");
		addButton.addActionListener(new AddButtonActionListener());
		buttonPanel.add(addButton, BorderLayout.WEST);

		JButton editButton = new JButton("Edit");
		editButton.addActionListener(new EditButtonActionListener());
		buttonPanel.add(editButton, BorderLayout.CENTER);

		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new DeleteButtonActionListener());
		buttonPanel.add(deleteButton, BorderLayout.EAST);

		add(scrollPane, BorderLayout.CENTER);

		validate();
	}

	private class AddButtonActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			getApplicationDialogManager().getCreateSWRLRuleDialog().setVisible(true);
		}
	}

	private class EditButtonActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			editSelectedSWRLRule();
		}
	}

	private void editSelectedSWRLRule()
	{
		String selectedRuleName = getSelectedSWRLRuleName();

		if (this.swrlRulesTableModel.hasSWRLRule(selectedRuleName)) {
			getApplicationDialogManager().getCreateSWRLRuleDialog(selectedRuleName, "TODO", "TODO").setVisible(true);
		}
	}

	private class DeleteButtonActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			String selectedRuleName = getSelectedSWRLRuleName();

			if (ImportedSWRLRulesView.this.swrlRulesTableModel.hasSWRLRule(selectedRuleName)
					&& getApplicationDialogManager().showConfirmDialog("Delete rule", "Do you really want to delete the rule?")) {
				ImportedSWRLRulesView.this.swrlRulesTableModel.removeSWRLRule(selectedRuleName);
			}
		}
	}

	private SWRLAPIApplicationDialogManager getApplicationDialogManager()
	{
		return this.applicationController.getApplicationDialogManager();
	}
}
