package org.swrlapi.xsd;

import java.util.Date;

import org.semanticweb.owlapi.vocab.XSDVocabulary;

public class XSDDate extends XSDType
{
	private final Date date;

	public XSDDate(String content)
	{
		super(content);

		this.date = XSDTimeUtil.xsdDateString2Date(content);

		setURI(XSDVocabulary.DATE.getIRI());
	}

	public XSDDate(java.util.Date date)
	{
		super(XSDTimeUtil.utilDate2XSDDateString(date));

		this.date = date;

		setURI(XSDVocabulary.DATE.getIRI());
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
	public int compareTo(XSDType o)
	{
		if (!(o instanceof XSDDate))
			return -1;

		return XSDTimeUtil.compareDates(this.date, ((XSDDate)o).date);
	}
}
