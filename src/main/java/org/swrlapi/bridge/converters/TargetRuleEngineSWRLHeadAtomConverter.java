package org.swrlapi.bridge.converters;

import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.swrlapi.core.SWRLAPIBuiltInAtom;

/**
 * This interface describes methods that can be implemented by a target rule engine to convert SWRL head atoms to native
 * rule clauses.
 * <p>
 * Implementors may also chose an alternate conversion approach.
 *
 * @see org.semanticweb.owlapi.model.SWRLAtom
 */
public interface TargetRuleEngineSWRLHeadAtomConverter<T> extends TargetRuleEngineConverter
{
  T convert(SWRLClassAtom atom);

  T convert(SWRLDataPropertyAtom atom);

  T convert(SWRLObjectPropertyAtom atom);

  T convert(SWRLSameIndividualAtom atom);

  T convert(SWRLDifferentIndividualsAtom atom);

  T convert(SWRLAPIBuiltInAtom atom);

  T convert(SWRLDataRangeAtom atom);
}
