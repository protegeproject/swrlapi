package org.swrlapi.core.visitors;

import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLVariable;

/**
 * @see org.semanticweb.owlapi.model.SWRLArgument
 */
public interface SWRLArgumentVisitor
{
	void visit(SWRLVariable swrlVariable);

	void visit(SWRLIndividualArgument swrlIndividualArgument);

	void visit(SWRLLiteralArgument swrlLiteralArgument);
}
