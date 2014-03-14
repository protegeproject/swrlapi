package org.swrlapi.xsd;

import org.semanticweb.owlapi.vocab.XSDVocabulary;
import org.swrlapi.exceptions.SQWRLLiteralException;

public class XSDDate extends XSDType
{
	public XSDDate(String content) throws SQWRLLiteralException
	{
		super(content);

		setURI(XSDVocabulary.DATE.getIRI());
	}

	public XSDDate(java.util.Date date) throws SQWRLLiteralException
	{
		super(XSDTimeUtil.utilDate2XSDDateString(date));

		setURI(XSDVocabulary.DATE.getIRI());
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
