package org.swrlapi.builtins;

import org.swrlapi.core.SWRLBuiltInBridge;
import org.swrlapi.exceptions.BuiltInException;
import org.swrlapi.exceptions.SWRLBuiltInLibraryException;

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
