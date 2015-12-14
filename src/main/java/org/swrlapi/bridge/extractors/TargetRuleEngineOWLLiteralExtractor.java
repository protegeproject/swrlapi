package org.swrlapi.bridge.extractors;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLLiteral;

public interface TargetRuleEngineOWLLiteralExtractor<T> extends TargetRuleEngineExtractor
{
  @NonNull OWLLiteral extract(@NonNull T targetRuleEngineLiteral);
}
