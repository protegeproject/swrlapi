package org.swrlapi.bridge.converters;

import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.core.resolvers.IRIResolver;
import org.swrlapi.core.resolvers.OWLClassExpressionResolver;
import org.swrlapi.core.resolvers.OWLDataPropertyExpressionResolver;
import org.swrlapi.core.resolvers.OWLDataRangeResolver;
import org.swrlapi.core.resolvers.OWLObjectPropertyExpressionResolver;

/**
 * This interface represents provides utility methods converters implemented by target rule engines to convert
 * OWLAPI-based OWL entities to a native rule engine representation of those entities.
 */
public abstract class TargetRuleEngineConverterBase implements TargetRuleEngineConverter
{
  private final SWRLRuleEngineBridge bridge;

  protected TargetRuleEngineConverterBase(SWRLRuleEngineBridge bridge)
  {
    this.bridge = bridge;
  }

  protected IRIResolver getIRIResolver()
  {
    return getBridge().getIRIResolver();
  }

  protected OWLClassExpressionResolver getOWLClassExpressionResolver()
  {
    return getBridge().getOWLClassExpressionResolver();
  }

  protected OWLDataRangeResolver getOWLDataRangeResolver()
  {
    return getBridge().getOWLDataRangeResolver();
  }

  protected OWLDataPropertyExpressionResolver getOWLDataPropertyExpressionResolver()
  {
    return getBridge().getOWLDataPropertyExpressionResolver();
  }

  protected OWLObjectPropertyExpressionResolver getOWLObjectPropertyExpressionResolver()
  {
    return getBridge().getOWLObjectPropertyExpressionResolver();
  }

  protected SWRLRuleEngineBridge getBridge()
  {
    return this.bridge;
  }
}
