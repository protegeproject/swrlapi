package org.swrlapi.builtins;

import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.exceptions.SWRLBuiltInLibraryException;

/**
 * Provides invocation context for invoked SWRL built-ins (such the name of invoking rule, whether the invocation is
 * in the consequent or the antecedent) and access to the invoking {@link org.swrlapi.builtins.SWRLBuiltInBridge}.
 *
 * @see org.swrlapi.builtins.SWRLBuiltInLibrary
 * @see org.swrlapi.builtins.SWRLBuiltInBridge
 */
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
