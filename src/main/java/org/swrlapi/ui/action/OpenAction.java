package org.swrlapi.ui.action;

import org.swrlapi.ui.dialog.SWRLAPIDialogManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class OpenAction implements ActionListener
{
  final Component parent;
  final SWRLAPIDialogManager dialogManager;

  public static final String TITLE = "Open";
  private static final String MESSAGE = "Open Ontology";
  private static final String EXTENSON = "owl";

  public OpenAction(Component parent, SWRLAPIDialogManager dialogManager)
  {
    this.parent = parent;
    this.dialogManager = dialogManager;
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    open();
  }

  public void open()
  {
    JFileChooser fileChooser = this.dialogManager.createFileChooser(TITLE, MESSAGE, EXTENSON);

     if (fileChooser.showOpenDialog(this.parent) == JFileChooser.APPROVE_OPTION) {
       File file = fileChooser.getSelectedFile();
       String fileName = file.getAbsolutePath();

       //https://github.com/owlcs/owlapi/blob/version4/contract/src/test/java/org/semanticweb/owlapi/examples/Examples.java#L167
     }
  }
}
