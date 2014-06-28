package org.swrlapi.builtins;

import java.lang.reflect.Method;
import java.util.List;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.exceptions.BuiltInException;
import org.swrlapi.exceptions.SWRLBuiltInLibraryException;
import org.swrlapi.sqwrl.values.SQWRLResultValueFactory;

/**
 * Defines an interface for a SWRL built-in library.
 * <p>
 * The class {@link AbstractSWRLBuiltInLibrary} provides an implementation of this interface.
 */
public interface SWRLBuiltInLibrary extends SWRLBuiltInContext
{
	String getLibraryName();

	// Reset library, discarding any internal state if any (e.g., caches).
	void reset() throws BuiltInException;

	/**
	 * Method to invoke a built-in in the library. Invoked by {@link SWRLBuiltInLibraryManager}.
	 */
	boolean invokeBuiltInMethod(Method method, SWRLBuiltInBridge bridge, String ruleName, String prefix,
			String builtInMethodName, int builtInIndex, boolean isInConsequent, List<SWRLBuiltInArgument> arguments)
			throws BuiltInException;

	/**
	 * Create a string that represents a unique invocation pattern for a built-in for a bridge/rule/built-in/arguments
	 * combination.
	 */
	String createInvocationPattern(SWRLBuiltInBridge invokingBridge, String invokingRuleName, int invokingBuiltInIndex,
			boolean isInConsequent, List<SWRLBuiltInArgument> arguments) throws BuiltInException;

	SQWRLResultValueFactory getSQWRLResultValueFactory() throws SWRLBuiltInLibraryException;

	IRI createIRI(String fullName) throws BuiltInException;
}
