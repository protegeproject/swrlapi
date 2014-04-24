package org.swrlapi.sqwrl.values.impl;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.values.SQWRLPropertyValue;

abstract class SQWRLPropertyValueImpl extends SQWRLNamedResultValueImpl implements SQWRLPropertyValue
{
	protected SQWRLPropertyValueImpl(IRI propertyIRI, String prefixedName)
	{
		super(propertyIRI, prefixedName);
	}
}
