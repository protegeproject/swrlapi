package org.swrlapi.sqwrl.values;

import org.swrlapi.sqwrl.exceptions.SQWRLException;

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