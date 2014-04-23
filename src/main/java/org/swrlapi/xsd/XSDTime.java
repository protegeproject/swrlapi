package org.swrlapi.xsd;

import org.semanticweb.owlapi.vocab.XSDVocabulary;

public class XSDTime extends XSDType
{
	private final org.apache.axis.types.Time time;

	public XSDTime(String content)
	{
		super(content);

		this.time = XSDTimeUtil.xsdTimeString2AxisTime(content);

		setURI(XSDVocabulary.TIME.getIRI());
	}

	public XSDTime(java.util.Date date)
	{
		super(XSDTimeUtil.utilDate2XSDTimeString(date));

		this.time = XSDTimeUtil.utilDate2XSDTime(date);

		setURI(XSDVocabulary.TIME.getIRI());
	}

	@Override
	protected void validate()
	{
		if (getContent() == null)
			throw new IllegalArgumentException("null content for xsd:Time");

		if (!XSDTimeUtil.isValidXSDTime(getContent()))
			throw new IllegalArgumentException("invalid xsd:Time '" + getContent() + "'");
	}

	@Override
	public int compareTo(XSDType o)
	{
		if (!(o instanceof XSDTime))
			return -1;

		return XSDTimeUtil.compareTimes(this.time, ((XSDTime)o).time);
	}
}
