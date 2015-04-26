package org.swrlapi.ui.model;

import org.swrlapi.sqwrl.SQWRLQueryEngine;

public interface SQWRLQueryEngineModel extends SWRLRuleEngineModel
{
	/**
	 * @return A SQWRL query engine
	 */
	SQWRLQueryEngine getSQWRLQueryEngine();
}
