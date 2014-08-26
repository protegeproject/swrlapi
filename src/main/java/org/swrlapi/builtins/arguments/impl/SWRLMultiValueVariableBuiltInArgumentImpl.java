package org.swrlapi.builtins.arguments.impl;

import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitor;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument;

/**
 * A class used to bind multiple arguments to a built-in argument
 */
class SWRLMultiValueVariableBuiltInArgumentImpl extends SWRLVariableBuiltInArgumentImpl implements
		SWRLMultiValueVariableBuiltInArgument
{
	private static final long serialVersionUID = 1L;

	private List<SWRLBuiltInArgument> arguments;

	public SWRLMultiValueVariableBuiltInArgumentImpl(IRI variableIRI, String variablePrefixedName)
	{
		super(variableIRI, variablePrefixedName);
		arguments = new ArrayList<SWRLBuiltInArgument>();
	}

	public SWRLMultiValueVariableBuiltInArgumentImpl(IRI variableIRI, String variablePrefixedName,
			List<SWRLBuiltInArgument> arguments)
	{
		super(variableIRI, variablePrefixedName);
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
	public <T> T accept(SWRLBuiltInArgumentVisitorEx<T> visitor)
	{
		return visitor.visit(this);
	}

	@Override
	public void accept(SWRLBuiltInArgumentVisitor visitor)
	{
		visitor.visit(this);
	}
}
