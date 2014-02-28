
package org.protege.swrlapi.xsd;

import org.protege.owl.portability.model.XSDNames;
import org.protege.swrlapi.exceptions.SQWRLLiteralException;

public class XSDTime extends XSDType
{
	public XSDTime(String content) throws SQWRLLiteralException
	{
		super(content);

		setURI(XSDNames.Full.TIME);
	}

	public XSDTime(java.util.Date date) throws SQWRLLiteralException
	{
		super(XSDTimeUtil.utilDate2XSDTimeString(date));

		setURI(XSDNames.Full.TIME);
	}

	protected void validate() throws SQWRLLiteralException
	{
		if (getContent() == null)
			throw new SQWRLLiteralException("null content for xsd:Time");

		if (!XSDTimeUtil.isValidXSDTime(getContent()))
			throw new SQWRLLiteralException("invalid xsd:Time '" + getContent() + "'");
	}
}
