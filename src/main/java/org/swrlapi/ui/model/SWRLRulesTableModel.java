package org.swrlapi.ui.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import org.swrlapi.ui.core.SWRLAPIView;

public class SWRLRulesTableModel extends AbstractTableModel implements SWRLAPIModel
{
	private static final long serialVersionUID = 1L;

	private static int ACTIVE_COLUMN = 0;
	private static int RULE_NAME_COLUMN = 1;
	private static int RULE_TEXT_COLUMN = 2;
	private static int SOURCE_SHEET_NAME_COLUMN = 3;
	private static int NUMBER_OF_COLUMNS = 4;

	private SWRLAPIView view = null;
	private boolean isModified = false;

	private final Map<String, SWRLRuleModel> swrlRuleModels;

	public SWRLRulesTableModel()
	{
		this.swrlRuleModels = new HashMap<String, SWRLRuleModel>();
	}

	public Set<SWRLRuleModel> getSWRLRuleModels()
	{
		return new HashSet<SWRLRuleModel>(swrlRuleModels.values());
	}

	public Set<SWRLRuleModel> getSWRLRuleModels(boolean isActiveFlag)
	{
		Set<SWRLRuleModel> result = new HashSet<SWRLRuleModel>();
		for (SWRLRuleModel swrlRuleModel : swrlRuleModels.values()) {
			if (swrlRuleModel.isActive() == isActiveFlag) {
				result.add(swrlRuleModel);
			}
		}
		return result;
	}

	public boolean hasSWRLRules()
	{
		return !swrlRuleModels.isEmpty();
	}

	public String getSWRLRuleNameByIndex(int ruleIndex)
	{
		if (ruleIndex >= 0 && ruleIndex < swrlRuleModels.values().size())
			return ((SWRLRuleModel)swrlRuleModels.values().toArray()[ruleIndex]).getRuleName();
		else
			return "";
	}

	public boolean hasSWRLRule(String ruleName)
	{
		return swrlRuleModels.containsKey(ruleName);
	}

	public void addSWRLRule(String ruleName, String ruleText, String comment)
	{
		SWRLRuleModel swrlRuleModel = new SWRLRuleModel(ruleName, ruleText, comment);

		if (!swrlRuleModels.containsKey(ruleName))
			swrlRuleModels.put(ruleName, swrlRuleModel);
		isModified = true;
		updateView();
	}

	public void removeSWRLRule(String ruleName)
	{
		if (swrlRuleModels.containsKey(ruleName))
			swrlRuleModels.remove(ruleName);
		isModified = true;
		updateView();
	}

	public void clearSWRLRules()
	{
		this.swrlRuleModels.clear();
		updateView();
		isModified = false;
	}

	public void setView(SWRLAPIView view)
	{
		this.view = view;
	}

	public boolean hasBeenModified()
	{
		return isModified;
	}

	public void clearModifiedStatus()
	{
		isModified = false;
	}

	@Override
	public int getRowCount()
	{
		return swrlRuleModels.size();
	}

	@Override
	public int getColumnCount()
	{
		return NUMBER_OF_COLUMNS;
	}

	@Override
	public String getColumnName(int column)
	{
		if (column == RULE_NAME_COLUMN)
			return new String("Comment");
		else if (column == RULE_TEXT_COLUMN)
			return new String("MM DSL expression");
		else if (column == SOURCE_SHEET_NAME_COLUMN)
			return new String("Sheet name");
		else if (column == ACTIVE_COLUMN)
			return new String("");
		else
			return null;
	}

	@Override
	public Object getValueAt(int row, int column)
	{
		Object result = null;

		if ((row < 0 || row >= getRowCount()) || ((column < 0 || column >= getColumnCount())))
			result = new String("OUT OF BOUNDS");
		else {
			if (column == RULE_TEXT_COLUMN)
				result = ((SWRLRuleModel)swrlRuleModels.values().toArray()[row]).getRuleText();
			else if (column == RULE_NAME_COLUMN)
				result = ((SWRLRuleModel)swrlRuleModels.values().toArray()[row]).getRuleName();
			else if (column == SOURCE_SHEET_NAME_COLUMN)
				result = ((SWRLRuleModel)swrlRuleModels.values().toArray()[row]).getComment();
			else if (column == ACTIVE_COLUMN)
				result = ((SWRLRuleModel)swrlRuleModels.values().toArray()[row]).isActive();
		}

		return result;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		return columnIndex == ACTIVE_COLUMN;
	};

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
			((SWRLRuleModel)swrlRuleModels.values().toArray()[rowIndex]).setActive((Boolean)aValue);
		} else {
			super.setValueAt(aValue, rowIndex, columnIndex);
		}
	}

	private void updateView()
	{
		if (view != null)
			view.update();
	}

	private class SWRLRuleModel
	{
		private final String ruleName, ruleText, comment;
		private boolean active;

		public SWRLRuleModel(String ruleName, String ruleText, String comment)
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
			return active;
		}

		public String getRuleText()
		{
			return ruleText;
		}

		public String getRuleName()
		{
			return ruleName;
		}

		public String getComment()
		{
			return comment;
		}

		@Override
		public String toString()
		{
			return "(ruleName: " + ruleName + ", ruleText: " + ruleText + ", comment: " + comment + ", active: " + active
					+ ")";
		}
	}
}
