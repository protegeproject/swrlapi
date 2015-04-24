package org.swrlapi.ui.model;

import java.util.List;

public interface SWRLAutoCompleter
{
	/**
	 * @param prefix A prefix string
	 * @return The possible completions
	 */
	List<String> getCompletions(String prefix);
}
