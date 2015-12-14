package org.swrlapi.ui.action;

import org.checkerframework.checker.nullness.qual.NonNull;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DisableAllRulesAction extends AbstractAction
{
  private static final long serialVersionUID = 1L;

  private static final String HELP_TEXT = "Disable all rules";

  public DisableAllRulesAction()
  {
    super(HELP_TEXT);
  }

  @Override public void actionPerformed(@NonNull ActionEvent e)
  {
  }
}