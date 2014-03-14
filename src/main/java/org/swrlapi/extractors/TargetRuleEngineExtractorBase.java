package org.swrlapi.extractors;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.converters.TargetRuleEngineConverterBase;
import org.swrlapi.core.OWLClassExpressionResolver;
import org.swrlapi.core.OWLNamedObjectResolver;
import org.swrlapi.core.SWRLRuleEngineBridge;
import org.swrlapi.core.arguments.SWRLAtomArgumentFactory;
import org.swrlapi.core.arguments.SWRLBuiltInArgumentFactory;
import org.swrlapi.exceptions.TargetRuleEngineException;
import org.swrlapi.ext.OWLDatatypeFactory;
import org.swrlapi.ext.OWLLiteralFactory;
import org.swrlapi.ext.SWRLAPIOWLDataFactory;

/**
 * This interface describes a set of methods that will be needed by target rule engines to create Portability API
 * representations the the OWL concepts from its native rule engine representation of those concepts.
 * <p>
 * The corresponding {@link TargetRuleEngineConverterBase} is used to create native rule engine representations of
 * Portability API OWL concepts.
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

	protected SWRLAtomArgumentFactory getSWRLAtomArgumentFactory()
	{
		return getBridge().getSWRLAtomArgumentFactory();
	}

	protected SWRLAPIOWLDataFactory getOWLDataFactory()
	{
		return getBridge().getOWLDataFactory();
	}

	protected OWLDatatypeFactory getOWLDatatypeFactory()
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

	protected OWLNamedObjectResolver getOWLNamedObjectResolver()
	{
		return getBridge().getOWLNamedObjectResolver();
	}

	protected IRI getIRI(String id) throws TargetRuleEngineException
	{
		return getOWLNamedObjectResolver().prefixedName2IRI(id);
	}

	private SWRLRuleEngineBridge getBridge()
	{
		return this.bridge;
	}
}
