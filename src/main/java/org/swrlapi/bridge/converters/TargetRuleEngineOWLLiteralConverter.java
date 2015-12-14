package org.swrlapi.bridge.converters;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLLiteral;

/**
 * Defines a method for converting OWLAPI {@link org.semanticweb.owlapi.model.OWLLiteral}s to a native rule engine
 * representation.
 *
 * @see org.semanticweb.owlapi.model.OWLLiteral
 */
public interface TargetRuleEngineOWLLiteralConverter<T> extends TargetRuleEngineConverter
{
  @NonNull T convert(@NonNull OWLLiteral literal);
}
