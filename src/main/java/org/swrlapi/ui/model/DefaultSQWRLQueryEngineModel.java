package org.swrlapi.ui.model;

import org.swrlapi.sqwrl.SQWRLQueryEngine;

public class DefaultSQWRLQueryEngineModel extends DefaultSWRLRuleEngineModel implements SQWRLQueryEngineModel
{
	private final SQWRLQueryEngine queryEngine;

	public DefaultSQWRLQueryEngineModel(SQWRLQueryEngine sqwrlQueryEngine)
	{
		super(sqwrlQueryEngine);
		this.queryEngine = sqwrlQueryEngine;
	}

	@Override
	public SQWRLQueryEngine getSQWRLQueryEngine()
	{
		return this.queryEngine;
	}
}
