package org.swrlapi.ui.model;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.core.SWRLRuleRenderer;
import org.swrlapi.ui.view.SWRLAPIView;

import javax.swing.table.AbstractTableModel;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * This class models a list of SWRL rules or SQWRL queries in an ontology for tabular display.
 *
 * @see org.swrlapi.ui.model.SWRLRuleEngineModel
 * @see org.swrlapi.core.SWRLAPIRule
 * @see org.swrlapi.sqwrl.SQWRLQuery
 */
public class SWRLRulesTableModel extends AbstractTableModel implements SWRLAPIModel
{
	private static final long serialVersionUID = 1L;

	public static final int ACTIVE_COLUMN = 0;
	public static final int RULE_NAME_COLUMN = 1;
	public static final int RULE_TEXT_COLUMN = 2;
	public static final int RULE_COMMENT_COLUMN = 3;

	public static final int NUMBER_OF_COLUMNS = 4;

	@NonNull
	private final SWRLRuleEngine swrlRuleEngine;
	@NonNull
	private final SWRLRuleRenderer swrlRuleRenderer;
	@NonNull
	private final SortedMap<String, SWRLRuleModel> swrlRuleModels; // rule name -> SWRLRuleModel

	private Optional<SWRLAPIView> view = Optional.empty();
	private boolean isModified = false;

	public SWRLRulesTableModel(@NonNull SWRLRuleEngine swrlRuleEngine, @NonNull SWRLRuleRenderer swrlRuleRenderer)
	{
		this.swrlRuleEngine = swrlRuleEngine;
		this.swrlRuleRenderer = swrlRuleRenderer;
		this.swrlRuleModels = new TreeMap<>();
	}

	public void setView(@NonNull SWRLAPIView view)
	{
		this.view = Optional.of(view);
		updateRuleModels();
	}

	@NonNull
	public Set<SWRLRuleModel> getSWRLRuleModels()
	{
		return new HashSet<>(this.swrlRuleModels.values());
	}

	@NonNull
	public Set<SWRLRuleModel> getSWRLRuleModels(boolean isActiveFlag)
	{
		return this.swrlRuleModels.values().stream().filter(swrlRuleModel -> swrlRuleModel.isActive() == isActiveFlag)
				.collect(Collectors.toSet());
	}

	public boolean hasSWRLRules()
	{
		return !this.swrlRuleModels.isEmpty();
	}

	@NonNull
	public String getSWRLRuleNameByIndex(int ruleIndex)
	{
		Optional<SWRLRuleModel> swrlRuleModel = getSWRLRuleModelByIndex(ruleIndex);

		if (swrlRuleModel.isPresent())
			return swrlRuleModel.get().getRuleName();
		else
			return "<INVALID_INDEX>";
	}

	@NonNull
	public String getSWRLRuleTextByIndex(int ruleIndex)
	{
		Optional<SWRLRuleModel> swrlRuleModel = getSWRLRuleModelByIndex(ruleIndex);

		if (swrlRuleModel.isPresent())
			return swrlRuleModel.get().getRuleText();
		else
			return "<INVALID_INDEX>";
	}

	@NonNull
	public String getSWRLRuleCommentByIndex(int ruleIndex)
	{
		Optional<SWRLRuleModel> swrlRuleModel = getSWRLRuleModelByIndex(ruleIndex);

		if (swrlRuleModel.isPresent())
			return swrlRuleModel.get().getComment();
		else
			return "<INVALID_INDEX>";
	}

	public boolean hasSWRLRule(@NonNull String ruleName)
	{
		return this.swrlRuleModels.containsKey(ruleName);
	}

	public boolean hasBeenModified()
	{
		return this.isModified;
	}

	public void clearModifiedStatus()
	{
		this.isModified = false;
	}

	@Override
	public int getRowCount()
	{
		return this.swrlRuleModels.size();
	}

	@Override
	public int getColumnCount()
	{
		return NUMBER_OF_COLUMNS;
	}

	@NonNull
	@Override
	public String getColumnName(int column)
	{
		if (column == RULE_NAME_COLUMN)
			return "Name";
		else if (column == RULE_TEXT_COLUMN)
			return "Rule";
		else if (column == RULE_COMMENT_COLUMN)
			return "Comment";
		else if (column == ACTIVE_COLUMN)
			return "";
		else
			return "";
	}

	@Nullable
	@Override
	public Object getValueAt(int row, int column)
	{
		Object result = null;

		if ((row < 0 || row >= getRowCount()) || ((column < 0 || column >= getColumnCount())))
			result = "OUT OF BOUNDS";
		else {
			if (column == RULE_TEXT_COLUMN)
				result = ((SWRLRuleModel)this.swrlRuleModels.values().toArray()[row]).getRuleText();
			else if (column == RULE_NAME_COLUMN)
				result = ((SWRLRuleModel)this.swrlRuleModels.values().toArray()[row]).getRuleName();
			else if (column == RULE_COMMENT_COLUMN)
				result = ((SWRLRuleModel)this.swrlRuleModels.values().toArray()[row]).getComment();
			else if (column == ACTIVE_COLUMN)
				result = ((SWRLRuleModel)this.swrlRuleModels.values().toArray()[row]).isActive();
		}
		return result;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		return columnIndex == ACTIVE_COLUMN;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex)
	{
		if (columnIndex == ACTIVE_COLUMN) {
			return Boolean.class;
		} else {
			return super.getColumnClass(columnIndex);
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex)
	{
		if (columnIndex == ACTIVE_COLUMN) {
			((SWRLRuleModel)this.swrlRuleModels.values().toArray()[rowIndex]).setActive((Boolean)aValue);
		} else {
			super.setValueAt(aValue, rowIndex, columnIndex);
		}
	}

	@Override
	public void updateView()
	{
		if (this.view.isPresent()) {
			updateRuleModels();
			this.view.get().update();
		}
	}

	private Optional<SWRLRuleModel> getSWRLRuleModelByIndex(int ruleIndex)
	{
		if (ruleIndex >= 0 && ruleIndex < this.swrlRuleModels.values().size())
			return Optional.of(((SWRLRuleModel)this.swrlRuleModels.values().toArray()[ruleIndex]));
		else
			return Optional.empty();
	}

	private void updateRuleModels()
	{
		this.swrlRuleModels.clear();

		for (SWRLAPIRule swrlapiRule : this.swrlRuleEngine.getSWRLRules()) {
			String ruleName = swrlapiRule.getRuleName();
			String ruleText = swrlRuleRenderer.renderSWRLRule(swrlapiRule);
			String comment = swrlapiRule.getComment();
			SWRLRuleModel swrlRuleModel = new SWRLRuleModel(ruleName, ruleText, comment);
			this.swrlRuleModels.put(ruleName, swrlRuleModel);
		}
	}

	private class SWRLRuleModel
	{
		@NonNull
		private final String ruleName, ruleText, comment;
		private boolean active;

		public SWRLRuleModel(@NonNull String ruleName, @NonNull String ruleText, @NonNull String comment)
		{
			this.active = true;
			this.ruleText = ruleText;
			this.ruleName = ruleName;
			this.comment = comment;
		}

		public void setActive(boolean active)
		{
			this.active = active;
		}

		public boolean isActive()
		{
			return this.active;
		}

		@NonNull
		public String getRuleText()
		{
			return this.ruleText;
		}

		@NonNull
		public String getRuleName()
		{
			return this.ruleName;
		}

		@NonNull
		public String getComment()
		{
			return this.comment;
		}

		@NonNull
		@Override
		public String toString()
		{
			return "(ruleName: " + this.ruleName + ", ruleText: " + this.ruleText + ", comment: " + this.comment
					+ ", active: " + this.active + ")";
		}
	}
}
