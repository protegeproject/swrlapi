package org.protege.swrlapi.core.arguments.impl;

import java.util.ArrayList;
import java.util.List;

import org.protege.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLMultiArgument;

/**
 * A class used to bind multiple arguments to a built-in argument
 */
class SWRLMultiArgumentImpl extends SWRLBuiltInArgumentImpl implements SWRLMultiArgument
{
	private List<SWRLBuiltInArgument> arguments;

	public SWRLMultiArgumentImpl()
	{
		arguments = new ArrayList<SWRLBuiltInArgument>();
	}

	public SWRLMultiArgumentImpl(List<SWRLBuiltInArgument> arguments)
	{
		this.arguments = arguments;
	}

	@Override
	public void addArgument(SWRLBuiltInArgument argument)
	{
		arguments.add(argument);
	}

	@Override
	public void setArguments(List<SWRLBuiltInArgument> arguments)
	{
		this.arguments = arguments;
	}

	@Override
	public List<SWRLBuiltInArgument> getArguments()
	{
		return arguments;
	}

	@Override
	public void setVariableName(String variableName)
	{
		super.setVariableName(variableName);

		for (SWRLBuiltInArgument argument : arguments)
			argument.setVariableName(variableName);
	}

	@Override
	public int getNumberOfArguments()
	{
		return arguments.size();
	}

	@Override
	public boolean hasNoArguments()
	{
		return arguments.size() == 0;
	}

	// TODO fix
	@Override
	public int compareTo(SWRLBuiltInArgument argument)
	{
		return getVariableName().compareTo(((SWRLMultiArgumentImpl)argument).getVariableName());
	}
}
