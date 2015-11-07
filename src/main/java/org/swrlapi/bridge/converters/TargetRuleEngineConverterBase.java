package org.swrlapi.bridge.converters;

import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.core.OWLObjectResolver;
import org.swrlapi.core.IRIResolver;

import checkers.nullness.quals.NonNull;

/**
 * This interface provides gives target rule engines access to {@link OWLObjectResolver} and {@link IRIResolver}
 * objects.
 */
public abstract class TargetRuleEngineConverterBase implements TargetRuleEngineConverter
{
  @NonNull private final SWRLRuleEngineBridge bridge;

  protected TargetRuleEngineConverterBase(@NonNull SWRLRuleEngineBridge bridge)
  {
    this.bridge = bridge;
  }

  @NonNull protected IRIResolver getIRIResolver()
  {
    return this.bridge.getIRIResolver();
  }

  @NonNull protected OWLObjectResolver getOWLObjectResolver()
  {
    return this.bridge.getOWLObjectResolver();
  }
}
