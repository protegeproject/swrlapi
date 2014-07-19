package org.swrlapi.bridge.converters;

import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.swrlapi.builtins.arguments.SQWRLCollectionVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This interface describes methods that can be implemented by a target rule engine to convert SWRL atom arguments to
 * native rule clauses.
 * <p>
 * Implementors may also chose an alternate conversion approach.
 *
 * @see org.semanticweb.owlapi.model.SWRLArgument
 */
public interface TargetRuleEngineSWRLAtomArgumentConverter<T> extends TargetRuleEngineConverter
{
	T convert(SWRLVariable argument) throws TargetRuleEngineException;

	T convert(SWRLLiteralArgument argument) throws TargetRuleEngineException;

	T convert(SWRLIndividualArgument argument) throws TargetRuleEngineException;

	T convert(SWRLVariableBuiltInArgument argument) throws TargetRuleEngineException;

	T convert(SWRLClassBuiltInArgument argument) throws TargetRuleEngineException;

	T convert(SWRLNamedIndividualBuiltInArgument argument) throws TargetRuleEngineException;

	T convert(SWRLObjectPropertyBuiltInArgument argument) throws TargetRuleEngineException;

	T convert(SWRLDataPropertyBuiltInArgument argument) throws TargetRuleEngineException;

	T convert(SWRLAnnotationPropertyBuiltInArgument argument) throws TargetRuleEngineException;

	T convert(SWRLDatatypeBuiltInArgument argument) throws TargetRuleEngineException;

	T convert(SWRLLiteralBuiltInArgument argument) throws TargetRuleEngineException;

	T convert(SQWRLCollectionVariableBuiltInArgument argument) throws TargetRuleEngineException;
}
