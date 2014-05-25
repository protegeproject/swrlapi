package org.swrlapi.bridge.converters;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.exceptions.TargetRuleEngineException;

public interface TargetRuleEngineOWLLiteralConverter<T> extends TargetRuleEngineConverter
{
	public T convert(OWLLiteral literal) throws TargetRuleEngineException;
}
