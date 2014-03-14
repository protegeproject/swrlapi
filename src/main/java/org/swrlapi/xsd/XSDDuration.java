package org.swrlapi.xsd;

import org.semanticweb.owlapi.vocab.XSDVocabulary;
import org.swrlapi.exceptions.SQWRLLiteralException;

public class XSDDuration extends XSDType
{
	public XSDDuration(String content) throws SQWRLLiteralException
	{
		super(content);

		setURI(XSDVocabulary.DURATION.getIRI());
	}

	@Override
	protected void validate() throws SQWRLLiteralException
	{
		if (getContent() == null)
			throw new SQWRLLiteralException("null content for XSD:duration literal");

		if (!XSDTimeUtil.isValidXSDDuration(getContent()))
			throw new SQWRLLiteralException("invalid xsd:Duration: " + getContent());
	}
}
