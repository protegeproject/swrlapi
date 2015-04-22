package org.swrlapi.builtins;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.swrlapi.exceptions.SWRLBuiltInBridgeException;

/**
 * This interface defines methods required by a built-in bridge controller.
 */
public interface SWRLBuiltInBridgeController
{
	/**
	 * Reset the controller
	 * 
	 * @throws SWRLBuiltInBridgeException If an error occurs during resetting
	 */
	void reset() throws SWRLBuiltInBridgeException;

	/**
	 * @return The number of OWL axioms injected into the controller
	 */
	int getNumberOfInjectedOWLAxioms();

	/**
	 * @param axiom An OWL axiom
	 * @return True if the axiom has been injected into the controller
	 */
	boolean isInjectedOWLAxiom(OWLAxiom axiom);

	/**
	 * @return A list of injected OWL axioms
	 */
	Set<OWLAxiom> getInjectedOWLAxioms();
}
