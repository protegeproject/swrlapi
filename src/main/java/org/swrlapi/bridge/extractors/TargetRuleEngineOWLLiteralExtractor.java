package org.swrlapi.bridge.extractors;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLLiteral;

public interface TargetRuleEngineOWLLiteralExtractor<T> extends TargetRuleEngineExtractor
{
  @NonNull OWLLiteral extract(@NonNull T targetRuleEngineLiteral);
}
