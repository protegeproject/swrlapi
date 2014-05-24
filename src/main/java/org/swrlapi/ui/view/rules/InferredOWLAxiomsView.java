package org.swrlapi.ui.view.rules;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.AbstractTableModel;

import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.ui.view.SWRLAPIView;

public class InferredOWLAxiomsView extends JPanel implements SWRLAPIView
{
	private static final long serialVersionUID = 1L;

	private final SWRLRuleEngine ruleEngine;
	private final InferredAxiomsTableModel inferredAxiomsModel;
	private final JTable table;

	public InferredOWLAxiomsView(SWRLRuleEngine ruleEngine)
	{
		this.ruleEngine = ruleEngine;
		this.inferredAxiomsModel = new InferredAxiomsTableModel();
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

	@Override
	public void update()
	{
		validate();
	}

	private class InferredAxiomsTableModel extends AbstractTableModel
	{
		private static final long serialVersionUID = 1L;

		@Override
		public int getRowCount()
		{
			return InferredOWLAxiomsView.this.ruleEngine.getNumberOfInferredOWLAxioms();
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
				return InferredOWLAxiomsView.this.ruleEngine.getInferredOWLAxioms().toArray()[row];
			}
		}
	}
}
