package org.swrlapi.ui.dialog;

import checkers.nullness.quals.NonNull;

import javax.swing.*;
import java.awt.*;

/**
 * Provides a set of standard dialogs.
 */
public interface SWRLAPIDialogManager
{
  @NonNull JDialog getSWRLRuleEditorDialog(Component parent);

  @NonNull JDialog getSWRLRuleEditorDialog(Component parent, String ruleName, String ruleText, String comment);

  int showConfirmCancelDialog(Component parent, String message, String title);

  boolean showConfirmDialog(Component parent, String message, String title);

  void showErrorMessageDialog(Component parent, String message, String title);

  String showInputDialog(Component parent, String message, String initialValue);

  void showMessageDialog(Component parent, String message, String title);

  @NonNull JFileChooser createFileChooser(String title, String fileDescription, String fileExtension);

  @NonNull JFileChooser createSaveFileChooser(String title, String fileDescription, String fileExtension, final boolean overwrite);
}
