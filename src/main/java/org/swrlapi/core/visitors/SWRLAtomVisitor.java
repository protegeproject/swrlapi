package org.swrlapi.core.visitors;

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
public interface SWRLAtomVisitor
{
  void visit(@NonNull SWRLClassAtom atom);

  void visit(@NonNull SWRLObjectPropertyAtom atom);

  void visit(@NonNull SWRLDataPropertyAtom atom);

  void visit(@NonNull SWRLSameIndividualAtom atom);

  void visit(@NonNull SWRLDifferentIndividualsAtom atom);

  void visit(@NonNull SWRLDataRangeAtom atom);

  void visit(@NonNull SWRLAPIBuiltInAtom atom);
}
