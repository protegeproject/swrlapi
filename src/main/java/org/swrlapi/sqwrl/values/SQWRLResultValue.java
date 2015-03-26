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
	boolean isEntity();

	boolean isClass();

	boolean isIndividual();

	boolean isObjectProperty();

	boolean isDataProperty();

	boolean isAnnotationProperty();

	boolean isLiteral();

	SQWRLEntityResultValue asEntityResult() throws SQWRLException;

	SQWRLClassResultValue asClassResult() throws SQWRLException;

	SQWRLIndividualResultValue asIndividualResult() throws SQWRLException;

	SQWRLObjectPropertyResultValue asObjectPropertyResult() throws SQWRLException;

	SQWRLDataPropertyResultValue asDataPropertyResult() throws SQWRLException;

	SQWRLAnnotationPropertyResultValue asAnnotationPropertyResult() throws SQWRLException;

	SQWRLLiteralResultValue asLiteralResult() throws SQWRLException;
}