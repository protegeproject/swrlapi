package org.protege.swrlapi.converters;

import org.protege.swrlapi.core.OWLClassExpressionResolver;
import org.protege.swrlapi.core.OWLPropertyExpressionResolver;
import org.protege.swrlapi.core.SWRLRuleEngineBridge;
import org.protege.swrlapi.ext.SWRLAPILiteralFactory;

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

	protected OWLClassExpressionResolver getOWLClassExpressionResolver()
	{
		return getBridge().getOWLClassExpressionResolver();
	}

	protected OWLPropertyExpressionResolver getOWLPropertyExpressionResolver()
	{
		return getBridge().getOWLPropertyExpressionResolver();
	}

	protected SWRLAPILiteralFactory getSWRLAPILiteralFactory()
	{
		return getBridge().getSWRLAPILiteralFactory();
	}

	private SWRLRuleEngineBridge getBridge()
	{
		return this.bridge;
	}
}
