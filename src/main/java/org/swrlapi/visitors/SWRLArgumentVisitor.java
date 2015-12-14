package org.swrlapi.visitors;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLVariable;

/**
 * @see org.semanticweb.owlapi.model.SWRLArgument
 */
public interface SWRLArgumentVisitor
{
  void visit(@NonNull SWRLVariable swrlVariable);

  void visit(@NonNull SWRLIndividualArgument swrlIndividualArgument);

  void visit(@NonNull SWRLLiteralArgument swrlLiteralArgument);
}
