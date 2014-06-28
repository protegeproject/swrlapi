package org.swrlapi.builtins;

import java.util.Collection;
import java.util.List;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.exceptions.BuiltInException;

public interface SWRLBuiltInArgumentResultHandler
{
	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			Collection<SWRLBuiltInArgument> resultArguments) throws BuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			SWRLBuiltInArgument resultArgument) throws BuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			SWRLLiteralBuiltInArgument resultArgument) throws BuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber, short resultArgument)
			throws BuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber, int resultArgument)
			throws BuiltInException;

	public boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			OWLLiteral resultArgument) throws BuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber, long resultArgument)
			throws BuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber, float resultArgument)
			throws BuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber, double resultArgument)
			throws BuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber, byte resultArgument)
			throws BuiltInException;

	boolean processResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber, String resultArgument)
			throws BuiltInException;

}
