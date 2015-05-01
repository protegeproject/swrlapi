package org.swrlapi.core.visitors;

import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLVariable;

/**
 * @see org.semanticweb.owlapi.model.SWRLArgument
 */
public interface SWRLArgumentVisitorEx<T>
{
  T visit(SWRLVariable swrlVariable);

  T visit(SWRLIndividualArgument swrlIndividualArgument);

  T visit(SWRLLiteralArgument swrlLiteralArgument);
}