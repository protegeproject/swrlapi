package org.swrlapi.factory;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.values.SQWRLIndividualResultValue;

class DefaultSQWRLIndividualResultValue extends DefaultSQWRLEntityResultValue implements SQWRLIndividualResultValue
{
  public DefaultSQWRLIndividualResultValue(IRI individualIRI, String prefixedName)
  {
    super(individualIRI, prefixedName);
  }

  @Override
  public boolean isIndividual()
  {
    return true;
  }
}
