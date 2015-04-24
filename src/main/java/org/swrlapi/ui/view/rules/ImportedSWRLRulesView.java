package org.swrlapi.ui.view.rules;

import java.awt.BorderLayout;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.AbstractTableModel;

import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.core.SWRLRuleRenderer;
import org.swrlapi.ui.view.SWRLAPIView;

public class ImportedSWRLRulesView extends JPanel implements SWRLAPIView
{
	private static final long serialVersionUID = 1L;

	private final SWRLRuleEngine swrlRuleEngine;
	private final SWRLRuleRenderer swrlRulePrinter;
	private final SWRLRulesTableModel swrlRulesTableModel;

	public ImportedSWRLRulesView(SWRLRuleEngine ruleEngine)
	{
		this.swrlRuleEngine = ruleEngine;
		this.swrlRulesTableModel = new SWRLRulesTableModel();
		JTable swrlRulesTable = new JTable(this.swrlRulesTableModel);
		this.swrlRulePrinter = ruleEngine.createSWRLRuleRenderer();

		JScrollPane scrollPane = new JScrollPane(swrlRulesTable);
		JViewport viewPort = scrollPane.getViewport();
		setLayout(new BorderLayout());
		viewPort.setBackground(swrlRulesTable.getBackground());
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
		public Object getValueAt(int row, int column)
		{
			if (row < 0 || row >= getRowCount())
				return "OUT OF BOUNDS!";
			else {
				Set<SWRLAPIRule> swrlRules = ImportedSWRLRulesView.this.swrlRuleEngine.getSWRLRules();
				SWRLAPIRule[] swrlRuleArray = swrlRules.toArray(new SWRLAPIRule[swrlRules.size()]);
				SWRLAPIRule swrlRule = swrlRuleArray[row];
				return swrlRule.accept(swrlRulePrinter);
			}
		}
	}
}
