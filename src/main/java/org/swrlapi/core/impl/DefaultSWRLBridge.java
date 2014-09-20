package org.swrlapi.core.impl;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.SWRLRuleEngineBridgeController;
import org.swrlapi.bridge.TargetSWRLRuleEngine;
import org.swrlapi.builtins.SWRLBuiltInBridge;
import org.swrlapi.builtins.SWRLBuiltInBridgeController;
import org.swrlapi.builtins.SWRLBuiltInLibraryManager;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentFactory;
import org.swrlapi.core.OWLLiteralFactory;
import org.swrlapi.core.SWRLAPIOWLDataFactory;
import org.swrlapi.core.SWRLAPIOWLDatatypeFactory;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.core.resolvers.IRIResolver;
import org.swrlapi.core.resolvers.OWLClassExpressionResolver;
import org.swrlapi.core.resolvers.OWLDataPropertyExpressionResolver;
import org.swrlapi.core.resolvers.OWLDataRangeResolver;
import org.swrlapi.core.resolvers.OWLIndividualResolver;
import org.swrlapi.core.resolvers.OWLObjectPropertyExpressionResolver;
import org.swrlapi.exceptions.SWRLBuiltInBridgeException;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.exceptions.SWRLRuleEngineBridgeException;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;
import org.swrlapi.owl2rl.OWL2RLPersistenceLayer;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.SQWRLResultGenerator;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Default implementation of a SWRL rule engine bridge, built-in bridge, built-in bridge controller, and rule engine
 * bridge controller.
 * <p/>
 * Asserted OWL axioms are managed by a {@link SWRLRuleEngine}, which passes them to a
 * {@link org.swrlapi.bridge.TargetSWRLRuleEngine} using the
 * {@link org.swrlapi.bridge.TargetSWRLRuleEngine#defineOWLAxiom(OWLAxiom)} call.
 */
public class DefaultSWRLBridge
		implements SWRLRuleEngineBridge, SWRLBuiltInBridge, SWRLBuiltInBridgeController, SWRLRuleEngineBridgeController
{
	private final SWRLAPIOWLOntology swrlapiOWLOntology;
	private final OWL2RLPersistenceLayer owl2RLPersistenceLayer;
	private final OWLClassExpressionResolver owlClassExpressionResolver;
	private final OWLDataRangeResolver owlDataRangeResolver;
	private final OWLDataPropertyExpressionResolver owlDataPropertyExpressionResolver;
	private final OWLObjectPropertyExpressionResolver owlObjectPropertyExpressionResolver;
	private final OWLIndividualResolver owlIndividualResolver;

	/**
	 * OWL axioms inferred by a rule engine (via the {@link #inferOWLAxiom(org.semanticweb.owlapi.model.OWLAxiom)} call).
	 * A {@link org.swrlapi.core.SWRLRuleEngine} can retrieve these using the the {@link #getInjectedOWLAxioms()} call
	 * after calling {@link org.swrlapi.bridge.TargetSWRLRuleEngine#runRuleEngine()}.
	 */
	private final Set<OWLAxiom> inferredOWLAxioms;

	/**
	 * OWL axioms inferred by SWRL built-ins (via the {@link #inferOWLAxiom(org.semanticweb.owlapi.model.OWLAxiom)}).
	 * A {@link org.swrlapi.core.SWRLRuleEngine} can retrieve these using the {@link #getInjectedOWLAxioms()} call
	 * after calling {@link org.swrlapi.bridge.TargetSWRLRuleEngine#runRuleEngine()}.
	 */
	private final Set<OWLAxiom> injectedOWLAxioms;

	/**
	 * The target rule engine implementation (e.g., Drools, Jess)
	 */
	private TargetSWRLRuleEngine targetSWRLRuleEngine;

	public DefaultSWRLBridge(SWRLAPIOWLOntology swrlapiOWLOntology, OWL2RLPersistenceLayer owl2RLPersistenceLayer)
			throws SWRLBuiltInBridgeException
	{
		this.swrlapiOWLOntology = swrlapiOWLOntology;
		this.owl2RLPersistenceLayer = owl2RLPersistenceLayer;
		this.targetSWRLRuleEngine = null;
		this.owlClassExpressionResolver = new OWLClassExpressionResolver(swrlapiOWLOntology.getOWLDataFactory());
		this.owlDataRangeResolver = new OWLDataRangeResolver(swrlapiOWLOntology.getOWLDataFactory());
		this.owlObjectPropertyExpressionResolver = new OWLObjectPropertyExpressionResolver();
		this.owlDataPropertyExpressionResolver = new OWLDataPropertyExpressionResolver();
		this.owlIndividualResolver = new OWLIndividualResolver();

		this.inferredOWLAxioms = new HashSet<>();
		this.injectedOWLAxioms = new HashSet<>();

		reset();
	}

	@Override
	public void setTargetSWRLRuleEngine(TargetSWRLRuleEngine targetSWRLRuleEngine)
	{
		this.targetSWRLRuleEngine = targetSWRLRuleEngine;
	}

	@Override
	public void reset() throws SWRLBuiltInBridgeException
	{
		this.inferredOWLAxioms.clear();
		this.injectedOWLAxioms.clear();

		SWRLBuiltInLibraryManager.invokeAllBuiltInLibrariesResetMethod(this);
	}

	@Override
	public boolean hasOntologyChanged()
	{
		return getSWRLAPIOWLOntology().hasOntologyChanged();
	}

	/**
	 * The inject methods can be used by SWRL built-ins to inject new axioms into a bridge, which will also reflect them
	 * in the underlying engine.
	 */
	@Override
	public void injectOWLAxiom(OWLAxiom axiom) throws SWRLBuiltInBridgeException
	{
		if (!this.injectedOWLAxioms.contains(axiom)) {
			this.injectedOWLAxioms.add(axiom);
			exportOWLAxiom(axiom); // Export the axiom to the rule engine.
		}
	}

	@Override
	public IRIResolver getIRIResolver()
	{
		return this.swrlapiOWLOntology.getIRIResolver();
	}

	@Override
	public OWLClassExpressionResolver getOWLClassExpressionResolver()
	{
		return this.owlClassExpressionResolver;
	}

	@Override
	public OWLDataRangeResolver getOWLDataRangeResolver()
	{
		return this.owlDataRangeResolver;
	}

	@Override
	public OWLObjectPropertyExpressionResolver getOWLObjectPropertyExpressionResolver()
	{
		return this.owlObjectPropertyExpressionResolver;
	}

	@Override
	public OWLDataPropertyExpressionResolver getOWLDataPropertyExpressionResolver()
	{
		return this.owlDataPropertyExpressionResolver;
	}

	@Override
	public OWLIndividualResolver getOWLIndividualResolver()
	{
		return this.owlIndividualResolver;
	}

	@Override
	public OWL2RLPersistenceLayer getOWL2RLPersistenceLayer()
	{
		return this.owl2RLPersistenceLayer;
	}

	@Override
	public Set<OWLAxiom> getInjectedOWLAxioms()
	{
		return new HashSet<>(this.injectedOWLAxioms);
	}

	@Override
	public int getNumberOfInjectedOWLAxioms()
	{
		return this.injectedOWLAxioms.size();
	}

	@Override
	public boolean isInjectedOWLAxiom(OWLAxiom axiom)
	{
		return this.injectedOWLAxioms.contains(axiom);
	}

	@Override
	public Set<OWLAxiom> getInferredOWLAxioms()
	{
		return this.inferredOWLAxioms;
	}

	@Override
	public int getNumberOfInferredOWLAxioms()
	{
		return this.inferredOWLAxioms.size();
	}

	@Override
	public void inferOWLAxiom(OWLAxiom axiom) throws SWRLRuleEngineBridgeException
	{
		// Exclude already asserted axioms
		if (!this.inferredOWLAxioms.contains(axiom) && !this.swrlapiOWLOntology.hasAssertedOWLAxiom(axiom))
			this.inferredOWLAxioms.add(axiom);
	}

	@Override
	public List<List<SWRLBuiltInArgument>> invokeSWRLBuiltIn(String ruleName, String builtInName, int builtInIndex,
			boolean isInConsequent, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
	{
		return SWRLBuiltInLibraryManager
				.invokeSWRLBuiltIn(this, ruleName, builtInName, builtInIndex, isInConsequent, arguments);
	}

	public boolean isOWLClass(IRI iri)
	{
		return getOWLOntology().containsClassInSignature(iri, true) || iri.equals(OWLRDFVocabulary.OWL_THING.getIRI())
				|| iri.equals(OWLRDFVocabulary.OWL_NOTHING.getIRI());
	}

	public boolean isOWLObjectProperty(IRI propertyIRI)
	{
		return getOWLOntology().containsObjectPropertyInSignature(propertyIRI, true);
	}

	public boolean isOWLDataProperty(IRI propertyIRI)
	{
		return getOWLOntology().containsDataPropertyInSignature(propertyIRI, true);
	}

	public boolean isOWLNamedIndividual(IRI individualIRI)
	{
		return getOWLOntology().containsIndividualInSignature(individualIRI, true);
	}

	public SQWRLResult getSQWRLResult(String queryName) throws SQWRLException
	{
		return this.swrlapiOWLOntology.getSQWRLResult(queryName);
	}

	@Override
	public SQWRLResultGenerator getSQWRLResultGenerator(String queryName) throws SQWRLException
	{
		return this.swrlapiOWLOntology.getSQWRLResultGenerator(queryName);
	}

	private void exportOWLAxiom(OWLAxiom axiom) throws SWRLBuiltInBridgeException
	{
		try {
			this.targetSWRLRuleEngine.defineOWLAxiom(axiom);
		} catch (TargetSWRLRuleEngineException e) {
			throw new SWRLBuiltInBridgeException(
					"error exporting OWL axiom " + axiom + " to target rule engine: " + e.getMessage(), e);
		}
	}

	@Override
	public OWLLiteralFactory getOWLLiteralFactory()
	{
		return getSWRLAPIOWLDataFactory().getOWLLiteralFactory();
	}

	@Override
	public SWRLAPIOWLDatatypeFactory getOWLDatatypeFactory()
	{
		return getSWRLAPIOWLDataFactory().getSWRLAPIOWLDatatypeFactory();
	}

	@Override
	public SWRLBuiltInArgumentFactory getSWRLBuiltInArgumentFactory()
	{
		return getSWRLAPIOWLDataFactory().getSWRLBuiltInArgumentFactory();
	}

	@Override
	public SWRLAPIOWLOntology getSWRLAPIOWLOntology()
	{
		return swrlapiOWLOntology;
	}

	@Override
	public SWRLAPIOWLDataFactory getSWRLAPIOWLDataFactory()
	{
		return getSWRLAPIOWLOntology().getSWRLAPIOWLDataFactory();
	}

	private OWLOntology getOWLOntology()
	{
		return getSWRLAPIOWLOntology().getOWLOntology();
	}
}