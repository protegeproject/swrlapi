package org.swrlapi.ui.action;

import org.swrlapi.ui.dialog.SWRLAPIDialogManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SaveAsAction implements ActionListener
{
  final Component parent;
  final SWRLAPIDialogManager dialogManager;

  public static final String TITLE = "Save As";
  private static final String MESSAGE = "Save Ontology";
  private static final String EXTENSON = "owl";

  public SaveAsAction(Component parent, SWRLAPIDialogManager dialogManager)
  {
    this.parent = parent;
    this.dialogManager = dialogManager;
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
      String fileName = file.getAbsolutePath();

      // if (!fileName.endsWith(".owl"))
      // fileName = fileName.concat(".owl");
      // clear modified statis

      //https://github.com/owlcs/owlapi/blob/version4/contract/src/test/java/org/semanticweb/owlapi/examples/Examples.java#L167
    }
    //this.dialogManager().showErrorMessageDialog(parent, ex.getMessage());
  }
}
