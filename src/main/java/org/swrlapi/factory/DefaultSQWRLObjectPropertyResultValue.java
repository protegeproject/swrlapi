package org.swrlapi.factory;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.values.SQWRLObjectPropertyResultValue;

class DefaultSQWRLObjectPropertyResultValue extends DefaultSQWRLPropertyResultValue
  implements SQWRLObjectPropertyResultValue
{
  public DefaultSQWRLObjectPropertyResultValue(IRI propertyIRI, String prefixedName)
  {
    super(propertyIRI, prefixedName);
  }

  @Override public boolean isObjectProperty()
  {
    return true;
  }
}
