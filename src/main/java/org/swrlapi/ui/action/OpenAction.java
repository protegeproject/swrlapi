package org.swrlapi.ui.action;

import checkers.nullness.quals.NonNull;
import org.swrlapi.ui.dialog.SWRLAPIDialogManager;
import org.swrlapi.ui.model.FileBackedOWLOntologyModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class OpenAction implements ActionListener
{
  @NonNull private final Component parent;
  @NonNull private final SWRLAPIDialogManager dialogManager;
  @NonNull private final FileBackedOWLOntologyModel ontologyModel;

  public static final String TITLE = "Open";
  private static final String MESSAGE = "Open Ontology";
  private static final String EXTENSION = "owl";

  public OpenAction(@NonNull Component parent, @NonNull FileBackedOWLOntologyModel ontologyModel,
    @NonNull SWRLAPIDialogManager dialogManager)
  {
    this.parent = parent;
    this.dialogManager = dialogManager;
    this.ontologyModel = ontologyModel;
  }

  @Override public void actionPerformed(ActionEvent e)
  {
    open();
  }

  public void open()
  {
    JFileChooser fileChooser = this.dialogManager.createFileChooser(TITLE, MESSAGE, EXTENSION);

    if (fileChooser.showOpenDialog(this.parent) == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();
      String fileName = file.getAbsolutePath();

      this.ontologyModel.changeBackingFile(file);

      //https://github.com/owlcs/owlapi/blob/version4/contract/src/test/java/org/semanticweb/owlapi/examples/Examples.java#L167
    }
  }
}
