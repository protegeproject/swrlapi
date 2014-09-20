package org.swrlapi.ui.view.owl2rl;

import org.swrlapi.ui.model.OWL2RLModel;
import org.swrlapi.ui.view.SWRLAPIView;

import javax.swing.*;
import java.awt.*;

/**
 * @see org.swrlapi.ui.model.OWL2RLModel
 */
public class OWL2RLControlView extends JPanel implements SWRLAPIView
{
	private static final long serialVersionUID = 1L;

	private static final int VIEW_PREFERRED_WIDTH = 900;
	private static final int VIEW_PREFERRED_HEIGHT = 300;
	private static final int CONSOLE_ROWS = 10;
	private static final int CONSOLE_COLUMNS = 80;

	private final OWL2RLTablesControlView owl2RLTablesControlView;

	public OWL2RLControlView(OWL2RLModel owl2RLModel)
	{
		this.owl2RLTablesControlView = new OWL2RLTablesControlView(owl2RLModel);

		initialize();
	}

	@Override
	public void update()
	{
		this.owl2RLTablesControlView.update();
	}

	private void initialize()
	{
		JTextArea console = createConsole();
		JScrollPane scrollPane = new JScrollPane(console);

		setLayout(new BorderLayout());
		scrollPane.setPreferredSize(new Dimension(VIEW_PREFERRED_WIDTH, VIEW_PREFERRED_HEIGHT));
		add(BorderLayout.CENTER, scrollPane);

		console.append(
				"The SWRLAPI supports an OWL profile called OWL 2 RL and uses an OWL 2 RL-based reasoner to perform reasoning.\n\n");
		console.append(
				"OWL 2 RL reasoning is performed primarily via a set of implication rules. These rules are described\n");
		console.append("in the following W3C document: http://www.w3.org/TR/owl2-profiles/#OWL_2_RL.\n");
		console.append(
				"This document divides these rules into a set of numbered tables and each rule is given a unique name.\n\n");
		console.append("The toggle buttons below allow all rules in particular tables to be enabled and disabled.\n\n");
		console.append(
				"The table-specific subtabs list individual rule names, indicate their support status, and allow suported rules to be\n");
		console.append(
				"enabled or disabled. A check next to each rule indicates whether that rule is enabled or disabled. Greyed-out\n");
		console.append("rules are either permanently enabled or currently unsupported and cannot be toggled.\n\n");

		add(BorderLayout.SOUTH, this.owl2RLTablesControlView);
	}

	private JTextArea createConsole()
	{
		JTextArea textArea = new JTextArea(CONSOLE_ROWS, CONSOLE_COLUMNS);

		textArea.setLineWrap(true);
		textArea.setBackground(Color.WHITE);
		textArea.setEditable(false);

		return textArea;
	}
}
