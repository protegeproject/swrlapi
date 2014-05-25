package org.swrlapi.bridge.converters;

import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.core.OWLClassExpressionResolver;
import org.swrlapi.core.OWLIRIResolver;
import org.swrlapi.core.OWLPropertyExpressionResolver;

/**
 * This interface represents provides utility methods converters implemented by target rule engines to convert OWL
 * objects, such as named classes and individuals, and OWL axioms from a native rule engine representation.
 */
public abstract class TargetRuleEngineConverterBase implements TargetRuleEngineConverter
{
	private final SWRLRuleEngineBridge bridge;

	public TargetRuleEngineConverterBase(SWRLRuleEngineBridge bridge)
	{
		this.bridge = bridge;
	}

	protected OWLIRIResolver getOWLIRIResolver()
	{
		return getBridge().getOWLIRIResolver();
	}

	protected OWLClassExpressionResolver getOWLClassExpressionResolver()
	{
		return getBridge().getOWLClassExpressionResolver();
	}

	protected OWLPropertyExpressionResolver getOWLPropertyExpressionResolver()
	{
		return getBridge().getOWLPropertyExpressionResolver();
	}

	protected SWRLRuleEngineBridge getBridge()
	{
		return this.bridge;
	}
}
