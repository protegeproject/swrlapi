package org.swrlapi.bridge.converters;

import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.swrlapi.core.SWRLAPIBuiltInAtom;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This interface describes methods that can be implemented by a target rule engine to convert SWRL head atoms to native
 * rule clauses.
 * <p>
 * Implementors may also chose an alternate conversion approach.
 */
public interface TargetRuleEngineSWRLHeadAtomConverter<T> extends TargetRuleEngineConverter
{
	T convert(SWRLClassAtom atom) throws TargetRuleEngineException;

	T convert(SWRLDataPropertyAtom atom) throws TargetRuleEngineException;

	T convert(SWRLObjectPropertyAtom atom) throws TargetRuleEngineException;

	T convert(SWRLSameIndividualAtom atom) throws TargetRuleEngineException;

	T convert(SWRLDifferentIndividualsAtom atom) throws TargetRuleEngineException;

	T convert(SWRLAPIBuiltInAtom atom) throws TargetRuleEngineException;

	T convert(SWRLDataRangeAtom atom) throws TargetRuleEngineException;
}
