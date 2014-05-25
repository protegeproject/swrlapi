package org.swrlapi.ui.view.rules;

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
import org.swrlapi.ui.view.SWRLAPIView;

public class ImportedSWRLRulesView extends JPanel implements SWRLAPIView
{
	private static final long serialVersionUID = 1L;

	private final SWRLRuleEngine swrlRuleEngine;
	private final SWRLAPIRulePrinter swrlRulePrinter;
	private final SWRLRulesTableModel swrlRulesTableModel;
	private final JTable swrlRulesTable;

	public ImportedSWRLRulesView(SWRLRuleEngine ruleEngine, DefaultPrefixManager prefixManager)
	{
		this.swrlRuleEngine = ruleEngine;
		this.swrlRulesTableModel = new SWRLRulesTableModel();
		this.swrlRulesTable = new JTable(this.swrlRulesTableModel);
		this.swrlRulePrinter = new SWRLAPIRulePrinter(prefixManager);

		JScrollPane scrollPane = new JScrollPane(this.swrlRulesTable);
		JViewport viewPort = scrollPane.getViewport();
		setLayout(new BorderLayout());
		viewPort.setBackground(this.swrlRulesTable.getBackground());
		add(BorderLayout.CENTER, scrollPane);
	}

	@Override
	public void validate()
	{
		this.swrlRulesTableModel.fireTableDataChanged();
		super.validate();
	}

	@Override
	public void update()
	{
		validate();
	}

	private class SWRLRulesTableModel extends AbstractTableModel
	{
		private static final long serialVersionUID = 1L;

		@Override
		public int getRowCount()
		{
			return ImportedSWRLRulesView.this.swrlRuleEngine.getNumberOfImportedSWRLRules();
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
				Set<SWRLAPIRule> swrlRules = ImportedSWRLRulesView.this.swrlRuleEngine.getSWRLRules();
				SWRLAPIRule[] swrlRuleArray = swrlRules.toArray(new SWRLAPIRule[swrlRules.size()]);
				SWRLAPIRule swrlRule = swrlRuleArray[row];
				return swrlRule.accept(swrlRulePrinter);
			}
		}
	}
}
