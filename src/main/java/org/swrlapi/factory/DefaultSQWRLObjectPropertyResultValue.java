package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.values.SQWRLObjectPropertyResultValue;

class DefaultSQWRLObjectPropertyResultValue extends DefaultSQWRLPropertyResultValue
  implements SQWRLObjectPropertyResultValue
{
  public DefaultSQWRLObjectPropertyResultValue(@NonNull IRI propertyIRI, @NonNull String prefixedName)
  {
    super(propertyIRI, prefixedName);
  }

  @Override public boolean isObjectProperty()
  {
    return true;
  }
}
