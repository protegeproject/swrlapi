package org.swrlapi.core.arguments;

import org.semanticweb.owlapi.model.SWRLDArgument;

/**
 * Interface representing an argument to a SWRL built-in. This interface represents the primary SWRLAPI extension point
 * for built-in arguments.
 * 
 * @see {@link SWRLClassBuiltInArgument}, {@link SWRLObjectPropertyBuiltInArgument},
 *      {@link SWRLDataPropertyBuiltInArgument}, {@link SWRLDataPropertyBuiltInArgument},
 *      {@link SWRLDatatypeBuiltInArgument}, {@link SWRLLiteralBuiltInArgument}, {@link SQWRLCollectionBuiltInArgument},
 *      {@link SWRLMultiArgument}.
 */
public interface SWRLBuiltInArgument extends SWRLAtomArgument, SWRLDArgument
{
	// TODO These methods should really be in SWRLVariableBuiltInArgument
	void setBuiltInResult(SWRLBuiltInArgument builtInResult);

	SWRLBuiltInArgument getBuiltInResult();

	boolean hasBuiltInResult();

	String getVariableName();

	void setVariableName(String variableName);

	boolean isVariable();

	boolean isUnbound();

	boolean isBound();

	void setUnbound();

	void setBound();

	@Override
	String toDisplayText();
}
