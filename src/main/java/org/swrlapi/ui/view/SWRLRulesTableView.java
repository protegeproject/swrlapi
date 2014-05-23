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

import org.swrlapi.ui.SWRLAPIApplicationController;
import org.swrlapi.ui.core.SQWRLApplicationView;
import org.swrlapi.ui.core.SWRLAPIView;
import org.swrlapi.ui.dialog.SWRLAPIApplicationDialogManager;
import org.swrlapi.ui.model.SWRLRuleModel;
import org.swrlapi.ui.model.SWRLRulesTableModel;

public class SWRLRulesTableView extends JPanel implements SWRLAPIView
{
	private static final long serialVersionUID = 1L;

	private final SWRLAPIApplicationController applicationController;
	private final JTable swrlRulesTable;

	public SWRLRulesTableView(SWRLAPIApplicationController applicationController)
	{
		this.applicationController = applicationController;
		this.swrlRulesTable = new JTable(getSWRLRulesTableModel());

		addTableListeners();
		setPreferredColumnWidths();

		getSWRLRulesTableModel().setView(this);

		createComponents();
	}

	private void addTableListeners()
	{
		swrlRulesTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() == 2) {
					if (e.getSource() == swrlRulesTable) {
						editSelectedClassMap();
					}
				}
			}
		});
	}

	private void setPreferredColumnWidths()
	{
		TableColumnModel columnModel = swrlRulesTable.getColumnModel();
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

	@Override
	public void update()
	{
		getSWRLRulesTableModel().fireTableDataChanged();
		validate();
	}

	public SWRLRuleModel getSelectedSWRLRule()
	{
		SWRLRuleModel selectedClassMap = null;
		int selectedRow = swrlRulesTable.getSelectedRow();

		if (selectedRow != -1)
			selectedClassMap = (SWRLRuleModel)getSWRLRulesTableModel().getSWRLRuleModels().toArray()[selectedRow];

		return selectedClassMap;
	}

	private void createComponents()
	{
		JPanel headingPanel, buttonPanel;
		JButton addButton, editButton, deleteButton;

		JScrollPane scrollPane = new JScrollPane(swrlRulesTable);
		JViewport viewport = scrollPane.getViewport();

		setLayout(new BorderLayout());

		headingPanel = new JPanel(new BorderLayout());
		add(headingPanel, BorderLayout.NORTH);

		viewport.setBackground(swrlRulesTable.getBackground());

		buttonPanel = new JPanel(new BorderLayout());
		headingPanel.add(buttonPanel, BorderLayout.EAST);

		addButton = new JButton("Add");
		addButton.addActionListener(new AddButtonActionListener());
		buttonPanel.add(addButton, BorderLayout.WEST);

		editButton = new JButton("Edit");
		editButton.addActionListener(new EditButtonActionListener());
		buttonPanel.add(editButton, BorderLayout.CENTER);

		deleteButton = new JButton("Delete");
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
			getDialogManager().getCreateSWRLRuleDialog().setVisible(true);
		}
	}

	private class EditButtonActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			editSelectedClassMap();
		}
	}

	private void editSelectedClassMap()
	{
		SWRLRuleModel swrlRuleModel = getSelectedSWRLRule();

		if (swrlRuleModel != null && getSWRLRulesTableModel().hasSWRLRule(swrlRuleModel.getRuleName())) {
			getDialogManager().getCreateSWRLRuleDialog(swrlRuleModel).setVisible(true);
		}
	}

	private class DeleteButtonActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			SWRLRuleModel swrlRuleModel = getSelectedSWRLRule();

			if (getSWRLRulesTableModel().hasSWRLRule(swrlRuleModel.getRuleName())
					&& getDialogManager().showConfirmDialog(getApplicationView(), "Delete rule",
							"Do you really want to delete the rule?")) {
				getSWRLRulesTableModel().removeSWRLRule(swrlRuleModel.getRuleName());
			}
		}
	}

	private SWRLRulesTableModel getSWRLRulesTableModel()
	{
		return applicationController.getApplicationModel().getSWRLRulesTableModel();
	}

	private SQWRLApplicationView getApplicationView()
	{
		return applicationController.getApplicationView();
	}

	private SWRLAPIApplicationDialogManager getDialogManager()
	{
		return getApplicationView().getApplicationDialogManager();
	}
}
