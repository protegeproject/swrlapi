
package org.protege.swrlapi.converters;

import java.util.Set;

import org.protege.owl.portability.swrl.atoms.SWRLClassAtomAdapter;
import org.protege.owl.portability.swrl.atoms.SWRLDataPropertyAtomAdapter;
import org.protege.owl.portability.swrl.atoms.SWRLDataRangeAtomAdapter;
import org.protege.owl.portability.swrl.atoms.SWRLDifferentIndividualsAtomAdapter;
import org.protege.owl.portability.swrl.atoms.SWRLObjectPropertyAtomAdapter;
import org.protege.owl.portability.swrl.atoms.SWRLSameIndividualAtomAdapter;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.protege.swrlapi.ext.SWRLAPIBuiltInAtom;

/**
 * This interface describes methods that can be implemented by a target rule engine to convert SWRL body atoms to native rule clauses.
 * <p>
 * This interface can be used by implementation that need to track variable definitions as each atom is defined.
 * <p>
 * Implementors may also chose an alternate conversion approach.
 */
public interface TargetRuleEngineSWRLBodyAtomWithVariableNamesConverter<T> extends TargetRuleEngineConverter
{
	T convert(SWRLClassAtomAdapter atom, Set<String> variableNames) throws TargetRuleEngineException;

	T convert(SWRLDataPropertyAtomAdapter atom, Set<String> variableNames) throws TargetRuleEngineException;

	T convert(SWRLObjectPropertyAtomAdapter atom, Set<String> variableNames) throws TargetRuleEngineException;

	T convert(SWRLSameIndividualAtomAdapter atom, Set<String> variableNames) throws TargetRuleEngineException;

	T convert(SWRLDifferentIndividualsAtomAdapter atom, Set<String> variableNames) throws TargetRuleEngineException;

	T convert(SWRLAPIBuiltInAtom atom, Set<String> variableNames) throws TargetRuleEngineException;

	T convert(SWRLDataRangeAtomAdapter atom, Set<String> variableNames) throws TargetRuleEngineException;
}
