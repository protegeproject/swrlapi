package org.swrlapi.ui.panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.AbstractTableModel;

import org.swrlapi.core.SWRLRuleEngine;

public class SWRLTabRuleListPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	private final SWRLRuleEngine ruleEngine;
	private final SWRLRulesTableModel rulesTableModel;
	private final JTable rulesTable;

	public SWRLTabRuleListPanel(SWRLRuleEngine ruleEngine)
	{
		this.ruleEngine = ruleEngine;
		this.rulesTableModel = new SWRLRulesTableModel();
		this.rulesTable = new JTable(this.rulesTableModel);

		setLayout(new BorderLayout());

		JScrollPane scrollPane = new JScrollPane(this.rulesTable);
		JViewport viewPort = scrollPane.getViewport();
		viewPort.setBackground(this.rulesTable.getBackground());

		add(BorderLayout.CENTER, scrollPane);
	}

	@Override
	public void validate()
	{
		this.rulesTableModel.fireTableDataChanged();
		super.validate();
	}

	private class SWRLRulesTableModel extends AbstractTableModel
	{
		private static final long serialVersionUID = 1L;

		@Override
		public int getRowCount()
		{
			return SWRLTabRuleListPanel.this.ruleEngine.getNumberOfImportedSWRLRules();
		}

		@Override
		public int getColumnCount()
		{
			return 1;
		}

		@Override
		public String getColumnName(int column)
		{
			return "Rules and Queries";
		}

		@Override
		public Object getValueAt(int row, int column)
		{
			if (row < 0 || row >= getRowCount())
				return new String("OUT OF BOUNDS");
			else
				return SWRLTabRuleListPanel.this.ruleEngine.getSWRLRules().toArray()[row];
		}
	}
}
