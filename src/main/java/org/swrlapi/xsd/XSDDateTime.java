package org.swrlapi.xsd;

import java.util.Date;

import org.semanticweb.owlapi.vocab.XSDVocabulary;

public class XSDDateTime extends XSDType
{
	private final Date datetime;

	public XSDDateTime(String content)
	{
		super(content);

		this.datetime = XSDTimeUtil.xsdDateTimeString2Date(getContent());

		setURI(XSDVocabulary.DATE_TIME.getIRI());
	}

	public XSDDateTime(java.util.Date datetime)
	{
		super(XSDTimeUtil.utilDate2XSDDateTimeString(datetime));

		this.datetime = datetime;

		setURI(XSDVocabulary.DATE_TIME.getIRI());
	}

	@Override
	protected void validate()
	{
		if (getContent() == null)
			throw new IllegalArgumentException("null content for xsd:DateTime");

		if (!XSDTimeUtil.isValidXSDDateTime(getContent()))
			throw new IllegalArgumentException("invalid xsd:DateTime " + getContent());
	}

	@Override
	public int compareTo(XSDType o)
	{
		if (!(o instanceof XSDDateTime))
			return -1;

		return XSDTimeUtil.compareDateTimes(this.datetime, ((XSDDateTime)o).datetime);
	}
}
