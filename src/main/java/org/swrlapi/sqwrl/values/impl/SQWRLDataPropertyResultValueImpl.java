package org.swrlapi.sqwrl.values.impl;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.values.SQWRLDataPropertyResultValue;

class SQWRLDataPropertyResultValueImpl extends SQWRLPropertyResultValueImpl implements SQWRLDataPropertyResultValue
{
  public SQWRLDataPropertyResultValueImpl(IRI propertyIRI, String prefixedName)
  {
    super(propertyIRI, prefixedName);
  }

  @Override
  public boolean isDataProperty() { return true; }

}
