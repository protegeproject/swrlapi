package org.swrlapi.factory;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.values.SQWRLDataPropertyResultValue;

class DefaultSQWRLDataPropertyResultValue extends DefaultSQWRLPropertyResultValue
  implements SQWRLDataPropertyResultValue
{
  public DefaultSQWRLDataPropertyResultValue(IRI propertyIRI, String prefixedName)
  {
    super(propertyIRI, prefixedName);
  }

  @Override public boolean isDataProperty()
  {
    return true;
  }

}
