package org.swrlapi.core.xsd;

import java.util.Date;

import org.semanticweb.owlapi.vocab.XSDVocabulary;

public class XSDDateTime extends XSDType<XSDDateTime>
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
	public boolean equals(Object o)
	{
		if (this == o)
			return true;

		if (!(o instanceof XSDDateTime))
			return false;

		XSDDateTime otherDateTime = (XSDDateTime)o;

		return this.datetime != null && otherDateTime.datetime != null && this.datetime.equals(otherDateTime.datetime);
	}

	@Override
	public int hashCode()
	{
		int code = 136;
		code += this.datetime.hashCode();
		return code;
	}

	@Override
	public int compareTo(XSDDateTime o)
	{
		if (this == o)
			return 0;

		return XSDTimeUtil.compareDateTimes(this.datetime, o.datetime);
	}
}
