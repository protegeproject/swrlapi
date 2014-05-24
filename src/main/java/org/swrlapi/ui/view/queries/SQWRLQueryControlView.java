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
import org.swrlapi.ui.view.SWRLAPIView;
import org.swrlapi.ui.view.SWRLRuleSelector;

public class SQWRLQueryControlView extends JPanel implements SWRLAPIView
{
	private static final long serialVersionUID = 1L;

	private final SQWRLQueryEngine queryEngine;

	private final int MaximumOpenResultPanels = 12;
	private final SWRLRuleSelector querySelector;
	private final JTextArea console;
	private final Icon queryEngineIcon;
	private final HashMap<String, SQWRLQueryResultView> resultPanels = new HashMap<String, SQWRLQueryResultView>();

	public SQWRLQueryControlView(SQWRLQueryEngine queryEngine, SWRLRuleSelector querySelector, Icon queryEngineIcon)
	{
		this.queryEngine = queryEngine;
		this.queryEngineIcon = queryEngineIcon;
		this.querySelector = querySelector;

		setLayout(new BorderLayout());

		this.console = createConsole();
		JScrollPane scrollPane = new JScrollPane(this.console);
		scrollPane.setPreferredSize(new Dimension(900, 300));

		add(BorderLayout.CENTER, scrollPane);

		JPanel panel = new JPanel(new FlowLayout());

		JButton button = createButton("Run", "Run all SWRL rules and SQWRL queries", new RunActionListener(this.console,
				this));
		panel.add(button);

		add(BorderLayout.SOUTH, panel);

		this.console.append("Using " + queryEngine.getTargetRuleEngineName() + ", "
				+ queryEngine.getTargetRuleEngineVersion() + " for query execution.\n\n");
		this.console.append("Executing queries in this tab does not modify the ontology.\n\n");
		this.console
				.append("The SWRLTab supports an OWL profile called OWL 2 RL and uses an OWL 2 RL-based reasoner to perform querying.\n");
		this.console.append("See the 'OWL 2 RL' subtab for more information on this reasoner.\n\n");
		this.console.append("Select a SQWRL query from the list above and press the 'Run' button.\n");
		this.console.append("If the selected query generates a result, the result will appear in a new sub tab.\n\n");
	}

	@Override
	public void update()
	{
		validate();
	}

	public void appendText(String text)
	{
		this.console.append(text);
	}

	public void removeResultPanel(String queryName)
	{
		if (this.resultPanels.containsKey(queryName)) {
			SQWRLQueryResultView resultPanel = this.resultPanels.get(queryName);
			this.resultPanels.remove(queryName);
			((JTabbedPane)getParent()).remove(resultPanel);
			((JTabbedPane)getParent()).setSelectedIndex(0);
		}
	}

	public void removeAllPanels()
	{
		for (SQWRLQueryResultView resultPanel : this.resultPanels.values())
			((JTabbedPane)getParent()).remove(resultPanel);
		this.resultPanels.clear();
	}

	private JButton createButton(String text, String toolTipText, ActionListener listener)
	{
		JButton button = new JButton(text);

		button.setToolTipText(toolTipText);
		button.setPreferredSize(new Dimension(160, 30));
		button.addActionListener(listener);

		return button;
	}

	private JTextArea createConsole()
	{
		JTextArea resultTextArea = new JTextArea(10, 80);
		resultTextArea.setLineWrap(true);
		resultTextArea.setBackground(Color.WHITE);
		resultTextArea.setEditable(false);
		return resultTextArea;
	}

	private class ListenerBase
	{
		protected final SQWRLQueryControlView controlPanel;
		protected final JTextArea console;

		public ListenerBase(JTextArea console, SQWRLQueryControlView controlPanel)
		{
			this.console = console;
			this.controlPanel = controlPanel;
		}
	}

	private class RunActionListener extends ListenerBase implements ActionListener
	{
		public RunActionListener(JTextArea console, SQWRLQueryControlView controlPanel)
		{
			super(console, controlPanel);
		}

		@Override
		public void actionPerformed(ActionEvent event)
		{
			String queryName = "";

			if (SQWRLQueryControlView.this.resultPanels.size() == SQWRLQueryControlView.this.MaximumOpenResultPanels) {
				this.console.append("A maximum of " + SQWRLQueryControlView.this.MaximumOpenResultPanels
						+ " result tabs may be open at once. ");
				this.console.append("Please close an existing tab to display results for the selected query.\n");
			} else {
				try {
					SWRLRuleSelector ruleSelector = SQWRLQueryControlView.this.querySelector;

					if (ruleSelector == null) {
						this.console.append("Configuration error: no query selector supplied. No queries can be executed.\n");
					} else {
						queryName = SQWRLQueryControlView.this.querySelector.getSelectedRuleName();

						if (queryName == null || queryName.length() == 0)
							this.console.append("No enabled SQWRL query selected.\n");
						else {
							long startTime = System.currentTimeMillis();
							SQWRLResult result = SQWRLQueryControlView.this.queryEngine.runSQWRLQuery(queryName);

							if (result == null || result.getNumberOfRows() == 0) {
								this.console.append("SQWRL query " + queryName + " did not generate any result.\n");
								if (SQWRLQueryControlView.this.resultPanels.containsKey(queryName)) {
									SQWRLQueryResultView resultPanel = SQWRLQueryControlView.this.resultPanels.get(queryName);
									SQWRLQueryControlView.this.resultPanels.remove(resultPanel);
									((JTabbedPane)getParent()).remove(resultPanel);
								}
							} else { // A result was returned
								SQWRLQueryResultView resultPanel;

								this.console.append("See the " + queryName + " tab to review results of the SQWRL query.\n");
								this.console.append("The query took " + (System.currentTimeMillis() - startTime) + " milliseconds. ");
								if (result.getNumberOfRows() == 1)
									this.console.append("1 row was returned.\n");
								else
									this.console.append("" + result.getNumberOfRows() + " rows were returned.\n");

								if (SQWRLQueryControlView.this.resultPanels.containsKey(queryName))
									resultPanel = SQWRLQueryControlView.this.resultPanels.get(queryName); // Existing tab found
								else { // Create new tab
									resultPanel = new SQWRLQueryResultView(SQWRLQueryControlView.this.queryEngine, queryName, result,
											this.controlPanel);
									SQWRLQueryControlView.this.resultPanels.put(queryName, resultPanel);
									((JTabbedPane)getParent()).addTab(queryName, SQWRLQueryControlView.this.queryEngineIcon, resultPanel,
											"Result Panel for query '" + queryName + "'");
								}
								resultPanel.validate();
								this.controlPanel.getParent().validate();
							}
						}
					}
				} catch (SQWRLInvalidQueryNameException e) {
					this.console.append(queryName + " is not a valid SQWRL query or is not enabled.\n");
				} catch (SQWRLException e) {
					if (queryName.length() == 0)
						this.console.append("Exception running SQWRL queries: " + e.getMessage() + "\n");
					else
						this.console.append("Exception when running SQWRL query " + queryName + ": " + e.getMessage() + "\n");
				}
			}
		}
	}
}
