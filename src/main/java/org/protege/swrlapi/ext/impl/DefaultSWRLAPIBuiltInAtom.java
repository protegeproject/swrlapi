package org.protege.swrlapi.ext.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.protege.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.protege.swrlapi.ext.SWRLAPIBuiltInAtom;

import uk.ac.manchester.cs.owl.owlapi.SWRLAtomImpl;

/**
 * Class representing a SWRL built-in atom
 */
public class DefaultSWRLAPIBuiltInAtom extends SWRLAtomImpl implements SWRLAPIBuiltInAtom
{
	private final String ruleName;
	private final URI builtInURI;
	private final String builtInPrefixedName;
	private List<SWRLBuiltInArgument> arguments;
	private int builtInIndex = -1; // Index of this built-in atom in rule body; left-to-right, first built-in index is 0,
																	// second in 1, and so on
	private Set<String> pathVariableNames = new HashSet<String>();
	private boolean sqwrlCollectionResultsUsed = false;

	public DefaultSWRLAPIBuiltInAtom(String ruleName, URI builtInURI, String builtInPrefixedName,
			List<SWRLBuiltInArgument> arguments)
	{
		super(new SWRLBuiltInPredicateImpl(builtInPrefixedName));
		this.ruleName = ruleName;
		this.builtInURI = builtInURI;
		this.builtInPrefixedName = builtInPrefixedName;
		this.arguments = arguments;
	}

	public DefaultSWRLAPIBuiltInAtom(String ruleName, URI builtInURI, String builtInPrefixedName)
	{
		super(new SWRLBuiltInPredicateImpl(builtInPrefixedName));
		this.ruleName = ruleName;
		this.builtInURI = builtInURI;
		this.builtInPrefixedName = builtInPrefixedName;
		this.arguments = new ArrayList<SWRLBuiltInArgument>();
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
		return this.builtInPrefixedName;
	}

	@Override
	public URI getBuiltInURI()
	{
		return this.builtInURI;
	}

	@Override
	public List<SWRLBuiltInArgument> getArguments()
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
	public Set<String> getPathVariableNames()
	{
		return Collections.unmodifiableSet(this.pathVariableNames);
	}

	@Override
	public boolean hasPathVariables()
	{
		return !this.pathVariableNames.isEmpty();
	}

	@Override
	public boolean usesAtLeastOneVariableOf(Set<String> variableNames)
	{
		Set<String> s = new HashSet<String>(variableNames);

		s.retainAll(getArgumentsVariableNames());

		return !s.isEmpty();
	}

	@Override
	public boolean isArgumentAVariable(int argumentNumber)
	{
		checkArgumentNumber(argumentNumber);

		return this.arguments.get(argumentNumber).isVariable();
	}

	@Override
	public boolean isArgumentUnbound(int argumentNumber)
	{
		checkArgumentNumber(argumentNumber);

		return this.arguments.get(argumentNumber).isUnbound();
	}

	@Override
	public boolean hasUnboundArguments()
	{
		for (SWRLBuiltInArgument argument : this.arguments)
			if (argument.isUnbound())
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
	public Set<String> getUnboundArgumentVariableNames()
	{
		Set<String> result = new HashSet<String>();

		for (SWRLBuiltInArgument argument : this.arguments)
			if (argument.isUnbound())
				result.add(argument.getVariableName());

		return Collections.unmodifiableSet(result);
	}

	@Override
	public String getArgumentVariableName(int argumentNumber)
	{
		checkArgumentNumber(argumentNumber);

		if (!this.arguments.get(argumentNumber).isVariable())
			throw new RuntimeException("expecting a variable for (0-offset) argument #" + argumentNumber);

		return this.arguments.get(argumentNumber).getVariableName();
	}

	@Override
	public List<String> getArgumentsVariableNames()
	{
		List<String> result = new ArrayList<String>();

		for (SWRLBuiltInArgument argument : this.arguments)
			if (argument.isVariable())
				result.add(argument.getVariableName());

		return Collections.unmodifiableList(result);
	}

	@Override
	public List<String> getArgumentsVariableNamesExceptFirst()
	{
		List<String> result = new ArrayList<String>();
		int argumentCount = 0;

		for (SWRLBuiltInArgument argument : this.arguments)
			if (argument.isVariable() && argumentCount++ != 0)
				result.add(argument.getVariableName());

		return Collections.unmodifiableList(result);
	}

	@Override
	public void addArguments(List<SWRLBuiltInArgument> additionalArguments)
	{
		this.arguments.addAll(additionalArguments);
	}

	@Override
	public void setPathVariableNames(Set<String> variableNames)
	{
		this.pathVariableNames = variableNames;
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

	@Override
	public String toString()
	{
		return toDisplayText();
	}

	@Override
	public String toDisplayText()
	{
		String result = this.builtInPrefixedName + "(";
		boolean isFirst = true;

		for (SWRLBuiltInArgument argument : getArguments()) {
			if (!isFirst)
				result += ", ";
			result += argument.toDisplayText();
			isFirst = false;
		}
		result += ")";

		return result;
	}

	private void checkArgumentNumber(int argumentNumber)
	{
		if (argumentNumber < 0 || argumentNumber > this.arguments.size())
			throw new RuntimeException("invalid (0-offset) argument #" + argumentNumber);
	}
}
