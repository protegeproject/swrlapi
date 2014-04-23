package org.swrlapi.xsd;

import org.semanticweb.owlapi.vocab.XSDVocabulary;

public class XSDDuration extends XSDType
{
	private final org.apache.axis.types.Duration duration;

	public XSDDuration(String content)
	{
		super(content);

		this.duration = XSDTimeUtil.xsdDurationString2AxisDuration(getContent());

		setURI(XSDVocabulary.DURATION.getIRI());
	}

	@Override
	protected void validate()
	{
		if (getContent() == null)
			throw new IllegalArgumentException("null content for XSD:duration literal");

		if (!XSDTimeUtil.isValidXSDDuration(getContent()))
			throw new IllegalArgumentException("invalid xsd:Duration: " + getContent());
	}

	@Override
	public int compareTo(XSDType o)
	{
		if (!(o instanceof XSDDuration))
			return -1;

		return XSDTimeUtil.compareDurations(this.duration, ((XSDDuration)o).duration);
	}
}
