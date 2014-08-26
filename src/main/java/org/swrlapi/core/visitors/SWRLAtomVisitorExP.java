package org.swrlapi.core.visitors;

import org.semanticweb.owlapi.model.*;
import org.swrlapi.core.SWRLAPIBuiltInAtom;

/**
 * @see org.semanticweb.owlapi.model.SWRLAtom
 */
public interface SWRLAtomVisitorExP<T, P>
{
	T visit(SWRLClassAtom atom, P p);

	T visit(SWRLObjectPropertyAtom atom, P p);

	T visit(SWRLDataPropertyAtom atom, P p);

	T visit(SWRLSameIndividualAtom atom, P p);

	T visit(SWRLDifferentIndividualsAtom atom, P p);

	T visit(SWRLDataRangeAtom atom, P p);

	T visit(SWRLAPIBuiltInAtom atom, P p);
}