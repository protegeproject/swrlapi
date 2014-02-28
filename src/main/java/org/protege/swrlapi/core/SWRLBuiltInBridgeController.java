
package org.protege.swrlapi.core;

import java.util.Set;

import org.protege.owl.portability.axioms.OWLAxiomAdapter;
import org.protege.swrlapi.exceptions.SWRLBuiltInBridgeException;

/**
 * This interface defines methods required by a built-in bridge controller. 
 */
public interface SWRLBuiltInBridgeController
{
	void resetController() throws SWRLBuiltInBridgeException;

	int getNumberOfInjectedOWLAxioms();

	boolean isInjectedOWLAxiom(OWLAxiomAdapter axiom);

	Set<OWLAxiomAdapter> getInjectedOWLAxioms();
}
