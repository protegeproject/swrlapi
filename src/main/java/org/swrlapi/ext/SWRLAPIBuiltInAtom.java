package org.swrlapi.ext;

import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.swrlapi.core.arguments.SWRLBuiltInArgument;

/**
 * The SWRLAPI's built-in atom extends the OWLAPI's built-in atom with additional functionality. In addition to the
 * {@link SWRLBuiltInArgument} class, this interface is the SWRLAPI's primary OWLAPI extension point.
 * 
 * @see SWRLBuiltInArgument
 */
public interface SWRLAPIBuiltInAtom extends SWRLBuiltInAtom
{
	String getBuiltInShortName();

	IRI getBuiltInIRI();

	int getBuiltInIndex();

	void setBuiltInIndex(int builtInIndex);

	boolean usesAtLeastOneVariableOf(Set<String> variableNames);

	List<SWRLBuiltInArgument> getBuiltInArguments();

	int getNumberOfArguments();

	boolean isArgumentAVariable(int argumentNumber);

	boolean isArgumentUnbound(int argumentNumber);

	boolean hasUnboundArguments();

	boolean hasVariableArguments();

	Set<String> getUnboundArgumentVariableNames();

	String getArgumentVariableName(int argumentNumber);

	List<String> getArgumentsVariableNames();

	List<String> getArgumentsVariableNamesExceptFirst();

	String getRuleName();

	// SQWRL-related methods

	void setBuiltInArguments(List<SWRLBuiltInArgument> arguments);

	void addArguments(List<SWRLBuiltInArgument> additionalArguments);

	void setPathVariableNames(Set<String> variableNames);

	boolean hasPathVariables();

	Set<String> getPathVariableNames(); // Indicates variables that this built-in atom depends on (directly or indirectly)

	void setUsesSQWRLCollectionResults();

	boolean usesSQWRLCollectionResults();

	String toDisplayText();
}
