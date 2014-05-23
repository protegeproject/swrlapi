package org.swrlapi.ui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.swrlapi.ui.ApplicationController;
import org.swrlapi.ui.core.ApplicationModel;
import org.swrlapi.ui.core.ApplicationView;
import org.swrlapi.ui.core.SWRLRulesDataSource;
import org.swrlapi.ui.core.View;
import org.swrlapi.ui.dialog.ApplicationDialogManager;
import org.swrlapi.ui.model.SWRLRulesDataSourceModel;

public class DataSourceView extends JPanel implements View
{
	private static final long serialVersionUID = 1L;

	private ApplicationController applicationController;
	private JTextField fileNameTextField;

	private JTabbedPane tabbedPane;

	public DataSourceView(ApplicationController applicationController)
	{
		this.applicationController = applicationController;
		getDataSourceModel().setView(this);

		createComponents();

		update();
	}

	public void setApplicationController(ApplicationController applicationController)
	{
		this.applicationController = applicationController;
		getDataSourceModel().setView(this);

		update();
	}

	public void clear()
	{
		applicationController = null;

		update();
	}

	@Override
	public void update()
	{
		tabbedPane.removeAll();

		if (applicationController != null && getDataSourceModel().hasSWRLRulesDataSource()) {
		}

		if (getDataSourceModel().hasFileName())
			fileNameTextField.setText(getDataSourceModel().getFileName());
		else
			fileNameTextField.setText("");

		validate();
	}

	private void createComponents()
	{
		JPanel footerPanel, buttonPanel;
		JButton openButton, closeButton;

		setLayout(new BorderLayout());

		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Workbook"));

		tabbedPane = new JTabbedPane();

		footerPanel = new JPanel(new BorderLayout());
		footerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Workbook File"));
		add(footerPanel, BorderLayout.SOUTH);

		fileNameTextField = createTextField("");
		fileNameTextField.setEnabled(true);
		footerPanel.add(fileNameTextField, BorderLayout.CENTER);

		buttonPanel = new JPanel(new BorderLayout());
		footerPanel.add(buttonPanel, BorderLayout.EAST);

		openButton = new JButton("Open");
		openButton.addActionListener(new OpenWorkbookAction());
		buttonPanel.add(openButton, BorderLayout.WEST);

		closeButton = new JButton("Close");
		closeButton.addActionListener(new CloseWorkbookAction());
		buttonPanel.add(closeButton, BorderLayout.EAST);

		add(tabbedPane, BorderLayout.CENTER);
	}

	private class OpenWorkbookAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			JFileChooser fileChooser = getApplicationDialogManager().createFileChooser("Open Data Source", "xls");
			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				String fileName = file.getAbsolutePath();

				try {
					SWRLRulesDataSource dataSource = new SWRLRulesDataSource();
					getDataSourceModel().setSWRLRulesDataSource(dataSource);
					getDataSourceModel().setFileName(fileName);
					getApplicationModel().dataSourceUpdated();
				} catch (Exception ex) {
					getApplicationDialogManager().showErrorMessageDialog(tabbedPane,
							"error opening file '" + fileName + "': " + ex.getMessage());
				}
			}
		}
	}

	private class CloseWorkbookAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (getDataSourceModel().hasSWRLRulesDataSource()
					&& getApplicationDialogManager().showConfirmDialog(tabbedPane, "Close Data Source",
							"Do you really want to close the data source?")) {
				getDataSourceModel().clearSWRLRulesDataSource();
				getDataSourceModel().clearFileName();
			}
		}
	}

	private JTextField createTextField(String text)
	{
		JTextField textField = new JTextField(text);
		textField.setPreferredSize(new Dimension(80, 30));
		return textField;
	}

	private ApplicationModel getApplicationModel()
	{
		return applicationController.getApplicationModel();
	}

	private SWRLRulesDataSourceModel getDataSourceModel()
	{
		return applicationController.getApplicationModel().getDataSourceModel();
	}

	private ApplicationView getApplicationView()
	{
		return applicationController.getApplicationViewController();
	}

	private ApplicationDialogManager getApplicationDialogManager()
	{
		return getApplicationView().getApplicationDialogManager();
	}
}
