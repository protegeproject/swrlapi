package org.swrlapi.sqwrl.values.impl;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.values.SQWRLIndividualResultValue;

class SQWRLIndividualResultValueImpl extends SQWRLEntityResultValueImpl implements SQWRLIndividualResultValue
{
  public SQWRLIndividualResultValueImpl(IRI individualIRI, String prefixedName)
  {
    super(individualIRI, prefixedName);
  }

  @Override
  public boolean isIndividual() { return true; }
}
