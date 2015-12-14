package org.swrlapi.visitors;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLVariable;

/**
 * @see org.semanticweb.owlapi.model.SWRLArgument
 */
public interface SWRLArgumentVisitorEx<T>
{
  @NonNull T visit(@NonNull SWRLVariable swrlVariable);

  @NonNull T visit(@NonNull SWRLIndividualArgument swrlIndividualArgument);

  @NonNull T visit(@NonNull SWRLLiteralArgument swrlLiteralArgument);
}