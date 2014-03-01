package org.protege.swrlapi.extractors;

import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLLiteral;

public interface TargetRuleEngineOWLLiteralExtractor<T> extends TargetRuleEngineExtractor
{
	public OWLLiteral extract(T targetRuleEngineLiteral) throws TargetRuleEngineException;
}
