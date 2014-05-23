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

public class SaveSWRLRulesAction implements ActionListener
{
	private final ApplicationController application;

	public SaveSWRLRulesAction(ApplicationController application)
	{
		this.application = application;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		saveSWRLRules();
	}

	public void saveSWRLRules()
	{
		SWRLRulesModel mappingExpressionsModel = application.getApplicationModel().getSWRLRulesModel();
		ApplicationView applicationView = application.getApplicationViewController();
		ApplicationModel applicationModel = application.getApplicationModel();
		MappingsControlView mappingsControlView = applicationView.getMappingsControlView();

		JFileChooser fileChooser = getApplicationDialogManager()
				.createSaveFileChooser("Save Mapping Ontology", "owl", true);

		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			String fileName = file.getAbsolutePath();
			Set<SWRLRuleModel> mappingExpressions = mappingExpressionsModel.getSWRLRuleModels();

			if (!fileName.endsWith(".owl"))
				fileName = fileName.concat(".owl");

			mappingsControlView.statusWindowAppend("Saving mappings ontology '" + fileName + "'...\n");

			try {
				application.getMappingExpressionsPersistenceLayer().saveSWRLRules(mappingExpressions, fileName);

				applicationModel.setMappingFileName(fileName);
				applicationModel.clearModifiedStatus();

				mappingsControlView.statusWindowAppend("Mapping file successfully saved.\n");
			} catch (Exception ex) {
				getApplicationDialogManager().showErrorMessageDialog(applicationView, ex.getMessage());
				mappingsControlView.statusWindowAppend("Error saving mapping file: " + ex.getMessage());
			}
		}
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
