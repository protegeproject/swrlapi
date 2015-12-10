package org.swrlapi.ui.action;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.ui.dialog.SWRLRuleEngineDialogManager;
import org.swrlapi.ui.model.FileBackedOntologyModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class OpenAction implements ActionListener
{
	@NonNull
	private final Component parent;
	private final @NonNull SWRLRuleEngineDialogManager dialogManager;
	@NonNull
	private final FileBackedOntologyModel ontologyModel;

	public static final String OPEN_TITLE = "Open";
	private static final String FILE_DESCRIPTION = "OWL Ontology";
	private static final String FILE_EXTENSION = "owl";
	private static final String ERROR_TITLE = "Error";

	public OpenAction(@NonNull Component parent, @NonNull FileBackedOntologyModel ontologyModel,
			@NonNull SWRLRuleEngineDialogManager dialogManager)
	{
		this.parent = parent;
		this.dialogManager = dialogManager;
		this.ontologyModel = ontologyModel;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		open();
	}

	public void open()
	{
		JFileChooser fileChooser = this.dialogManager.createFileChooser(OPEN_TITLE, FILE_DESCRIPTION, FILE_EXTENSION);

		if (fileChooser.showOpenDialog(this.parent) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();

			try {
				this.ontologyModel.open(file);
			} catch (OWLOntologyCreationException e) {
				this.dialogManager.showErrorMessageDialog(this.parent, e.getMessage() != null ? e.getMessage() : "", ERROR_TITLE);
			}
		}
	}
}
