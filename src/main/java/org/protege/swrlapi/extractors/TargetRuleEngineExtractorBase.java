package org.protege.swrlapi.extractors;

import org.protege.swrlapi.converters.TargetRuleEngineConverterBase;
import org.protege.swrlapi.core.OWLClassExpressionResolver;
import org.protege.swrlapi.core.OWLNamedObjectResolver;
import org.protege.swrlapi.core.SWRLRuleEngineBridge;
import org.protege.swrlapi.core.arguments.SWRLAtomArgumentFactory;
import org.protege.swrlapi.core.arguments.SWRLBuiltInArgumentFactory;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.protege.swrlapi.ext.OWLDatatypeFactory;
import org.protege.swrlapi.ext.OWLLiteralFactory;
import org.protege.swrlapi.ext.SWRLAPIOWLDataFactory;
import org.semanticweb.owlapi.model.IRI;

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
