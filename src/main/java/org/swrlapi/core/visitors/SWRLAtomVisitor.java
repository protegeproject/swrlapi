package org.swrlapi.core.visitors;

import org.semanticweb.owlapi.model.*;
import org.swrlapi.core.SWRLAPIBuiltInAtom;

/**
 * @see org.semanticweb.owlapi.model.SWRLAtom
 */
public interface SWRLAtomVisitor
{
  void visit(SWRLClassAtom atom);

  void visit(SWRLObjectPropertyAtom atom);

  void visit(SWRLDataPropertyAtom atom);

  void visit(SWRLSameIndividualAtom atom);

  void visit(SWRLDifferentIndividualsAtom atom);

  void visit(SWRLDataRangeAtom atom);

  void visit(SWRLAPIBuiltInAtom atom);
}
