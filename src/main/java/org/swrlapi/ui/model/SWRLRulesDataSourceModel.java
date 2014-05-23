package org.swrlapi.ui.model;

import org.swrlapi.ui.core.Model;
import org.swrlapi.ui.core.SWRLRulesDataSource;
import org.swrlapi.ui.core.View;

public class SWRLRulesDataSourceModel implements Model
{
	private View view = null;
	private SWRLRulesDataSource swrlRulesDataSource = null;
	private String fileName = null;

	public SWRLRulesDataSourceModel(SWRLRulesDataSource dataSource)
	{
		this.swrlRulesDataSource = dataSource;
	}

	public void setView(View view)
	{
		this.view = view;
	}

	public void setSWRLRulesDataSource(SWRLRulesDataSource swrlRulesDataSource)
	{
		this.swrlRulesDataSource = swrlRulesDataSource;
		updateView();
	}

	public boolean hasSWRLRulesDataSource()
	{
		return this.swrlRulesDataSource != null;
	}

	public void clearSWRLRulesDataSource()
	{
		this.swrlRulesDataSource = null;
		updateView();
	}

	public SWRLRulesDataSource getSWRLRulesDataSource()
	{
		return swrlRulesDataSource;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
		updateView();
	}

	public void clearFileName()
	{
		fileName = null;
		updateView();
	}

	public boolean hasFileName()
	{
		return fileName != null;
	}

	public String getFileName()
	{
		return fileName;
	}

	private void updateView()
	{
		if (view != null)
			view.update();
	}
}
