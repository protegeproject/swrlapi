
package org.protege.swrlapi.xsd;

import org.protege.owl.portability.model.XSDNames;
import org.protege.swrlapi.exceptions.SQWRLLiteralException;

public class XSDDuration extends XSDType
{
  public XSDDuration(String content) throws SQWRLLiteralException 
  { 
    super(content); 

    setURI(XSDNames.Full.DURATION);
  } 

  protected void validate() throws SQWRLLiteralException
  {
    if (getContent() == null) throw new SQWRLLiteralException("null content for XSD:duration literal");

    if (!XSDTimeUtil.isValidXSDDuration(getContent())) throw new SQWRLLiteralException("invalid xsd:Duration: " + getContent());
  }  
}

