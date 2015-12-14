package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
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
