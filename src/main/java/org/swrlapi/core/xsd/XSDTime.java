package org.swrlapi.core.xsd;

import org.semanticweb.owlapi.vocab.XSDVocabulary;

public class XSDTime extends XSDType<XSDTime>
{
  private final org.apache.axis.types.Time time;

  public XSDTime(String content)
  {
    super(content, XSDVocabulary.TIME.getIRI());

    this.time = XSDTimeUtil.xsdTimeString2AxisTime(content);
  }

  public XSDTime(java.util.Date date)
  {
    super(XSDTimeUtil.utilDate2XSDTimeString(date), XSDVocabulary.TIME.getIRI());

    this.time = XSDTimeUtil.utilDate2XSDTime(date);
  }

  @Override protected void validate()
  {
    if (getContent() == null)
      throw new IllegalArgumentException("null content for xsd:Time");

    if (!XSDTimeUtil.isValidXSDTime(getContent()))
      throw new IllegalArgumentException("invalid xsd:Time '" + getContent() + "'");
  }

  @Override public String getContent()
  {
    return this.content;
  }

  @Override public boolean equals(Object o)
  {
    if (this == o)
      return true;

    if (!(o instanceof XSDTime))
      return false;

    XSDTime otherTime = (XSDTime)o;

    return this.time != null && otherTime.time != null && this.time.equals(otherTime.time);
  }

  @Override public int hashCode()
  {
    int code = 156;
    code += this.time.hashCode();
    return code;
  }

  @Override public int compareTo(XSDTime o)
  {
    if (o == null)
      throw new NullPointerException();

    if (this == o)
      return 0;

    return XSDTimeUtil.compareTimes(this.time, o.time);
  }
}
