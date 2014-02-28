package org.protege.swrlapi.ui.panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.AbstractTableModel;

import org.protege.swrlapi.core.SWRLRuleEngine;

public class SWRLImportedAxiomsPanel extends JPanel
{
	private static final long serialVersionUID = 1984640370794297469L;

	private final SWRLRuleEngine ruleEngine;
	private final ImportedAxiomsModel importedAxiomsModel;
	private final JTable table;

	public SWRLImportedAxiomsPanel(SWRLRuleEngine ruleEngine)
	{
		JScrollPane scrollPane;
		JViewport viewPort;

		this.ruleEngine = ruleEngine;
		this.importedAxiomsModel = new ImportedAxiomsModel();
		this.table = new JTable(this.importedAxiomsModel);

		setLayout(new BorderLayout());
		scrollPane = new JScrollPane(this.table);
		viewPort = scrollPane.getViewport();
		viewPort.setBackground(this.table.getBackground());

		add(BorderLayout.CENTER, scrollPane);
	}

	@Override
	public void validate()
	{
		this.importedAxiomsModel.fireTableDataChanged();
		super.validate();
	}

	private class ImportedAxiomsModel extends AbstractTableModel
	{
		private static final long serialVersionUID = -2853546629765781069L;

		@Override
		public int getRowCount()
		{
			return SWRLImportedAxiomsPanel.this.ruleEngine.getNumberOfAssertedOWLAxioms();
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
				return SWRLImportedAxiomsPanel.this.ruleEngine.getAssertedOWLAxioms().toArray()[row];
		}
	}
}
