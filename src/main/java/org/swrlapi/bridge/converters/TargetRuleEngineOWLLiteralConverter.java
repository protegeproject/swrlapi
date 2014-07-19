package org.swrlapi.bridge.converters;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * @see org.semanticweb.owlapi.model.OWLLiteral
 */
public interface TargetRuleEngineOWLLiteralConverter<T> extends TargetRuleEngineConverter
{
	public T convert(OWLLiteral literal) throws TargetRuleEngineException;
}
