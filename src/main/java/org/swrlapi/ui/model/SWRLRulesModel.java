package org.swrlapi.ui.model;

import java.util.HashSet;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import org.swrlapi.ui.core.Model;
import org.swrlapi.ui.core.SWRLRuleModel;
import org.swrlapi.ui.core.View;

public class SWRLRulesModel extends AbstractTableModel implements Model
{
	private static final long serialVersionUID = 1L;

	private static int ACTIVE_COLUMN = 0;
	private static int RULE_NAME_COLUMN = 1;
	private static int RULE_TEXT_COLUMN = 2;
	private static int SOURCE_SHEET_NAME_COLUMN = 3;
	private static int NUMBER_OF_COLUMNS = 4;

	private View view = null;
	private boolean isModified = false;

	private Set<SWRLRuleModel> swrlRuleModels;

	public SWRLRulesModel(Set<SWRLRuleModel> swrlRuleModels)
	{
		this.swrlRuleModels = swrlRuleModels;
	}

	public SWRLRulesModel()
	{
		this.swrlRuleModels = new HashSet<SWRLRuleModel>();
	}

	public Set<SWRLRuleModel> getSWRLRuleModels()
	{
		return new HashSet<SWRLRuleModel>(swrlRuleModels);
	}

	public Set<SWRLRuleModel> getSWRLRuleModels(boolean isActiveFlag)
	{
		Set<SWRLRuleModel> res = new HashSet<SWRLRuleModel>();
		for (SWRLRuleModel expr : swrlRuleModels) {
			if (expr.isActive() == isActiveFlag) {
				res.add(expr);
			}
		}
		return res;
	}

	public boolean hasSWRLRuleModels()
	{
		return !swrlRuleModels.isEmpty();
	}

	public boolean hasSWRLRuleModel(SWRLRuleModel swrlRuleModel)
	{
		return swrlRuleModels.contains(swrlRuleModel);
	}

	public void setSWRLRuleModels(Set<SWRLRuleModel> swrlRuleModels)
	{
		this.swrlRuleModels = new HashSet<SWRLRuleModel>(swrlRuleModels);
		updateView();
	}

	public void addSWRLRuleModel(SWRLRuleModel swrlRuleModel)
	{
		if (!swrlRuleModels.contains(swrlRuleModel))
			swrlRuleModels.add(swrlRuleModel);
		isModified = true;
		updateView();
	}

	public void removeSWRLRuleModel(SWRLRuleModel swrlRuleModel)
	{
		if (swrlRuleModels.contains(swrlRuleModel))
			swrlRuleModels.remove(swrlRuleModel);
		isModified = true;
		updateView();
	}

	public void clearSWRLRuleModels()
	{
		swrlRuleModels = new HashSet<SWRLRuleModel>();
		updateView();
		isModified = false;
	}

	public void setView(View view)
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
				result = ((SWRLRuleModel)swrlRuleModels.toArray()[row]).getRuleText();
			else if (column == RULE_NAME_COLUMN)
				result = ((SWRLRuleModel)swrlRuleModels.toArray()[row]).getRuleName();
			else if (column == SOURCE_SHEET_NAME_COLUMN)
				result = ((SWRLRuleModel)swrlRuleModels.toArray()[row]).getComment();
			else if (column == ACTIVE_COLUMN)
				result = ((SWRLRuleModel)swrlRuleModels.toArray()[row]).isActive();
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
			((SWRLRuleModel)swrlRuleModels.toArray()[rowIndex]).setActive((Boolean)aValue);
		} else {
			super.setValueAt(aValue, rowIndex, columnIndex);
		}
	}

	private void updateView()
	{
		if (view != null)
			view.update();
	}
}
