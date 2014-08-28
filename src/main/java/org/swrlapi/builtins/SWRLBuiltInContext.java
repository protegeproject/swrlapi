package org.swrlapi.builtins;

import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.exceptions.SWRLBuiltInLibraryException;

public interface SWRLBuiltInContext
{
	SWRLBuiltInBridge getBuiltInBridge() throws SWRLBuiltInLibraryException;

	String getInvokingRuleName() throws SWRLBuiltInLibraryException;

	int getInvokingBuiltInIndex() throws SWRLBuiltInLibraryException;

	void invokeResetMethod(SWRLBuiltInBridge bridge) throws SWRLBuiltInLibraryException;

	// Antecedent or consequent handling
	void checkThatInConsequent() throws SWRLBuiltInException;

	void checkThatInAntecedent() throws SWRLBuiltInException;

	boolean getIsInConsequent() throws SWRLBuiltInLibraryException;
}
