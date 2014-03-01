package org.protege.swrlapi.converters;

import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLLiteral;

public interface TargetRuleEngineOWLLiteralConverter<T> extends TargetRuleEngineConverter
{
	public T convert(OWLLiteral literal) throws TargetRuleEngineException;
}
