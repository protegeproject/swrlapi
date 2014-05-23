package org.swrlapi.ui.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.swrlapi.ui.SWRLAPIApplicationController;
import org.swrlapi.ui.action.CloseSWRLRuleEditorAction;
import org.swrlapi.ui.action.OpenSWRLRuleEditorAction;
import org.swrlapi.ui.action.RunSWRLRulesAction;
import org.swrlapi.ui.action.SaveSWRLRuleAction;
import org.swrlapi.ui.core.SWRLAPIApplicationModel;
import org.swrlapi.ui.core.ConfigurationOptionsManager;
import org.swrlapi.ui.core.SWRLAPIView;

public class SQWRLQueryControllerView extends JPanel implements SWRLAPIView
{
	private static final long serialVersionUID = 1L;

	private final SWRLAPIApplicationController application;
	private JTextField fileNameTextField;
	private JTextArea statusWindow;
	private JButton saveButton, saveAsButton;

	public SQWRLQueryControllerView(SWRLAPIApplicationController application)
	{
		this.application = application;

		createComponents();

		statusWindowAppend("MappingMaster V0.95\n\n");
		statusWindowAppend("See http://protege.cim3.net/cgi-bin/wiki.pl?MappingMaster for documentation.\n");
		statusWindowAppend("Use the Expressions tab to define mappings using MappingMaster's DSL.\n");
		statusWindowAppend("Click the Map button to perform mappings.\n");
	}

	@Override
	public void update()
	{
		if (getApplicationModel().hasMappingFile()) {
			fileNameTextField.setText(getApplicationModel().getMappingFileName());
			saveButton.setEnabled(true);
			saveAsButton.setEnabled(true);
		} else {
			fileNameTextField.setText("");
			saveButton.setEnabled(false);
			saveAsButton.setEnabled(true);
		}

		validate();
	}

	public void clearStatusWindow()
	{
		statusWindow.setText("");
	}

	public void statusWindowAppend(String text)
	{
		statusWindow.append(text);
	}

	// TODO: need to make option stuff more generic. MappingConfigurationOptionsManager should return list of options
	private void createComponents()
	{
		JPanel headingPanel, mappingsButtonPanel, optionsPanel, fileButtonPanel, footerPanel;
		JButton openButton, closeButton, mapExpressionsButton;
		JScrollPane scrollPane;

		setLayout(new BorderLayout());

		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));

		headingPanel = new JPanel(new BorderLayout());
		add(headingPanel, BorderLayout.NORTH);

		mapExpressionsButton = new JButton("Map");
		mapExpressionsButton.setPreferredSize(new Dimension(125, 25));
		mapExpressionsButton.addActionListener(new RunSWRLRulesAction(application));

		mappingsButtonPanel = new JPanel(new GridLayout(3, 1));
		mappingsButtonPanel.add(new JPanel(), 0);
		mappingsButtonPanel.add(mapExpressionsButton, 1);
		mappingsButtonPanel.add(new JPanel(), 2);

		headingPanel.add(mappingsButtonPanel, BorderLayout.WEST);

		optionsPanel = new JPanel(new GridLayout(5, 2));
		headingPanel.add(optionsPanel, BorderLayout.EAST);

		statusWindow = new JTextArea();
		statusWindow.setBorder(BorderFactory.createEtchedBorder());
		statusWindow.setBackground(Color.WHITE);
		statusWindow.setLineWrap(true);
		statusWindow.setEditable(false);
		scrollPane = new JScrollPane(statusWindow);

		add(scrollPane, BorderLayout.CENTER);

		footerPanel = new JPanel(new BorderLayout());
		footerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Mapping Ontology"));
		add(footerPanel, BorderLayout.SOUTH);

		fileNameTextField = createTextField("");
		fileNameTextField.setEnabled(true);
		footerPanel.add(fileNameTextField, BorderLayout.CENTER);

		fileButtonPanel = new JPanel(new GridLayout(1, 4));
		footerPanel.add(fileButtonPanel, BorderLayout.EAST);

		openButton = new JButton("Open");
		openButton.addActionListener(new OpenSWRLRuleEditorAction(application));
		fileButtonPanel.add(openButton);

		saveButton = new JButton("Save");
		saveButton.addActionListener(new SaveSWRLRuleAction(application));
		saveButton.setEnabled(false);
		fileButtonPanel.add(saveButton);

		saveAsButton = new JButton("Save As...");
		saveAsButton.addActionListener(new SaveSWRLRuleAction(application));
		saveAsButton.setEnabled(true);
		fileButtonPanel.add(saveAsButton, BorderLayout.CENTER);

		closeButton = new JButton("Close");
		closeButton.addActionListener(new CloseSWRLRuleEditorAction(application));
		fileButtonPanel.add(closeButton);
	}

	private SWRLAPIApplicationModel getApplicationModel()
	{
		return application.getApplicationModel();
	}

	private ConfigurationOptionsManager getOptionsManager()
	{
		return getApplicationModel().getConfigurationOptionsManager();
	}

	private class ConfigurationActionListener implements ActionListener
	{
		private final String optionName;

		public ConfigurationActionListener(String optionName)
		{
			this.optionName = optionName;
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			// JComboBox cb = (JComboBox)e.getSource();
			// String selectedItem = (String)cb.getSelectedItem();
		}

	}

	private JTextField createTextField(String text)
	{
		JTextField textField = new JTextField(text);
		textField.setPreferredSize(new Dimension(80, 30));
		return textField;
	}
}
