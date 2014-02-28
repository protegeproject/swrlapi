package org.protege.swrlapi.ui.panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.AbstractTableModel;

import org.protege.swrlapi.core.SWRLRuleEngine;

public class SWRLRulesPanel extends JPanel
{
	private static final long serialVersionUID = -4915968331047546704L;

	private final SWRLRuleEngine ruleEngine;
	private final RulesModel rulesModel;
	private final JTable table;

	public SWRLRulesPanel(SWRLRuleEngine ruleEngine)
	{
		JScrollPane scrollPane;
		JViewport viewPort;

		this.ruleEngine = ruleEngine;
		this.rulesModel = new RulesModel();
		this.table = new JTable(this.rulesModel);

		setLayout(new BorderLayout());

		scrollPane = new JScrollPane(this.table);
		viewPort = scrollPane.getViewport();
		viewPort.setBackground(this.table.getBackground());

		add(BorderLayout.CENTER, scrollPane);
	}

	@Override
	public void validate()
	{
		this.rulesModel.fireTableDataChanged();
		super.validate();
	}

	private class RulesModel extends AbstractTableModel
	{
		private static final long serialVersionUID = -951029122187501424L;

		@Override
		public int getRowCount()
		{
			return SWRLRulesPanel.this.ruleEngine.getNumberOfImportedSWRLRules();
		}

		@Override
		public int getColumnCount()
		{
			return 1;
		}

		@Override
		public String getColumnName(int column)
		{
			return "Imported Rules and Queries";
		}

		@Override
		public Object getValueAt(int row, int column)
		{
			Object result = null;

			if (row < 0 || row >= getRowCount())
				result = new String("OUT OF BOUNDS");
			else
				result = SWRLRulesPanel.this.ruleEngine.getSWRLRules().toArray()[row];

			return result;
		}
	}
}
