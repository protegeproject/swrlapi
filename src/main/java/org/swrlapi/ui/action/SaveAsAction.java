package org.swrlapi.ui.action;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.swrlapi.ui.dialog.SWRLAPIDialogManager;
import org.swrlapi.ui.model.FileBackedOWLOntologyModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Optional;

public class SaveAsAction implements ActionListener
{
  @NonNull private final Component parent;
  @NonNull private final SWRLAPIDialogManager dialogManager;
  @NonNull private final FileBackedOWLOntologyModel ontologyModel;

  public static final String TITLE = "Save As";
  private static final String MESSAGE = "Save Ontology";
  private static final String EXTENSON = "owl";

  public SaveAsAction(@NonNull Component parent, @NonNull FileBackedOWLOntologyModel ontologyModel,
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
    JFileChooser fileChooser = this.dialogManager.createSaveFileChooser(TITLE, MESSAGE, EXTENSON, true);

    if (fileChooser.showOpenDialog(this.parent) == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();

      try {
        this.ontologyModel.setBackingFile(file);
        this.ontologyModel.save();
      } catch (OWLOntologyStorageException e) {
        this.dialogManager.showErrorMessageDialog(this.parent, e.getMessage(), "Error");
      }
    }
  }
}
