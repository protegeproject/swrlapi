package org.swrlapi.ui.action;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.swrlapi.ui.dialog.SWRLAPIDialogManager;
import org.swrlapi.ui.model.FileBackedOntologyModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SaveAsAction implements ActionListener
{
  @NonNull private final Component parent;
  @NonNull private final SWRLAPIDialogManager dialogManager;
  @NonNull private final FileBackedOntologyModel ontologyModel;

  public static final String SAVE_AS_TITLE = "Save As";
  private static final String FILE_DESCRIPTION = "OWL Ontology";
  private static final String FILE_EXTENSION = "owl";
  private static final String ERROR_TITLE = "Error";

  public SaveAsAction(@NonNull Component parent, @NonNull FileBackedOntologyModel ontologyModel,
    @NonNull SWRLAPIDialogManager dialogManager)
  {
    this.parent = parent;
    this.dialogManager = dialogManager;
    this.ontologyModel = ontologyModel;
  }

  @Override public void actionPerformed(@NonNull ActionEvent e)
  {
    saveAs();
  }

  public void saveAs()
  {
    JFileChooser fileChooser = this.dialogManager
      .createSaveFileChooser(SAVE_AS_TITLE, FILE_DESCRIPTION, FILE_EXTENSION, true);

    if (fileChooser.showSaveDialog(this.parent) == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();

      try {
        this.ontologyModel.saveAs(file);
      } catch (OWLOntologyStorageException e) {
        this.dialogManager.showErrorMessageDialog(this.parent, e.getMessage(), ERROR_TITLE);
      }
    }
  }
}
