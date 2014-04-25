package org.swrlapi.ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.exceptions.SWRLRuleEngineException;

public class SWRLTabRulesControlPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	private final SWRLRuleEngine ruleEngine;
	private final String ruleEngineName;
	private final String ruleEngineVersion;

	public SWRLTabRulesControlPanel(SWRLRuleEngine ruleEngine)
	{
		this.ruleEngine = ruleEngine;
		this.ruleEngineName = ruleEngine.getTargetRuleEngineName();
		this.ruleEngineVersion = ruleEngine.getTargetRuleEngineVersion();

		setLayout(new BorderLayout());

		JTextArea console = createConsole();
		JScrollPane scrollPane = new JScrollPane(console);
		scrollPane.setPreferredSize(new Dimension(900, 300));

		add(BorderLayout.CENTER, scrollPane);

		JPanel buttonsPanel = new JPanel(new FlowLayout());

		JButton button = createButton("OWL+SWRL->" + ruleEngineName,
				"Translate SWRL rules and relevant OWL knowledge to rule engine", new ImportActionListener(ruleEngine, console,
						this));
		buttonsPanel.add(button);

		button = createButton("Run " + ruleEngineName, "Run the rule engine", new RunActionListener(ruleEngine, console,
				this));

		buttonsPanel.add(button);

		button = createButton(ruleEngineName + "->OWL", "Translate asserted rule engine knowledge to OWL knowledge",
				new ExportActionListener(this.ruleEngine, console, this));
		buttonsPanel.add(button);

		add(BorderLayout.SOUTH, buttonsPanel);

		console.append("Using the " + ruleEngineName + " rule engine, " + ruleEngineVersion + ".\n\n");
		console.append("Press the 'OWL+SWRL->" + ruleEngineName
				+ "' button to transfer SWRL rules and relevant OWL knowledge to the rule engine.\n");
		console.append("Press the 'Run " + ruleEngineName + "' button to run the rule engine.\n");
		console.append("Press the '" + ruleEngineName
				+ "->OWL' button to transfer the inferred rule engine knowledge to OWL knowledge.\n\n");
		console
				.append("The SWRLTab supports an OWL profile called OWL 2 RL and uses an OWL 2 RL-based reasoner to perform reasoning.\n");
		console.append("See the 'OWL 2 RL' subtab for more information on this reasoner.");
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
		JTextArea textArea = new JTextArea(10, 80);

		textArea.setLineWrap(true);
		textArea.setBackground(Color.WHITE);
		textArea.setEditable(false);

		return textArea;
	}

	private class ListenerBase
	{
		protected final SWRLRuleEngine ruleEngine;
		protected final JTextArea console;
		protected final SWRLTabRulesControlPanel controlPanel;

		public ListenerBase(SWRLRuleEngine ruleEngine, JTextArea console, SWRLTabRulesControlPanel controlPanel)
		{
			this.ruleEngine = ruleEngine;
			this.console = console;
			this.controlPanel = controlPanel;
		}
	}

	private class ImportActionListener extends ListenerBase implements ActionListener
	{
		public ImportActionListener(SWRLRuleEngine ruleEngine, JTextArea console, SWRLTabRulesControlPanel controlPanel)
		{
			super(ruleEngine, console, controlPanel);
		}

		@Override
		public void actionPerformed(ActionEvent event)
		{
			try {
				long startTime = System.currentTimeMillis();
				this.ruleEngine.importSWRLRulesAndOWLKnowledge();

				this.console.setText("");
				this.console.append("OWL axioms successfully transferred to rule engine.\n");
				this.console.append("Number of SWRL rules exported to rule engine: "
						+ this.ruleEngine.getNumberOfImportedSWRLRules() + "\n");
				this.console.append("Number of OWL class declarations exported to rule engine: "
						+ this.ruleEngine.getNumberOfAssertedOWLClassDeclarationAxioms() + "\n");
				this.console.append("Number of OWL individual declarations exported to rule engine: "
						+ this.ruleEngine.getNumberOfAssertedOWLIndividualDeclarationsAxioms() + "\n");
				this.console.append("Number of OWL object property declarations exported to rule engine: "
						+ this.ruleEngine.getNumberOfAssertedOWLObjectPropertyDeclarationAxioms() + "\n");
				this.console.append("Number of OWL data property declarations exported to rule engine: "
						+ this.ruleEngine.getNumberOfAssertedOWLDataPropertyDeclarationAxioms() + "\n");
				this.console.append("Total number of OWL axioms exported to rule engine: "
						+ this.ruleEngine.getNumberOfAssertedOWLAxioms() + "\n");
				this.console.append("The transfer took " + (System.currentTimeMillis() - startTime) + " millisecond(s).\n");
				this.console.append("Press the 'Run " + SWRLTabRulesControlPanel.this.ruleEngineName
						+ "' button to run the rule engine.\n");
			} catch (SWRLRuleEngineException e) {
				this.console.append("Exception importing SWRL rules and OWL knowledge: " + e.toString() + "\n");
			}
			this.controlPanel.getParent().validate();
		}
	}

	private class RunActionListener extends ListenerBase implements ActionListener
	{
		public RunActionListener(SWRLRuleEngine ruleEngine, JTextArea textArea, SWRLTabRulesControlPanel controlPanel)
		{
			super(ruleEngine, textArea, controlPanel);
		}

		@Override
		public void actionPerformed(ActionEvent event)
		{
			try {
				long startTime = System.currentTimeMillis();
				this.ruleEngine.run();

				this.console.append("Successful execution of rule engine.\n");
				this.console.append("Number of inferred axioms: " + this.ruleEngine.getNumberOfInferredOWLAxioms() + "\n");
				if (this.ruleEngine.getNumberOfInjectedOWLAxioms() != 0)
					this.console.append("Number of axioms injected by built-ins: "
							+ this.ruleEngine.getNumberOfInjectedOWLAxioms() + "\n");
				this.console.append("The process took " + (System.currentTimeMillis() - startTime) + " millisecond(s).\n");
				this.console.append("Look at the 'Inferred Axioms' tab to see the inferred axioms.\n");
				this.console.append("Press the '" + SWRLTabRulesControlPanel.this.ruleEngineName
						+ "->OWL' button to translate the inferred axioms to OWL knowledge.\n");
			} catch (SWRLRuleEngineException e) {
				this.console.append("Exception running rule engine: " + e.getMessage() + "\n");
			}
			this.controlPanel.getParent().validate();
		}
	}

	private class ExportActionListener extends ListenerBase implements ActionListener
	{
		public ExportActionListener(SWRLRuleEngine ruleEngine, JTextArea textArea, SWRLTabRulesControlPanel controlPanel)
		{
			super(ruleEngine, textArea, controlPanel);
		}

		@Override
		public void actionPerformed(ActionEvent event)
		{
			try {
				long startTime = System.currentTimeMillis();
				this.ruleEngine.writeInferredKnowledge();

				this.console.append("Successfully transferred inferred axioms to OWL model.\n");
				this.console.append("The process took " + (System.currentTimeMillis() - startTime) + " millisecond(s).\n");
			} catch (SWRLRuleEngineException e) {
				this.console.append("Exception exporting knowledge to OWL: " + e.toString() + "\n");
			}
			this.controlPanel.getParent().validate();
		}
	}
}
