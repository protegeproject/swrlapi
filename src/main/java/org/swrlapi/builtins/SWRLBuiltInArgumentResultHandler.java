package org.swrlapi.builtins;

import java.util.Collection;
import java.util.List;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.exceptions.SWRLBuiltInException;

public interface SWRLBuiltInArgumentResultHandler
{
	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			Collection<SWRLBuiltInArgument> resultArguments) throws SWRLBuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			SWRLBuiltInArgument resultArgument) throws SWRLBuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			SWRLLiteralBuiltInArgument resultArgument) throws SWRLBuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber, short resultArgument)
			throws SWRLBuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber, int resultArgument)
			throws SWRLBuiltInException;

	public boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			OWLLiteral resultArgument) throws SWRLBuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber, long resultArgument)
			throws SWRLBuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber, float resultArgument)
			throws SWRLBuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber, double resultArgument)
			throws SWRLBuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber, byte resultArgument)
			throws SWRLBuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber, String resultArgument)
			throws SWRLBuiltInException;

}
