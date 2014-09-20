package org.swrlapi.ui.view.rules;

import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.core.impl.DefaultSWRLAPIRuleRenderer;
import org.swrlapi.ui.view.SWRLAPIView;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.Set;

public class ImportedSWRLRulesView extends JPanel implements SWRLAPIView
{
	private static final long serialVersionUID = 1L;

	private final SWRLRuleEngine swrlRuleEngine;
	private final DefaultSWRLAPIRuleRenderer swrlRulePrinter;
	private final SWRLRulesTableModel swrlRulesTableModel;

	public ImportedSWRLRulesView(SWRLRuleEngine ruleEngine, SWRLAPIOWLOntology swrlapiowlOntology)
	{
		this.swrlRuleEngine = ruleEngine;
		this.swrlRulesTableModel = new SWRLRulesTableModel();
		JTable swrlRulesTable = new JTable(this.swrlRulesTableModel);
		this.swrlRulePrinter = new DefaultSWRLAPIRuleRenderer(swrlapiowlOntology);

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
