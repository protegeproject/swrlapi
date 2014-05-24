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
 * A panel holding the result for a single SQWRL query
 */
public class SQWRLQueryResultView extends JPanel implements SWRLAPIView
{
	private static final long serialVersionUID = 1L;

	private final String queryName;
	private final JTable table;
	private final SQWRLQueryEngine queryEngine;
	private final SQWRLQueryControlView sqwrlControlPanel;
	private final SQWRLQueryResultTableModel sqwrlQueryResultTableModel;
	private SQWRLResult sqwrlResult;

	private static File currentDirectory = null;

	public SQWRLQueryResultView(SQWRLQueryEngine sqwrlQueryEngine, String queryName, SQWRLResult sqwrlResult,
			SQWRLQueryControlView controlPanel)
	{
		this.queryEngine = sqwrlQueryEngine;
		this.queryName = queryName;
		this.sqwrlResult = sqwrlResult;
		this.sqwrlControlPanel = controlPanel;
		this.sqwrlQueryResultTableModel = new SQWRLQueryResultTableModel();
		this.table = new JTable(this.sqwrlQueryResultTableModel);

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
		this.sqwrlQueryResultTableModel.fireTableStructureChanged();
		super.validate();
	}

	@Override
	public void update()
	{
		validate();
	}

	private class RunQueriesActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			SQWRLQueryResultView.this.sqwrlResult = null;

			try {
				SQWRLQueryResultView.this.sqwrlResult = SQWRLQueryResultView.this.queryEngine
						.runSQWRLQuery(SQWRLQueryResultView.this.queryName);

				if (SQWRLQueryResultView.this.sqwrlResult == null
						|| SQWRLQueryResultView.this.sqwrlResult.getNumberOfRows() == 0) {
					SQWRLQueryResultView.this.sqwrlControlPanel.appendText("No result returned for SQWRL query '"
							+ SQWRLQueryResultView.this.queryName + "' - closing tab.\n");
					SQWRLQueryResultView.this.sqwrlControlPanel.removeResultPanel(SQWRLQueryResultView.this.queryName);
				} else
					validate();
			} catch (SQWRLInvalidQueryNameException e) {
				SQWRLQueryResultView.this.sqwrlControlPanel.appendText("Invalid query name "
						+ SQWRLQueryResultView.this.queryName + ".\n");
			} catch (SQWRLException e) {
				SQWRLQueryResultView.this.sqwrlControlPanel.appendText("Exception running SQWRL query '"
						+ SQWRLQueryResultView.this.queryName + "': " + e.getMessage() + "\n");
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
			SQWRLQueryResultView.this.sqwrlControlPanel.removeResultPanel(SQWRLQueryResultView.this.queryName);
			SQWRLQueryResultView.this.sqwrlControlPanel.appendText("'" + SQWRLQueryResultView.this.queryName
					+ "' tab closed.\n");
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
			int returnValue = this.chooser.showOpenDialog(SQWRLQueryResultView.this.sqwrlControlPanel);
			FileWriter writer;

			try {
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = this.chooser.getSelectedFile();
					currentDirectory = this.chooser.getCurrentDirectory();
					writer = new FileWriter(selectedFile);
					SQWRLQueryResultView.this.sqwrlResult = SQWRLQueryResultView.this.queryEngine
							.getSQWRLResult(SQWRLQueryResultView.this.queryName);

					if (SQWRLQueryResultView.this.sqwrlResult != null) {
						int numberOfColumns = SQWRLQueryResultView.this.sqwrlResult.getNumberOfColumns();

						for (int i = 0; i < numberOfColumns; i++) {
							if (i != 0)
								writer.write(", ");
							writer.write(SQWRLQueryResultView.this.sqwrlResult.getColumnName(i));
						}
						writer.write("\n");

						while (SQWRLQueryResultView.this.sqwrlResult.hasNext()) {
							for (int i = 0; i < numberOfColumns; i++) {
								SQWRLResultValue value = SQWRLQueryResultView.this.sqwrlResult.getValue(i);
								if (i != 0)
									writer.write(", ");
								if (value instanceof SQWRLLiteralResultValue && ((SQWRLLiteralResultValue)value).isQuotableType())
									writer.write("\"" + value + "\"");
								else
									writer.write("" + value);
							}
							writer.write("\n");
							SQWRLQueryResultView.this.sqwrlResult.next();
						}
						SQWRLQueryResultView.this.sqwrlResult.reset();
						writer.close();
						SQWRLQueryResultView.this.sqwrlControlPanel.appendText("Sucessfully saved results of query "
								+ SQWRLQueryResultView.this.queryName + " to CSV file " + selectedFile.getPath() + ".\n");
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

	private class SQWRLQueryResultTableModel extends AbstractTableModel
	{
		private static final long serialVersionUID = 1L;

		@Override
		public int getRowCount()
		{
			try {
				return (SQWRLQueryResultView.this.sqwrlResult == null) ? 0 : SQWRLQueryResultView.this.sqwrlResult
						.getNumberOfRows();
			} catch (SQWRLException e) {
				return 0;
			}
		}

		@Override
		public int getColumnCount()
		{
			try {
				return (SQWRLQueryResultView.this.sqwrlResult == null) ? 0 : SQWRLQueryResultView.this.sqwrlResult
						.getNumberOfColumns();
			} catch (SQWRLException e) {
				return 0;
			}
		}

		@Override
		public String getColumnName(int columnIndex)
		{
			try {
				return (SQWRLQueryResultView.this.sqwrlResult == null) ? "" : SQWRLQueryResultView.this.sqwrlResult
						.getColumnName(columnIndex);
			} catch (SQWRLException e) {
				return "INVALID";
			}
		}

		@Override
		public Object getValueAt(int row, int column)
		{
			try {
				SQWRLResultValue value = (SQWRLQueryResultView.this.sqwrlResult == null) ? null
						: SQWRLQueryResultView.this.sqwrlResult.getValue(column, row);
				if (value.isNamed()) {
					SQWRLNamedResultValue namedValue = value.asNamedResult();
					return namedValue.getPrefixedName();
				} else if (value.isLiteral()) {
					SQWRLLiteralResultValue literalValue = value.asLiteralResult();
					return literalValue.getLiteral();
				} else
					return "INVALID";
			} catch (SQWRLException e) {
				return "INVALID";
			}
		}
	}
}
