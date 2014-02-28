
package org.protege.swrlapi.converters;

import org.protege.swrlapi.core.arguments.SQWRLCollectionBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLAnnotationPropertyAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLClassAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLClassBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLDataPropertyAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLDatatypeAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLDatatypeBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLIndividualAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLIndividualBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLLiteralAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLLiteralBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLObjectPropertyAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLVariableAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLVariableBuiltInArgument;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This interface describes methods that can be implemented by a target rule engine to convert SWRL arguments to atoms and built-ins to native rule clauses.
 * <p>
 * Implementors may also chose an alternate conversion approach.
 */
public interface TargetRuleEngineSWRLAtomArgumentConverter<T> extends TargetRuleEngineConverter
{
	T convert(SWRLVariableAtomArgument variableArgument) throws TargetRuleEngineException;;

	T convert(SWRLClassAtomArgument classArgument) throws TargetRuleEngineException;

	T convert(SWRLIndividualAtomArgument individualArgument) throws TargetRuleEngineException;

	T convert(SWRLObjectPropertyAtomArgument propertyArgument) throws TargetRuleEngineException;
	
	T convert(SWRLDataPropertyAtomArgument propertyArgument) throws TargetRuleEngineException;
	
	T convert(SWRLAnnotationPropertyAtomArgument propertyArgument) throws TargetRuleEngineException;
	
	T convert(SWRLDatatypeAtomArgument propertyArgument) throws TargetRuleEngineException;

	T convert(SWRLLiteralAtomArgument argument) throws TargetRuleEngineException;
	
	T convert(SWRLVariableBuiltInArgument argument) throws TargetRuleEngineException;

	T convert(SWRLClassBuiltInArgument argument) throws TargetRuleEngineException;

	T convert(SWRLIndividualBuiltInArgument argument) throws TargetRuleEngineException;

	T convert(SWRLObjectPropertyBuiltInArgument argument) throws TargetRuleEngineException;

	T convert(SWRLDataPropertyBuiltInArgument argument) throws TargetRuleEngineException;
	
	T convert(SWRLAnnotationPropertyBuiltInArgument argument) throws TargetRuleEngineException;

	T convert(SWRLDatatypeBuiltInArgument argument) throws TargetRuleEngineException;

	T convert(SWRLLiteralBuiltInArgument argument) throws TargetRuleEngineException;

	T convert(SQWRLCollectionBuiltInArgument argument) throws TargetRuleEngineException;
}
