package org.swrlapi.ui.model;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public interface SWRLAutoCompleter
{
  /**
   * @param prefix A prefix string
   * @return The possible completions
   */
  @NonNull List<@NonNull String> getCompletions(@NonNull String prefix);
}
