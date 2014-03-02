package org.protege.swrlapi.xsd;

import org.protege.swrlapi.exceptions.SQWRLLiteralException;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

public class XSDTime extends XSDType
{
	public XSDTime(String content) throws SQWRLLiteralException
	{
		super(content);

		setURI(XSDVocabulary.TIME.getIRI());
	}

	public XSDTime(java.util.Date date) throws SQWRLLiteralException
	{
		super(XSDTimeUtil.utilDate2XSDTimeString(date));

		setURI(XSDVocabulary.TIME.getIRI());
	}

	@Override
	protected void validate() throws SQWRLLiteralException
	{
		if (getContent() == null)
			throw new SQWRLLiteralException("null content for xsd:Time");

		if (!XSDTimeUtil.isValidXSDTime(getContent()))
			throw new SQWRLLiteralException("invalid xsd:Time '" + getContent() + "'");
	}
}
