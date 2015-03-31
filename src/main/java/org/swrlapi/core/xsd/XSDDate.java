package org.swrlapi.core.xsd;

import java.util.Date;

import org.semanticweb.owlapi.vocab.XSDVocabulary;

public class XSDDate extends XSDType<XSDDate>
{
	private final Date date;

	public XSDDate(String content)
	{
		super(content, XSDVocabulary.DATE.getIRI());

		this.date = XSDTimeUtil.xsdDateString2Date(content);
	}

	public XSDDate(java.util.Date date)
	{
		super(XSDTimeUtil.utilDate2XSDDateString(date), XSDVocabulary.DATE.getIRI());

		this.date = date;
	}

	@Override
	protected void validate()
	{
		if (getContent() == null)
			throw new IllegalArgumentException("null content for xsd:Date");

		if (!XSDTimeUtil.isValidXSDDate(getContent()))
			throw new IllegalArgumentException("invalid xsd:Date '" + getContent() + "'");
	}

	@Override
	public String getContent()
	{
		return content;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;

		if (!(o instanceof XSDDate))
			return false;

		XSDDate otherDate = (XSDDate)o;

		return this.date != null && otherDate.date != null && this.date.equals(otherDate.date);
	}

	@Override
	public int hashCode()
	{
		int code = 56;
		code += this.date.hashCode();
		return code;
	}

	@Override
	public int compareTo(XSDDate o)
	{
		if (this == o)
			return 0;

		return XSDTimeUtil.compareDates(this.date, o.date);
	}
}
