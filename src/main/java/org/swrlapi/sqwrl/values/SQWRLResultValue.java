package org.swrlapi.sqwrl.values;

import org.swrlapi.sqwrl.exceptions.SQWRLException;

/**
 * Base result value provided by a {@link org.swrlapi.sqwrl.SQWRLResult}.
 *
 * @see org.swrlapi.sqwrl.values.SQWRLEntityResultValue
 * @see org.swrlapi.sqwrl.values.SQWRLClassResultValue
 * @see org.swrlapi.sqwrl.values.SQWRLIndividualResultValue
 * @see org.swrlapi.sqwrl.values.SQWRLObjectPropertyResultValue
 * @see org.swrlapi.sqwrl.values.SQWRLDataPropertyResultValue
 * @see org.swrlapi.sqwrl.values.SQWRLAnnotationPropertyResultValue
 * @see org.swrlapi.sqwrl.values.SQWRLLiteralResultValue
 */
public interface SQWRLResultValue
{
	/**
	 * @return True if the result value is an entity
	 */
	boolean isEntity();

	/**
	 * @return True if the result value is a class
	 */
	boolean isClass();

	/**
	 * @return True if the result value is an individual
	 */
	boolean isIndividual();

	/**
	 * @return True if the result value is an object property
	 */
	boolean isObjectProperty();

	/**
	 * @return True if the result value is a data property
	 */
	boolean isDataProperty();

	/**
	 * @return True if the result value is an annotation property
	 */
	boolean isAnnotationProperty();

	/**
	 * @return True if the result value is a literal
	 */
	boolean isLiteral();

	/**
	 * @return A SQWRL entity result value
	 * @throws SQWRLException If the result value cannot be converted to an entity result value
	 */
	SQWRLEntityResultValue asEntityResult() throws SQWRLException;

	/**
	 * @return A SQWRL entity result value
	 * @throws SQWRLException If the result value cannot be converted to a class result value
	 */
	SQWRLClassResultValue asClassResult() throws SQWRLException;

	/**
	 * @return A SQWRL entity result value
	 * @throws SQWRLException If the result value cannot be converted to an individual result value
	 */
	SQWRLIndividualResultValue asIndividualResult() throws SQWRLException;

	/**
	 * @return A SQWRL entity result value
	 * @throws SQWRLException If the result value cannot be converted to an onject property result value
	 */
	SQWRLObjectPropertyResultValue asObjectPropertyResult() throws SQWRLException;

	/**
	 * @return A SQWRL entity result value
	 * @throws SQWRLException If the result value cannot be converted to a data property result value
	 */
	SQWRLDataPropertyResultValue asDataPropertyResult() throws SQWRLException;

	/**
	 * @return A SQWRL entity result value
	 * @throws SQWRLException If the result value cannot be converted to an annotation property result value
	 */
	SQWRLAnnotationPropertyResultValue asAnnotationPropertyResult() throws SQWRLException;

	/**
	 * @return A SQWRL entity result value
	 * @throws SQWRLException If the result value cannot be converted to a literal result value
	 */
	SQWRLLiteralResultValue asLiteralResult() throws SQWRLException;
}