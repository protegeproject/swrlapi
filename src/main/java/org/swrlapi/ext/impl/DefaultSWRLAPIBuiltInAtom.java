package org.swrlapi.ext.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLObjectVisitor;
import org.semanticweb.owlapi.model.SWRLObjectVisitorEx;
import org.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.swrlapi.ext.SWRLAPIBuiltInAtom;

import uk.ac.manchester.cs.owl.owlapi.SWRLBuiltInAtomImpl;

/**
 * Class representing a SWRL built-in atom
 */
public class DefaultSWRLAPIBuiltInAtom extends SWRLBuiltInAtomImpl implements SWRLAPIBuiltInAtom
{
	private static final long serialVersionUID = 1L;

	private final String ruleName;
	private final IRI builtInIRI;
	private final String builtInShortName;
	private List<SWRLBuiltInArgument> arguments;
	private int builtInIndex = -1; // Index of this built-in atom in rule body; left-to-right, first built-in index is 0,
																	// second in 1, and so on
	private Set<String> pathVariableNames = new HashSet<String>();
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
	public String getBuiltInPrefixedName()
	{
		throw new RuntimeException("Not implemented");
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

	@Override
	public void accept(SWRLObjectVisitor visitor)
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public <O> O accept(SWRLObjectVisitorEx<O> visitor)
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLEntity> getSignature()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLAnonymousIndividual> getAnonymousIndividuals()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLClass> getClassesInSignature()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLDataProperty> getDataPropertiesInSignature()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLObjectProperty> getObjectPropertiesInSignature()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLNamedIndividual> getIndividualsInSignature()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLDatatype> getDatatypesInSignature()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLClassExpression> getNestedClassExpressions()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void accept(OWLObjectVisitor visitor)
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public <O> O accept(OWLObjectVisitorEx<O> visitor)
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public boolean isTopEntity()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public boolean isBottomEntity()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public int compareTo(OWLObject o)
	{
		throw new RuntimeException("Not implemented");
	}

	private void checkArgumentNumber(int argumentNumber)
	{
		if (argumentNumber < 0 || argumentNumber > this.arguments.size())
			throw new RuntimeException("invalid (0-offset) argument #" + argumentNumber);
	}
}
