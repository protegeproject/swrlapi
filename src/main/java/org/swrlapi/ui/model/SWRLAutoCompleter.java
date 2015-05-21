package org.swrlapi.ui.model;

import checkers.nullness.quals.NonNull;

import java.util.List;

public interface SWRLAutoCompleter
{
  /**
   * @param prefix A prefix string
   * @return The possible completions
   */
  @NonNull List<String> getCompletions(@NonNull String prefix);
}
