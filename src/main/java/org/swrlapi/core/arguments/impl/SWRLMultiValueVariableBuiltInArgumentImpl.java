package org.swrlapi.core.arguments.impl;

import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.swrlapi.core.arguments.SWRLMultiValueVariableBuiltInArgument;

/**
 * A class used to bind multiple arguments to a built-in argument
 */
class SWRLMultiValueVariableBuiltInArgumentImpl extends SWRLVariableBuiltInArgumentImpl implements
		SWRLMultiValueVariableBuiltInArgument
{
	private static final long serialVersionUID = 1L;

	private List<SWRLBuiltInArgument> arguments;

	public SWRLMultiValueVariableBuiltInArgumentImpl(IRI variableIRI, String variableShortName)
	{
		super(variableIRI, variableShortName);
		arguments = new ArrayList<SWRLBuiltInArgument>();
	}

	public SWRLMultiValueVariableBuiltInArgumentImpl(IRI variableIRI, String variableShortName,
			List<SWRLBuiltInArgument> arguments)
	{
		super(variableIRI, variableShortName);
		this.arguments = arguments;
	}

	@Override
	public SWRLMultiValueVariableBuiltInArgument asMultiValueVariable()
	{
		return this;
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
	public int getNumberOfArguments()
	{
		return arguments.size();
	}

	@Override
	public boolean hasNoArguments()
	{
		return arguments.size() == 0;
	}

	@Override
	public String toDisplayText()
	{
		throw new RuntimeException("Not implemented");
	}
}
