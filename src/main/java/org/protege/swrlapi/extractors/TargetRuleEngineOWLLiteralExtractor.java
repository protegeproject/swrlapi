
package org.protege.swrlapi.extractors;

import org.protege.owl.portability.model.OWLLiteralAdapter;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;

public interface TargetRuleEngineOWLLiteralExtractor<T> extends TargetRuleEngineExtractor
{
	public OWLLiteralAdapter extract(T targetRuleEngineLiteral) throws TargetRuleEngineException;
}
