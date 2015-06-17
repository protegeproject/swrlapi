package org.swrlapi.factory;

import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.ui.model.SQWRLQueryEngineModel;

import checkers.nullness.quals.NonNull;

public class DefaultSQWRLQueryEngineModel extends DefaultSWRLRuleEngineModel implements SQWRLQueryEngineModel
{
	@NonNull
	private final SQWRLQueryEngine queryEngine;

	public DefaultSQWRLQueryEngineModel(@NonNull SQWRLQueryEngine sqwrlQueryEngine)
	{
		super(sqwrlQueryEngine);
		this.queryEngine = sqwrlQueryEngine;
	}

	@NonNull
	@Override
	public SQWRLQueryEngine getSQWRLQueryEngine()
	{
		return this.queryEngine;
	}
}
