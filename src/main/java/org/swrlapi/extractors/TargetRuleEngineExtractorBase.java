package org.swrlapi.extractors;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.swrlapi.converters.TargetRuleEngineConverterBase;
import org.swrlapi.core.OWLClassExpressionResolver;
import org.swrlapi.core.OWLIRIResolver;
import org.swrlapi.core.OWLLiteralFactory;
import org.swrlapi.core.SWRLRuleEngineBridge;
import org.swrlapi.core.arguments.SWRLBuiltInArgumentFactory;
import org.swrlapi.exceptions.TargetRuleEngineException;
import org.swrlapi.ext.SWRLAPIOWLDatatypeFactory;
import org.swrlapi.ext.SWRLAPIOWLDataFactory;

/**
 * This interface describes a set of methods that will be needed by target rule engines to create OWLAPI representations
 * the the OWL entities from its native rule engine representation of those concepts.
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

	protected OWLIRIResolver getOWLIRIResolver()
	{
		return getBridge().getOWLIRIResolver();
	}

	protected IRI shortName2IRI(String shortName) throws TargetRuleEngineException
	{
		return getOWLIRIResolver().shortName2IRI(shortName);
	}

	private SWRLRuleEngineBridge getBridge()
	{
		return this.bridge;
	}
}
