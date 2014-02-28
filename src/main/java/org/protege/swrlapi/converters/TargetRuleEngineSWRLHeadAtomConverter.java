
package org.protege.swrlapi.converters;

import org.protege.owl.portability.swrl.atoms.SWRLClassAtomAdapter;
import org.protege.owl.portability.swrl.atoms.SWRLDataPropertyAtomAdapter;
import org.protege.owl.portability.swrl.atoms.SWRLDataRangeAtomAdapter;
import org.protege.owl.portability.swrl.atoms.SWRLDifferentIndividualsAtomAdapter;
import org.protege.owl.portability.swrl.atoms.SWRLObjectPropertyAtomAdapter;
import org.protege.owl.portability.swrl.atoms.SWRLSameIndividualAtomAdapter;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.protege.swrlapi.ext.SWRLAPIBuiltInAtom;

/**
 * This interface describes methods that can be implemented by a target rule engine to convert SWRL head atoms to native rule clauses.
 * <p>
 * Implementors may also chose an alternate conversion approach.
 */
public interface TargetRuleEngineSWRLHeadAtomConverter<T> extends TargetRuleEngineConverter
{
	T convert(SWRLClassAtomAdapter atom) throws TargetRuleEngineException;

	T convert(SWRLDataPropertyAtomAdapter atom) throws TargetRuleEngineException;

	T convert(SWRLObjectPropertyAtomAdapter atom) throws TargetRuleEngineException;

	T convert(SWRLSameIndividualAtomAdapter atom) throws TargetRuleEngineException;

	T convert(SWRLDifferentIndividualsAtomAdapter atom) throws TargetRuleEngineException;

	T convert(SWRLAPIBuiltInAtom atom) throws TargetRuleEngineException;

	T convert(SWRLDataRangeAtomAdapter atom) throws TargetRuleEngineException;
}
