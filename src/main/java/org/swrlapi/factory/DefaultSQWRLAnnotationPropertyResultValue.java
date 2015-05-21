package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.values.SQWRLAnnotationPropertyResultValue;

class DefaultSQWRLAnnotationPropertyResultValue extends DefaultSQWRLPropertyResultValue implements
    SQWRLAnnotationPropertyResultValue
{
  public DefaultSQWRLAnnotationPropertyResultValue(@NonNull IRI propertyIRI, @NonNull String prefixedName)
  {
    super(propertyIRI, prefixedName);
  }

  @Override
  public boolean isAnnotationProperty()
  {
    return true;
  }
}
