package org.swrlapi.sqwrl.values;

import org.swrlapi.core.SWRLAPILiteral;

/**
 * Represents a literal result value provided by a {@link org.swrlapi.sqwrl.SQWRLResult}.
 * 
 * @see org.swrlapi.sqwrl.SQWRLResult
 * @see org.swrlapi.core.SWRLAPILiteral
 */
public interface SQWRLLiteralResultValue extends SQWRLResultValue, Comparable<SQWRLLiteralResultValue>, SWRLAPILiteral
{
	/**
	 * @return The prefixed name of the literal's datatype
	 */
	String getDatatypePrefixedName();
}
