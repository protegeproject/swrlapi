package org.swrlapi.core.arguments;

import org.semanticweb.owlapi.model.SWRLDArgument;

/**
 * Interface representing an argument to a SWRL built-in. It extends the {@link SWRLDArgument} interface, which is the
 * SWRLAPI's primary OWLAPI extension point. The {@link SWRLBuiltInArgument} interface represents SWRL built-in atoms in
 * the SWRLAPI, which has a wider range of built-in argument types than the OWLAPI. The OWLAPI envisions built-in
 * arguments as simple literals or variables only. In addition to literals and variables, the SWRLAPI allows OWL named
 * objects (classes, named individuals, properties, datatypes) as well as SQWRL-specific arguments.
 * 
 * @see SWRLLiteralBuiltInArgument, SWRLVariableBuiltInArgument, SWRLClassBuiltInArgument,
 *      SWRLNamedIndividualBuiltInArgument, SWRLObjectPropertyBuiltInArgument, SWRLDataPropertyBuiltInArgument,
 *      SWRLDataPropertyBuiltInArgument, SWRLAnnotationPropertyBuiltInArgument, SWRLDatatypeBuiltInArgument,
 *      SWRLMultiValueBuiltInArgument, SQWRLCollectionBuiltInArgument
 */
public interface SWRLBuiltInArgument extends SWRLDArgument
{ // TODO These methods should really be in SWRLVariableBuiltInArgument
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

	String toDisplayText();
}
