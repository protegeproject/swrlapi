package org.swrlapi.bridge.converters;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.bridge.SWRLRuleEngineBridge;

import java.util.Optional;

/**
 * This interface provides gives target rule engines the ability to resolve an IRI to a prefixed name.
 */
public abstract class TargetRuleEngineConverterBase implements TargetRuleEngineConverter
{
  @NonNull private final SWRLRuleEngineBridge bridge;

  protected TargetRuleEngineConverterBase(@NonNull SWRLRuleEngineBridge bridge)
  {
    this.bridge = bridge;
  }

  @NonNull protected String iri2PrefixedName(IRI iri)
  {
    Optional<@NonNull String> prefixedName = this.bridge.getIRIResolver().iri2PrefixedName(iri);

    if (prefixedName.isPresent())
      return prefixedName.get();
    else
      throw new IllegalArgumentException("could not get prefixed name for IRI " + iri);
  }

  @NonNull protected SWRLRuleEngineBridge getBridge()
  {
    return this.bridge;
  }
}
