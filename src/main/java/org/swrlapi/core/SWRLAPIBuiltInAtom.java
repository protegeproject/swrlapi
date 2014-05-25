package org.swrlapi.core;

import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;

/**
 * The SWRLAPI's built-in atom extends the OWLAPI's built-in atom with additional functionality. In addition to the
 * {@link SWRLBuiltInArgument} class, this interface is the SWRLAPI's primary OWLAPI extension point.
 * 
 * @see SWRLBuiltInArgument
 */
public interface SWRLAPIBuiltInAtom extends SWRLBuiltInAtom
{
	String getBuiltInPrefixedName();

	IRI getBuiltInIRI();

	int getBuiltInIndex();

	void setBuiltInIndex(int builtInIndex);

	boolean usesAtLeastOneVariableOf(Set<String> variablePrefixedNames);

	List<SWRLBuiltInArgument> getBuiltInArguments();

	int getNumberOfArguments();

	boolean isArgumentAVariable(int argumentNumber);

	boolean isArgumentUnbound(int argumentNumber);

	boolean hasUnboundArguments();

	boolean hasVariableArguments();

	Set<String> getUnboundArgumentVariablePrefixedNames();

	String getArgumentVariablePrefixedName(int argumentNumber);

	List<String> getArgumentsVariablePrefixedNames();

	List<String> getArgumentsShortVariableNamesExceptFirst();

	String getRuleName();

	// SQWRL-related methods

	void setBuiltInArguments(List<SWRLBuiltInArgument> arguments);

	void addArguments(List<SWRLBuiltInArgument> additionalArguments);

	void setPathVariablePrefixedNames(Set<String> variablePrefixedNames);

	boolean hasPathVariables();

	Set<String> getPathVariablePrefixedNames(); // Indicates variables that this built-in atom depends on (directly or
	// indirectly)

	void setUsesSQWRLCollectionResults();

	boolean usesSQWRLCollectionResults();
}
