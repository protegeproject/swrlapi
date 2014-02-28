package org.protege.swrlapi.xsd;

import org.protege.owl.portability.model.XSDNames;
import org.protege.swrlapi.exceptions.SQWRLLiteralException;

public class XSDDate extends XSDType
{
	public XSDDate(String content) throws SQWRLLiteralException
	{
		super(content);

		setURI(XSDNames.Full.DATE);
	}

	public XSDDate(java.util.Date date) throws SQWRLLiteralException
	{
		super(XSDTimeUtil.utilDate2XSDDateString(date));

		setURI(XSDNames.Full.DATE);
	}

	@Override
	protected void validate() throws SQWRLLiteralException
	{
		if (getContent() == null)
			throw new SQWRLLiteralException("null content for xsd:Date");

		if (!XSDTimeUtil.isValidXSDDate(getContent()))
			throw new SQWRLLiteralException("invalid xsd:Date '" + getContent() + "'");
	}
}
