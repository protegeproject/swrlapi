package org.swrlapi.bridge;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.factory.SWRLBuiltInArgumentFactory;
import org.swrlapi.factory.OWLDatatypeFactory;
import org.swrlapi.factory.OWLLiteralFactory;
import org.swrlapi.factory.SWRLAPIOWLDataFactory;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.core.IRIResolver;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.exceptions.SWRLRuleEngineBridgeException;
import org.swrlapi.owl2rl.OWL2RLPersistenceLayer;
import org.swrlapi.core.OWLObjectResolver;

import java.util.List;

/**
 * A SWRL rule engine bridge defines the interface seen by a target implementation of a SWRL rule engine. A target
 * implementation uses this interface primarily to get a variety of factories that is uses to create OWL axioms and
 * associated entities during its inference process. It then uses the
 * {@link #inferOWLAxiom(org.semanticweb.owlapi.model.OWLAxiom)} method to supply the invoker with the axioms it has
 * inferred. The target rule engine implementation also uses this interface to invoke SWRL built-ins.
 *
 * @see org.swrlapi.bridge.TargetSWRLRuleEngine
 * @see org.swrlapi.bridge.SWRLRuleEngineBridgeController
 */
public interface SWRLRuleEngineBridge
{
  /**
   * This method is used by a target rule engine to inform the bridge of its implementation.
   *
   * @param targetSWRLRuleEngine The target rule engine
   */
  void setTargetSWRLRuleEngine(TargetSWRLRuleEngine targetSWRLRuleEngine);

  /**
   * The infer methods are used by a target rule engines to put inferred axioms into the bridge.
   *
   * @param axiom The axiom to infer
   * @throws SWRLRuleEngineBridgeException If an error occurs during the infer process
   */
  void inferOWLAxiom(@NonNull OWLAxiom axiom) throws SWRLRuleEngineBridgeException;

  /**
   * This method can be used by a target rule engine to invoke built-ins. If the built-in evaluates to false, an empty
   * list is returned. If it evaluates to true, one of more argument lists are returned, one for each combination of
   * arguments where the build-in predicate evaluates to true.
   *
   * @param ruleName       The name of the invoking rule
   * @param builtInName    The name of the built-in to invoke
   * @param builtInIndex   The 0-based index of the built-in in the rule
   * @param isInConsequent Is the built-in in the rule consequent
   * @param arguments      The arguments to the built-in
   * @return A list of argument bindings if the built-in evaluates to true; an empty list otherwise
   * @throws SWRLBuiltInException If the parameters are invalid or an error occurs during invocation
   */
  @NonNull List<List<SWRLBuiltInArgument>> invokeSWRLBuiltIn(@NonNull String ruleName, @NonNull String builtInName,
    int builtInIndex, boolean isInConsequent, @NonNull List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * A target rule engine can create OWL axioms using the OWL factory supplied by the bridge.
   *
   * @return A SWRLAPI-based OWL data factory
   */
  @NonNull SWRLAPIOWLDataFactory getSWRLAPIOWLDataFactory();

  /**
   * A target rule engine can create OWL datatypes using the OWL factory supplied by the bridge.
   *
   * @return An OWLAPI datatype factory
   */
  @NonNull OWLDatatypeFactory getOWLDatatypeFactory();

  /**
   * A target rule engine can create OWL literals using the OWL factory supplied by the bridge.
   *
   * @return An OWL literal factory
   */
  @NonNull OWLLiteralFactory getOWLLiteralFactory();

  /**
   * A target rule engine can create SWRL built-in arguments using the OWL factory supplied by the bridge.
   *
   * @return A built-in argument factory
   */
  @NonNull SWRLBuiltInArgumentFactory getSWRLBuiltInArgumentFactory();

  /**
   * A named object resolver can be used by a target rule engine to determine the type of a named OWL entity given its
   * URI or prefixed name. It can also be used to get the URI of an OWL named entity using its prefixed name.
   *
   * @return An IRI resolver
   */
  @NonNull IRIResolver getIRIResolver();

  /**
   * An OWL object resolver can be used by a target rule engine to resolve OWL objects.
   *
   * @return An OWL object resolver
   */
  @NonNull OWLObjectResolver getOWLObjectResolver();

  /**
   * Get the underlying persistence layer for the OWL 2 RL reasoner used by the rule and query engine.
   *
   * @return A persistence layer for OWL 2 RL settings
   */
  @NonNull OWL2RLPersistenceLayer getOWL2RLPersistenceLayer();

  /**
   * See if the active ontology has changed since last knowledge rule engine call to {@link SWRLRuleEngine#reset()}.
   *
   * @return The change status of the ontology
   */
  boolean hasOntologyChanged();
}
