package org.swrlapi.ui.owl2rl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class OWL2RLControlPanel extends JPanel
{
	private static final long serialVersionUID = 5752143738619057723L;

	private final OWL2RLTablesControlPanel tablesControlPanel;

	public OWL2RLControlPanel(OWL2RLModel owl2RLModel)
	{
		this.tablesControlPanel = new OWL2RLTablesControlPanel(owl2RLModel);

		initialize();
	}

	public void update()
	{
		this.tablesControlPanel.update();
	}

	private void initialize()
	{
		JTextArea textArea = createTextArea();
		JScrollPane scrollPane = new JScrollPane(textArea);

		setLayout(new BorderLayout());

		scrollPane.setPreferredSize(new Dimension(900, 300));

		add(BorderLayout.CENTER, scrollPane);

		textArea
				.append("The SWRLTab supports an OWL profile called OWL 2 RL and uses an OWL 2 RL-based reasoner to perform reasoning.\n");
		textArea
				.append("OWL 2 RL reasoning is performed in the SWRLTab primarily via a set of implication rules. These rules are described\n");
		textArea.append("in the following W3C document: http://www.w3.org/TR/owl2-profiles/#OWL_2_RL.\n\n");
		textArea
				.append("In this document, the rules are divided into a set of numbered tables and and each rule is given a unique name.\n");
		textArea
				.append("The following subtabs list these rule names, indicate their support status, and allow suported rules to be\n");
		textArea
				.append("enabled or disabled. A check next to each rule indicates whether that rule is enabled or disabled. Greyed-out\n");
		textArea
				.append("rules are either permanently enabled or currently unsupported and cannot be toggled. The toggle buttons below\n");
		textArea.append("allow all rules in particular tables to be enabled and disabled.\n\n");
		textArea.append("Further information can be found at: http://protege.cim3.net/cgi-bin/wiki.pl?SWRLTabOWL2RL.\n");

		add(BorderLayout.SOUTH, this.tablesControlPanel);
	}

	private JTextArea createTextArea()
	{
		JTextArea textArea = new JTextArea(10, 80);
		textArea.setLineWrap(true);
		textArea.setBackground(Color.WHITE);
		textArea.setEditable(false);
		return textArea;
	}
}
