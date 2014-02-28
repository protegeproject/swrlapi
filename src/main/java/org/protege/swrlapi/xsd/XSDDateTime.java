
package org.protege.swrlapi.xsd;

import org.protege.owl.portability.model.XSDNames;
import org.protege.swrlapi.exceptions.SQWRLLiteralException;

public class XSDDateTime extends XSDType
{
	public XSDDateTime(String content) throws SQWRLLiteralException
	{
		super(content);

		setURI(XSDNames.Full.DATE_TIME);
	}

	public XSDDateTime(java.util.Date date) throws SQWRLLiteralException
	{
		super(XSDTimeUtil.utilDate2XSDDateTimeString(date));

		setURI(XSDNames.Full.DATE_TIME);
	}

	protected void validate() throws SQWRLLiteralException
	{
		if (getContent() == null)
			throw new SQWRLLiteralException("null content for xsd:DateTime");

		if (!XSDTimeUtil.isValidXSDDateTime(getContent()))
			throw new SQWRLLiteralException("invalid xsd:DateTime " + getContent());
	}
}
