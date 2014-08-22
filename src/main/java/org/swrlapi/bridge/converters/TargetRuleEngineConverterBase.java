package org.swrlapi.bridge.converters;

import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.core.*;

/**
 * This interface represents provides utility methods converters implemented by target rule engines to convert
 * OWLAPI-based OWL entities to a native rule engine representation of those entities.
 */
public abstract class TargetRuleEngineConverterBase implements TargetRuleEngineConverter
{
	private final SWRLRuleEngineBridge bridge;

	public TargetRuleEngineConverterBase(SWRLRuleEngineBridge bridge)
	{
		this.bridge = bridge;
	}

	protected SWRLAPIIRIResolver getIRIResolver()
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
