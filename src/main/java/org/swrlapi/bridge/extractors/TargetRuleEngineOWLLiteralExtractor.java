package org.swrlapi.bridge.extractors;

import org.semanticweb.owlapi.model.OWLLiteral;

public interface TargetRuleEngineOWLLiteralExtractor<T> extends TargetRuleEngineExtractor
{
	public OWLLiteral extract(T targetRuleEngineLiteral);
}
