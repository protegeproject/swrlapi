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
 * Utility methods for dealing with result arguments from SWRL built-ins.*
 * <p>
 * Each method will take a list of built-in arguments, an index of a particular argument, and a result argument of a
 * particular type. It will determine if arguments are equal, in which case it will evaluate to true; otherwise it will
 * evaluate to false.
 *
 * @see org.swrlapi.builtins.AbstractSWRLBuiltInLibrary
 */
public interface SWRLBuiltInResultArgumentHandler
{
	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber, byte resultArgument)
			throws SWRLBuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber, short resultArgument)
			throws SWRLBuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber, int resultArgument)
			throws SWRLBuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber, long resultArgument)
			throws SWRLBuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber, float resultArgument)
			throws SWRLBuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber, double resultArgument)
			throws SWRLBuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber, String resultArgument)
			throws SWRLBuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber, boolean resultArgument)
			throws SWRLBuiltInException;

	public boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			XSDTime resultArgument) throws SWRLBuiltInException;

	public boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			XSDDate resultArgument) throws SWRLBuiltInException;

	public boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			XSDDateTime resultArgument) throws SWRLBuiltInException;

	public boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			XSDDuration resultArgument) throws SWRLBuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			Collection<SWRLBuiltInArgument> resultArguments) throws SWRLBuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			SWRLBuiltInArgument resultArgument) throws SWRLBuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			SWRLLiteralBuiltInArgument resultArgument) throws SWRLBuiltInException;

	public boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			OWLLiteral resultArgument) throws SWRLBuiltInException;
}
