package org.swrlapi.builtins.arguments;

/**
 * @see org.swrlapi.builtins.arguments.SWRLBuiltInArgument
 * @see org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitor
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
