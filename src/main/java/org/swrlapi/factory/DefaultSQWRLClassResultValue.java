package org.swrlapi.factory;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.values.SQWRLClassResultValue;

class DefaultSQWRLClassResultValue extends DefaultSQWRLEntityResultValue implements SQWRLClassResultValue
{
  public DefaultSQWRLClassResultValue(IRI classIRI, String prefixedName)
  {
    super(classIRI, prefixedName);
  }

  @Override public boolean isClass()
  {
    return true;
  }
}
