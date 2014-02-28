package org.protege.swrlapi.core.arguments.impl;

import org.protege.swrlapi.core.arguments.SWRLVariableBuiltInArgument;

class SWRLVariableBuiltInArgumentImpl extends SWRLBuiltInArgumentImpl implements SWRLVariableBuiltInArgument
{
	public SWRLVariableBuiltInArgumentImpl(String variableName)
	{
		setVariableName(variableName);
	}
}
