package org.swrlapi.visitors;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.swrlapi.core.SWRLAPIBuiltInAtom;

/**
 * @see org.semanticweb.owlapi.model.SWRLAtom
 */
public interface SWRLAtomVisitorEx<T>
{
  @NonNull T visit(@NonNull SWRLClassAtom atom);

  @NonNull T visit(@NonNull SWRLObjectPropertyAtom atom);

  @NonNull T visit(@NonNull SWRLDataPropertyAtom atom);

  @NonNull T visit(@NonNull SWRLSameIndividualAtom atom);

  @NonNull T visit(@NonNull SWRLDifferentIndividualsAtom atom);

  @NonNull T visit(@NonNull SWRLDataRangeAtom atom);

  @NonNull T visit(@NonNull SWRLAPIBuiltInAtom atom);
}
