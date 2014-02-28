package org.protege.swrlapi.ui.panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.AbstractTableModel;

import org.protege.swrlapi.core.SWRLRuleEngine;

public class SWRLInferredAxiomsPanel extends JPanel
{
	private static final long serialVersionUID = 719501547316287846L;

	private final SWRLRuleEngine ruleEngine;
	private final InferredAxiomsModel inferredAxiomsModel;
	private final JTable table;

	public SWRLInferredAxiomsPanel(SWRLRuleEngine ruleEngine)
	{
		JScrollPane scrollPane;
		JViewport viewPort;

		this.ruleEngine = ruleEngine;
		this.inferredAxiomsModel = new InferredAxiomsModel();
		this.table = new JTable(this.inferredAxiomsModel);

		setLayout(new BorderLayout());

		scrollPane = new JScrollPane(this.table);
		viewPort = scrollPane.getViewport();
		viewPort.setBackground(this.table.getBackground());

		add(BorderLayout.CENTER, scrollPane);
	}

	@Override
	public void validate()
	{
		this.inferredAxiomsModel.fireTableDataChanged();
		super.validate();
	}

	private class InferredAxiomsModel extends AbstractTableModel
	{
		private static final long serialVersionUID = -4899659692392140788L;

		@Override
		public int getRowCount()
		{
			return SWRLInferredAxiomsPanel.this.ruleEngine.getNumberOfInferredOWLAxioms();
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
				return SWRLInferredAxiomsPanel.this.ruleEngine.getInferredOWLAxioms().toArray()[row];
			}
		}
	}
}
