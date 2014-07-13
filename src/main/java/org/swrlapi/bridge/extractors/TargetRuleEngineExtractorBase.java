package org.swrlapi.bridge.extractors;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineConverterBase;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentFactory;
import org.swrlapi.core.*;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This interface describes a set of methods that will be needed by a target rule engine to create OWLAPI representations
 * of OWL entities from its native rule engine representation of those entities.
 * <p>
 * The corresponding {@link TargetRuleEngineConverterBase} is used to create native rule engine representations of
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

	protected OWLClassExpressionResolver getOWLClassExpressionResolver()
	{
		return getBridge().getOWLClassExpressionResolver();
	}

	protected OWLDataRangeResolver getOWLDataRangeResolver()
	{
		return getBridge().getOWLDataRangeResolver();
	}

	protected SWRLAPIIRIResolver getIRIResolver()
	{
		return getBridge().getIRIResolver();
	}

	protected IRI prefixedName2IRI(String prefixedName) throws TargetRuleEngineException
	{
		return getIRIResolver().prefixedName2IRI(prefixedName);
	}

	private SWRLRuleEngineBridge getBridge()
	{
		return this.bridge;
	}
}
