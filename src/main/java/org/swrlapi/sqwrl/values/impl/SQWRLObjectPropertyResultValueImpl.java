package org.swrlapi.sqwrl.values.impl;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.values.SQWRLObjectPropertyResultValue;

class SQWRLObjectPropertyResultValueImpl extends SQWRLPropertyResultValueImpl implements SQWRLObjectPropertyResultValue
{
  public SQWRLObjectPropertyResultValueImpl(IRI propertyIRI, String prefixedName)
  {
    super(propertyIRI, prefixedName);
  }

  @Override
  public boolean isObjectProperty() { return true; }
}
