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

import org.swrlapi.ui.ApplicationController;
import org.swrlapi.ui.core.ApplicationView;
import org.swrlapi.ui.core.SWRLRuleModel;
import org.swrlapi.ui.core.View;
import org.swrlapi.ui.dialog.ApplicationDialogManager;
import org.swrlapi.ui.model.SWRLRulesModel;

public class SWRLRulesView extends JPanel implements View
{
	private static final long serialVersionUID = 1L;

	private final ApplicationController applicationController;
	private final JTable swrlRulesTable;

	public SWRLRulesView(ApplicationController applicationController)
	{
		this.applicationController = applicationController;

		swrlRulesTable = new JTable(getMappingExpressionsModel());
		addTableListeners();
		setPreferredColumnWidths();

		getMappingExpressionsModel().setView(this);

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
		getMappingExpressionsModel().fireTableDataChanged();
		validate();
	}

	/**
	 * Returns the selected class map if one is selected; null is returned otherwise.
	 */
	public SWRLRuleModel getSelectedClassMap()
	{
		SWRLRuleModel selectedClassMap = null;
		int selectedRow = swrlRulesTable.getSelectedRow();

		if (selectedRow != -1)
			selectedClassMap = (SWRLRuleModel)getMappingExpressionsModel().getSWRLRuleModels().toArray()[selectedRow];

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
			getApplicationDialogManager().getCreateMappingExpressionDialog().setVisible(true);
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
		SWRLRuleModel selectedClassMap = getSelectedClassMap();

		if (selectedClassMap != null && getMappingExpressionsModel().hasSWRLRuleModel(selectedClassMap)) {
			getApplicationDialogManager().getCreateMappingExpressionDialog(selectedClassMap).setVisible(true);
		} // if
	}

	private class DeleteButtonActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			SWRLRuleModel selectedClassMap = getSelectedClassMap();

			if (getMappingExpressionsModel().hasSWRLRuleModel(selectedClassMap)
					&& getApplicationDialogManager().showConfirmDialog(getApplicationView(), "Delete Expression",
							"Do you really want to delete the expression?")) {
				getMappingExpressionsModel().removeSWRLRuleModel(selectedClassMap);
			} // if
		}
	}

	private SWRLRulesModel getMappingExpressionsModel()
	{
		return applicationController.getApplicationModel().getSWRLRulesModel();
	}

	private ApplicationView getApplicationView()
	{
		return applicationController.getApplicationViewController();
	}

	private ApplicationDialogManager getApplicationDialogManager()
	{
		return getApplicationView().getApplicationDialogManager();
	}
}
