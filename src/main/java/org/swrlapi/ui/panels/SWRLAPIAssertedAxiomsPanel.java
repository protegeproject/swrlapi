package org.swrlapi.ui.panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.AbstractTableModel;

import org.swrlapi.core.SWRLRuleEngine;

public class SWRLAPIAssertedAxiomsPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	private final SWRLRuleEngine ruleEngine;
	private final SWRLAPIAssertedAxiomsTableModel assertedAxiomsModel;
	private final JTable table;

	public SWRLAPIAssertedAxiomsPanel(SWRLRuleEngine ruleEngine)
	{
		this.ruleEngine = ruleEngine;
		this.assertedAxiomsModel = new SWRLAPIAssertedAxiomsTableModel();
		this.table = new JTable(this.assertedAxiomsModel);

		setLayout(new BorderLayout());

		JScrollPane scrollPane = new JScrollPane(this.table);
		JViewport viewPort = scrollPane.getViewport();
		viewPort.setBackground(this.table.getBackground());

		add(BorderLayout.CENTER, scrollPane);
	}

	@Override
	public void validate()
	{
		this.assertedAxiomsModel.fireTableDataChanged();
		super.validate();
	}

	private class SWRLAPIAssertedAxiomsTableModel extends AbstractTableModel
	{
		private static final long serialVersionUID = 1L;

		@Override
		public int getRowCount()
		{
			return SWRLAPIAssertedAxiomsPanel.this.ruleEngine.getNumberOfAssertedOWLAxioms();
		}

		@Override
		public int getColumnCount()
		{
			return 1;
		}

		@Override
		public String getColumnName(int column)
		{
			return "Asserted Axioms";
		}

		@Override
		public Object getValueAt(int row, int column)
		{
			if (row < 0 || row >= getRowCount())
				return new String("OUT OF BOUNDS");
			else
				return SWRLAPIAssertedAxiomsPanel.this.ruleEngine.getAssertedOWLAxioms().toArray()[row];
		}
	}
}
