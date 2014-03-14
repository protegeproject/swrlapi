package org.swrlapi.ui.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.AbstractTableModel;

import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.exceptions.SQWRLInvalidQueryNameException;
import org.swrlapi.sqwrl.values.SQWRLClassValue;
import org.swrlapi.sqwrl.values.SQWRLIndividualValue;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.swrlapi.sqwrl.values.SQWRLPropertyValue;
import org.swrlapi.sqwrl.values.SQWRLResultValue;

public class SQWRLQueryResultPanel extends JPanel
{
	private static final long serialVersionUID = -7760249381644759870L;

	private final String queryName;
	private final JTable table;
	private final SQWRLQueryEngine queryEngine;
	private final SQWRLQueryControlPanel controlPanel;
	private final SQWRLQueryResultModel swrlQueryResultModel;
	private SQWRLResult result;

	private static File currentDirectory = null;

	public SQWRLQueryResultPanel(SQWRLQueryEngine queryEngine, String queryName, SQWRLResult result,
			SQWRLQueryControlPanel controlPanel)
	{
		this.queryEngine = queryEngine;
		this.queryName = queryName;
		this.result = result;
		this.controlPanel = controlPanel;

		this.swrlQueryResultModel = new SQWRLQueryResultModel();
		this.table = new JTable(this.swrlQueryResultModel);

		setLayout(new BorderLayout());

		JPanel buttonsPanel = new JPanel(new FlowLayout());

		JButton saveResultButton = createButton("Save as CSV...", "Save the result as a CSV file...",
				new SaveResultActionListener());
		buttonsPanel.add(saveResultButton);

		JButton runQueriesButton = createButton("Rerun", "Rerun this SQWRL query", new RunQueriesActionListener());
		buttonsPanel.add(runQueriesButton);

		JButton closeTabButton = createButton("Close", "Close the tab for this query", new CloseTabActionListener());
		buttonsPanel.add(closeTabButton);

		JScrollPane scrollPane = new JScrollPane(this.table);
		JViewport viewPort = scrollPane.getViewport();
		viewPort.setBackground(this.table.getBackground());

		add(BorderLayout.CENTER, scrollPane);
		add(BorderLayout.SOUTH, buttonsPanel);
	}

	@Override
	public void validate()
	{
		this.swrlQueryResultModel.fireTableStructureChanged();
		super.validate();
	}

	private class RunQueriesActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			SQWRLQueryResultPanel.this.result = null;

			try {
				SQWRLQueryResultPanel.this.result = SQWRLQueryResultPanel.this.queryEngine
						.runSQWRLQuery(SQWRLQueryResultPanel.this.queryName);

				if (SQWRLQueryResultPanel.this.result == null || SQWRLQueryResultPanel.this.result.getNumberOfRows() == 0) {
					SQWRLQueryResultPanel.this.controlPanel.appendText("No result returned for SQWRL query '"
							+ SQWRLQueryResultPanel.this.queryName + "' - closing tab.\n");
					SQWRLQueryResultPanel.this.controlPanel.removeResultPanel(SQWRLQueryResultPanel.this.queryName);
				} else
					validate();
			} catch (SQWRLInvalidQueryNameException e) {
				SQWRLQueryResultPanel.this.controlPanel.appendText("Invalid query name " + SQWRLQueryResultPanel.this.queryName
						+ ".\n");
			} catch (SQWRLException e) {
				SQWRLQueryResultPanel.this.controlPanel.appendText("Exception running SQWRL query '"
						+ SQWRLQueryResultPanel.this.queryName + "': " + e.getMessage() + "\n");
			}

			/*
			 * if (result == null) { controlPanel.removeAllPanels(); controlPanel.appendText("Closing all result tabs.\n"); }
			 */
		}
	}

	private class CloseTabActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			SQWRLQueryResultPanel.this.controlPanel.removeResultPanel(SQWRLQueryResultPanel.this.queryName);
			SQWRLQueryResultPanel.this.controlPanel
					.appendText("'" + SQWRLQueryResultPanel.this.queryName + "' tab closed.\n");
		}
	}

	private class SaveResultActionListener implements ActionListener
	{
		private final JFileChooser chooser;

		public SaveResultActionListener()
		{
			this.chooser = new JFileChooser();
			this.chooser.setCurrentDirectory(currentDirectory);
		}

		@Override
		public void actionPerformed(ActionEvent event)
		{
			saveResults();
		}

		private void saveResults()
		{
			int returnValue = this.chooser.showOpenDialog(SQWRLQueryResultPanel.this.controlPanel);
			FileWriter writer;

			try {
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = this.chooser.getSelectedFile();
					currentDirectory = this.chooser.getCurrentDirectory();
					writer = new FileWriter(selectedFile);
					SQWRLQueryResultPanel.this.result = SQWRLQueryResultPanel.this.queryEngine
							.getSQWRLResult(SQWRLQueryResultPanel.this.queryName);

					if (SQWRLQueryResultPanel.this.result != null) {
						int numberOfColumns = SQWRLQueryResultPanel.this.result.getNumberOfColumns();

						for (int i = 0; i < numberOfColumns; i++) {
							if (i != 0)
								writer.write(", ");
							writer.write(SQWRLQueryResultPanel.this.result.getColumnName(i));
						}
						writer.write("\n");

						while (SQWRLQueryResultPanel.this.result.hasNext()) {
							for (int i = 0; i < numberOfColumns; i++) {
								SQWRLResultValue value = SQWRLQueryResultPanel.this.result.getValue(i);
								if (i != 0)
									writer.write(", ");
								if (value instanceof SQWRLLiteralResultValue && ((SQWRLLiteralResultValue)value).isQuotableType())
									writer.write("\"" + value + "\"");
								else
									writer.write("" + value);
							}
							writer.write("\n");
							SQWRLQueryResultPanel.this.result.next();
						}
						SQWRLQueryResultPanel.this.result.reset();
						writer.close();
						SQWRLQueryResultPanel.this.controlPanel.appendText("Sucessfully saved results of query "
								+ SQWRLQueryResultPanel.this.queryName + " to CSV file " + selectedFile.getPath() + ".\n");
					}
				}
			} catch (Throwable e) {
				JOptionPane.showMessageDialog(null, "Error saving file: " + e.getMessage(), "Error saving file",
						JOptionPane.ERROR_MESSAGE);
				// TODO: findbugs - stream not closed on all paths
			}
		}
	}

	private JButton createButton(String text, String toolTipText, ActionListener listener)
	{
		JButton button = new JButton(text);

		button.setToolTipText(toolTipText);
		button.setPreferredSize(new Dimension(160, 30));
		button.addActionListener(listener);

		return button;
	}

	private class SQWRLQueryResultModel extends AbstractTableModel
	{
		private static final long serialVersionUID = -3862264549852664485L;

		@Override
		public int getRowCount()
		{
			int count;

			try {
				count = (SQWRLQueryResultPanel.this.result == null) ? 0 : SQWRLQueryResultPanel.this.result.getNumberOfRows();
			} catch (SQWRLException e) {
				count = 0;
			}

			return count;
		}

		@Override
		public int getColumnCount()
		{
			int count;

			try {
				count = (SQWRLQueryResultPanel.this.result == null) ? 0 : SQWRLQueryResultPanel.this.result
						.getNumberOfColumns();
			} catch (SQWRLException e) {
				count = 0;
			}

			return count;
		}

		@Override
		public String getColumnName(int columnIndex)
		{
			String columnName;

			try {
				columnName = (SQWRLQueryResultPanel.this.result == null) ? "" : SQWRLQueryResultPanel.this.result
						.getColumnName(columnIndex);
			} catch (SQWRLException e) {
				columnName = "INVALID";
			}

			return columnName;
		}

		@Override
		public Object getValueAt(int row, int column)
		{
			String representation;

			try {
				SQWRLResultValue value = (SQWRLQueryResultPanel.this.result == null) ? null : SQWRLQueryResultPanel.this.result
						.getValue(column, row);
				if (value instanceof SQWRLIndividualValue) {
					SQWRLIndividualValue objectValue = (SQWRLIndividualValue)value;
					representation = objectValue.getPrefixedName();
				} else if (value instanceof SQWRLResultValue) {
					SQWRLResultValue datatypeValue = value;
					representation = datatypeValue.toString();
				} else if (value instanceof SQWRLClassValue) {
					SQWRLClassValue classValue = (SQWRLClassValue)value;
					representation = classValue.getPrefixedName();
				} else if (value instanceof SQWRLPropertyValue) {
					SQWRLPropertyValue propertyValue = (SQWRLPropertyValue)value;
					representation = propertyValue.getPrefixedName();
				} else
					representation = "INVALID";
			} catch (SQWRLException e) {
				representation = "INVALID";
			}
			return representation;
		}
	}
}
