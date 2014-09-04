package org.swrlapi.builtins.arguments;

import org.semanticweb.owlapi.model.SWRLVariable;
import org.swrlapi.exceptions.SWRLBuiltInException;

/**
 * Represents a variable argument to a SWRL built-in atom.
 *
 * @see org.semanticweb.owlapi.model.SWRLVariable
 */
public interface SWRLVariableBuiltInArgument extends SWRLBuiltInArgument, SWRLVariable
{
	String getVariablePrefixedName();

	String getVariableName();

	@Override
	boolean isVariable();

	boolean hasBuiltInResult();

	SWRLBuiltInArgument getBuiltInResult();

	void setBuiltInResult(SWRLBuiltInArgument builtInResult) throws SWRLBuiltInException;

	boolean isUnbound();

	boolean isBound();

	void setUnbound();

	void setBound();
}
