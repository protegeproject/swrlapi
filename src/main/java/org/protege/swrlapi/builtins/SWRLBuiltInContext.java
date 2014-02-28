package org.protege.swrlapi.builtins;

import org.protege.swrlapi.core.SWRLBuiltInBridge;
import org.protege.swrlapi.exceptions.BuiltInException;
import org.protege.swrlapi.exceptions.SWRLBuiltInLibraryException;

public interface SWRLBuiltInContext
{
	SWRLBuiltInBridge getBuiltInBridge() throws SWRLBuiltInLibraryException;

	String getInvokingRuleName() throws SWRLBuiltInLibraryException;

	int getInvokingBuiltInIndex() throws SWRLBuiltInLibraryException;

	void invokeResetMethod(SWRLBuiltInBridge bridge) throws SWRLBuiltInLibraryException;

	// Antecedent or consequent handling
	void checkThatInConsequent() throws BuiltInException;

	void checkThatInAntecedent() throws BuiltInException;

	boolean getIsInConsequent() throws SWRLBuiltInLibraryException;
}
