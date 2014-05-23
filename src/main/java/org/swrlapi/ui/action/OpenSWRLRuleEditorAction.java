package org.swrlapi.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Set;

import javax.swing.JFileChooser;

import org.swrlapi.ui.ApplicationController;
import org.swrlapi.ui.core.ApplicationModel;
import org.swrlapi.ui.core.ApplicationView;
import org.swrlapi.ui.core.SWRLRuleModel;
import org.swrlapi.ui.dialog.ApplicationDialogManager;
import org.swrlapi.ui.model.SWRLRulesModel;
import org.swrlapi.ui.view.MappingsControlView;

public class OpenSWRLRuleEditorAction implements ActionListener
{
	private final ApplicationController application;

	public OpenSWRLRuleEditorAction(ApplicationController application)
	{
		this.application = application;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		openMappings();
	}

	public void openMappings()
	{
		SWRLRulesModel mappingExpressionsModel = application.getApplicationModel().getSWRLRulesModel();
		ApplicationView applicationView = application.getApplicationViewController();
		ApplicationModel applicationModel = application.getApplicationModel();
		MappingsControlView mappingsControlView = applicationView.getMappingsControlView();

		JFileChooser fileChooser = getApplicationDialogManager().createFileChooser("Open Mapping Ontology", "owl");

		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			String fileName = file.getAbsolutePath();
			Set<SWRLRuleModel> mappingExpressions = null;

			mappingsControlView.statusWindowAppend("Opening mappings file '" + fileName + "'...\n");

			try {
				mappingExpressions = application.getMappingExpressionsPersistenceLayer().getSWRLRuleModels(fileName);

				mappingsControlView.statusWindowAppend("Mapping file successfully opened.\n");
			} catch (Exception ex) {
				getApplicationDialogManager().showErrorMessageDialog(applicationView, ex.getMessage());
				mappingsControlView.statusWindowAppend("Error opening mapping file: " + ex.getMessage() + "\n");
			}

			if (mappingExpressions != null) {
				mappingsControlView.statusWindowAppend("Found " + mappingExpressions.size()
						+ " mapping expressions(s) in mapping file.\n");
				mappingExpressionsModel.setSWRLRuleModels(mappingExpressions);
				applicationModel.setMappingFileName(fileName);
			} else
				mappingsControlView.statusWindowAppend("No mappings defined in mapping file.\n");

			applicationModel.clearModifiedStatus();
		} // if
	}

	private ApplicationView getApplicationView()
	{
		return application.getApplicationViewController();
	}

	private ApplicationDialogManager getApplicationDialogManager()
	{
		return getApplicationView().getApplicationDialogManager();
	}
}
