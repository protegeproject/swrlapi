package org.swrlapi.sqwrl.values.impl;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.values.SQWRLPropertyResultValue;

abstract class SQWRLPropertyResultValueImpl extends SQWRLEntityResultValueImpl implements SQWRLPropertyResultValue
{
  protected SQWRLPropertyResultValueImpl(IRI propertyIRI, String prefixedName)
  {
    super(propertyIRI, prefixedName);
  }
}
