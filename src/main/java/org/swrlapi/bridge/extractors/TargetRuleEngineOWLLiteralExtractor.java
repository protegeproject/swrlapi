package org.swrlapi.bridge.extractors;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.exceptions.TargetRuleEngineException;

public interface TargetRuleEngineOWLLiteralExtractor<T> extends TargetRuleEngineExtractor
{
	public OWLLiteral extract(T targetRuleEngineLiteral) throws TargetRuleEngineException;
}
