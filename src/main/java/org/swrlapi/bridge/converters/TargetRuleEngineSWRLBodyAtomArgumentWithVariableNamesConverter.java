package org.swrlapi.bridge.converters;

import java.util.Set;

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
 * @see org.semanticweb.owlapi.model.SWRLArgument
 */
public interface TargetRuleEngineSWRLBodyAtomArgumentWithVariableNamesConverter<T> extends TargetRuleEngineConverter
{
  T convert(SWRLVariable variableArgument);

  T convert(SWRLIndividualArgument individualArgument);

  T convert(SWRLLiteralArgument literalArgument);

  T convert(SWRLVariableBuiltInArgument variableArgument);

  T convert(SWRLClassBuiltInArgument classArgument);

  T convert(SWRLNamedIndividualBuiltInArgument individualArgument);

  T convert(SWRLObjectPropertyBuiltInArgument propertyArgument);

  T convert(SWRLDataPropertyBuiltInArgument propertyArgument);

  T convert(SWRLAnnotationPropertyBuiltInArgument propertyArgument);

  T convert(SWRLLiteralBuiltInArgument argument);

  T convert(SWRLVariableBuiltInArgument argument, String fieldName,
      Set<String> previouslyEncounteredVariablePrefixedNames);

  T convert(SWRLVariable argument, String fieldName, Set<String> previouslyEncounteredVariablePrefixedNames);

  T convert(SWRLIndividualArgument argument, String fieldName, Set<String> previouslyEncounteredVariablePrefixedNames);

  T convert(SWRLLiteralArgument argument, String fieldName, Set<String> previouslyEncounteredVariablePrefixedNames);

  T convert(SWRLClassBuiltInArgument argument, String fieldName, Set<String> previouslyEncounteredVariablePrefixedNames);

  T convert(SWRLNamedIndividualBuiltInArgument argument, String fieldName,
      Set<String> previouslyEncounteredVariablePrefixedNames);

  T convert(SWRLObjectPropertyBuiltInArgument argument, String fieldName,
      Set<String> previouslyEncounteredVariablePrefixedNames);

  T convert(SWRLDataPropertyBuiltInArgument argument, String fieldName,
      Set<String> previouslyEncounteredVariablePrefixedNames);

  T convert(SWRLAnnotationPropertyBuiltInArgument argument, String fieldName,
      Set<String> previouslyEncounteredVariablePrefixedNames);

  T convert(SWRLLiteralBuiltInArgument argument, String fieldName,
      Set<String> previouslyEncounteredVariablePrefixedNames);

  T convert(SWRLDatatypeBuiltInArgument datatypeArgument);

  T convert(SWRLDatatypeBuiltInArgument datatypeArgument, String fieldName,
      Set<String> previouslyEncounteredVariablePrefixedNames);

  T convert(SQWRLCollectionVariableBuiltInArgument argument);

  T convert(SQWRLCollectionVariableBuiltInArgument argument, String fieldName,
      Set<String> previouslyEncounteredVariablePrefixedNames);
}
