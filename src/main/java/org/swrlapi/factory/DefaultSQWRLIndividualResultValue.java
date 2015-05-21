package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.values.SQWRLIndividualResultValue;

class DefaultSQWRLIndividualResultValue extends DefaultSQWRLEntityResultValue implements SQWRLIndividualResultValue
{
  public DefaultSQWRLIndividualResultValue(@NonNull IRI individualIRI, @NonNull String prefixedName)
  {
    super(individualIRI, prefixedName);
  }

  @Override
  public boolean isIndividual()
  {
    return true;
  }
}
