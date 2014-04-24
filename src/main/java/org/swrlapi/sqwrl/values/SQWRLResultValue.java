package org.swrlapi.sqwrl.values;

public interface SQWRLResultValue
{
	boolean isNamed();

	boolean isLiteral();

	SQWRLLiteralResultValue asLiteralResult();

	SQWRLNamedResultValue asNamedResult();
}