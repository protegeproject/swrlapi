package org.protege.swrlapi.core;

import java.util.Set;

import org.protege.swrlapi.exceptions.SWRLBuiltInBridgeException;
import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * This interface defines methods required by a built-in bridge controller.
 */
public interface SWRLBuiltInBridgeController
{
	void resetController() throws SWRLBuiltInBridgeException;

	int getNumberOfInjectedOWLAxioms();

	boolean isInjectedOWLAxiom(OWLAxiom axiom);

	Set<OWLAxiom> getInjectedOWLAxioms();
}
