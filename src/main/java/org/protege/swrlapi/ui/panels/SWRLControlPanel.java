package org.protege.swrlapi.ui.panels;

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

import org.protege.swrlapi.core.SWRLRuleEngine;
import org.protege.swrlapi.exceptions.SWRLRuleEngineException;

public class SWRLControlPanel extends JPanel
{
	private static final long serialVersionUID = -5576805226747255245L;

	private final String ruleEngineName;

	public SWRLControlPanel(SWRLRuleEngine ruleEngine, String ruleEngineName)
	{
		JTextArea textArea;
		JButton button;
		JScrollPane scrollPane;
		JPanel buttonsPanel;

		this.ruleEngineName = ruleEngineName;

		setLayout(new BorderLayout());

		textArea = createTextArea();
		scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(900, 300));

		add(BorderLayout.CENTER, scrollPane);

		buttonsPanel = new JPanel(new FlowLayout());

		button = createButton("OWL+SWRL->" + ruleEngineName,
				"Translate SWRL rules and relevant OWL knowledge to rule engine", new ImportActionListener(ruleEngine,
						textArea, this));
		buttonsPanel.add(button);

		button = createButton("Run " + ruleEngineName, "Run the rule engine", new RunActionListener(ruleEngine, textArea,
				this));

		buttonsPanel.add(button);

		button = createButton(ruleEngineName + "->OWL", "Translate asserted rule engine knowledge to OWL knowledge",
				new ExportActionListener(ruleEngine, textArea, this));
		buttonsPanel.add(button);

		add(BorderLayout.SOUTH, buttonsPanel);

		textArea.append("Using the " + ruleEngineName + " rule engine, " + ruleEngine.getTargetRuleEngineVersion()
				+ ".\n\n");
		textArea.append("Press the 'OWL+SWRL->" + ruleEngineName
				+ "' button to transfer SWRL rules and relevant OWL knowledge to the rule engine.\n");
		textArea.append("Press the 'Run " + ruleEngineName + "' button to run the rule engine.\n");
		textArea.append("Press the '" + ruleEngineName
				+ "->OWL' button to transfer the inferred rule engine knowledge to OWL knowledge.\n\n");
		textArea
				.append("The SWRLTab supports an OWL profile called OWL 2 RL and uses an OWL 2 RL-based reasoner to perform reasoning.\n");
		textArea.append("See the 'OWL 2 RL' subtab for more information on this reasoner.");
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
		JTextArea textArea = new JTextArea(10, 80);
		textArea.setLineWrap(true);
		textArea.setBackground(Color.WHITE);
		textArea.setEditable(false);
		return textArea;
	}

	private class ListenerBase
	{
		protected SWRLRuleEngine ruleEngine;
		protected JTextArea textArea;
		protected SWRLControlPanel controlPanel;

		public ListenerBase(SWRLRuleEngine ruleEngine, JTextArea textArea, SWRLControlPanel controlPanel)
		{
			this.ruleEngine = ruleEngine;
			this.textArea = textArea;
			this.controlPanel = controlPanel;
		}
	}

	private class ImportActionListener extends ListenerBase implements ActionListener
	{
		public ImportActionListener(SWRLRuleEngine ruleEngine, JTextArea textArea, SWRLControlPanel controlPanel)
		{
			super(ruleEngine, textArea, controlPanel);
		}

		@Override
		public void actionPerformed(ActionEvent event)
		{
			try {
				long startTime = System.currentTimeMillis();
				this.ruleEngine.importSWRLRulesAndOWLKnowledge();

				this.textArea.setText("");
				this.textArea.append("OWL axioms successfully transferred to rule engine.\n");
				this.textArea.append("Number of SWRL rules exported to rule engine: "
						+ this.ruleEngine.getNumberOfImportedSWRLRules() + "\n");
				this.textArea.append("Number of OWL class declarations exported to rule engine: "
						+ this.ruleEngine.getNumberOfAssertedOWLClassDeclarationAxioms() + "\n");
				this.textArea.append("Number of OWL individual declarations exported to rule engine: "
						+ this.ruleEngine.getNumberOfAssertedOWLIndividualDeclarationsAxioms() + "\n");
				this.textArea.append("Number of OWL object property declarations exported to rule engine: "
						+ this.ruleEngine.getNumberOfAssertedOWLObjectPropertyDeclarationAxioms() + "\n");
				this.textArea.append("Number of OWL data property declarations exported to rule engine: "
						+ this.ruleEngine.getNumberOfAssertedOWLDataPropertyDeclarationAxioms() + "\n");
				this.textArea.append("Total number of OWL axioms exported to rule engine: "
						+ this.ruleEngine.getNumberOfAssertedOWLAxioms() + "\n");
				this.textArea.append("The transfer took " + (System.currentTimeMillis() - startTime) + " millisecond(s).\n");
				this.textArea.append("Press the 'Run " + SWRLControlPanel.this.ruleEngineName
						+ "' button to run the rule engine.\n");
			} catch (SWRLRuleEngineException e) {
				this.textArea.append("Exception importing SWRL rules and OWL knowledge: " + e.toString() + "\n");
			}
			this.controlPanel.getParent().validate();
		}
	}

	private class RunActionListener extends ListenerBase implements ActionListener
	{
		public RunActionListener(SWRLRuleEngine ruleEngine, JTextArea textArea, SWRLControlPanel controlPanel)
		{
			super(ruleEngine, textArea, controlPanel);
		}

		@Override
		public void actionPerformed(ActionEvent event)
		{
			try {
				long startTime = System.currentTimeMillis();
				this.ruleEngine.run();

				this.textArea.append("Successful execution of rule engine.\n");
				this.textArea.append("Number of inferred axioms: " + this.ruleEngine.getNumberOfInferredOWLAxioms() + "\n");
				if (this.ruleEngine.getNumberOfInjectedOWLAxioms() != 0)
					this.textArea.append("Number of axioms injected by built-ins: "
							+ this.ruleEngine.getNumberOfInjectedOWLAxioms() + "\n");
				this.textArea.append("The process took " + (System.currentTimeMillis() - startTime) + " millisecond(s).\n");
				this.textArea.append("Look at the 'Inferred Axioms' tab to see the inferred axioms.\n");
				this.textArea.append("Press the '" + SWRLControlPanel.this.ruleEngineName
						+ "->OWL' button to translate the inferred axioms to OWL knowledge.\n");
			} catch (SWRLRuleEngineException e) {
				this.textArea.append("Exception running rule engine: " + e.getMessage() + "\n");
			}
			this.controlPanel.getParent().validate();
		}
	}

	private class ExportActionListener extends ListenerBase implements ActionListener
	{
		public ExportActionListener(SWRLRuleEngine ruleEngine, JTextArea textArea, SWRLControlPanel controlPanel)
		{
			super(ruleEngine, textArea, controlPanel);
		}

		@Override
		public void actionPerformed(ActionEvent event)
		{
			try {
				long startTime = System.currentTimeMillis();
				this.ruleEngine.writeInferredKnowledge();

				this.textArea.append("Successfully transferred inferred axioms to OWL model.\n");
				this.textArea.append("The process took " + (System.currentTimeMillis() - startTime) + " millisecond(s).\n");
			} catch (SWRLRuleEngineException e) {
				this.textArea.append("Exception exporting knowledge to OWL: " + e.toString() + "\n");
			}
			this.controlPanel.getParent().validate();
		}
	}
}
