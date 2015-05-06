package org.swrlapi.ui.action;

import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.swrlapi.ui.dialog.SWRLAPIDialogManager;
import org.swrlapi.ui.model.FileBackedOWLOntologyModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SaveAsAction implements ActionListener
{
  private final Component parent;
  private final SWRLAPIDialogManager dialogManager;
  private final FileBackedOWLOntologyModel ontologyModel;

  public static final String TITLE = "Save As";
  private static final String MESSAGE = "Save Ontology";
  private static final String EXTENSON = "owl";

  public SaveAsAction(Component parent, FileBackedOWLOntologyModel ontologyModel, SWRLAPIDialogManager dialogManager)
  {
    this.parent = parent;
    this.dialogManager = dialogManager;
    this.ontologyModel = ontologyModel;
  }

  @Override public void actionPerformed(ActionEvent e)
  {
    saveAs();
  }

  public void saveAs()
  {
    JFileChooser fileChooser = this.dialogManager.createSaveFileChooser(TITLE, MESSAGE, EXTENSON, true);

    if (fileChooser.showOpenDialog(this.parent) == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();

			try {
				this.ontologyModel.saveAs(file);
			} catch (OWLOntologyStorageException e) {
				this.dialogManager.showErrorMessageDialog(this.parent, e.getMessage(), "Error");
			}
    }
  }
}
