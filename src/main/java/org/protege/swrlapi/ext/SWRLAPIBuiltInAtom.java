package org.protege.swrlapi.ext;

import java.net.URI;
import java.util.List;
import java.util.Set;

import org.protege.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;

public interface SWRLAPIBuiltInAtom extends SWRLBuiltInAtom
{
	String getRuleName();

	String getBuiltInPrefixedName();

	URI getBuiltInURI();

	int getBuiltInIndex();

	void setBuiltInIndex(int builtInIndex);

	boolean usesAtLeastOneVariableOf(Set<String> variableNames);

	@Override
	List<SWRLBuiltInArgument> getArguments(); // TODO Rename to avoid OWLAPI collision

	int getNumberOfArguments();

	boolean isArgumentAVariable(int argumentNumber);

	boolean isArgumentUnbound(int argumentNumber);

	boolean hasUnboundArguments();

	boolean hasVariableArguments();

	Set<String> getUnboundArgumentVariableNames();

	String getArgumentVariableName(int argumentNumber);

	List<String> getArgumentsVariableNames();

	List<String> getArgumentsVariableNamesExceptFirst();

	// SQWRL-related methods

	void setBuiltInArguments(List<SWRLBuiltInArgument> arguments);

	void addArguments(List<SWRLBuiltInArgument> additionalArguments);

	void setPathVariableNames(Set<String> variableNames);

	boolean hasPathVariables();

	Set<String> getPathVariableNames(); // Indicates variables that this built-in atom depends on (directly or indirectly)

	void setUsesSQWRLCollectionResults();

	boolean usesSQWRLCollectionResults();
}
