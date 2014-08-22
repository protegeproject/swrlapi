package org.swrlapi.ui.view.queries;

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
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.swrlapi.sqwrl.values.SQWRLNamedResultValue;
import org.swrlapi.sqwrl.values.SQWRLResultValue;
import org.swrlapi.ui.view.SWRLAPIView;

/**
 * A view holding the result for a single SQWRL query.
 *
 * @see org.swrlapi.sqwrl.SQWRLQueryEngine
 * @see org.swrlapi.sqwrl.SQWRLResult
 * @see org.swrlapi.ui.view.queries.SQWRLQueryControlView
 */
public class SQWRLResultView extends JPanel implements SWRLAPIView
{
	private static final long serialVersionUID = 1L;

	private final String queryName;
	private final SQWRLQueryEngine sqwrlQueryEngine;
	private final SQWRLQueryControlView sqwrlQueryControlView;
	private final SQWRLQueryResultTableModel sqwrlQueryResultTableModel;
	private SQWRLResult sqwrlResult;

	private static File currentDirectory = null;

	public SQWRLResultView(SQWRLQueryEngine sqwrlQueryEngine, String queryName, SQWRLResult sqwrlResult,
			SQWRLQueryControlView sqwrlQueryControlView)
	{
		this.sqwrlQueryEngine = sqwrlQueryEngine;
		this.queryName = queryName;
		this.sqwrlResult = sqwrlResult;
		this.sqwrlQueryControlView = sqwrlQueryControlView;
		this.sqwrlQueryResultTableModel = new SQWRLQueryResultTableModel();

		setLayout(new BorderLayout());
		JTable sqwrlQueryResultTable = new JTable(this.sqwrlQueryResultTableModel);

		JPanel buttonsPanel = new JPanel(new FlowLayout());
		JButton saveSQWRLResultButton = createButton("Save as CSV...", "Save the result as a CSV file...",
				new SaveSQWRLResultActionListener());
		buttonsPanel.add(saveSQWRLResultButton);
		JButton runSQWRLQueryButton = createButton("Rerun", "Rerun this SQWRL query", new RunSQWRLQueryActionListener());
		buttonsPanel.add(runSQWRLQueryButton);
		JButton closeSQWRLResultButton = createButton("Close", "Close the tab for this query",
				new CloseSQWRLResultActionListener());
		buttonsPanel.add(closeSQWRLResultButton);

		JScrollPane scrollPane = new JScrollPane(sqwrlQueryResultTable);
		JViewport viewPort = scrollPane.getViewport();
		viewPort.setBackground(sqwrlQueryResultTable.getBackground());

		add(BorderLayout.CENTER, scrollPane);
		add(BorderLayout.SOUTH, buttonsPanel);
	}

	@Override
	public void validate()
	{
		this.sqwrlQueryResultTableModel.fireTableStructureChanged();
		super.validate();
	}

	@Override
	public void update()
	{
		validate();
	}

	private class RunSQWRLQueryActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			SQWRLResultView.this.sqwrlResult = null;

			try {
				SQWRLResultView.this.sqwrlResult = SQWRLResultView.this.sqwrlQueryEngine
						.runSQWRLQuery(SQWRLResultView.this.queryName);

				if (SQWRLResultView.this.sqwrlResult == null || SQWRLResultView.this.sqwrlResult.getNumberOfRows() == 0) {
					SQWRLResultView.this.sqwrlQueryControlView.appendToConsole("No result returned for SQWRL query '"
							+ SQWRLResultView.this.queryName + "' - closing tab.\n");
					SQWRLResultView.this.sqwrlQueryControlView.removeSQWRLResultView(SQWRLResultView.this.queryName);
				} else
					validate();
			} catch (SQWRLInvalidQueryNameException e) {
				SQWRLResultView.this.sqwrlQueryControlView.appendToConsole("Invalid query name "
						+ SQWRLResultView.this.queryName + ".\n");
			} catch (SQWRLException e) {
				SQWRLResultView.this.sqwrlQueryControlView.appendToConsole("Exception running SQWRL query '"
						+ SQWRLResultView.this.queryName + "': " + e.getMessage() + "\n");
			}

			/*
			 * if (sqwrlResult == null) { controlPanel.removeAllPanels();
			 * controlPanel.appendText("Closing all result tabs.\n"); }
			 */
		}
	}

	private class CloseSQWRLResultActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			SQWRLResultView.this.sqwrlQueryControlView.removeSQWRLResultView(SQWRLResultView.this.queryName);
			SQWRLResultView.this.sqwrlQueryControlView.appendToConsole("'" + SQWRLResultView.this.queryName
					+ "' tab closed.\n");
		}
	}

	private class SaveSQWRLResultActionListener implements ActionListener
	{
		private final JFileChooser chooser;

		public SaveSQWRLResultActionListener()
		{
			this.chooser = new JFileChooser();
			this.chooser.setCurrentDirectory(currentDirectory);
		}

		@Override
		public void actionPerformed(ActionEvent event)
		{
			saveSQWRLResultAsCSV();
		}

		private void saveSQWRLResultAsCSV()
		{
			try {
				int returnValue = this.chooser.showOpenDialog(SQWRLResultView.this.sqwrlQueryControlView);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = this.chooser.getSelectedFile();
					currentDirectory = this.chooser.getCurrentDirectory();
					FileWriter writer = new FileWriter(selectedFile);
					SQWRLResultView.this.sqwrlResult = SQWRLResultView.this.sqwrlQueryEngine
							.getSQWRLResult(SQWRLResultView.this.queryName);

					if (SQWRLResultView.this.sqwrlResult != null) {
						int numberOfColumns = SQWRLResultView.this.sqwrlResult.getNumberOfColumns();
						for (int i = 0; i < numberOfColumns; i++) {
							if (i != 0)
								writer.write(", ");
							writer.write(SQWRLResultView.this.sqwrlResult.getColumnName(i));
						}
						writer.write("\n");

						while (SQWRLResultView.this.sqwrlResult.hasNext()) {
							for (int i = 0; i < numberOfColumns; i++) {
								SQWRLResultValue value = SQWRLResultView.this.sqwrlResult.getValue(i);
								if (i != 0)
									writer.write(", ");
								if (value instanceof SQWRLLiteralResultValue && ((SQWRLLiteralResultValue)value).isQuotableType())
									writer.write("\"" + value + "\"");
								else
									writer.write("" + value);
							}
							writer.write("\n");
							SQWRLResultView.this.sqwrlResult.next();
						}
						SQWRLResultView.this.sqwrlResult.reset();
						writer.close();
						SQWRLResultView.this.sqwrlQueryControlView.appendToConsole("Sucessfully saved results of query "
								+ SQWRLResultView.this.queryName + " to CSV file " + selectedFile.getPath() + ".\n");
					}
				}
			} catch (Throwable e) {
				JOptionPane.showMessageDialog(null, "Error saving file: " + e.getMessage(), "Error saving file",
						JOptionPane.ERROR_MESSAGE);
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

	private class SQWRLQueryResultTableModel extends AbstractTableModel
	{
		private static final long serialVersionUID = 1L;

		@Override
		public int getRowCount()
		{
			try {
				return (SQWRLResultView.this.sqwrlResult == null) ? 0 : SQWRLResultView.this.sqwrlResult.getNumberOfRows();
			} catch (SQWRLException e) {
				return 0;
			}
		}

		@Override
		public int getColumnCount()
		{
			try {
				return (SQWRLResultView.this.sqwrlResult == null) ? 0 : SQWRLResultView.this.sqwrlResult.getNumberOfColumns();
			} catch (SQWRLException e) {
				return 0;
			}
		}

		@Override
		public String getColumnName(int columnIndex)
		{
			try {
				return (SQWRLResultView.this.sqwrlResult == null) ? "" : SQWRLResultView.this.sqwrlResult
						.getColumnName(columnIndex);
			} catch (SQWRLException e) {
				return "INVALID";
			}
		}

		@Override
		public Object getValueAt(int row, int column)
		{
			try {
				SQWRLResultValue sqwrlResultValue = (SQWRLResultView.this.sqwrlResult == null) ? null
						: SQWRLResultView.this.sqwrlResult.getValue(column, row);
				if (sqwrlResultValue.isNamed()) {
					SQWRLNamedResultValue sqwrlNamedResultValue = sqwrlResultValue.asNamedResult();
					return sqwrlNamedResultValue.getPrefixedName();
				} else if (sqwrlResultValue.isLiteral()) {
					SQWRLLiteralResultValue sqwrLiteralResultValue = sqwrlResultValue.asLiteralResult();
					return sqwrLiteralResultValue.getLiteral();
				} else
					return "INVALID";
			} catch (SQWRLException e) {
				return "INVALID";
			}
		}
	}
}
