package org.swrlapi.builtins.arguments;

/**
 * @see SWRLBuiltInArgument
 */
public interface SWRLBuiltInArgumentVisitorEx<T>
{
	T visit(SWRLClassBuiltInArgument argument);

	T visit(SWRLNamedIndividualBuiltInArgument argument);

	T visit(SWRLObjectPropertyBuiltInArgument argument);

	T visit(SWRLDataPropertyBuiltInArgument argument);

	T visit(SWRLAnnotationPropertyBuiltInArgument argument);

	T visit(SWRLDatatypeBuiltInArgument argument);

	T visit(SWRLLiteralBuiltInArgument argument);

	T visit(SWRLVariableBuiltInArgument argument);

	T visit(SWRLMultiValueVariableBuiltInArgument argument);

	T visit(SQWRLCollectionVariableBuiltInArgument argument);
}
