package org.swrlapi.ui.panels;

import java.awt.BorderLayout;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.AbstractTableModel;

import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.ext.SWRLAPIRule;
import org.swrlapi.ext.impl.SWRLAPIRulePrinter;

public class SWRLTabRuleListPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	private final SWRLRuleEngine ruleEngine;
	private final SWRLAPIRulePrinter rulePrinter;
	private final SWRLRulesTableModel rulesTableModel;
	private final JTable rulesTable;

	public SWRLTabRuleListPanel(SWRLRuleEngine ruleEngine, DefaultPrefixManager prefixManager)
	{
		this.ruleEngine = ruleEngine;
		this.rulesTableModel = new SWRLRulesTableModel();
		this.rulesTable = new JTable(this.rulesTableModel);
		this.rulePrinter = new SWRLAPIRulePrinter(prefixManager);

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
				return new String("OUT OF BOUNDS!");
			else {
				Set<SWRLAPIRule> rules = SWRLTabRuleListPanel.this.ruleEngine.getSWRLRules();
				SWRLAPIRule[] arr = rules.toArray(new SWRLAPIRule[rules.size()]);
				SWRLAPIRule rule = arr[row];
				return rule.accept(rulePrinter);
			}
		}
	}
}
