package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.values.SQWRLAnnotationPropertyResultValue;

class DefaultSQWRLAnnotationPropertyResultValue extends DefaultSQWRLPropertyResultValue
  implements SQWRLAnnotationPropertyResultValue
{
  public DefaultSQWRLAnnotationPropertyResultValue(@NonNull IRI propertyIRI, @NonNull String prefixedName,
    @NonNull String shortForm)
  {
    super(propertyIRI, prefixedName, shortForm);
  }

  @Override public boolean isAnnotationProperty()
  {
    return true;
  }
}
