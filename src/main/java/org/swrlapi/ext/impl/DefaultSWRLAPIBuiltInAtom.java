package org.swrlapi.ext.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.swrlapi.core.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.ext.SWRLAPIBuiltInAtom;

import uk.ac.manchester.cs.owl.owlapi.SWRLBuiltInAtomImpl;

public class DefaultSWRLAPIBuiltInAtom extends SWRLBuiltInAtomImpl implements SWRLAPIBuiltInAtom
{
	private static final long serialVersionUID = 1L;

	private final String ruleName;
	private final IRI builtInIRI;
	private final String builtInShortName;
	private List<SWRLBuiltInArgument> arguments;
	private int builtInIndex = -1; // Index of this built-in atom in rule body; left-to-right, first built-in index is 0,
																	// second in 1, and so on
	private Set<String> pathVariableShortNames = new HashSet<String>();
	private boolean sqwrlCollectionResultsUsed = false;

	public DefaultSWRLAPIBuiltInAtom(String ruleName, IRI builtInIRI, String builtInShortName,
			List<SWRLBuiltInArgument> arguments)
	{
		super(builtInIRI, new ArrayList<SWRLDArgument>(arguments));
		this.ruleName = ruleName;
		this.builtInIRI = builtInIRI;
		this.builtInShortName = builtInShortName;
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
	public String getBuiltInShortName()
	{
		return builtInShortName;
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
	public Set<String> getPathVariableShortNames()
	{
		return Collections.unmodifiableSet(this.pathVariableShortNames);
	}

	@Override
	public boolean hasPathVariables()
	{
		return !this.pathVariableShortNames.isEmpty();
	}

	@Override
	public boolean usesAtLeastOneVariableOf(Set<String> variableShortNames)
	{
		Set<String> s = new HashSet<String>(variableShortNames);

		s.retainAll(getArgumentsVariableShortNames());

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
	public Set<String> getUnboundArgumentVariableShortNames()
	{
		Set<String> result = new HashSet<String>();

		for (SWRLBuiltInArgument argument : this.arguments)
			if (argument.isVariable() && argument.asVariable().isUnbound())
				result.add(argument.asVariable().getVariableShortName());

		return Collections.unmodifiableSet(result);
	}

	@Override
	public String getArgumentVariableShortName(int argumentNumber)
	{
		checkArgumentNumber(argumentNumber);

		if (!this.arguments.get(argumentNumber).isVariable())
			throw new RuntimeException("expecting a variable for (0-offset) argument #" + argumentNumber);

		return this.arguments.get(argumentNumber).asVariable().getVariableShortName();
	}

	@Override
	public List<String> getArgumentsVariableShortNames()
	{
		List<String> result = new ArrayList<String>();

		for (SWRLBuiltInArgument argument : this.arguments)
			if (argument.isVariable())
				result.add(argument.asVariable().getVariableShortName());

		return Collections.unmodifiableList(result);
	}

	@Override
	public List<String> getArgumentsShortVariableNamesExceptFirst()
	{
		List<String> result = new ArrayList<String>();
		int argumentCount = 0;

		for (SWRLBuiltInArgument argument : this.arguments)
			if (argument.isVariable() && argumentCount++ != 0)
				result.add(argument.asVariable().getVariableShortName());

		return Collections.unmodifiableList(result);
	}

	@Override
	public void addArguments(List<SWRLBuiltInArgument> additionalArguments)
	{
		this.arguments.addAll(additionalArguments);
	}

	@Override
	public void setPathVariableShortNames(Set<String> variableShortNames)
	{
		this.pathVariableShortNames = new HashSet<String>(variableShortNames);
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
		String result = this.builtInShortName + "(";
		boolean isFirst = true;

		for (SWRLBuiltInArgument argument : getBuiltInArguments()) {
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
