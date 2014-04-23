package org.swrlapi.xsd;

import org.semanticweb.owlapi.vocab.XSDVocabulary;

public class XSDDuration extends XSDType<XSDDuration>
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
	public boolean equals(Object o)
	{
		if (this == o)
			return true;

		if (!(o instanceof XSDDuration))
			return false;

		XSDDuration otherDuration = (XSDDuration)o;

		return this.duration != null && otherDuration.duration != null && this.duration.equals(otherDuration.duration);
	}

	@Override
	public int hashCode()
	{
		int code = 34;
		code += this.duration.hashCode();
		return code;
	}

	@Override
	public int compareTo(XSDDuration o)
	{
		if (this == o)
			return 0;

		return XSDTimeUtil.compareDurations(this.duration, o.duration);
	}
}
