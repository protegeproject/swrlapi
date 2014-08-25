package org.swrlapi.bridge.converters;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Defines a method for converting OWLAPI {@link org.semanticweb.owlapi.model.OWLLiteral}s to a native rule
 * engine representation.
 *
 * @see org.semanticweb.owlapi.model.OWLLiteral
 */
public interface TargetRuleEngineOWLLiteralConverter<T> extends TargetRuleEngineConverter
{
	public T convert(OWLLiteral literal);
}
