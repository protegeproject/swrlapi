package org.swrlapi.ui.view.queries;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.exceptions.SQWRLInvalidQueryNameException;
import org.swrlapi.ui.view.SQWRLQuerySelector;
import org.swrlapi.ui.view.SWRLAPIView;

public class SQWRLQueryControlView extends JPanel implements SWRLAPIView
{
	private static final long serialVersionUID = 1L;

	private static final int VIEW_PREFERRED_WIDTH = 900;
	private static final int VIEW_PREFERRED_HEIGHT = 300;
	private static final int TOOLTIP_PREFERRED_WIDTH = 160;
	private static final int TOOLTIP_PREFERRED_HEIGHT = 30;
	private static final int CONSOLE_ROWS = 10;
	private static final int CONSOLE_COLUMNS = 80;
	private static final int MAXIMUM_OPEN_RESULT_VIEWS = 12;

	private final SQWRLQueryEngine sqwrlQueryEngine;
	private final SQWRLQuerySelector sqwrlQuerySelector;
	private final JTextArea console;
	private final Icon queryEngineIcon;
	private final HashMap<String, SQWRLResultView> sqwrlResultViews = new HashMap<String, SQWRLResultView>();

	public SQWRLQueryControlView(SQWRLQueryEngine sqwrlQueryEngine, SQWRLQuerySelector sqwrlQuerySelector,
			Icon queryEngineIcon)
	{
		this.sqwrlQueryEngine = sqwrlQueryEngine;
		this.queryEngineIcon = queryEngineIcon;
		this.sqwrlQuerySelector = sqwrlQuerySelector;

		setLayout(new BorderLayout());
		this.console = createConsole();
		JScrollPane scrollPane = new JScrollPane(this.console);
		scrollPane.setPreferredSize(new Dimension(VIEW_PREFERRED_WIDTH, VIEW_PREFERRED_HEIGHT));
		add(BorderLayout.CENTER, scrollPane);

		JPanel panel = new JPanel(new FlowLayout());
		JButton runButton = createButton("Run", "Run all SWRL rules and SQWRL queries", new RunActionListener(this.console,
				this));
		panel.add(runButton);
		add(BorderLayout.SOUTH, panel);

		appendToConsole("Select a SQWRL query from the list above and press the 'Run' button.\n");
		appendToConsole("If the selected query generates a result, the result will appear in a new sub tab.\n\n");
		appendToConsole("The SWRLAPI supports an OWL profile called OWL 2 RL and uses an OWL 2 RL-based reasoner to perform querying.\n");
		appendToConsole("See the 'OWL 2 RL' subtab for more information on this reasoner.\n\n");
		appendToConsole("Executing queries in this tab does not modify the ontology.\n\n");
		appendToConsole("Using " + sqwrlQueryEngine.getTargetRuleEngineName() + ", "
				+ sqwrlQueryEngine.getTargetRuleEngineVersion() + " for query execution.\n\n");
	}

	@Override
	public void update()
	{
		validate();
	}

	public void appendToConsole(String text)
	{
		this.console.append(text);
	}

	public void removeSQWRLResultView(String queryName)
	{
		if (this.sqwrlResultViews.containsKey(queryName)) {
			SQWRLResultView sqwrlResultView = this.sqwrlResultViews.get(queryName);
			this.sqwrlResultViews.remove(queryName);
			((JTabbedPane)getParent()).remove(sqwrlResultView);
			((JTabbedPane)getParent()).setSelectedIndex(0);
		}
	}

	public void removeAllSQWRLResultViews()
	{
		for (SQWRLResultView sqwrlResultView : this.sqwrlResultViews.values())
			((JTabbedPane)getParent()).remove(sqwrlResultView);
		this.sqwrlResultViews.clear();
	}

	private JButton createButton(String text, String toolTipText, ActionListener listener)
	{
		JButton button = new JButton(text);

		button.setToolTipText(toolTipText);
		button.setPreferredSize(new Dimension(TOOLTIP_PREFERRED_WIDTH, TOOLTIP_PREFERRED_HEIGHT));
		button.addActionListener(listener);

		return button;
	}

	private JTextArea createConsole()
	{
		JTextArea console = new JTextArea(CONSOLE_ROWS, CONSOLE_COLUMNS);
		console.setLineWrap(true);
		console.setBackground(Color.WHITE);
		console.setEditable(false);
		return console;
	}

	private class ListenerBase
	{
		protected final SQWRLQueryControlView sqwrlQueryControlView;
		protected final JTextArea console;

		public ListenerBase(JTextArea console, SQWRLQueryControlView sqwrlQueryControlView)
		{
			this.console = console;
			this.sqwrlQueryControlView = sqwrlQueryControlView;
		}
	}

	private class RunActionListener extends ListenerBase implements ActionListener
	{
		public RunActionListener(JTextArea console, SQWRLQueryControlView sqwrlQueryControlView)
		{
			super(console, sqwrlQueryControlView);
		}

		@Override
		public void actionPerformed(ActionEvent event)
		{
			runSQWRLQuery();
		}

		private void runSQWRLQuery()
		{
			String queryName = "";

			if (SQWRLQueryControlView.this.sqwrlResultViews.size() == SQWRLQueryControlView.MAXIMUM_OPEN_RESULT_VIEWS) {
				appendToConsole("A maximum of " + SQWRLQueryControlView.MAXIMUM_OPEN_RESULT_VIEWS
						+ " result tabs may be open at once. ");
				appendToConsole("Please close an existing tab to display results for the selected query.\n");
			} else {
				try {
					SQWRLQuerySelector querySelector = SQWRLQueryControlView.this.sqwrlQuerySelector;

					if (querySelector == null) {
						appendToConsole("Configuration error: no query selector supplied. No queries can be executed!\n");
					} else {
						queryName = SQWRLQueryControlView.this.sqwrlQuerySelector.getSelectedQueryName();

						if (queryName == null || queryName.length() == 0)
							appendToConsole("No enabled SQWRL query selected.\n");
						else {
							long startTime = System.currentTimeMillis();
							SQWRLResult sqwrlResult = SQWRLQueryControlView.this.sqwrlQueryEngine.runSQWRLQuery(queryName);

							if (sqwrlResult == null || sqwrlResult.getNumberOfRows() == 0)
								indicateEmptySQWRLResult(queryName);
							else
								displaySQWRLResult(queryName, sqwrlResult, startTime);
						}
					}
				} catch (SQWRLInvalidQueryNameException e) {
					appendToConsole(queryName + " is not a valid SQWRL query or is not enabled.\n");
				} catch (SQWRLException e) {
					if (queryName.length() == 0)
						appendToConsole("Exception running SQWRL queries: " + e.getMessage() + "\n");
					else
						appendToConsole("Exception when running SQWRL query " + queryName + ": " + e.getMessage() + "\n");
				}
			}
		}

		private void indicateEmptySQWRLResult(String queryName)
		{
			appendToConsole("SQWRL query " + queryName + " did not generate any result.\n");

			if (SQWRLQueryControlView.this.sqwrlResultViews.containsKey(queryName)) {
				SQWRLResultView queryResultsView = SQWRLQueryControlView.this.sqwrlResultViews.get(queryName);
				SQWRLQueryControlView.this.sqwrlResultViews.remove(queryResultsView);
				((JTabbedPane)getParent()).remove(queryResultsView);
			}
		}

		private void displaySQWRLResult(String queryName, SQWRLResult sqwrlResult, long startTime) throws SQWRLException
		{
			appendToConsole("See the " + queryName + " tab to review results of the SQWRL query.\n");
			appendToConsole("The query took " + (System.currentTimeMillis() - startTime) + " milliseconds. ");
			if (sqwrlResult.getNumberOfRows() == 1)
				appendToConsole("1 row was returned.\n");
			else
				appendToConsole("" + sqwrlResult.getNumberOfRows() + " rows were returned.\n");

			SQWRLResultView sqwrlQueryResultView;
			if (SQWRLQueryControlView.this.sqwrlResultViews.containsKey(queryName)) // Existing result tab found
				sqwrlQueryResultView = SQWRLQueryControlView.this.sqwrlResultViews.get(queryName);
			else { // Create new result tab
				sqwrlQueryResultView = new SQWRLResultView(SQWRLQueryControlView.this.sqwrlQueryEngine, queryName, sqwrlResult,
						this.sqwrlQueryControlView);
				SQWRLQueryControlView.this.sqwrlResultViews.put(queryName, sqwrlQueryResultView);
				((JTabbedPane)getParent()).addTab(queryName, SQWRLQueryControlView.this.queryEngineIcon, sqwrlQueryResultView,
						"SQWRL Result for query '" + queryName + "'");
			}
			sqwrlQueryResultView.validate();
			this.sqwrlQueryControlView.getParent().validate();
		}

		private void appendToConsole(String text)
		{
			this.console.append(text);
		}
	}
}
