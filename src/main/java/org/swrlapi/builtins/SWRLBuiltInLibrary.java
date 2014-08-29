package org.swrlapi.builtins;

import java.lang.reflect.Method;
import java.util.List;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.exceptions.SWRLBuiltInLibraryException;
import org.swrlapi.sqwrl.values.SQWRLResultValueFactory;

/**
 * Defines a base interface for a SWRL built-in library. All built-in library implementation muse implement this
 * interface.
 * <p>
 * The class {@link org.swrlapi.builtins.AbstractSWRLBuiltInLibrary} provides a default implementation of this
 * interface and provides and array if methods for dealing with built-in arguments.
 *
 * @see org.swrlapi.builtins.AbstractSWRLBuiltInLibrary
 * @see org.swrlapi.builtins.SWRLBuiltInContext
 * @see org.swrlapi.builtins.SWRLBuiltInBridge
 */
public interface SWRLBuiltInLibrary extends SWRLBuiltInContext
{
	String getLibraryName();

	// Reset library, discarding any internal state if any (e.g., caches).
	void reset() throws SWRLBuiltInException;

	/**
	 * Method to invoke a built-in in the library. Invoked by {@link SWRLBuiltInLibraryManager}.
	 */
	boolean invokeBuiltInMethod(Method method, SWRLBuiltInBridge bridge, String ruleName, String prefix,
			String builtInMethodName, int builtInIndex, boolean isInConsequent, List<SWRLBuiltInArgument> arguments)
			throws SWRLBuiltInException;

	/**
	 * Create a string that represents a unique invocation pattern for a built-in for a bridge/rule/built-in/arguments
	 * combination.
	 */
	String createInvocationPattern(SWRLBuiltInBridge invokingBridge, String invokingRuleName, int invokingBuiltInIndex,
			boolean isInConsequent, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

	SQWRLResultValueFactory getSQWRLResultValueFactory() throws SWRLBuiltInLibraryException;

	IRI createIRI(String fullName) throws SWRLBuiltInException;
}
