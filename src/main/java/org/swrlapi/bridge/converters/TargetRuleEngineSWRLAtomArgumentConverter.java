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
	T convert(SWRLVariable argument);

	T convert(SWRLLiteralArgument argument);

	T convert(SWRLIndividualArgument argument);

	T convert(SWRLVariableBuiltInArgument argument);

	T convert(SWRLClassBuiltInArgument argument);

	T convert(SWRLNamedIndividualBuiltInArgument argument);

	T convert(SWRLObjectPropertyBuiltInArgument argument);

	T convert(SWRLDataPropertyBuiltInArgument argument);

	T convert(SWRLAnnotationPropertyBuiltInArgument argument);

	T convert(SWRLDatatypeBuiltInArgument argument);

	T convert(SWRLLiteralBuiltInArgument argument);

	T convert(SQWRLCollectionVariableBuiltInArgument argument);
}
