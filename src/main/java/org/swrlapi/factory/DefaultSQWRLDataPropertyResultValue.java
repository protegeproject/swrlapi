package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.values.SQWRLDataPropertyResultValue;

class DefaultSQWRLDataPropertyResultValue extends DefaultSQWRLPropertyResultValue
  implements SQWRLDataPropertyResultValue
{
  public DefaultSQWRLDataPropertyResultValue(@NonNull IRI propertyIRI, @NonNull String prefixedName)
  {
    super(propertyIRI, prefixedName);
  }

  @Override public boolean isDataProperty()
  {
    return true;
  }

}
