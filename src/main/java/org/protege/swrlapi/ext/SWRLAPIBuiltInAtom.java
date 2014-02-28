package org.protege.swrlapi.ext;

import java.net.URI;
import java.util.List;
import java.util.Set;

import org.protege.owl.portability.swrl.atoms.SWRLBuiltInAtomAdapter;
import org.protege.swrlapi.core.arguments.SWRLBuiltInArgument;

public interface SWRLAPIBuiltInAtom extends SWRLBuiltInAtomAdapter
{
	String getRuleName();

	String getBuiltInPrefixedName();

	URI getBuiltInURI();

	int getBuiltInIndex();

	void setBuiltInIndex(int builtInIndex);

	boolean usesAtLeastOneVariableOf(Set<String> variableNames);

	List<SWRLBuiltInArgument> getArguments();

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
