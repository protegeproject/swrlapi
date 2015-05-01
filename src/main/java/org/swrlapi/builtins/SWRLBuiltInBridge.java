package org.swrlapi.builtins;

import java.util.List;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.core.SWRLAPIOWLDataFactory;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.resolvers.IRIResolver;
import org.swrlapi.exceptions.SWRLBuiltInBridgeException;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.sqwrl.SQWRLResultGenerator;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

/**
 * The SWRL Built-in Bridge defines the methods seen by SWRL built-in implementations at run time.
 *
 * @see org.swrlapi.builtins.SWRLBuiltInLibrary
 */
public interface SWRLBuiltInBridge
{
  /**
   * This call is used by SWRL built-ins to inject OWL axioms into the bridge.
   *
   * @param axiom The axiom to inject
   * @throws SWRLBuiltInBridgeException If an error occurs during inhection
   */
  void injectOWLAxiom(OWLAxiom axiom) throws SWRLBuiltInBridgeException;

  /**
   * This call is used by the SQWRL built-in library to get the result generator for a SQWRL query that is currently
   * being executed.
   *
   * @param queryName The name of the SQWRL query
   * @return A SQWRL result generator
   * @throws SQWRLException If the query name is invalid
   */
  SQWRLResultGenerator getSQWRLResultGenerator(String queryName) throws SQWRLException;

  /**
   * This call can be used by built-ins to create OWL axioms (which they can inject into the bridge using the
   * {@link #injectOWLAxiom(OWLAxiom)} call.
   *
   * @return A SWRLAPI-based OWL data factory
   */
  SWRLAPIOWLDataFactory getSWRLAPIOWLDataFactory();

  /**
   * All named objects are recorded by a {@link org.swrlapi.core.resolvers.IRIResolver}. If a built-in injects a named
   * object it should also record it with this resolver.
   *
   * @return An IRI resolver
   */
  IRIResolver getIRIResolver();

  /**
   * This call can be used by built-ins to access the current active ontology. In general, built-ins should not directly
   * access the active ontology. A built-in should be able to evaluate its arguments directly without access to the
   * ontology. However, some specialized ABox and TBox built-ins may require directy ontology access (e.g.,
   * thox:isIndividual(?i)).
   *
   * @return A SWRLAPI-based OWL ontology
   */
  SWRLAPIOWLOntology getSWRLAPIOWLOntology();

  /**
   * This call can be used by built-ins to invoke another built-in. Unless you really know what you are doing its use
   * should be avoided. It is currently used only by the swrlx built-in library.
   * 
   * @param ruleName The name of the invoking rule
   * @param builtInName The name of the built-in to invoke
   * @param builtInIndex The 0-based index of the built-in in the rule
   * @param isInConsequent True if the built-in is in the rule consequent
   * @param arguments The built-in arguments
   * @return A list of built-in argument bindings
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  List<List<SWRLBuiltInArgument>> invokeSWRLBuiltIn(String ruleName, String builtInName, int builtInIndex,
      boolean isInConsequent, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;
}
