package org.swrlapi.converters;

import java.util.Set;

import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.swrlapi.exceptions.TargetRuleEngineException;
import org.swrlapi.ext.SWRLAPIBuiltInAtom;

/**
 * This interface describes methods that can be implemented by a target rule engine to convert SWRL body atoms to native
 * rule clauses.
 * <p>
 * This interface can be used by implementation that need to track variable definitions as each atom is defined.
 * <p>
 * Implementors may also chose an alternate conversion approach.
 */
public interface TargetRuleEngineSWRLBodyAtomWithVariableNamesConverter<T> extends TargetRuleEngineConverter
{
	T convert(SWRLClassAtom atom, Set<String> variableNames) throws TargetRuleEngineException;

	T convert(SWRLDataPropertyAtom atom, Set<String> variableNames) throws TargetRuleEngineException;

	T convert(SWRLObjectPropertyAtom atom, Set<String> variableNames) throws TargetRuleEngineException;

	T convert(SWRLSameIndividualAtom atom, Set<String> variableNames) throws TargetRuleEngineException;

	T convert(SWRLDifferentIndividualsAtom atom, Set<String> variableNames) throws TargetRuleEngineException;

	T convert(SWRLAPIBuiltInAtom atom, Set<String> variableNames) throws TargetRuleEngineException;

	T convert(SWRLDataRangeAtom atom, Set<String> variableNames) throws TargetRuleEngineException;
}
