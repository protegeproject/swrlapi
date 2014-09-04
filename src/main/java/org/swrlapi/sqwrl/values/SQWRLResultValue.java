package org.swrlapi.sqwrl.values;

import org.swrlapi.sqwrl.exceptions.SQWRLException;

public interface SQWRLResultValue
{
	boolean isNamed();

	boolean isLiteral();

	SQWRLLiteralResultValue asLiteralResult();

	SQWRLNamedResultValue asNamedResult();
}