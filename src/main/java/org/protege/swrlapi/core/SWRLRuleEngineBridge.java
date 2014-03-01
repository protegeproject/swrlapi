package org.protege.swrlapi.core;

import java.util.List;

import org.protege.swrlapi.core.arguments.SWRLAtomArgumentFactory;
import org.protege.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLBuiltInArgumentFactory;
import org.protege.swrlapi.exceptions.BuiltInException;
import org.protege.swrlapi.exceptions.SWRLRuleEngineBridgeException;
import org.protege.swrlapi.ext.OWLDatatypeFactory;
import org.protege.swrlapi.ext.OWLLiteralFactory;
import org.protege.swrlapi.ext.SWRLAPILiteralFactory;
import org.protege.swrlapi.ext.SWRLAPIOWLDataFactory;
import org.protege.swrlapi.owl2rl.OWL2RLPersistenceLayer;
import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * The SWRL Rule Engine Bridge defines the interface seen by a target implementation of a SWRL rule engine. The
 * implementation uses this interface primarily to infer axioms and to invoke built-ins.
 * <p>
 * Detailed documentation for this mechanism can be found <a
 * href="http://protege.cim3.net/cgi-bin/wiki.pl?SWRLRuleEngineBridgeFAQ">here</a>.
 */
public interface SWRLRuleEngineBridge
{
	/**
	 * This method is used by a target rule engine to inform the bridge of its implementation.
	 */
	void setTargetRuleEngine(TargetRuleEngine targetRuleEngine);

	/**
	 * A target rule engine can create OWL axioms using the OWL factory supplied by the bridge.
	 */
	SWRLAPIOWLDataFactory getOWLDataFactory();

	OWLDatatypeFactory getOWLDatatypeFactory();

	OWLLiteralFactory getOWLLiteralFactory();

	SWRLAPILiteralFactory getSWRLAPILiteralFactory();

	SWRLBuiltInArgumentFactory getSWRLBuiltInArgumentFactory();

	SWRLAtomArgumentFactory getSWRLAtomArgumentFactory();

	/**
	 * A named object resolver can be used by a target rule engine to determine the type of a named OWL entity given its
	 * URI or prefixed name. It can also be used to get the URI of an OWL named entity using its prefixed name.
	 */
	OWLNamedObjectResolver getOWLNamedObjectResolver();

	/**
	 * A class expression resolver can be used by a target rule engine to resolve OWL class expressions.
	 */
	OWLClassExpressionResolver getOWLClassExpressionResolver();

	/**
	 * A property expression resolver can be used by a target rule engine to resolve OWL property expressions.
	 */
	OWLPropertyExpressionResolver getOWLPropertyExpressionResolver();

	/**
	 * Get the underlying persistence layer for the OWL 2 RL reasoner used by the rule and query engine.
	 */
	OWL2RLPersistenceLayer getOWL2RLPersistenceLayer();

	/**
	 * The infer methods are used by a target rule engines to put inferred axioms into the bridge.
	 */
	void inferOWLAxiom(OWLAxiom axiom) throws SWRLRuleEngineBridgeException;

	/**
	 * This method can be used by a target rule engine to invoke built-ins. If the built-in evaluates to false, an empty
	 * list is returned. If it evaluates to true, one of more argument lists are returned, one for each combination of
	 * arguments where the build-in predicate evalates to true.
	 */
	List<List<SWRLBuiltInArgument>> invokeSWRLBuiltIn(String ruleName, String builtInName, int builtInIndex,
			boolean isInConsequent, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	/**
	 * See if the active ontology has changed since last knowledge rule engine call to {@link SWRLRuleEngine#reset()}.
	 */
	boolean hasOntologyChanged();
}
