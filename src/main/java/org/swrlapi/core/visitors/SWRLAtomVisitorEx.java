package org.swrlapi.core.visitors;

import org.semanticweb.owlapi.model.*;
import org.swrlapi.core.SWRLAPIBuiltInAtom;

/**
 * @see org.semanticweb.owlapi.model.SWRLAtom
 */
public interface SWRLAtomVisitorEx<T>
{
	T visit(SWRLClassAtom atom);

	T visit(SWRLObjectPropertyAtom atom);

  T visit(SWRLDataPropertyAtom atom);

	T visit(SWRLSameIndividualAtom atom);

	T visit(SWRLDifferentIndividualsAtom atom);

	T visit(SWRLDataRangeAtom atom);

	T visit(SWRLAPIBuiltInAtom atom);
}
