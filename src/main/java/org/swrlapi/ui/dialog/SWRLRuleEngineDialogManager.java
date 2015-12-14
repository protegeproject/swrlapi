package org.swrlapi.ui.dialog;

import org.checkerframework.checker.nullness.qual.NonNull;

import javax.swing.*;
import java.awt.*;

/**
 * Provides a set of standard dialogs.
 */
public interface SWRLRuleEngineDialogManager
{
  @NonNull JDialog getSWRLRuleEditorDialog(@NonNull Component parent);

  @NonNull JDialog getSWRLRuleEditorDialog(@NonNull Component parent, @NonNull String ruleName,
    @NonNull String ruleText, @NonNull String comment);

  int showConfirmCancelDialog(@NonNull Component parent, @NonNull String message, @NonNull String title);

  boolean showConfirmDialog(@NonNull Component parent, @NonNull String message, @NonNull String title);

  void showErrorMessageDialog(@NonNull Component parent, @NonNull String message, @NonNull String title);

  String showInputDialog(@NonNull Component parent, @NonNull String message, @NonNull String initialValue);

  void showMessageDialog(@NonNull Component parent, @NonNull String message, @NonNull String title);

  @NonNull JFileChooser createFileChooser(@NonNull String title, @NonNull String fileDescription,
    @NonNull String fileExtension);

  @NonNull JFileChooser createSaveFileChooser(@NonNull String title, @NonNull String fileDescription,
    @NonNull String fileExtension, final boolean overwrite);
}
