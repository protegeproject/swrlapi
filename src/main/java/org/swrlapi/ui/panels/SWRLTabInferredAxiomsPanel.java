package org.swrlapi.ui.panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.AbstractTableModel;

import org.swrlapi.core.SWRLRuleEngine;

public class SWRLTabInferredAxiomsPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	private final SWRLRuleEngine ruleEngine;
	private final SWRLAPIInferredAxiomsModel inferredAxiomsModel;
	private final JTable table;

	public SWRLTabInferredAxiomsPanel(SWRLRuleEngine ruleEngine)
	{
		this.ruleEngine = ruleEngine;
		this.inferredAxiomsModel = new SWRLAPIInferredAxiomsModel();
		this.table = new JTable(this.inferredAxiomsModel);

		setLayout(new BorderLayout());

		JScrollPane scrollPane = new JScrollPane(this.table);
		JViewport viewPort = scrollPane.getViewport();
		viewPort.setBackground(this.table.getBackground());

		add(BorderLayout.CENTER, scrollPane);
	}

	@Override
	public void validate()
	{
		this.inferredAxiomsModel.fireTableDataChanged();
		super.validate();
	}

	private class SWRLAPIInferredAxiomsModel extends AbstractTableModel
	{
		private static final long serialVersionUID = 1L;

		@Override
		public int getRowCount()
		{
			return SWRLTabInferredAxiomsPanel.this.ruleEngine.getNumberOfInferredOWLAxioms();
		}

		@Override
		public int getColumnCount()
		{
			return 1;
		}

		@Override
		public String getColumnName(int column)
		{
			return "Inferred Axioms";
		}

		@Override
		public Object getValueAt(int row, int column)
		{
			if (row < 0 || row >= getRowCount())
				return new String("OUT OF BOUNDS");
			else {
				return SWRLTabInferredAxiomsPanel.this.ruleEngine.getInferredOWLAxioms().toArray()[row];
			}
		}
	}
}
