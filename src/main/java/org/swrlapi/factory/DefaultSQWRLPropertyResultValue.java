package org.swrlapi.factory;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.values.SQWRLPropertyResultValue;

abstract class DefaultSQWRLPropertyResultValue extends DefaultSQWRLEntityResultValue implements SQWRLPropertyResultValue
{
  protected DefaultSQWRLPropertyResultValue(IRI propertyIRI, String prefixedName)
  {
    super(propertyIRI, prefixedName);
  }
}
