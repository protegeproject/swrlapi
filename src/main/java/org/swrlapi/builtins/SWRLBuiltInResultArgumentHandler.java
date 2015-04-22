package org.swrlapi.builtins;

import java.util.Collection;
import java.util.List;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.core.xsd.XSDDate;
import org.swrlapi.core.xsd.XSDDateTime;
import org.swrlapi.core.xsd.XSDDuration;
import org.swrlapi.core.xsd.XSDTime;
import org.swrlapi.exceptions.SWRLBuiltInException;

/**
 * Utility methods for built-in implementations to handle results.
 * <p>
 * Each method will take a list of built-in arguments, an index of a particular argument, and a generated result
 * argument of a particular type. It will determine if the specified argument and the generated result arguments are
 * equal, in which case it will evaluate to true; otherwise it will evaluate to false.
 *
 * @see org.swrlapi.builtins.AbstractSWRLBuiltInLibrary
 */
public interface SWRLBuiltInResultArgumentHandler
{
	/**
	 * @param arguments A list of built-in arguments
	 * @param resultArgumentNumber The index of the byte result argument
	 * @param generatedResultArgument The generated result argument
	 * @return If the specified argument is equal to the generated result argument
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			byte generatedResultArgument) throws SWRLBuiltInException;

	/**
	 * @param arguments A list of built-in arguments
	 * @param resultArgumentNumber The index of the result argument
	 * @param generatedResultArgument The generated short result argument
	 * @return If the specified argument is equal to the generated result argument
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			short generatedResultArgument) throws SWRLBuiltInException;

	/**
	 * @param arguments A list of built-in arguments
	 * @param resultArgumentNumber The index of the result argument
	 * @param generatedResultArgument The generated int result argument
	 * @return If the specified argument is equal to the generated result argument
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			int generatedResultArgument) throws SWRLBuiltInException;

	/**
	 * @param arguments A list of built-in arguments
	 * @param resultArgumentNumber The index of the result argument
	 * @param generatedResultArgument The generated long result argument
	 * @return If the specified argument is equal to the generated result argument
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			long generatedResultArgument) throws SWRLBuiltInException;

	/**
	 * @param arguments A list of built-in arguments
	 * @param resultArgumentNumber The index of the result argument
	 * @param generatedResultArgument The generated float result argument
	 * @return If the specified argument is equal to the generated result argument
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			float generatedResultArgument) throws SWRLBuiltInException;

	/**
	 * @param arguments A list of built-in arguments
	 * @param resultArgumentNumber The index of the result argument
	 * @param generatedResultArgument The generated double result argument
	 * @return If the specified argument is equal to the generated result argument
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			double generatedResultArgument) throws SWRLBuiltInException;

	/**
	 * @param arguments A list of built-in arguments
	 * @param resultArgumentNumber The index of the result argument
	 * @param generatedResultArgument The generated string result argument
	 * @return If the specified argument is equal to the generated result argument
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			String generatedResultArgument) throws SWRLBuiltInException;

	/**
	 * @param arguments A list of built-in arguments
	 * @param resultArgumentNumber The index of the result argument
	 * @param generatedResultArgument The generated boolean result argument
	 * @return If the specified argument is equal to the generated result argument
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			boolean generatedResultArgument) throws SWRLBuiltInException;

	/**
	 * @param arguments A list of built-in arguments
	 * @param resultArgumentNumber The index of the result argument
	 * @param generatedResultArgument The generated time result argument
	 * @return If the specified argument is equal to the generated result argument
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	public boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			XSDTime generatedResultArgument) throws SWRLBuiltInException;

	/**
	 * @param arguments A list of built-in arguments
	 * @param resultArgumentNumber The index of the result argument
	 * @param generatedResultArgument The generated date result argument
	 * @return If the specified argument is equal to the generated result argument
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	public boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			XSDDate generatedResultArgument) throws SWRLBuiltInException;

	/**
	 * @param arguments A list of built-in arguments
	 * @param resultArgumentNumber The index of the result argument
	 * @param generatedResultArgument The generated datetime result argument
	 * @return If the specified argument is equal to the generated result argument
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	public boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			XSDDateTime generatedResultArgument) throws SWRLBuiltInException;

	/**
	 * @param arguments A list of built-in arguments
	 * @param resultArgumentNumber The index of the result argument
	 * @param generatedResultArgument The generated duration result argument
	 * @return If the specified argument is equal to the generated result argument
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	public boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			XSDDuration generatedResultArgument) throws SWRLBuiltInException;

	/**
	 * @param arguments A list of built-in arguments
	 * @param resultArgumentNumber The index of the result argument
	 * @param generatedResultArguments The generated result arguments
	 * @return If the specified argument is equal to the generated result argument
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			Collection<SWRLBuiltInArgument> generatedesultArguments) throws SWRLBuiltInException;

	/**
	 * @param arguments A list of built-in arguments
	 * @param resultArgumentNumber The index of the result argument
	 * @param generatedResultArgument The generated result argument
	 * @return If the specified argument is equal to the generated result argument
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			SWRLBuiltInArgument generatedResultArgument) throws SWRLBuiltInException;

	/**
	 * @param arguments A list of built-in arguments
	 * @param resultArgumentNumber The index of the result argument
	 * @param generatedResultArgument The generated OWL literal argument result argument
	 * @return If the specified argument is equal to the generated result argument
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			SWRLLiteralBuiltInArgument generatedResultArgument) throws SWRLBuiltInException;

	/**
	 * @param arguments A list of built-in arguments
	 * @param resultArgumentNumber The index of the result argument
	 * @param generatedResultArgument The generated OWL literal result argument
	 * @return If the specified argument is equal to the generated result argument
	 * @throws SWRLBuiltInException If an error occurs during processing
	 */
	public boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			OWLLiteral generatedResultArgument) throws SWRLBuiltInException;
}
