package org.swrlapi.builtins.arguments;

import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;

/**
 * @see org.swrlapi.builtins.arguments.SWRLBuiltInArgument
 */
public interface SWRLBuiltInArgumentVisitor
{
	void visit(SWRLClassBuiltInArgument argument);

	void visit(SWRLNamedIndividualBuiltInArgument argument);

	void visit(SWRLObjectPropertyBuiltInArgument argument);

	void visit(SWRLDataPropertyBuiltInArgument argument);

	void visit(SWRLAnnotationPropertyBuiltInArgument argument);

	void visit(SWRLLiteralBuiltInArgument argument);

	void visit(SWRLDatatypeBuiltInArgument argument);

	void visit(SWRLVariableBuiltInArgument argument);

	void visit(SQWRLCollectionVariableBuiltInArgument argument);

	void visit(SWRLMultiValueVariableBuiltInArgument argument);
}
