package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
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
