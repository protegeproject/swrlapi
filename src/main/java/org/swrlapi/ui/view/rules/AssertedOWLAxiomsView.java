package org.swrlapi.ui.view.rules;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.AbstractTableModel;

import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.ui.view.SWRLAPIView;

public class AssertedOWLAxiomsView extends JPanel implements SWRLAPIView
{
	private static final long serialVersionUID = 1L;

	private final SWRLRuleEngine ruleEngine;
	private final AssertedAxiomsTableModel assertedAxiomsTableModel;
	private final JTable table;

	public AssertedOWLAxiomsView(SWRLRuleEngine ruleEngine)
	{
		this.ruleEngine = ruleEngine;
		this.assertedAxiomsTableModel = new AssertedAxiomsTableModel();
		this.table = new JTable(this.assertedAxiomsTableModel);

		setLayout(new BorderLayout());

		JScrollPane scrollPane = new JScrollPane(this.table);
		JViewport viewPort = scrollPane.getViewport();
		viewPort.setBackground(this.table.getBackground());

		add(BorderLayout.CENTER, scrollPane);
	}

	@Override
	public void validate()
	{
		this.assertedAxiomsTableModel.fireTableDataChanged();
		super.validate();
	}

	@Override
	public void update()
	{
		validate();
	}

	private class AssertedAxiomsTableModel extends AbstractTableModel
	{
		private static final long serialVersionUID = 1L;

		@Override
		public int getRowCount()
		{
			return AssertedOWLAxiomsView.this.ruleEngine.getNumberOfAssertedOWLAxioms();
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
				return AssertedOWLAxiomsView.this.ruleEngine.getAssertedOWLAxioms().toArray()[row];
		}
	}
}
