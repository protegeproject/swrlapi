package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.ui.model.SQWRLQueryEngineModel;

public class DefaultSQWRLQueryEngineModel extends DefaultSWRLRuleEngineModel implements SQWRLQueryEngineModel
{
  @NonNull private final SQWRLQueryEngine queryEngine;

  public DefaultSQWRLQueryEngineModel(@NonNull SQWRLQueryEngine sqwrlQueryEngine)
  {
    super(sqwrlQueryEngine);
    this.queryEngine = sqwrlQueryEngine;
  }

  @NonNull @Override
  public SQWRLQueryEngine getSQWRLQueryEngine()
  {
    return this.queryEngine;
  }

	@Override
	public void updateView() { }
}
