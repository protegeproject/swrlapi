package org.swrlapi.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.core.SWRLAPIBuiltInAtom;

import org.swrlapi.exceptions.SWRLBuiltInException;
import uk.ac.manchester.cs.owl.owlapi.SWRLBuiltInAtomImpl;

class DefaultSWRLAPIBuiltInAtom extends SWRLBuiltInAtomImpl implements SWRLAPIBuiltInAtom
{
	private static final long serialVersionUID = 1L;

	private final String ruleName;
	private final IRI builtInIRI;
	private final String builtInPrefixedName;
	private List<SWRLBuiltInArgument> arguments;
	private int builtInIndex = -1; // Index of this built-in atom in rule body; left-to-right, first built-in index is 0,
																	// second in 1, and so on
	private Set<String> pathVariablePrefixedNames = new HashSet<String>();
	private boolean sqwrlCollectionResultsUsed = false;

	public DefaultSWRLAPIBuiltInAtom(String ruleName, IRI builtInIRI, String builtInPrefixedName,
			List<SWRLBuiltInArgument> arguments)
	{
		super(builtInIRI, new ArrayList<SWRLDArgument>(arguments));
		this.ruleName = ruleName;
		this.builtInIRI = builtInIRI;
		this.builtInPrefixedName = builtInPrefixedName;
		this.arguments = arguments;
	}

	@Override
	public String getRuleName()
	{
		return this.ruleName;
	}

	@Override
	public void setBuiltInArguments(List<SWRLBuiltInArgument> arguments)
	{
		this.arguments = arguments;
	}

	@Override
	public String getBuiltInPrefixedName()
	{
		return builtInPrefixedName;
	}

	@Override
	public IRI getBuiltInIRI()
	{
		return this.builtInIRI;
	}

	@Override
	public List<SWRLBuiltInArgument> getBuiltInArguments()
	{
		return Collections.unmodifiableList(this.arguments);
	}

	@Override
	public int getNumberOfArguments()
	{
		return this.arguments.size();
	}

	@Override
	public int getBuiltInIndex()
	{
		return this.builtInIndex;
	}

	@Override
	public void setBuiltInIndex(int builtInIndex)
	{
		this.builtInIndex = builtInIndex;
	}

	@Override
	public Set<String> getPathVariablePrefixedNames()
	{
		return Collections.unmodifiableSet(this.pathVariablePrefixedNames);
	}

	@Override
	public boolean hasPathVariables()
	{
		return !this.pathVariablePrefixedNames.isEmpty();
	}

	@Override
	public boolean usesAtLeastOneVariableOf(Set<String> variablePrefixedNames)
	{
		Set<String> s = new HashSet<String>(variablePrefixedNames);

		s.retainAll(getArgumentsVariablePrefixedNames());

		return !s.isEmpty();
	}

	@Override
	public boolean isArgumentAVariable(int argumentNumber)
	{
		checkArgumentNumber(argumentNumber);

		return this.arguments.get(argumentNumber) instanceof SWRLVariableBuiltInArgument;
	}

	@Override
	public boolean isArgumentUnbound(int argumentNumber)
	{
		checkArgumentNumber(argumentNumber);

		return this.arguments.get(argumentNumber).isVariable()
				&& this.arguments.get(argumentNumber).asVariable().isUnbound();
	}

	@Override
	public boolean hasUnboundArguments()
	{
		for (SWRLBuiltInArgument argument : this.arguments)
			if (argument.isVariable() && argument.asVariable().isUnbound())
				return true;
		return false;
	}

	@Override
	public boolean hasVariableArguments()
	{
		for (SWRLBuiltInArgument argument : this.arguments)
			if (argument.isVariable())
				return true;
		return false;
	}

	@Override
	public Set<String> getUnboundArgumentVariablePrefixedNames()
	{
		Set<String> result = new HashSet<String>();

		for (SWRLBuiltInArgument argument : this.arguments)
			if (argument.isVariable() && argument.asVariable().isUnbound())
				result.add(argument.asVariable().getVariablePrefixedName());

		return Collections.unmodifiableSet(result);
	}

	@Override
	public String getArgumentVariablePrefixedName(int argumentNumber)
	{
		checkArgumentNumber(argumentNumber);

		if (!this.arguments.get(argumentNumber).isVariable())
			throw new SWRLBuiltInException("expecting a variable for (0-offset) argument #" + argumentNumber);

		return this.arguments.get(argumentNumber).asVariable().getVariablePrefixedName();
	}

	@Override
	public List<String> getArgumentsVariablePrefixedNames()
	{
		List<String> result = new ArrayList<String>();

		for (SWRLBuiltInArgument argument : this.arguments)
			if (argument.isVariable())
				result.add(argument.asVariable().getVariablePrefixedName());

		return Collections.unmodifiableList(result);
	}

	@Override
	public List<String> getArgumentsShortVariableNamesExceptFirst()
	{
		List<String> result = new ArrayList<String>();
		int argumentCount = 0;

		for (SWRLBuiltInArgument argument : this.arguments)
			if (argument.isVariable() && argumentCount++ != 0)
				result.add(argument.asVariable().getVariablePrefixedName());

		return Collections.unmodifiableList(result);
	}

	@Override
	public void addArguments(List<SWRLBuiltInArgument> additionalArguments)
	{
		this.arguments.addAll(additionalArguments);
	}

	@Override
	public void setPathVariablePrefixedNames(Set<String> variablePrefixedNames)
	{
		this.pathVariablePrefixedNames = new HashSet<String>(variablePrefixedNames);
	}

	@Override
	public void setUsesSQWRLCollectionResults()
	{
		this.sqwrlCollectionResultsUsed = true;
	}

	@Override
	public boolean usesSQWRLCollectionResults()
	{
		return this.sqwrlCollectionResultsUsed;
	}

	private void checkArgumentNumber(int argumentNumber)
	{
		if (argumentNumber < 0 || argumentNumber > this.arguments.size())
			throw new SWRLBuiltInException("invalid (0-offset) argument #" + argumentNumber);
	}
}
