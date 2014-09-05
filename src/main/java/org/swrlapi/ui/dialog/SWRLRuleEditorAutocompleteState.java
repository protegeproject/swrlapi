package org.swrlapi.ui.dialog;

import java.util.ArrayList;
import java.util.List;

class SWRLRuleEditorAutocompleteState
{
	private final int textPosition;
	private final String prefix;
	private final List<String> expansions;
	private int expansionIndex;

	public SWRLRuleEditorAutocompleteState(int textPosition, String prefix, List<String> expansions)
	{
		this.textPosition = textPosition;
		this.prefix = prefix;
		this.expansions = new ArrayList<String>(expansions);
		this.expansionIndex = 0;
	}

	public int getTextPosition()
	{
		return this.textPosition;
	}

	public String getPrefix()
	{
		return this.prefix;
	}

	public List<String> getExpansions()
	{
		return new ArrayList<String>(this.expansions);
	}

	public int getExpansionIndex()
	{
		return this.expansionIndex;
	}

	public String getCurrentExpansion()
	{
		if (!this.expansions.isEmpty()) {
			return this.expansions.get(this.expansionIndex);
		} else
			return "";
	}

	public String getNextExpansion()
	{
		if (!this.expansions.isEmpty()) {
			this.expansionIndex++;

			if (this.expansionIndex == this.expansions.size())
				this.expansionIndex = 0;

			return this.expansions.get(this.expansionIndex);
		} else
			return "";
	}
}
