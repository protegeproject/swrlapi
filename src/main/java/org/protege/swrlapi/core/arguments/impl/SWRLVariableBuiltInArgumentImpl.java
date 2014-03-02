package org.protege.swrlapi.core.arguments.impl;

import org.protege.swrlapi.core.arguments.SWRLVariableBuiltInArgument;

class SWRLVariableBuiltInArgumentImpl extends SWRLBuiltInArgumentImpl implements SWRLVariableBuiltInArgument
{
	private static final long serialVersionUID = 6661536795353536908L;

	public SWRLVariableBuiltInArgumentImpl(String variableName)
	{
		setVariableName(variableName);
	}
}
