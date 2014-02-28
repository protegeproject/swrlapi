
package org.protege.swrlapi.converters;

import org.protege.owl.portability.model.OWLLiteralAdapter;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;

public interface TargetRuleEngineOWLLiteralConverter<T> extends TargetRuleEngineConverter
{
	public T convert(OWLLiteralAdapter literal) throws TargetRuleEngineException;
}
