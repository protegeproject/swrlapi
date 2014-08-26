package org.swrlapi.bridge.extractors;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineConverterBase;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentFactory;
import org.swrlapi.core.OWLLiteralFactory;
import org.swrlapi.core.SWRLAPIOWLDataFactory;
import org.swrlapi.core.SWRLAPIOWLDatatypeFactory;
import org.swrlapi.core.resolvers.IRIResolver;
import org.swrlapi.core.resolvers.OWLClassExpressionResolver;
import org.swrlapi.core.resolvers.OWLDataPropertyExpressionResolver;
import org.swrlapi.core.resolvers.OWLDataRangeResolver;
import org.swrlapi.core.resolvers.OWLObjectPropertyExpressionResolver;

/**
 * This interface describes a set of methods that will be needed by a target rule engine to create OWLAPI representations
 * of OWL properties from the rule engine's native rule engine representation of those properties.
 * <p>
 * The corresponding {@link TargetRuleEngineConverterBase} is used when creating native rule engine representations of
 * OWLAPI OWL objects.
 * 
 * @see TargetRuleEngineConverterBase
 */
public abstract class TargetRuleEngineExtractorBase implements TargetRuleEngineExtractor
{
	private final SWRLRuleEngineBridge bridge;

	public TargetRuleEngineExtractorBase(SWRLRuleEngineBridge bridge)
	{
		this.bridge = bridge;
	}

	protected SWRLBuiltInArgumentFactory getSWRLBuiltInArgumentFactory()
	{
		return getBridge().getSWRLBuiltInArgumentFactory();
	}

	protected SWRLAPIOWLDataFactory getSWRLAPIOWLDataFactory()
	{
		return getBridge().getSWRLAPIOWLDataFactory();
	}

	protected OWLDataFactory getOWLDataFactory()
	{
		return getBridge().getSWRLAPIOWLDataFactory();
	}

	protected SWRLAPIOWLDatatypeFactory getOWLDatatypeFactory()
	{
		return getBridge().getOWLDatatypeFactory();
	}

	protected OWLLiteralFactory getOWLLiteralFactory()
	{
		return getBridge().getOWLLiteralFactory();
	}

	protected OWLClassExpressionResolver getOWLClassExpressionResolver() {
		return getBridge().getOWLClassExpressionResolver();
	}

	protected OWLObjectPropertyExpressionResolver getOWLObjectPropertyExpressionResolver() {
		return getBridge().getOWLObjectPropertyExpressionResolver();
	}

	protected OWLDataPropertyExpressionResolver getOWLDataPropertyExpressionResolver() {
		return getBridge().getOWLDataPropertyExpressionResolver();
	}

	protected OWLDataRangeResolver getOWLDataRangeResolver()
	{
		return getBridge().getOWLDataRangeResolver();
	}

	protected IRIResolver getIRIResolver()
	{
		return getBridge().getIRIResolver();
	}

	protected IRI prefixedName2IRI(String prefixedName)
	{
		return getIRIResolver().prefixedName2IRI(prefixedName);
	}

	private SWRLRuleEngineBridge getBridge()
	{
		return this.bridge;
	}
}
