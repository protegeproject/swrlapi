package org.swrlapi.ui.action;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.swrlapi.ui.dialog.SWRLRuleEngineDialogManager;
import org.swrlapi.ui.model.FileBackedOntologyModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SaveAsAction implements ActionListener
{
  public static final String SAVE_AS_TITLE = "Save As";
  private static final String FILE_DESCRIPTION = "OWL Ontology";
  private static final String FILE_EXTENSION = "owl";
  private static final String ERROR_TITLE = "Error";

  @NonNull private final Component parent;
  @NonNull private final SWRLRuleEngineDialogManager dialogManager;
  @NonNull private final FileBackedOntologyModel ontologyModel;

  public SaveAsAction(@NonNull Component parent, @NonNull FileBackedOntologyModel ontologyModel,
      @NonNull SWRLRuleEngineDialogManager dialogManager)
  {
    this.parent = parent;
    this.dialogManager = dialogManager;
    this.ontologyModel = ontologyModel;
  }

  @Override public void actionPerformed(@NonNull ActionEvent e)
  {
    saveAs();
  }

  private void saveAs()
  {
    JFileChooser fileChooser = this.dialogManager
        .createSaveFileChooser(SAVE_AS_TITLE, FILE_DESCRIPTION, FILE_EXTENSION, true);

    if (fileChooser.showSaveDialog(this.parent) == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();

      try {
        this.ontologyModel.saveAs(file);
      } catch (OWLOntologyStorageException e) {
        this.dialogManager
            .showErrorMessageDialog(this.parent, e.getMessage() != null ? e.getMessage() : "", ERROR_TITLE);
      }
    }
  }
}
