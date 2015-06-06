package org.swrlapi.bridge.converters;

import checkers.nullness.quals.NonNull;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.resolvers.OWLObjectResolver;
import org.swrlapi.core.IRIResolver;
import org.swrlapi.bridge.resolvers.OWLClassExpressionResolver;
import org.swrlapi.bridge.resolvers.OWLDataPropertyExpressionResolver;
import org.swrlapi.bridge.resolvers.OWLDataRangeResolver;
import org.swrlapi.bridge.resolvers.OWLObjectPropertyExpressionResolver;

/**
 * This interface represents provides utility methods converters implemented by target rule engines to convert
 * OWLAPI-based OWL entities to a native rule engine representation of those entities.
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
    return getBridge().getIRIResolver();
  }

  protected @NonNull OWLObjectResolver getOWLObjectResolver()
  {
    return getBridge().getOWLObjectResolver();
  }

  @NonNull protected SWRLRuleEngineBridge getBridge()
  {
    return this.bridge;
  }
}
