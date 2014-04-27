package org.swrlapi.ui.owl2rl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class OWL2RLControlPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

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
		JTextArea console = createConsole();
		JScrollPane scrollPane = new JScrollPane(console);

		setLayout(new BorderLayout());

		scrollPane.setPreferredSize(new Dimension(900, 300));

		add(BorderLayout.CENTER, scrollPane);

		console
				.append("The SWRLTab supports an OWL profile called OWL 2 RL and uses an OWL 2 RL-based reasoner to perform reasoning.\n\n");
		console
				.append("OWL 2 RL reasoning is performed primarily via a set of implication rules. These rules are described\n");
		console.append("in the following W3C document: http://www.w3.org/TR/owl2-profiles/#OWL_2_RL.\n");
		console
				.append("This document divides these rules into a set of numbered tables and each rule is given a unique name.\n\n");
		console.append("The toggle buttons below allow all rules in particular tables to be enabled and disabled.\n\n");
		console
				.append("The table-specific subtabs list individual rule names, indicate their support status, and allow suported rules to be\n");
		console
				.append("enabled or disabled. A check next to each rule indicates whether that rule is enabled or disabled. Greyed-out\n");
		console.append("rules are either permanently enabled or currently unsupported and cannot be toggled.\n\n");

		add(BorderLayout.SOUTH, this.tablesControlPanel);
	}

	private JTextArea createConsole()
	{
		JTextArea textArea = new JTextArea(10, 80);

		textArea.setLineWrap(true);
		textArea.setBackground(Color.WHITE);
		textArea.setEditable(false);

		return textArea;
	}
}
