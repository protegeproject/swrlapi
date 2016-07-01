package org.swrlapi.visitors;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.exceptions.SWRLBuiltInException;

/**
 * SWRLAPI OWL axiom visitor customized to deal with {@link org.swrlapi.core.SWRLAPIRule}s instead of OWLAPI-based
 * {@link org.swrlapi.core.SWRLAPIRule}s.
 *
 * @see org.swrlapi.core.SWRLAPIRule
 * @see org.semanticweb.owlapi.model.OWLAxiom
 * @see org.semanticweb.owlapi.model.OWLAxiomVisitor
 */
public interface SWRLAPIOWLAxiomVisitor extends OWLAxiomVisitor
{
  void visit(@NonNull SWRLAPIRule swrlapiRule) throws SWRLBuiltInException;
}
