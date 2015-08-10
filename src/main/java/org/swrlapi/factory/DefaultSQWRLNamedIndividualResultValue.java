package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.values.SQWRLNamedIndividualResultValue;

class DefaultSQWRLNamedIndividualResultValue extends DefaultSQWRLEntityResultValue implements
    SQWRLNamedIndividualResultValue
{
  public DefaultSQWRLNamedIndividualResultValue(@NonNull IRI individualIRI, @NonNull String prefixedName)
  {
    super(individualIRI, prefixedName);
  }

  @Override
  public boolean isNamedIndividual()
  {
    return true;
  }
}
