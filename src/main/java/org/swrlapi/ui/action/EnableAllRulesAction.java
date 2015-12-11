package org.swrlapi.ui.action;

import checkers.nullness.quals.NonNull;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class EnableAllRulesAction extends AbstractAction
{
  private static final long serialVersionUID = 1L;

  private static final String HELP_TEXT = "Enable all rules";

  public EnableAllRulesAction()
  {
    super(HELP_TEXT);
  }

  @Override public void actionPerformed(@NonNull ActionEvent e)
  {
  }
}