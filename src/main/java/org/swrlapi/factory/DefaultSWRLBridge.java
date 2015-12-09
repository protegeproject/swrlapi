package org.swrlapi.factory;

import checkers.nullness.quals.MonotonicNonNull;
import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.swrlapi.bridge.SWRLBridge;
import org.swrlapi.bridge.TargetSWRLRuleEngine;
import org.swrlapi.core.OWLObjectResolver;
import org.swrlapi.builtins.SWRLBuiltInLibraryManager;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.core.IRIResolver;
import org.swrlapi.core.SWRLAPIOWLOntology;
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
 * <p>
 * Asserted OWL axioms are managed by a {@link org.swrlapi.core.SWRLRuleEngine}, which passes them to a
 * {@link org.swrlapi.bridge.TargetSWRLRuleEngine} using the
 * {@link org.swrlapi.bridge.TargetSWRLRuleEngine#defineOWLAxiom(OWLAxiom)} call.
 */
public class DefaultSWRLBridge implements SWRLBridge
{
  @NonNull private final SWRLAPIOWLOntology swrlapiOWLOntology;
  @NonNull private final OWL2RLPersistenceLayer owl2RLPersistenceLayer;
  @NonNull private final OWLObjectResolver owlObjectResolver;
  @NonNull private final SWRLBuiltInLibraryManager builtInLibraryManager;

  /**
   * OWL axioms inferred by a rule engine (via the {@link #inferOWLAxiom(org.semanticweb.owlapi.model.OWLAxiom)} call).
   * A {@link org.swrlapi.core.SWRLRuleEngine} can retrieve these using the the {@link #getInjectedOWLAxioms()} call
   * after calling {@link org.swrlapi.bridge.TargetSWRLRuleEngine#runRuleEngine()}.
   */
  @NonNull private final Set<OWLAxiom> inferredOWLAxioms;

  /**
   * OWL axioms inferred by SWRL built-ins (via the {@link #inferOWLAxiom(org.semanticweb.owlapi.model.OWLAxiom)}). A
   * {@link org.swrlapi.core.SWRLRuleEngine} can retrieve these using the {@link #getInjectedOWLAxioms()} call after
   * calling {@link org.swrlapi.bridge.TargetSWRLRuleEngine#runRuleEngine()}.
   */
  @NonNull private final Set<OWLAxiom> injectedOWLAxioms;

  /**
   * The target rule engine implementation (e.g., Drools, Jess)
   */
  @MonotonicNonNull private TargetSWRLRuleEngine targetSWRLRuleEngine;

  public DefaultSWRLBridge(@NonNull SWRLAPIOWLOntology swrlapiOWLOntology,
      @NonNull OWL2RLPersistenceLayer owl2RLPersistenceLayer) throws SWRLBuiltInBridgeException
  {
    this.swrlapiOWLOntology = swrlapiOWLOntology;
    this.owl2RLPersistenceLayer = owl2RLPersistenceLayer;
    this.targetSWRLRuleEngine = null;
    //this.owlObjectResolver = new DefaultOWLObjectResolver(swrlapiOWLOntology.getOWLDataFactory());
    this.owlObjectResolver = SWRLAPIFactory.createOWLObjectResolver(swrlapiOWLOntology.getOWLDataFactory());
    this.builtInLibraryManager = new SWRLBuiltInLibraryManager();

    this.inferredOWLAxioms = new HashSet<>();
    this.injectedOWLAxioms = new HashSet<>();

    this.builtInLibraryManager.invokeAllBuiltInLibrariesResetMethod(this);
  }

  @Override public void setTargetSWRLRuleEngine(@NonNull TargetSWRLRuleEngine targetSWRLRuleEngine)
  {
    this.targetSWRLRuleEngine = targetSWRLRuleEngine;
  }

  @Override public void reset() throws SWRLBuiltInBridgeException
  {
    this.inferredOWLAxioms.clear();
    this.injectedOWLAxioms.clear();

    this.builtInLibraryManager.invokeAllBuiltInLibrariesResetMethod(this);
  }

  @Override public boolean hasOntologyChanged()
  {
    return getSWRLAPIOWLOntology().hasOntologyChanged();
  }

  /**
   * The inject methods can be used by SWRL built-ins to inject new axioms into a bridge, which will also reflect them
   * in the underlying engine.
   */
  @Override public void injectOWLAxiom(@NonNull OWLAxiom axiom) throws SWRLBuiltInBridgeException
  {
    if (!this.injectedOWLAxioms.contains(axiom)) {
      this.injectedOWLAxioms.add(axiom);
      exportOWLAxiom(axiom); // Export the axiom to the rule engine.
    }
  }

  @NonNull @Override public IRIResolver getIRIResolver()
  {
    return this.swrlapiOWLOntology.getIRIResolver();
  }

  @Override @NonNull public OWLObjectResolver getOWLObjectResolver()
  {
    return this.owlObjectResolver;
  }

  @NonNull @Override public OWL2RLPersistenceLayer getOWL2RLPersistenceLayer()
  {
    return this.owl2RLPersistenceLayer;
  }

  @NonNull @Override public Set<OWLAxiom> getInjectedOWLAxioms()
  {
    return new HashSet<>(this.injectedOWLAxioms);
  }

  @Override public int getNumberOfInjectedOWLAxioms()
  {
    return this.injectedOWLAxioms.size();
  }

  @Override public boolean isInjectedOWLAxiom(@NonNull OWLAxiom axiom)
  {
    return this.injectedOWLAxioms.contains(axiom);
  }

  @NonNull @Override public Set<OWLAxiom> getInferredOWLAxioms()
  {
    return this.inferredOWLAxioms;
  }

  @Override public int getNumberOfInferredOWLAxioms()
  {
    return this.inferredOWLAxioms.size();
  }

  @Override public void inferOWLAxiom(@NonNull OWLAxiom axiom) throws SWRLRuleEngineBridgeException
  {
    // Exclude already asserted axioms
    if (!this.inferredOWLAxioms.contains(axiom) && !this.swrlapiOWLOntology.hasAssertedOWLAxiom(axiom))
      this.inferredOWLAxioms.add(axiom);
  }

  @NonNull @Override public List<List<SWRLBuiltInArgument>> invokeSWRLBuiltIn(@NonNull String ruleName,
      @NonNull String builtInName, int builtInIndex, boolean isInConsequent,
      @NonNull List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return builtInLibraryManager
        .invokeSWRLBuiltIn(this, ruleName, builtInName, builtInIndex, isInConsequent, arguments);
  }

  public boolean isOWLClass(@NonNull IRI iri)
  {
    return getOWLOntology().containsClassInSignature(iri, Imports.INCLUDED) || iri
        .equals(OWLRDFVocabulary.OWL_THING.getIRI()) || iri.equals(OWLRDFVocabulary.OWL_NOTHING.getIRI());
  }

  public boolean isOWLObjectProperty(@NonNull IRI propertyIRI)
  {
    return getOWLOntology().containsObjectPropertyInSignature(propertyIRI, Imports.INCLUDED);
  }

  public boolean isOWLDataProperty(@NonNull IRI propertyIRI)
  {
    return getOWLOntology().containsDataPropertyInSignature(propertyIRI, Imports.INCLUDED);
  }

  public boolean isOWLNamedIndividual(@NonNull IRI individualIRI)
  {
    return getOWLOntology().containsIndividualInSignature(individualIRI, Imports.INCLUDED);
  }

  @NonNull public SQWRLResult getSQWRLResult(@NonNull String queryName) throws SQWRLException
  {
    return this.swrlapiOWLOntology.getSQWRLResult(queryName);
  }

  @NonNull @Override public SQWRLResultGenerator getSQWRLResultGenerator(@NonNull String queryName)
      throws SQWRLException
  {
    return this.swrlapiOWLOntology.getSQWRLResultGenerator(queryName);
  }

  private void exportOWLAxiom(@NonNull OWLAxiom axiom) throws SWRLBuiltInBridgeException
  {
    try {
      this.targetSWRLRuleEngine.defineOWLAxiom(axiom);
    } catch (TargetSWRLRuleEngineException e) {
      throw new SWRLBuiltInBridgeException(
          "error exporting OWL axiom " + axiom + " to target rule engine: " + e.getMessage(), e);
    }
  }

  @NonNull @Override public OWLLiteralFactory getOWLLiteralFactory()
  {
    return getSWRLAPIOWLDataFactory().getOWLLiteralFactory();
  }

  @NonNull @Override public OWLDatatypeFactory getOWLDatatypeFactory()
  {
    return getSWRLAPIOWLDataFactory().getOWLDatatypeFactory();
  }

  @NonNull @Override public SWRLBuiltInArgumentFactory getSWRLBuiltInArgumentFactory()
  {
    return getSWRLAPIOWLDataFactory().getSWRLBuiltInArgumentFactory();
  }

  @NonNull @Override public SWRLAPIOWLOntology getSWRLAPIOWLOntology()
  {
    return this.swrlapiOWLOntology;
  }

  @NonNull @Override public SWRLAPIOWLDataFactory getSWRLAPIOWLDataFactory()
  {
    return getSWRLAPIOWLOntology().getSWRLAPIOWLDataFactory();
  }

  @NonNull private OWLOntology getOWLOntology()
  {
    return getSWRLAPIOWLOntology().getOWLOntology();
  }
}