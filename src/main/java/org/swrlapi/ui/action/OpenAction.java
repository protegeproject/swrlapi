package org.swrlapi.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OpenAction implements ActionListener
{
  public OpenAction()
  {
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    open();
  }

  public void open()
  {
    // JFileChooser fileChooser = getApplicationDialogManager().createFileChooser("Open Mapping Ontology", "owl");
    //
    // if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
    // File file = fileChooser.getSelectedFile();
    // String fileName = file.getAbsolutePath();
    //
    // try {
    // mappingExpressions = application.getMappingExpressionsPersistenceLayer().getMappingExpressions(fileName);
    // } catch (MappingMasterException ex) {
    // getApplicationDialogManager().showErrorMessageDialog(applicationView, ex.getMessage());
    // }
    //
    // if (mappingExpressions != null) {
    // applicationModel.setMappingFileName(fileName);
    // } else
    // applicationModel.clearModifiedStatus();
    // }
  }
}
