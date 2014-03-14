package org.swrlapi.core.arguments;

import org.semanticweb.owlapi.model.SWRLDArgument;

/**
 * Interface representing an argument to a SWRL built-in. This interface represents the primary SWRLAPI extension point
 * of the OWLAPI to represent SWRL built-in atoms. The OWLAPI envisions built-in arguments as simple literals only. The
 * SWRLAPI al classes, properties, datatypes, individuals, as well as SQWRL-specific arguments.
 * 
 * @see {@link SWRLClassBuiltInArgument}, {@link SWRLIndividualBuiltInArgument},
 *      {@link SWRLObjectPropertyBuiltInArgument}, {@link SWRLDataPropertyBuiltInArgument},
 *      {@link SWRLDataPropertyBuiltInArgument}, {@link SWRLAnnotationPropertyBuiltInArgument},
 *      {@link SWRLDatatypeBuiltInArgument}, {@link SWRLLiteralBuiltInArgument} , {@link SQWRLCollectionBuiltInArgument}
 *      , {@link SWRLVariableBuiltInArgument}, {@link SWRLMultiArgument}.
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
}
