package org.swrlapi.ui.action;

import checkers.nullness.quals.NonNull;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DisableAllRulesAction extends AbstractAction
{
  private static final long serialVersionUID = 1L;

  public DisableAllRulesAction()
  {
    super("Disable all rules");
  }

  @Override
  public void actionPerformed(@NonNull ActionEvent e)
  {
  }
}