package org.swrlapi.factory;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.values.SQWRLAnnotationPropertyResultValue;

class DefaultSQWRLAnnotationPropertyResultValue extends DefaultSQWRLPropertyResultValue implements
    SQWRLAnnotationPropertyResultValue
{
  public DefaultSQWRLAnnotationPropertyResultValue(IRI propertyIRI, String prefixedName)
  {
    super(propertyIRI, prefixedName);
  }

  @Override
  public boolean isAnnotationProperty()
  {
    return true;
  }
}
