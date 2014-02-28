package org.protege.swrlapi.ui.panels;

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

import org.protege.swrlapi.sqwrl.SQWRLQueryEngine;
import org.protege.swrlapi.sqwrl.SQWRLResult;
import org.protege.swrlapi.sqwrl.exceptions.SQWRLInvalidQueryNameException;
import org.protege.swrlapi.sqwrl.exceptions.SQWRLException;

public class SQWRLQueryControlPanel extends JPanel
{
	private static final long serialVersionUID = -7466410717705438076L;

	private final SQWRLQueryEngine queryEngine;
	private final JTextArea textArea;
	private final int MaximumOpenResultPanels = 12;
	private final Icon queryEngineIcon;
	private final SWRLRuleSelector ruleSelector;
	private HashMap<String, SQWRLQueryResultPanel> resultPanels;

	public SQWRLQueryControlPanel(SQWRLQueryEngine queryEngine, Icon queryEngineIcon, SWRLRuleSelector ruleSelector)
	{
		JPanel panel;
		JButton button;
		JScrollPane scrollPane;

		this.queryEngine = queryEngine;
		this.queryEngineIcon = queryEngineIcon;
		this.ruleSelector = ruleSelector;

		this.resultPanels = new HashMap<String, SQWRLQueryResultPanel>();

		setLayout(new BorderLayout());

		this.textArea = createTextArea();
		scrollPane = new JScrollPane(this.textArea);
		scrollPane.setPreferredSize(new Dimension(900, 300));

		add(BorderLayout.CENTER, scrollPane);

		panel = new JPanel(new FlowLayout());

		button = createButton("Run", "Run all SWRL rules and SQWRL queries", new RunActionListener(this.textArea, this));
		panel.add(button);

		add(BorderLayout.SOUTH, panel);

		this.textArea.append("Using " + queryEngine.getTargetRuleEngineName() + ", "
				+ queryEngine.getTargetRuleEngineVersion() + " for query execution.\n\n");
		this.textArea.append("The protege.properties file can be used to chose an alternative rule engine.\n");
		this.textArea.append("\nSee http://protege.cim3.net/cgi-bin/wiki.pl?SQWRLQueryTab for documentation.\n\n");
		this.textArea.append("Executing queries in this tab does not modify the ontology.\n\n");
		this.textArea
				.append("The SWRLTab supports an OWL profile called OWL 2 RL and uses an OWL 2 RL-based reasoner to perform querying.\n");
		this.textArea.append("See the 'OWL 2 RL' subtab for more information on this reasoner.\n\n");
		this.textArea.append("Select a SQWRL query from the list above and press the 'Run' button.\n");
		this.textArea.append("If the selected query generates a result, the result will appear in a new sub tab.\n\n");
	}

	public void appendText(String text)
	{
		this.textArea.append(text);
	}

	public void removeResultPanel(String queryName)
	{
		if (this.resultPanels.containsKey(queryName)) {
			SQWRLQueryResultPanel resultPanel = this.resultPanels.get(queryName);
			this.resultPanels.remove(queryName);
			((JTabbedPane)getParent()).remove(resultPanel);
			((JTabbedPane)getParent()).setSelectedIndex(0);
		}
	}

	public void removeAllPanels()
	{
		for (SQWRLQueryResultPanel resultPanel : this.resultPanels.values())
			((JTabbedPane)getParent()).remove(resultPanel);
		this.resultPanels = new HashMap<String, SQWRLQueryResultPanel>();
	}

	private JButton createButton(String text, String toolTipText, ActionListener listener)
	{
		JButton button = new JButton(text);

		button.setToolTipText(toolTipText);
		button.setPreferredSize(new Dimension(160, 30));
		button.addActionListener(listener);

		return button;
	}

	private JTextArea createTextArea()
	{
		JTextArea resultTextArea = new JTextArea(10, 80);
		resultTextArea.setLineWrap(true);
		resultTextArea.setBackground(Color.WHITE);
		resultTextArea.setEditable(false);
		return resultTextArea;
	}

	private class ListenerBase
	{
		protected JTextArea listenerTextArea;
		protected SQWRLQueryControlPanel controlPanel;

		public ListenerBase(JTextArea textArea, SQWRLQueryControlPanel controlPanel)
		{
			this.listenerTextArea = textArea;
			this.controlPanel = controlPanel;
		}
	}

	private class RunActionListener extends ListenerBase implements ActionListener
	{
		public RunActionListener(JTextArea textArea, SQWRLQueryControlPanel controlPanel)
		{
			super(textArea, controlPanel);
		}

		@Override
		public void actionPerformed(ActionEvent event)
		{
			SQWRLQueryResultPanel resultPanel;
			String queryName = "";
			SQWRLResult result = null;

			if (SQWRLQueryControlPanel.this.resultPanels.size() == SQWRLQueryControlPanel.this.MaximumOpenResultPanels) {
				this.listenerTextArea.append("A maximum of " + SQWRLQueryControlPanel.this.MaximumOpenResultPanels
						+ " result tabs may be open at once. ");
				this.listenerTextArea.append("Please close an existing tab to display results for the selected rule.\n");
			} else {
				try {
					queryName = SQWRLQueryControlPanel.this.ruleSelector.getSelectedRuleName();

					if (queryName == null || queryName.length() == 0)
						this.listenerTextArea.append("No enabled SQWRL query selected.\n");
					else {
						long startTime = System.currentTimeMillis();
						result = SQWRLQueryControlPanel.this.queryEngine.runSQWRLQuery(queryName);

						if (result == null || result.getNumberOfRows() == 0) {
							this.listenerTextArea.append("SQWRL query " + queryName + " did not generate any result.\n");
							if (SQWRLQueryControlPanel.this.resultPanels.containsKey(queryName)) {
								resultPanel = SQWRLQueryControlPanel.this.resultPanels.get(queryName);
								SQWRLQueryControlPanel.this.resultPanels.remove(resultPanel);
								((JTabbedPane)getParent()).remove(resultPanel);
							}
						} else { // A result was returned
							this.listenerTextArea.append("See the " + queryName + " tab to review results of the SQWRL query.\n");
							this.listenerTextArea.append("The query took " + (System.currentTimeMillis() - startTime)
									+ " milliseconds. ");
							if (result.getNumberOfRows() == 1)
								this.listenerTextArea.append("1 row was returned.\n");
							else
								this.listenerTextArea.append("" + result.getNumberOfRows() + " rows were returned.\n");

							if (SQWRLQueryControlPanel.this.resultPanels.containsKey(queryName))
								resultPanel = SQWRLQueryControlPanel.this.resultPanels.get(queryName); // Existing tab found
							else { // Create new tab
								resultPanel = new SQWRLQueryResultPanel(SQWRLQueryControlPanel.this.queryEngine, queryName, result,
										this.controlPanel);
								SQWRLQueryControlPanel.this.resultPanels.put(queryName, resultPanel);
								((JTabbedPane)getParent()).addTab(queryName, SQWRLQueryControlPanel.this.queryEngineIcon, resultPanel,
										"Result Panel for query '" + queryName + "'");
							}
							resultPanel.validate();
							this.controlPanel.getParent().validate();
						}
					}
				} catch (SQWRLInvalidQueryNameException e) {
					this.listenerTextArea.append(queryName + " is not a valid SQWRL query or is not enabled.\n");
				} catch (SQWRLException e) {
					if (queryName.length() == 0)
						this.listenerTextArea.append("Exception running SQWRL queries: " + e.getMessage() + "\n");
					else
						this.listenerTextArea.append("Exception when running SQWRL query " + queryName + ": " + e.getMessage()
								+ "\n");
				}
			}
		}
	}
}
